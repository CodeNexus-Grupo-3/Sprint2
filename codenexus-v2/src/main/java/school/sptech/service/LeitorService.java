package school.sptech.service;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import school.sptech.model.Dados;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LeitorService {

    public List<Dados> extrairDataset(String caminhoArquivo) {
        System.out.println("Iniciando a extração de dados do arquivo " + caminhoArquivo);

        List<Dados> dadosExtraidos = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Formatando a Duração
                Integer duracao = 0;
                try {
                    String duracaoString = formatter.formatCellValue(row.getCell(0));
                    String[] partes = duracaoString.split(":");
                    duracao = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
                } catch (Exception e) {
                    System.err.println("Erro ao converter duração na linha " + row.getRowNum());
                    continue;
                }

                String vitoria = formatter.formatCellValue(row.getCell(1));
                String time1 = formatter.formatCellValue(row.getCell(2));
                String time2 = formatter.formatCellValue(row.getCell(6));
                Integer totalBaron = null;
                Integer totalDrag = null;
                Integer totalTorres = null;
                Integer totalAbates = null;
                Integer totalMortes = null;
                Integer totalAssistencias = null;
                Integer totalGold = null;
                Integer totalDano = null;

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
                    System.err.println("Linha inválida (vitória não bate)");
                    continue;
                }

                Dados dado = new Dados(
                        duracao,
                        totalBaron,
                        totalDrag,
                        totalTorres,
                        totalAbates,
                        totalMortes,
                        totalAssistencias,
                        totalGold,
                        totalDano
                );

                dadosExtraidos.add(dado);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + caminhoArquivo, e);
        }
        System.out.println("Retornar lista dos dados extraídos");
        return dadosExtraidos;
    }

    private Integer getInteger(Row row, Integer cell) {
        try {
            if (row.getCell(cell) == null) return 0;
            return (int) row.getCell(cell).getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer soma(Row row, Integer cell) {
        Integer total = 0;
        for (int i = cell; i <= cell + 20; i += 5) {
            total += getInteger(row, i);
        }
        return total;
    }
}