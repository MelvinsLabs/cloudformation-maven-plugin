/*
 *
 */

package me.melvins.labs.mojo;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mels
 */
@Mojo(name = "CreateStack", defaultPhase = LifecyclePhase.DEPLOY)
public class CreateStackMojo extends AbstractMojo {

    private static final Logger LOGGER = LogManager.getLogger(CreateStackMojo.class, new MessageFormatMessageFactory());

    @Parameter(required = true)
    private String stackName;

    @Parameter(required = true)
    private String templateUrl;

    @Parameter
    private Map<String, String> tags;

    @Override
    public String toString() {
        return "Configuration {" +
                "stackName='" + stackName + '\'' +
                ", templateUrl='" + templateUrl + '\'' +
                ", tags=" + tags +
                '}';
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        LOGGER.info("Executing Goal: CreateStack");
        LOGGER.info(toString());

        CreateStackRequest createStackRequest = new CreateStackRequest();
        createStackRequest.setStackName(stackName);
        createStackRequest.setTags(this.createTags());
        createStackRequest.setTemplateURL(templateUrl);

        AmazonCloudFormationClient amazonCloudFormationClient =
                new AmazonCloudFormationClient(new InstanceProfileCredentialsProvider());
        amazonCloudFormationClient.createStack(createStackRequest);
    }

    private List<Tag> createTags() {
        List<Tag> awsTags = new ArrayList<>();

        tags.forEach((k, v) -> {
            awsTags.add(new Tag().withKey(k).withValue(v));
        });

        return awsTags;
    }

}
