package com.myorg;
import software.amazon.awscdk.core.App;
import java.util.Arrays;

public class NewCdkProjectApp {
    public static void main(final String[] args) {
        App app = new App();        new NewCdkProjectStack(app, "NewCdkProjectStack");        app.synth();
    }
}

