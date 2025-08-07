import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;

@Service
public class CognitoUserDetailsService {

    @Value("${cognito.user.pool.id}")
    private String userPoolId;

    @Value("${cognito.client.id}")
    private String clientId;

    private final AWSCognitoIdentityProvider cognitoIdentityProvider;

    public CognitoUserDetailsService(AWSCognitoIdentityProvider cognitoIdentityProvider) {
        this.cognitoIdentityProvider = cognitoIdentityProvider;
    }

    public UserDetails loadUserByUsername(String username, String password) {
        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
            .withAuthFlow("ADMIN_NO_SRP_AUTH")
            .withAuthParameters(
                "USERNAME", username,
                "PASSWORD", password
            )
            .withUserPoolId(userPoolId)
            .withClientId(clientId);

        AdminInitiateAuthResult authResult = cognitoIdentityProvider.adminInitiateAuth(authRequest);

        // Check the authentication result
        if ("SUCCESS".equals(authResult.getAuthenticationResult().getAuthenticationResultCode())) {
            // Authentication is successful, create UserDetails
            String sub = authResult.getAuthenticationResult().getSub();
            String email = authResult.getAuthenticationResult().getUsername();

            // You can retrieve additional user attributes as needed

            return new User(sub, username, 
                true, true, true, true,
                new SimpleGrantedAuthority("ROLE_USER")
            );
        } else {
            // Handle authentication failure
            throw new AuthenticationException("Authentication failed");
        }
    }
}