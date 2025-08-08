
/*
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-cognitoidp</artifactId>
    <version>1.12.742</version> <!-- Use latest version -->
</dependency>
*/

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.Arrays;

public class CognitoAdminCreateUserExample {

    public static void main(String[] args) {

        // AWS credentials (replace with your actual keys or use Default Credential Provider)
        String accessKey = "YOUR_ACCESS_KEY";
        String secretKey = "YOUR_SECRET_KEY";

        // Cognito user pool and region
        String userPoolId = "us-east-1_example123"; // Replace with your User Pool ID
        String username = "newuser@example.com";

        // Set up AWS Cognito client
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion("us-east-1") // Replace with your region
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        // User attributes (optional)
        AttributeType emailAttr = new AttributeType()
                .withName("email")
                .withValue("newuser@example.com");

        AttributeType emailVerified = new AttributeType()
                .withName("email_verified")
                .withValue("true");

        // Create request
        AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username)
                .withTemporaryPassword("TempP@ssw0rd123!") // Must meet password policy
                .withUserAttributes(Arrays.asList(emailAttr, emailVerified))
                .withMessageAction(MessageActionType.SUPPRESS) // Optional: don't send invitation email
                .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

        try {
            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);
            System.out.println("User created: " + createUserResult.getUser().getUsername());
        } catch (UsernameExistsException e) {
            System.err.println("User already exists: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}