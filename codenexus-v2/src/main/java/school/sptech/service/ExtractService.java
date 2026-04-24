package school.sptech.service;

import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ExtractService {

    private final S3Client s3Client;
    private final LogService logService;

    // Construtor
    public ExtractService(S3Client s3Client, LogService logService) {
        this.s3Client = s3Client;
        this.logService = logService;
    }

    // Lista os buckets de um S3
    public List<Bucket> listarBuckets () {
        try {
            logService.sucesso("INFO", "Listando buckets", "ExtractService");
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            logService.sucesso("SUCESSO", "Buckets listados com sucesso", "ExtractService");
            return buckets;
        } catch (Exception e) {
            logService.erro("ERRO", "Erro ao listar buckets", "ExtractService", e.getMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }

    // Lista os objetos de um bucket
    public List<S3Object> listarObjetos (String nomeBucket) {
        try {
            logService.sucesso("INFO", "Listando os Objetos de um Bucket", "ExtractService");
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(nomeBucket)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();

            if (objects.isEmpty()) {
                logService.sucesso("INFO", "Nenhum objeto encontrado no bucket: " + nomeBucket, "ExtractService");
            } else {
                logService.sucesso("SUCESSO", objects.size() + " listados com sucesso", "ExtractService");
            }

            return objects;
        } catch (Exception e) {
            logService.erro("ERRO", "Erro ao listar objetos do bucket: " + nomeBucket, "ExtractService", e.getMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }

    // Extrai os objetos de uma lista de objetos de um bucket
    public List<File> extrairObjetos (List<S3Object> objects, String bucketName) {
        try {
            logService.sucesso("INFO", "Extraindo os Objetos de um Bucket", "ExtractService");

            File pasta = criarDiretorio();
            List<File> arquivos = new ArrayList<>();

            for (S3Object object : objects) {
                try {
                    GetObjectRequest getObjects = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(object.key())
                            .build();

                    String nomeArquivo = new File(object.key()).getName();
                    File file = new File(pasta, nomeArquivo);

                    try (InputStream inputStream = s3Client.getObject(getObjects, ResponseTransformer.toInputStream())) {
                        Files.copy(inputStream, file.toPath());
                    }

                    arquivos.add(file);
                } catch (Exception e) {
                    logService.erro("ERRO", "Erro ao baixar arquivo: " + object.key(), "ExtractService", e.getMessage(), e.toString());
                }
            }

            logService.sucesso("SUCESSO", "Objetos extraídos do bucket com sucesso", "ExtractService");

            return arquivos;
        } catch (Exception e) {
            logService.erro("ERRO", "Erro ao extrair objetos do bucket", "ExtractService", e.getMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }

    // Interno: Cria um diretório e retorna sua referência
    private File criarDiretorio() {
        File pasta = new File("ArquivosS3");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        return pasta;
    }

    // Getters
    public S3Client getS3Client() {
        return s3Client;
    }
    public LogService getLogService() {
        return logService;
    }
}