import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.HashMap;
import java.util.Map;

public class CognitoAuthExample {
    private static final String CLIENT_ID = "your_app_client_id";
    private static final String USER_POOL_ID = "your_user_pool_id";
    private static final Region REGION = Region.US_EAST_1; // Change as needed

    public static void main(String[] args) {
        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        String email = "testuser@example.com";
        String password = "YourSecurePassword123!";

        registerUser(cognitoClient, email, password);
        signInUser(cognitoClient, email, password);
    }

    public static void registerUser(CognitoIdentityProviderClient client, String email, String password) {
        AttributeType emailAttr = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        try {
            SignUpRequest request = SignUpRequest.builder()
                    .clientId(CLIENT_ID)
                    .username(email)
                    .password(password)
                    .userAttributes(emailAttr)
                    .build();

            SignUpResponse response = client.signUp(request);
            System.out.println("Sign-up successful: " + response.userConfirmed());
        } catch (CognitoIdentityProviderException e) {
            System.err.println("Sign-up failed: " + e.awsErrorDetails().errorMessage());
        }
    }

    public static void signInUser(CognitoIdentityProviderClient client, String email, String password) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", password);

        try {
            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .clientId(CLIENT_ID)
                    .authParameters(authParams)
                    .build();

            InitiateAuthResponse authResponse = client.initiateAuth(authRequest);
            String idToken = authResponse.authenticationResult().idToken();
            System.out.println("Login successful. ID Token: " + idToken);
        } catch (CognitoIdentityProviderException e) {
            System.err.println("Login failed: " + e.awsErrorDetails().errorMessage());
        }
    }
}