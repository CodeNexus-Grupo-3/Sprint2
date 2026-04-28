package school.sptech.service;

import school.sptech.model.Dados;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.util.List;

public class ETLService {

    private final ExtractService extractService;
    private final TransformService transformService;
    private final LoadService loadService;
    private final LogService logService;

    public ETLService(ExtractService extractService, TransformService transformService, LoadService loadService, LogService logService) {
        this.extractService = extractService;
        this.transformService = transformService;
        this.loadService = loadService;
        this.logService = logService;
    }

    public void executar() {
        // EXTRACT (S3)
        logService.sucesso("INFO", "Extração de dados do S3 iniciada", "ETLService");

        String nomeBucket = "s3-codenexus";

        List<S3Object> listaObjetos = extractService.listarObjetos(nomeBucket);

        if (listaObjetos.isEmpty()) {
            logService.sucesso("INFO", "Nenhum arquivo para extração", "ETLService");
            return;
        }

        List<File> arquivos = extractService.extrairObjetos(listaObjetos, nomeBucket);

        logService.sucesso("SUCESSO", "Extração de Dados Concluída (1/3)", "ETLService");

        // TRANSFORM (Apache POI)
        logService.sucesso("INFO", "Transformação de dados via Apache POI iniciada", "ETLService");

        if (arquivos.isEmpty()) {
            logService.sucesso("INFO", "Nenhum arquivo para transformação", "ETLService");
            return;
        }

        List<Dados> dados = transformService.lerArquivos(arquivos);

        logService.sucesso("SUCESSO", "Transformação de Dados Concluída (2/3)", "ETLService");

        // LOAD (JDBC)
        logService.sucesso("INFO", "Inserção no banco iniciada", "ETLService");
        try {
            loadService.save(dados);
            logService.sucesso("SUCESSO", "Carregamento de Dados Concluído (3/3)", "ETLService");
        } catch (Exception e) {
            logService.erro("ERRO", "Erro na etapa de inserção de dados", "ETLService", e.getMessage(), e.toString());
        }
    }
}
