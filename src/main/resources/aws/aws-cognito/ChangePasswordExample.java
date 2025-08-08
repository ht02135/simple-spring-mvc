
/*
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-cognitoidp</artifactId>
    <version>1.12.742</version> <!-- Check for the latest version -->
</dependency>
*/

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.ChangePasswordRequest;
import com.amazonaws.services.cognitoidp.model.ChangePasswordResult;

public class ChangePasswordExample {
    public static void main(String[] args) {
        // AWS credentials (use environment variables or a secure method in production)
        String accessKey = "YOUR_AWS_ACCESS_KEY";
        String secretKey = "YOUR_AWS_SECRET_KEY";

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        // Build the Cognito client
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1") // Replace with your region
                .build();

        try {
            // Required: Access token from a successful user login
            String accessToken = "USER_ACCESS_TOKEN";
            String previousPassword = "oldPassword123!";
            String proposedPassword = "newPassword123!";

            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest()
                    .withAccessToken(accessToken)
                    .withPreviousPassword(previousPassword)
                    .withProposedPassword(proposedPassword);

            ChangePasswordResult changePasswordResult = cognitoClient.changePassword(changePasswordRequest);
            System.out.println("Password changed successfully.");

        } catch (Exception e) {
            System.err.println("Failed to change password: " + e.getMessage());
        } finally {
            // Properly shut down the client
            cognitoClient.shutdown();
        }
    }
}

