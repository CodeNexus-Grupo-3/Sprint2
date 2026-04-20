package school.sptech;

import school.sptech.client.S3Provider;
import school.sptech.model.Dados;
import school.sptech.service.LeitorService;
import school.sptech.service.S3Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        S3Provider provider = new S3Provider();
        S3Client s3Client = provider.getS3Client();
        S3Service s3 = new S3Service(s3Client);
        String nomeBucket = "s3-codenexus";

        try {
            List<Bucket> listaBuckets = s3.listarBuckets();
            for (Bucket bucket : listaBuckets) {
                if (bucket.name().equals(nomeBucket)) {
                    s3.setBucketName(nomeBucket);
                    break;
                }
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar buckets: " + e.getMessage());
        }

        try {
            s3.extrairObjetos(s3.listarObjetos(nomeBucket));
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }

        LeitorService leitor = new LeitorService();
        File pasta = new File("ArquivosS3");
        List<Dados> dados = new ArrayList<>();

        for (File file : pasta.listFiles()) {
            dados = leitor.extrairDataset(file.getPath());
        }
    }
}