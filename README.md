___
Copyright (c) 2015, 2016, University of Washington, School of Nursing
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
___

# JTrueNTHOAuth-Client

TrueNTHConnect includes its own OAuth library to ease communication
with our Shared Services. This component was designed to provide a stable
and uniform methodology for issuing requests and interpreting their outcomes, while observing our specific OAuth needs. The artifacts explicitly
extend ScribeJava elements; thus, it has a stable and mature implementation as its base. 

Additionally, the library was conceived as a independent
project, not linked in anyway to the portal, in order to make it available to
other projects to come. 

## Documentation
https://uwcirg.github.io/JTrueNTHOAuth-Client

## Dependences
1. scribe-1.3.0.jar
2. javax.json-1.04.jar
3. commons-codec-1.10.jar

## How to use
This library was designed to behave like the ScribeJava OAuth library. 
We developed its interface to be similar to that library and, for most situations, its usage is aligned with Scribe's documentation. 

### 1.Create a service
A service is the main object that will provide all OAuth services and centralize all necessary configuration. 
The class is named TrueNTHOAuthService. The following example demonstrates how to create an instance, using the staging server configuration in the comments to illustrate possible values.

```Java
TrueNTHOAuthService service = new TrueNTHServiceBuilder()
		    .provider(TrueNTHOAuthProvider.class)
		    .baseAuthorizationURL(SS_AUTHORIZATION_URL) // As in https://stg.us.truenth.org/oauth/authorize
		    .accessTokenEndpointURL(SS_TOKEN_ENDPOINT) // As in https://stg.us.truenth.org/oauth/token
		    accessTokenStatusEndpointURL(SS_TOKEN_STATUS_ENDPOINT) // As in https://stg.us.truenth.org/oauth/oauth/token-status
		    .baseURL(SS_BASE) // As in https://stg.us.truenth.org
		    .resourceURL(SS_API) // As in https://stg.us.truenth.org/api
		    .rolesURL(SS_ROLE_API) // As in https://stg.us.truenth.org/api/user/#userId/roles
		    .callbackURL(YOUR_APP_CALLBACK_URL) // This is where users land after getting an Authorization code from SS.
		    .apiKey(YOUR_API_KEY)
		    .apiSecret(YOUR_API_SECRET)
		    .build();
```

[TrueNTHOAuthService class documentation](http://uwcirg.github.io/JTrueNTHOAuth-Client/index.html?edu/uw/cirg/truenth/oauth/TrueNTHOAuthService.html)

[SS API specification](https://stg.us.truenth.org/dist/)

### 2.Using the service
Here, you will find some examples we use in our code. 
This should be just a guideline and you can adapt your code accordingly to implement your protocol.

Whenever you see `services.get(companyId)`, you can just assume that we are retrieving a service instance created as described above (we use a service repository).

We built some helper classes to help manage basic functionally and the library is responsible for automatically setting all necessary parameters for us.

Those methods are implemented in a helper class called TrueNTHConnectUtil, which is used inside the system to consistently call functionalities from our OAuth library. We suggest you implement one like it in your own system, although it is not necessary.

#### Example: TrueNTHConnectUtil
The following method fetches a OAuth token, using the code provided by the user.

The library provides:

1. TrueNTHOAuthService
2. Verifier, from Scribe
3. TrueNTHAccessToken 

```Java
@Override
public TrueNTHAccessToken getAccessToken(long companyId, String code) {

		TrueNTHOAuthService service = services.get(companyId);
		if (service == null) return null;

		Verifier verifierCode = new Verifier(code);
		return service.getAccessToken(null, verifierCode);
}
```

This method provides the system with the ability to fetch any resource from Shared Services' API, given that the token has the necessary permissions.

The library provides:

1. OAuthRequest, from Scribe
2. Json, coming from javax.json
3. JsonObject, coming from javax.json

```Java
@Override
public JsonObject getResources(long companyId, String path, Token accessToken) {

		try {
		    TrueNTHOAuthService service = services.get(companyId);
		    if (service == null) return null;
	
		    URL url = new URL(service.getResourceURL().concat(path));
	
		    OAuthRequest request = new OAuthRequest(Verb.GET, url.toString());
		    service.signRequest(accessToken, request);
	
		    String json = request.send().getBody();
	
		    return Json.createReader(new StringReader(json)).readObject();
	
		} catch (Exception e) {
	
		    log.error(e);
	
		    return null;
		}
}
```

An interesting use of this function is the following code, which is used to obtain users' demographic information.

```Java
	final JsonObject data = TrueNTHConnectUtil.getResources(companyId, "/demographics", accessToken);

	if ((data == null) || (data.getJsonObject("error") != null)) { return null; }

	final SSDemographics demographics = extractor.extractDemographics(data);
```

#### Retrieving Roles

Now, the following method demonstrates how to retrieve roles from SS.

Basically, whenever SS sends us a set of roles for a given user, it does so by sending a JSON object. 
Our library is able to read such data and return it as a list of Java objects.
 
```Java 
@Override
public List<SSRole> getTrueNTHRoles(final long companyId, final long trueNTHUserId, final TrueNTHAccessToken accessToken) {

	TrueNTHOAuthService service = null;
	URL url = null;
	String json = null;

	try {
	    service = services.get(companyId);
	    if (service == null) { return null; }

	    final SSRoleExtractorJson roleExtractor = new SSRoleExtractorJson();

	    url = new URL(service.getRolesURL(trueNTHUserId));

	    final OAuthRequest request = new OAuthRequest(Verb.GET, url.toString());
	    service.signRequest(accessToken, request);

	    json = request.send().getBody();

	    return roleExtractor.extractRoles(Json.createReader(new StringReader(json)).readObject());

	} catch (final Exception e) {

	    log.error(e);
	    log.error(url + "/n" + json);
	    return null;
	}
}
```

With this helper class defined, we can just use it to retrieve resources whenever necessary.

#### Login case
The following code chunk was extracted from a Struts action, which is responsible for retrieving information about the user: demographic and roles.

```Java
/**
 * TrueNTH OAuth login action.
 *
 * <p>
 * Sets login environment for auto login. This action represents the callback
 * functionality in the OAuth service configuration. As such, the user will be
 * redirected here after they obtained an authorization grant (parameter named
 * code).
 * </p>
 * 
 * @throws PrincipalException
 * @throws SystemException
 * @throws IOException
 */
@Override
public String execute(HttpServletRequest request, HttpServletResponse response) throws PrincipalException, SystemException, IOException {

		//...
		final String code = ParamUtil.getString(request, "code");
		TrueNTHAccessToken accessToken;

		accessToken = TrueNTHConnectUtil.getAccessToken(companyId, code);
		session.setAttribute(TrueNTHConnectWebkeys.TRUENTH_ACCESS_TOKEN, accessToken);

		final TrueNTHUser user = setTrueNTHCredentials(session, themeDisplay.getCompanyId(), accessToken);

		if (user != null) {

		    updateGroups(companyId, user.getTrueNTHAssociation(), accessToken);

		    if ((user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE)) {

				redirectUpdateAccount(request, response, user);

				return null;
		    }
		}
		//...

}
```
This example shows that the library provides everything that is necessary to communicate with SS, and to fetch resources. However, each application should implement their own logic in order to effectively use the obtained data. In the example, we use the library to get an access code from SS. Then, we use this token in two occasions to identify the user: setTrueNTHCredentials and updateGroups. 

