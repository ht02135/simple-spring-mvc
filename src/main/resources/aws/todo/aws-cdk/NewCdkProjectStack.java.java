package com.myorg;import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.iam.IManagedPolicy;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;import java.util.ArrayList;
import java.util.List;public class NewCdkProjectStack extends Stack {
    public NewCdkProjectStack(final Construct scope, final String id) {
        this(scope, id, null);
    }public NewCdkProjectStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);// The code that defines your stack goes here
        Bucket.Builder.create(this, "MyFirstBucket")
                .versioned(true)
                .bucketName("cdk-unique-bucket-name")
                .encryption(BucketEncryption.S3_MANAGED)
                .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();List<IManagedPolicy> policies = new ArrayList<>();
        policies.add(ManagedPolicy
                .fromManagedPolicyArn(this, "admin",
                        "arn:aws:iam::aws:policy/AdministratorAccess"));Role.Builder.create(this, "SCLauncher")
                .roleName("SCLauncher")
                .description("Role to provision project in ServiceCatalog, used by Service Catalog service")
                .managedPolicies(policies)
                .assumedBy(ServicePrincipal.Builder.create("servicecatalog.amazonaws.com").build())
                .build();
    }
}