package school.sptech;

import school.sptech.client.DBConnectionProvider;
import school.sptech.client.S3Provider;
import school.sptech.service.*;

public class Main {
    public static void main(String[] args) {
        // Clients
        S3Provider s3Provider = new S3Provider();
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();

        // Service
        LogService logService = new LogService(dbConnectionProvider.getConnection());
        ExtractService extractService = new ExtractService(s3Provider.getS3Client(), logService);
        TransformService transformService = new TransformService(logService);
        LoadService loadService = new LoadService(dbConnectionProvider.getConnection(), logService);
        ETLService etlService = new ETLService(extractService, transformService, loadService, logService);

        etlService.executar();
    }
}