
/*
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-cognitoidp</artifactId>
    <version>1.12.686</version> <!-- Use latest -->
</dependency>
*/

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.HashMap;
import java.util.Map;

public class CognitoAuthExample {
    public static void main(String[] args) {
        String userPoolId = "us-east-1_XXXXXXXXX";
        String clientId = "XXXXXXXXXXXXXXXXXXXX";
        String username = "your_username";
        String password = "your_password";

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("ACCESS_KEY", "SECRET_KEY");
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion("us-east-1")
            .build();

        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);

        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
            .withUserPoolId(userPoolId)
            .withClientId(clientId)
            .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
            .withAuthParameters(authParams);

        try {
            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

            if (result.getChallengeName() != null) {
                System.out.println("Challenge triggered: " + result.getChallengeName());

                // Handle challenge — for example:
                if ("NEW_PASSWORD_REQUIRED".equals(result.getChallengeName())) {
                    respondToNewPasswordChallenge(cognitoClient, result, "NewSecurePassword123!");
                }

                // You can handle other challenges like SMS_MFA, etc.
            } else {
                System.out.println("Authentication successful! Tokens:");
                System.out.println("Access Token: " + result.getAuthenticationResult().getAccessToken());
            }

        } catch (NotAuthorizedException e) {
            System.err.println("Authentication failed: " + e.getMessage());
        }
    }

    private static void respondToNewPasswordChallenge(AWSCognitoIdentityProvider client, AdminInitiateAuthResult result, String newPassword) {
        Map<String, String> challengeResponses = new HashMap<>();
        challengeResponses.put("USERNAME", "your_username");
        challengeResponses.put("NEW_PASSWORD", newPassword);

        AdminRespondToAuthChallengeRequest challengeRequest = new AdminRespondToAuthChallengeRequest()
            .withChallengeName(result.getChallengeName())
            .withClientId("XXXXXXXXXXXXXXXXXXXX")
            .withUserPoolId("us-east-1_XXXXXXXXX")
            .withChallengeResponses(challengeResponses)
            .withSession(result.getSession());

        AdminRespondToAuthChallengeResult challengeResult = client.adminRespondToAuthChallenge(challengeRequest);

        if (challengeResult.getAuthenticationResult() != null) {
            System.out.println("Authentication completed after challenge!");
            System.out.println("Access Token: " + challengeResult.getAuthenticationResult().getAccessToken());
        } else {
            System.out.println("Challenge response failed or more steps required.");
        }
    }
}