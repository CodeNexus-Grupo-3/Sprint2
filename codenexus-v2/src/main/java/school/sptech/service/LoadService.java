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
            return;
        }

        String sql = "INSERT INTO Dashboard VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, dados, dados.size(), (ps, dado) -> {
            ps.setInt(1, dado.getDuracao());
            ps.setInt(2, dado.getTotalBaron());
            ps.setInt(3, dado.getTotalDrag());
            ps.setInt(4, dado.getTotalTorres());
            ps.setInt(5, dado.getTotalAbates());
            ps.setInt(6, dado.getTotalMortes());
            ps.setInt(7, dado.getTotalAssistencias());
            ps.setInt(8, dado.getTotalGold());
            ps.setInt(9, dado.getTotalDano());
        });
    }

    public void clean() {
        try {
            logService.sucesso("INFO", "Iniciando limpeza da tabela Dashboard", "LoadService");

            jdbcTemplate.execute("TRUNCATE TABLE Dashboard");

            logService.sucesso("SUCESSO", "Tabela limpa com sucesso", "LoadService");

        } catch (DataAccessException e) {
            logService.erro("ERRO", "Erro ao limpar tabela", "LoadService", e.getMessage(), e.toString());
        }
    }
}