package school.sptech.service;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.Dados;

import java.util.List;

public class LoadService {

    private final JdbcTemplate jdbcTemplate;
    private final LogService logService;

    public LoadService(JdbcTemplate jdbcTemplate, LogService logService) {
        this.jdbcTemplate = jdbcTemplate;
        this.logService = logService;
    }

    public void save(List<Dados> dados) {
        if (dados == null || dados.isEmpty()) {
            logService.sucesso("INFO", "Nenhum dado para inserir", "LoadService");
            return;
        }

        logService.sucesso("INFO", "Iniciando inserção de " + dados.size() + " registros", "LoadService");

        for (Dados dado : dados) {
            try {
                jdbcTemplate.update(
                        "INSERT INTO Dashboard VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        dado.getDuracao(),
                        dado.getTotalBaron(),
                        dado.getTotalDrag(),
                        dado.getTotalTorres(),
                        dado.getTotalAbates(),
                        dado.getTotalMortes(),
                        dado.getTotalAssistencias(),
                        dado.getTotalGold(),
                        dado.getTotalDano());
            } catch (DataAccessException e) {
                logService.erro("ERRO", "Erro ao inserir dados no banco", "LoadService", e.getMessage(), e.toString());
            }
        }

        logService.sucesso("SUCESSO", "Dados inseridos com sucesso", "LoadService");
    }
}