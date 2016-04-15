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

###Documentation
https://uwcirg.github.io/JTrueNTHOAuth-Client

###Dependences
1. scribe-1.3.0.jar
2. javax.json-1.04.jar
3. commons-codec-1.10.jar

###How to use
This library was designed to behave like the ScribeJava OAuth library. 
We developed its interface to be similar to that library and for most situations, its usage is aligned with Scribe's documentation. 

####1.Create a service
A service is the main object that will provide all OAuth services and centralize all necessary configuration. 
The class is named TrueNTHOAuthService.

```Java
TrueNTHOAuthService service = new TrueNTHServiceBuilder()
		    .provider(TrueNTHOAuthProvider.class)
		    .baseAuthorizationURL(serverConfig.getAuthURL())
		    .accessTokenEndpointURL(serverConfig.getTokenURL())
		    .baseURL(CS_BASE) // As https://stg.us.truenth.org
		    .resourceURL(CS_API) // As https://stg.us.truenth.org/api
		    .rolesURL(CS_ROLE_API) // As https://stg.us.truenth.org/api/user/#userId/roles
		    .callbackURL(YOUR_APP_CALLBACK_URL) 
		    .apiKey(YOUR_API_KEY)
		    .apiSecret(YOUR_API_SECRET)
		    .signatureType(SignatureType.Header)
		    .scope("email")
		    .build();
```

(As our API becomes more stable, this example will be simplified.)

####2.Using the service
Here, you will find some examples we use in our code. 
This should be just a guideline, and you can adapt your code accordingly to implement your protocol.

Whenever you see services.get(companyId), you can just assume that we are retrieving a service instance created as described above (we use a service repository).

We built some helper classes to help managing basic functionally and the library is responsible for setting all necessary parameters for you automatically.

These are some methods in one of our client applications, which use the library.

Those methods are implemented in a helper class called TrueNTHConnectUtil, which is used inside the system to consistently call functionalities from our OAuth library. We suggest you implement one like it in your own system, although it is not necessary.

#####Real example: TrueNTHConnectUtil
The following method fetches a OAuth token, using the code provided by the user.

The library provides:

1. TrueNTHOAuthService
2. Verifier
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

This method provides the system with the ability to fetch any resource from the Shared Services' API, given that the token has the necessary permissions.

The library provides:

1. OAuthRequest, coming from Scribe
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

Now, this method demonstrates how to retrieve roles from CS.

Basically, whenever CS sends us a set of roles for a given user, it does so by sending a JSON object. 
Our library is able to read such data and return it as a list of Java objects.

Please note that the library does not provide TrueNTHRoleJsonExtractor or  TrueNTHRole, as TrueNTHRole is an internal representation of roles in our system. 
We will be putting a basic representation of such elements into the library this week (you can watch the library on Git to receive the new code). 
PS: roles are not necessary in order to implement login protocols.

```Java 
@Override
public List<TrueNTHRole> getTrueNTHRoles(long companyId, long trueNTHUserId, TrueNTHAccessToken accessToken) {

		TrueNTHOAuthService service = null;
		URL url = null;
		String json = null;
	
		try {
		    service = services.get(companyId);
		    if (service == null) return null;
	
		    TrueNTHRoleJsonExtractor roleExtractor = new TrueNTHRoleJsonExtractor();
	
		    url = new URL(service.getRolesURL(trueNTHUserId));
	
		    OAuthRequest request = new OAuthRequest(Verb.GET, url.toString());
		    service.signRequest(accessToken, request);
	
		    json = request.send().getBody();
	
		    return roleExtractor.extractRoles(Json.createReader(new StringReader(json)).readObject());
	
		} catch (Exception e) {
	
		    log.error(e);
		    log.error(url + "/n" + json);
		    return null;
		}
}
```

With this helper class defined, we can just use it to retrieve resources whenever necessary.
The following code represents a Struts action, which is responsible for retrieving information about the user: demographics and roles.

```Java
/**
* Executes the struts login action: sets login environment for auto login.
* 
* @throws PrincipalException
* @throws SystemException
* @throws IOException
*/
@Override
public String execute(HttpServletRequest request, HttpServletResponse response) throws PrincipalException, SystemException, IOException {

		boolean canLogin = Maintenance.startLogin();
	
		try {
	
		    String redirect = ParamUtil.getString(request, "redirect");
		    HttpSession session = request.getSession();
	
		    if (canLogin) {
		
				ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
				long companyId = themeDisplay.getCompanyId();
		
				if (!TrueNTHConnectUtil.isEnabled(companyId)) { throw new PrincipalException(); }
		
				String code = ParamUtil.getString(request, "code");
		
				try {
		
				    TrueNTHAccessToken accessToken = TrueNTHConnectUtil.getAccessToken(companyId, code);
		
				    TrueNTHUser user = setTrueNTHCredentials(session, themeDisplay.getCompanyId(), accessToken);
		
				    if ((user != null)) {
		
						updateGroups(companyId, user.getTrueNTHAssociation(), accessToken);
			
						if ((user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE)) {
			
						    redirectUpdateAccount(request, response, user);
			
						    return null;
						}
				    }
		
				} catch (Exception e) {
				    // Invalid codes, tokens, communication with CS
				    // (demographics and roles APIs)...
				    log.error(e);
				    session.invalidate();
				    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
		    } else {
				session.setAttribute(Maintenance.PRIVATE_BLOCKED, true);
		    }
		    
		    if (Validator.isNotNull(redirect)) response.sendRedirect(redirect);
		    return null;
	
		} finally {
		    if (canLogin) Maintenance.finishLogin();
		}

}
```

Those examples present all that is necessary to fetch demographic information and roles; however, how systems use and store this information needs to be implemented in each system, as the internal needs greatly changes. Here is an example, the updateGroups function, used on the above example, fetches the user's roles and stores them into our database, next we use this information to associate the user with user groups, which is one of our internal representation for CS roles.

```Java
/**
* Updates TrueNTH user groups.
* 
* @param companyId
* @param association
* @param accessToken
* @throws SystemException
* @throws PortalException
*/
protected void updateGroups(long companyId, TrueNTHAssociation association, TrueNTHAccessToken accessToken) throws SystemException,
	    PortalException {

		long[] trueNTHRoleIds = getTrueNTHRoles(companyId, association.getTrueNTHId(), accessToken);
		TrueNTHAssociationLocalServiceUtil.updateTrueNTHRoles(association.getAssociationId(), trueNTHRoleIds);
	
		UserGroupLocalServiceUtil.setUserUserGroups(association.getUserId(), getUpdatedGroupIds(companyId, association, trueNTHRoleIds));
}
```
