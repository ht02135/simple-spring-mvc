
/*
Setting up a valid web hosting infrastructure using AWS CDK in Java involves creating several AWS resources including:
    S3 bucket (for static asset hosting or logs)
    VPC with public and private subnets
    Internet Gateway
    Security Groups
    Application Load Balancer (ALB)
    Route 53 domain name configuration
////////////////////
Final Goal
You’ll provision:
    A VPC with public and private subnets.
    A security group allowing HTTP(S) traffic.
    An ALB in public subnets forwarding to an EC2 instance in private subnets.
    Route 53 domain name pointing to the ALB.
    (Optional) S3 bucket for storing web assets or logs.
////////////////////
Prerequisites
    Java (JDK 17+ recommended)
    AWS CLI configured
    AWS CDK installed (npm install -g aws-cdk)
    Maven
    Bootstrap your environment:
cdk bootstrap
////////////////////
Project Structure
my-webapp/
├── cdk.json
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── mycompany/
                    └── webstack/
                        └── WebHostingStack.java
////////////////////
Step-by-Step CDK Setup
1. Initialize the CDK Java Project
cdk init app --language java
////////////////////
Then add these dependencies to your pom.xml:

<dependencies>
  <dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>aws-ec2</artifactId>
    <version>2.158.0</version>
  </dependency>
  <dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>aws-elasticloadbalancingv2</artifactId>
    <version>2.158.0</version>
  </dependency>
  <dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>aws-route53</artifactId>
    <version>2.158.0</version>
  </dependency>
  <dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>aws-route53-targets</artifactId>
    <version>2.158.0</version>
  </dependency>
  <dependency>
    <groupId>software.amazon.awscdk</groupId>
    <artifactId>aws-s3</artifactId>
    <version>2.158.0</version>
  </dependency>
</dependencies>
////////////////////
Deploy
cdk synth
cdk deploy
////////////////////
Replace with Your Values
    Replace "example.com" with your registered domain name.
    Replace "your-keypair-name" with your EC2 key pair.
    Route53 Hosted Zone must already exist or you can use PublicHostedZone.Builder.create(...).

Testing
    After deploy, go to your domain (e.g., http://www.example.com)
    You should see the web server (e.g., Apache/Nginx/Java app) respond from the EC2 instance.
*/

package com.mycompany.webstack;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.constructs.Construct;
import software.amazon.awscdk.StackProps;

import software.amazon.awscdk.services.s3.Bucket;

import software.amazon.awscdk.services.ec2.*;

import software.amazon.awscdk.services.elasticloadbalancingv2.*;
import software.amazon.awscdk.services.elasticloadbalancingv2.targets.InstanceTarget;

import software.amazon.awscdk.services.route53.*;
import software.amazon.awscdk.services.route53.targets.LoadBalancerTarget;

import java.util.List;

public class WebHostingStack extends Stack {
    public WebHostingStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public WebHostingStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // 1. Create an S3 bucket (optional - for logs or static content)
        Bucket siteBucket = Bucket.Builder.create(this, "WebSiteAssets")
                .versioned(true)
                .build();

        // 2. Create a VPC with public and private subnets
        Vpc vpc = Vpc.Builder.create(this, "WebAppVPC")
                .maxAzs(2)
                .natGateways(1)
                .build();

        // 3. Security group for web traffic
        SecurityGroup webSg = SecurityGroup.Builder.create(this, "WebSG")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();

        webSg.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP");
        webSg.addIngressRule(Peer.anyIpv4(), Port.tcp(443), "Allow HTTPS");

        // 4. EC2 instance (private subnet)
        AmazonLinuxImage ami = AmazonLinuxImage.Builder.create()
                .generation(AmazonLinuxGeneration.AMAZON_LINUX_2)
                .build();

        Instance webInstance = Instance.Builder.create(this, "WebServer")
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_WITH_EGRESS).build())
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .machineImage(ami)
                .securityGroup(webSg)
                .keyName("your-keypair-name") // Replace with actual keypair
                .build();

        // 5. Load Balancer (public subnet)
        ApplicationLoadBalancer alb = ApplicationLoadBalancer.Builder.create(this, "WebALB")
                .vpc(vpc)
                .internetFacing(true)
                .securityGroup(webSg)
                .build();

        ApplicationListener listener = alb.addListener("Listener", BaseApplicationListenerProps.builder()
                .port(80)
                .open(true)
                .build());

        listener.addTargets("WebTargets", AddApplicationTargetsProps.builder()
                .port(80)
                .targets(List.of(new InstanceTarget(webInstance)))
                .build());

        // 6. Route53 setup (hosted zone + A record)
        HostedZone hostedZone = HostedZone.fromLookup(this, "HostedZone", HostedZoneProviderProps.builder()
                .domainName("example.com") // Replace with your domain
                .build());

        ARecord.Builder.create(this, "AliasRecord")
                .zone(hostedZone)
                .target(RecordTarget.fromAlias(new LoadBalancerTarget(alb)))
                .recordName("www") // www.example.com
                .build();
    }
}
