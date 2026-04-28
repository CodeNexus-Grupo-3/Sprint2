package school.sptech.client;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3Provider {

    private final AwsCredentialsProvider credentials;

    public S3Provider() {
        this.credentials = DefaultCredentialsProvider.create();
    }

    public S3Client getS3Client() {
        String region = System.getenv("AWS_REGION");

        if (region == null) {
            throw new RuntimeException("AWS_REGION não definida");
        }

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentials)
                .build();
    }
}