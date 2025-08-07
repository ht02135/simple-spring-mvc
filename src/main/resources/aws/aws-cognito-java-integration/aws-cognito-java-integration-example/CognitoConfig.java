@Configuration
public class CognitoConfig {

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;


    @Bean
    public AWSCognitoIdentityProvider cognitoIdentityProvider() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.fromName(region))
                .build();
    }
}