package school.sptech.service;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.model.Dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TransformService {

    // Atributos
    private final LogService logService;

    // Construtor
    public TransformService(LogService logService) {
        this.logService = logService;
    }

    public void processarArquivos(List<File> arquivos, LoadService loadService) {

        List<Dados> batch = new ArrayList<>();
        int batchSize = 500;
        DataFormatter formatter = new DataFormatter();

        for (File file : arquivos) {

            if (!file.getName().endsWith(".xlsx"))
                continue;

            try (InputStream arquivo = new FileInputStream(file);
                    Workbook workbook = new XSSFWorkbook(arquivo)) {

                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {

                    if (row.getRowNum() == 0)
                        continue;

                    Dados dado = construirDado(row, formatter);

                    if (dado == null)
                        continue;

                    batch.add(dado);

                    if (batch.size() >= batchSize) {
                        loadService.save(batch);
                        batch.clear();
                    }
                }

            } catch (Exception e) {
                logService.erro("ERRO", "Erro ao processar arquivo", "TransformService",
                        e.getMessage(), e.toString());
            }
        }

        if (!batch.isEmpty()) {
            loadService.save(batch);
        }
    }

    private Dados construirDado(Row row, DataFormatter formatter) {

        Integer duracao;
        try {
            String duracaoString = formatter.formatCellValue(row.getCell(0));
            String[] partes = duracaoString.split(":");
            duracao = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
        } catch (Exception e) {
            return null;
        }

        String vitoria = formatter.formatCellValue(row.getCell(1));
        String time1 = formatter.formatCellValue(row.getCell(2));
        String time2 = formatter.formatCellValue(row.getCell(6));

        Integer totalBaron;
        Integer totalDrag;
        Integer totalTorres;
        Integer totalAbates;
        Integer totalMortes;
        Integer totalAssistencias;
        Integer totalGold;
        Integer totalDano;

        if (vitoria.equals(time1)) {
            totalBaron = getInteger(row, 3);
            totalDrag = getInteger(row, 4);
            totalTorres = getInteger(row, 5);
            totalAbates = soma(row, 10);
            totalMortes = soma(row, 11);
            totalAssistencias = soma(row, 12);
            totalGold = soma(row, 13);
            totalDano = soma(row, 14);

        } else if (vitoria.equals(time2)) {
            totalBaron = getInteger(row, 7);
            totalDrag = getInteger(row, 8);
            totalTorres = getInteger(row, 9);
            totalAbates = soma(row, 35);
            totalMortes = soma(row, 36);
            totalAssistencias = soma(row, 37);
            totalGold = soma(row, 38);
            totalDano = soma(row, 39);

        } else {
            return null;
        }

        return new Dados(
                duracao,
                totalBaron,
                totalDrag,
                totalTorres,
                totalAbates,
                totalMortes,
                totalAssistencias,
                totalGold,
                totalDano);
    }

    // Casting de valores Double para Integer
    private Integer getInteger(Row row, Integer cell) {
        try {
            if (row.getCell(cell) == null)
                return 0;
            return (int) row.getCell(cell).getNumericCellValue();
        } catch (Exception e) {
            logService.erro("ERRO", "Erro ao ler célula " + cell + " na linha " + row.getRowNum(), "TransformService",
                    e.getMessage(), null);
            return 0;
        }
    }

    // Soma das células dos jogadores
    private Integer soma(Row row, Integer cell) {
        Integer total = 0;
        for (int i = cell; i <= cell + 20; i += 5) {
            total += getInteger(row, i);
        }
        return total;
    }
}