/*
 *
 */

package me.melvins.labs.mojo;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.Tag;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeRequest;
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
@Mojo(name = "UpdateLambdaCode", defaultPhase = LifecyclePhase.DEPLOY)
public class UpdateLambdaCodeMojo extends AbstractMojo {

    private static final Logger LOGGER =
            LogManager.getLogger(UpdateLambdaCodeMojo.class, new MessageFormatMessageFactory());

    @Parameter(required = true)
    private String functionName;

    @Parameter(required = true)
    private String s3Bucket;

    @Parameter(required = true)
    private String s3Key;

    @Override
    public String toString() {
        return "Configuration {" +
                "functionName='" + functionName + '\'' +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", s3Key='" + s3Key + '\'' +
                '}';
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        LOGGER.info("Executing Goal: UpdateLambdaCode");
        LOGGER.info(toString());

        AWSLambdaClient awsLambdaClient =
                new AWSLambdaClient(
                        new AWSCredentialsProviderChain(
                                new InstanceProfileCredentialsProvider(), new ProfileCredentialsProvider()));

        UpdateFunctionCodeRequest updateFunctionCodeRequest = new UpdateFunctionCodeRequest();
        updateFunctionCodeRequest.setFunctionName(functionName);
        updateFunctionCodeRequest.setS3Bucket(s3Bucket);
        updateFunctionCodeRequest.setS3Key(s3Key);
        updateFunctionCodeRequest.setPublish(true);

        awsLambdaClient.updateFunctionCode(updateFunctionCodeRequest);

    }

}
