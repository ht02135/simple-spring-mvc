
public AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
      ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider =
           new ClasspathPropertiesFileCredentialsProvider();

       return AWSCognitoIdentityProviderClientBuilder.standard()
                      .withCredentials(propertiesFileCredentialsProvider)
                             .withRegion(cognitoConfig.getRegion())
                             .build();

   }

public static UserType signUp(UserSignUpRequest signUpRequest){
    AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
    AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
            .withUserPoolId("us-east-1_Qqtfujski")
            .withUsername(signUpRequest.getUsername())
            .withUserAttributes(
            new AttributeType()
                    .withName("email")
                    .withValue(signUpRequest.getEmail()),
            new AttributeType()
                    .withName("name")
                    .withValue(signUpRequest.getName()),
            new AttributeType()
                    .withName("family_name")
                    .withValue(signUpRequest.getLastName()),
            new AttributeType()
                    .withName("phone_number")
                    .withValue(signUpRequest.getPhoneNumber()),
            new AttributeType()
                    .withName("custom:companyName")
                    .withValue(signUpRequest.getCompanyName()),
            new AttributeType()
                    .withName("custom:companyPosition")
                    .withValue(signUpRequest.getCompanyPosition()),
            new AttributeType()
                    .withName("email_verified")
                    .withValue("true"))
            .withTemporaryPassword("!j8fkxv2oTjLEMd")
            .withMessageAction("SUPPRESS")
            .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
            .withForceAliasCreation(Boolean.FALSE);
    AdminCreateUserResult createUserResult =  cognitoClient.adminCreateUser(cognitoRequest);
    UserType cognitoUser =  createUserResult.getUser();

    return cognitoUser;
}

public  SpringSecurityUser signIn(AuthenticationRequestauthenticationRequest){
    AuthenticationResultType authenticationResult = null;
    AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

    final Map<String, String>authParams = new HashMap<>();
    authParams.put(USERNAME, authenticationRequest.getUsername());  
    authParams.put(PASS_WORD, authenticationRequest.getPassword());

   final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
       authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
       .withClientId(cognitoConfig.getClientId())
       .withUserPoolId(cognitoConfig.getUserPoolId())
       withAuthParameters(authParams);

   AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

   //Has a Challenge
   if(StringUtils.isNotBlank(result.getChallengeName())) {
//If the challenge is required new Password validates if it has the new password variable.
   if(NEW_PASS_WORD_REQUIRED.equals(result.getChallengeName())){
      if(null == authenticationRequest.getNewPassword()) {
throw new CognitoException(messages.get(USER_MUST_PROVIDE_A_NEW_PASS_WORD), CognitoException.USER_MUST_CHANGE_PASS_WORD_EXCEPTION_CODE, result.getChallengeName());
      }else{
       //we still need the username
       final Map<String, String> challengeResponses = new HashMap<>();
       challengeResponses.put(USERNAME, authenticationRequest.getUsername());
       challengeResponses.put(PASS_WORD, authenticationRequest.getPassword());
       //add the new password to the params map
       challengeResponses.put(NEW_PASS_WORD, authenticationRequest.getNewPassword());
       //populate the challenge response
        final AdminRespondToAuthChallengeRequest request =
new AdminRespondToAuthChallengeRequest();
                                           request.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
          .withChallengeResponses(challengeResponses)
          .withClientId(cognitoConfig.getClientId())
          .withUserPoolId(cognitoConfig.getPoolId())
          .withSession(result.getSession());

      AdminRespondToAuthChallengeResult resultChallenge =  
               cognitoClient.adminRespondToAuthChallenge(request);
      authenticationResult = resultChallenge.getAuthenticationResult();
      }
  }else{
    //has another challenge
    throw new CognitoException(result.getChallengeName(),    
  CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, result.getChallengeName());
  }
   }else{
       //Doesn't have a challenge
       authenticationResult = result.getAuthenticationResult();
   }
   cognitoClient.shutdown();
   return userAuthenticated;
}

public void addUserToGroup(String username, String groupname){

	   AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
	   AdminAddUserToGroupRequestaddUserToGroupRequest = new AdminAddUserToGroupRequest()
	              .withGroupName(groupname)
	              .withUserPoolId(cognitoConfig.getPoolId())
	              .withUsername(username);

	   cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

	   cognitoClient.shutdown();

	                             
	}

public void changePassword(PasswordRequest passwordRequest) {

    AWSCognitoIdentityProvider cognitoClient= getAmazonCognitoIdentityClient();
    ChangePasswordRequest changePasswordRequest= newChangePasswordRequest()
             .withAccessToken(passwordRequest.getAccessToken())
             .withPreviousPassword(passwordRequest.getOldPassword())
             .withProposedPassword(passwordRequest.getPassword());

     cognitoClient.changePassword(changePasswordRequest);
     cognitoClient.shutdown();

}

public UserResponse getUserInfo(String username) {

    AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();             
AdminGetUserRequest userRequest = new AdminGetUserRequest()
                   .withUsername(username)
                   .withUserPoolId(cognitoConfig.getUserPoolId());


    AdminGetUserResult userResult = cognitoClient.adminGetUser(userRequest);

    UserResponse userResponse = new UserResponse();
    userResponse.setUsername(userResult.getUsername());
    userResponse.setUserStatus(userResult.getUserStatus());
    userResponse.setUserCreateDate(userResult.getUserCreateDate());
    userResponse.setLastModifiedDate(userResult.getUserLastModifiedDate());

    List userAttributes = userResult.getUserAttributes();
    for(AttributeTypeattribute: userAttributes) {
           if(attribute.getName().equals("custom:companyName")) {
              userResponse.setCompanyName(attribute.getValue());
}else if(attribute.getName().equals("custom:companyPosition")) {
              userResponse.setCompanyPosition(attribute.getValue());
           }else if(attribute.getName().equals("email")) {
              userResponse.setEmail(attribute.getValue());
           }
    }

     cognitoClient.shutdown();
    return userResponse;
           
}

@Bean
public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
     ResourceRetriever resourceRetriever =
          new DefaultResourceRetriever(jwtConfiguration.getConnectionTimeout(),//2000
               jwtConfiguration.getReadTimeout()//2000);
     //https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json.
     URL jwkSetURL= new URL(jwtConfiguration.getJwkUrl());
     //Creates the JSON Web Key (JWK)
     JWKSource keySource= new RemoteJWKSet(jwkSetURL, resourceRetriever);
     ConfigurableJWTProcessor jwtProcessor= new DefaultJWTProcessor();
     //RSASSA-PKCS-v1_5 using SHA-256 hash algorithm
     JWSKeySelector keySelector= new JWSVerificationKeySelector(RS256, keySource);
     jwtProcessor.setJWSKeySelector(keySelector);
     return jwtProcessor;
 }

   We initialize the com.nimbusds.jwt.proc.ConfigurableJWTProcessor:

@Autowired       private ConfigurableJWTProcessor configurableJWTProcessor;

   Then, we extract the access token from the Authentication Header of the request. In this case, we are going to use the Bearer JWT Access Token.

public Authentication getAuthentication(HttpServletRequest request) throws ParseException, BadJOSEException, JOSEException {
    String idToken = request.getHeader(jwtConfiguration.getHttpHeader());
    if(null == idToken) {
       throw new CognitoException(NO_TOKEN_FOUND,
CognitoException.NO_TOKEN_PROVIDED_EXCEPTION,
"No token found in Http Authorization Header");
    }else{
idToken = extractAndDecodeJwt(idToken);
      JWTClaimsSetclaimsSet = null;
      claimsSet= configurableJWTProcessor.process(idToken, null);
      if (!isIssuedCorrectly(claimsSet)) {
        throw new CognitoException(INVALID_TOKEN,
              CognitoException.INVALID_TOKEN_EXCEPTION_CODE,
              String.format("Issuer %s in JWT token doesn't match cognito idp %s",
              claimsSet.getIssuer(),jwtConfiguration.getCognitoIdentityPoolUrl()));
      }

     if(!isIdToken(claimsSet)) {
        throw new CognitoException(INVALID_TOKEN,
               CognitoException.NOT_A_TOKEN_EXCEPTION,
               "JWT Token doesn't seem to be an ID Token");
    }

    String username = claimsSet.getClaims()
          .get(jwtConfiguration.getUserNameField()).toString();

    List groups = (List) claimsSet.getClaims()
             .get(jwtConfiguration.getGroupsField());
    List grantedAuthorities = convertList(groups, group-> new
             SimpleGrantedAuthority(ROLE_PREFIX+ group.toUpperCase()));
    User user = new User(username, EMPTY_STRING, grantedAuthorities);

    return new CognitoJwtAuthentication(user, claimsSet, grantedAuthorities);

    }
}

private boolean isIssuedCorrectly(JWTClaimsSet claimsSet) {
    return claimsSet.getIssuer().equals(jwtConfiguration.getCognitoIdentityPoolUrl());
}

private boolean isIdToken(JWTClaimsSet claimsSet) {
    return claimsSet.getClaim("token_use").equals("id");
}
