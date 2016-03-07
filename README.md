# JTrueNTHOAuth-Client

TrueNTHConnect includes its own OAuth library to ease communication
with our Central Services. This component was designed to provide a stable
and uniform methodology for issuing requests and interpreting their out-
comes, while observing our specific OAuth needs. The artifacts explicitly
extend ScribeJava elements; thus, it has a stable and mature implementation as its base. 

Additionally, the library was conceived as a independent
project, not linked in anyway to the portal, in order to make it available for
other projects to come. 

###Dependences
1. scribe-1.3.0.jar
2. javax.json-1.04.jar
3. commons-codec-1.10.jar

###How to use
This library was designed to behave like the ScribeJava OAuth library. 
We developed its interface to be similar to that library and for most situations its usage is aligned with Scribe's documentation. 

####Create a service
A service is the main object that will provide all OAuth services and centralize all necessary configuration. 
The class is named TrueNTHOAuthService.

```Java
TrueNTHOAuthService service = new TrueNTHServiceBuilder()
		    .provider(TrueNTHOAuthProvider.class)
		    .baseAuthorizationURL(serverConfig.getAuthURL())
		    .accessTokenEndpointURL(serverConfig.getTokenURL())
		    .baseURL(serverConfig.getCSBaseURL())
		    .resourceURL(serverConfig.getResourceURL())
		    .rolesURL(serverConfig.getCSRolesURL())
		    .callbackURL(serverConfig.getRedirectURL())
		    .apiKey(serverConfig.getAppID())
		    .apiSecret(serverConfig.getAppSecret())
		    .signatureType(SignatureType.Header)
		    .scope("email")
		    .build();
```

####Using the service
Here you will find some examples we use in our code. 
This should be just a guideline, and you can adapt your code accordingly to implement your protocol.

Whenever you see services.get(companyId), you can just assume that we are retrieving a service instance, created as described above (we use a service repository).

We built some helper classes to help managing basic functionally and the library is responsible for setting all necessary parameters for you automatically.

This are some methods in one of our client applications, which use the library.

```Java
    @Override
    public TrueNTHAccessToken getAccessToken(long companyId, String code) {

		TrueNTHOAuthService service = services.get(companyId);
		if (service == null) return null;

		Verifier verifierCode = new Verifier(code);
		return service.getAccessToken(null, verifierCode);
    }
    
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
Which this helper class defined we can just use it to retrieve resources whenever necessary.
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


###Reporting bugs
vdls@uw.edu