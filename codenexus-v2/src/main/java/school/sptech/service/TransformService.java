package school.sptech.service;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

    // Leitura dos Arquivos Extraídos do Bucket
    public List<Dados> lerArquivos(List<File> arquivos) {

        logService.sucesso("INFO", "Tratativa dos Dados iniciada", "TransformService");

        List<Dados> dados = new ArrayList<>();

        for (File file : arquivos) {
            if (!file.getName().endsWith(".xlsx")) {
                continue;
            }

            logService.sucesso("INFO", "Processando arquivo: " + file.getName(), "TransformService");

            try {
                dados.addAll(extrairDataset(file.getPath()));
                logService.sucesso("SUCESSO", "Arquivo " + file.getName() + " processado com sucesso", "TransformService");
            } catch (Exception e) {
                logService.erro("ERRO", "Erro ao processar arquivo: " + file.getName(), "TransformService", e.getMessage(), e.toString());
            }
        }

        logService.sucesso("SUCESSO", "Dados tratados com sucesso", "TransformService");

        return dados;
    }

    // Extração dos dados dos Arquivos listados
    private List<Dados> extrairDataset(String caminhoArquivo) {
        logService.sucesso("INFO", "Iniciando a extração de dados do arquivo " + caminhoArquivo, "TransformService");

        List<Dados> dadosExtraidos = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (
                InputStream arquivo = new FileInputStream(caminhoArquivo);
                Workbook workbook = StreamingReader.builder()
                        .rowCacheSize(100)
                        .bufferSize(4096)
                        .open(arquivo);
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Formatando a Duração
                Integer duracao;
                try {
                    String duracaoString = formatter.formatCellValue(row.getCell(0));
                    String[] partes = duracaoString.split(":");
                    duracao = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
                }
                catch (Exception e) {
                    logService.erro("ERRO", "Erro ao converter duração na linha " + row.getRowNum(), "TransformService", e.getMessage(), e.toString());
                    continue;
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
                }
                else if (vitoria.equals(time2)) {
                    totalBaron = getInteger(row, 7);
                    totalDrag = getInteger(row, 8);
                    totalTorres = getInteger(row, 9);
                    totalAbates = soma(row, 35);
                    totalMortes = soma(row, 36);
                    totalAssistencias = soma(row, 37);
                    totalGold = soma(row, 38);
                    totalDano = soma(row, 39);
                }
                else {
                    logService.erro("ERRO", "Linha inválida (vitória não bate) - linha " + row.getRowNum(), "TransformService", "Valor de vitória não corresponde a nenhum time", null);
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
            logService.erro("ERRO", "Erro ao ler arquivo: " + caminhoArquivo, "TransformService", e.getMessage(), e.toString());
            return dadosExtraidos;
        }
        return dadosExtraidos;
    }

    // Casting de valores Double para Integer
    private Integer getInteger(Row row, Integer cell) {
        try {
            if (row.getCell(cell) == null) return 0;
            return (int) row.getCell(cell).getNumericCellValue();
        } catch (Exception e) {
            logService.erro("ERRO", "Erro ao ler célula " + cell + " na linha " + row.getRowNum(), "TransformService", e.getMessage(), null);
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
