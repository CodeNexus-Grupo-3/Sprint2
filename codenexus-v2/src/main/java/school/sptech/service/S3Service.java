package school.sptech.service;

import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class S3Service {
    private final S3Client s3Client;
    private String bucketName;

    public S3Service (S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public List<Bucket> listarBuckets () {
        return s3Client.listBuckets().buckets();
    }

    public List<S3Object> listarObjetos (String nomeBucket) {
        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket(nomeBucket)
                .build();

        return s3Client.listObjects(listObjects).contents();
    }

    public void extrairObjetos (List<S3Object> objects) throws IOException {
        File pasta = new File("ArquivosS3");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        for (S3Object object : objects) {
            GetObjectRequest getObjects = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(object.key())
                    .build();

            InputStream inputStream = s3Client.getObject(getObjects, ResponseTransformer.toInputStream());
            File file = new File(pasta + object.key());
            Files.copy(inputStream, file.toPath());
            System.out.println("Arquivo baixado: " + object.key());
        }
    }

    public S3Client getS3Client() {
        return s3Client;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}