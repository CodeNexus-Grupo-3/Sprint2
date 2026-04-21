package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.client.DBConnectionProvider;
import school.sptech.model.Dados;
import school.sptech.model.Log;
import school.sptech.service.BDService;
import school.sptech.service.LeitorService;
import school.sptech.service.LogService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        LogService logService = new LogService();

        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
        JdbcTemplate connection = dbConnectionProvider.getConnection();
        BDService bd = new BDService(connection);

        // S3
//        Log logInicioS3 = logService.sucesso("INFO", "Extração de dados do S3 iniciada", "S3Service");
//        bd.saveLog(logInicioS3);
//
//        S3Provider provider = new S3Provider();
//        S3Client s3Client = provider.getS3Client();
//        S3Service s3 = new S3Service(s3Client);
//        String nomeBucket = "s3-codenexus";
//
//        try {
//            List<Bucket> listaBuckets = s3.listarBuckets();
//            for (Bucket bucket : listaBuckets) {
//                if (bucket.name().equals(nomeBucket)) {
//                    s3.setBucketName(nomeBucket);
//                    break;
//                }
//            }
//            Log log = logService.sucesso("SUCESSO", "Buckets Listados", "S3Service");
//            bd.saveLog(log);
//        }
//        catch (SdkClientException e) {
//            Log log = logService.erro(
//                    "ERRO",
//                    "Credenciais AWS",
//                    "S3Service",
//                    e.getMessage(),
//                    e.toString()
//            );
//            bd.saveLog(log);
//        }
//        catch (S3Exception e) {
//            Log log = logService.erro(
//                    "ERRO",
//                    "Listar buckets",
//                    "S3Service",
//                    e.awsErrorDetails().errorMessage(),
//                    e.toString()
//            );
//            bd.saveLog(log);
//        }
//
//        try {
//            List<S3Object> listaObjetos = s3.listarObjetos(nomeBucket);
//            if (listaObjetos.isEmpty()) {
//                Log log = logService.erro(
//                        "ERRO",
//                        "Nenhum arquivo no bucket",
//                        "S3Service",
//                        "Lista de objetos vazia",
//                        null
//                );
//                bd.saveLog(log);
//            } else {
//                Log log = logService.sucesso("SUCESSO", "Arquivos do bucket listados", "S3Service");
//                bd.saveLog(log);
//                s3.extrairObjetos(listaObjetos);
//            }
//            Log log = logService.sucesso("SUCESSO", "Extração de Dados Concluída (1/3)", "S3Service");
//            bd.saveLog(log);
//        }
//        catch (S3Exception e) {
//            Log log = logService.erro(
//                    "ERRO",
//                    "Download arquivos S3",
//                    "S3Service",
//                    e.getMessage(),
//                    e.toString()
//            );
//            bd.saveLog(log);
//        }

        // APACHE POI
        Log logInicioApache = logService.sucesso("INFO", "Transformação de dados via Apache POI iniciada", "LeitorService");
        bd.saveLog(logInicioApache);

        LeitorService leitor = new LeitorService();
        File pasta = new File("ArquivosS3");
        List<Dados> dados = new ArrayList<>();

        File[] arquivos = pasta.listFiles();
        if (arquivos == null) {
            Log log = logService.erro(
                    "ERRO",
                    "Leitura de arquivos",
                    "LeitorService",
                    "Pasta não encontrada ou vazia",
                    null
            );
            bd.saveLog(log);
            return;
        } else if (arquivos.length == 0) {
            Log log = logService.sucesso("INFO", "Nenhum arquivo para leitura", "LeitorService");
            bd.saveLog(log);
            return;
        }

        Log log1 = logService.sucesso("SUCESSO", "Arquivos da pasta listados", "LeitorService");
        bd.saveLog(log1);

        for (File arquivo : arquivos) {
            if (!arquivo.getName().endsWith(".xlsx")) {
                continue;
            }

            List<Dados> dado = leitor.extrairDataset(arquivo.getPath());

            if (dado != null) {
                dados.addAll(dado);
            }
        }

        Log log2 = logService.sucesso("SUCESSO", "Transformação de Dados Concluída (2/3)", "LeitorService");
        bd.saveLog(log2);

        // JDBC
        Log logInicioBD = logService.sucesso("INFO", "Inserção no banco iniciada", "BDService");
        bd.saveLog(logInicioBD);
        try {
            bd.save(dados);

            Log log = logService.sucesso("SUCESSO", "Carregamento de Dados Concluído (3/3)", "BDService");
            bd.saveLog(log);
        }
        catch (Exception e) {
            Log log = logService.erro(
                    "ERRO",
                    "Inserção de dados",
                    "BDService",
                    e.getMessage(),
                    e.toString()
            );
            bd.saveLog(log);
        }
    }
}