@Service
public class UserService {

    @Autowired
    private AmazonCognitoIdentityProvider cognitoIdentityProvider;

    public void registerUser(UserRegistrationRequest userRequest) {
        try {
            // Prepare user's attributes
            List<AttributeType> userAttributes = new ArrayList<>();
            userAttributes.add(new AttributeType().withName("email").withValue(userRequest.getEmail()));
            // Prepare the request
            AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                .withUserPoolId("cognito-userpool-id") // use environment variable
                .withUsername(userRequest.getUsername())
                .withUserAttributes(userAttributes)
                .withTemporaryPassword(userRequest.getPassword())
                .withDesiredDeliveryMediums("EMAIL");

            // Use Cognito API
            UserType newUser = cognitoIdentityProvider.adminCreateUser(createUserRequest);

            // Add the user to a group
            cognitoIdentityProvider.adminAddUserToGroup(new AdminAddUserToGroupRequest()
                    .withGroupName("yourGroupName")
                    .withUserPoolId("cognito-userpool-id") // use environment variable
                    .withUsername(userRequest.getUsername()));

        } catch (Exception e) {
            // Handle register errors
            throw new RuntimeException("Error registering user : " + e.getMessage(), e);
        }
    }

    public boolean loginUser(UserLoginRequest userRequest) {
        try {
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withUserPoolId("cognito-userpool-id") // use environment variable
                .withClientId("cognito-client-id") // use environment variable
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withAuthParameters(Collections.singletonMap("USERNAME", userRequest.getUsername()))

            AdminInitiateAuthResponse authResponse = cognitoIdentityProvider.adminInitiateAuth(authRequest);
            if (ChallengeNameType.PASSWORD_VERIFIER.toString().equals(authResponse.getChallengeName())) {
                return true;
            }

        } catch (Exception e) {
            // Handle login errors
            return false;
        }
        return false; 
    }
}