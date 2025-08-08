
/*
<dependency>
  <groupId>software.amazon.awssdk</groupId>
  <artifactId>cognitoidentityprovider</artifactId>
  <version>2.25.26</version> <!-- or latest version -->
</dependency>
*/

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminAddUserToGroupRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

public class AddUserToGroupExample {

    public static void main(String[] args) {

        String userPoolId = "us-west-2_Abc123456"; // Replace with your user pool ID
        String username = "john.doe@example.com";  // The username of the user
        String groupName = "Admins";               // The group to add the user to

        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_WEST_2) // Change to your region
                .build();

        try {
            AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .groupName(groupName)
                    .build();

            cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
            System.out.println("User " + username + " added to group " + groupName);

        } catch (CognitoIdentityProviderException e) {
            System.err.println("Failed to add user to group: " + e.awsErrorDetails().errorMessage());
        } finally {
            cognitoClient.close();
        }
    }
}