package me.melvins.labs.mojo;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Created by Melvin_Mathai on 5/23/2016.
 */
@Mojo(name = "CreateStack", defaultPhase = LifecyclePhase.DEPLOY)
public class CreateStackMojo extends AbstractMojo {

    @Parameter
    private String templateUrl;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("Executing CreateStack Goal");
        System.out.println("Template " + templateUrl);

        CreateStackRequest createStackRequest = new CreateStackRequest();
        createStackRequest.setTemplateURL(templateUrl);

        AmazonCloudFormationClient amazonCloudFormationClient = new AmazonCloudFormationClient(new InstanceProfileCredentialsProvider());
        amazonCloudFormationClient.createStack(createStackRequest);
    }

}
