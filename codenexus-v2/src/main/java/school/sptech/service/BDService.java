package school.sptech.service;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.Dados;
import school.sptech.model.Log;

import java.util.List;

public class BDService {

    private final JdbcTemplate jdbcTemplate;

    public BDService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(List<Dados> dados) {
        System.out.println("Início da inserção no banco");

        if (dados == null || dados.isEmpty()) {
            System.out.println("Nenhum dado para inserir");
            return;
        }

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
                System.err.println("Erro ao inserir dado da dashboard no banco");
            }
        }

        System.out.println("Fim da inserção no banco");
    }

    public void saveLog(Log log) {
        if (log == null) {
            System.out.println("Nenhum log para inserir");
            return;
        }

        try {
            jdbcTemplate.update(
                    "INSERT INTO Log VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)",
                    log.getDataHora(),
                    log.getStatus(),
                    log.getEvento(),
                    log.getServico(),
                    log.getMensagemErro(),
                    log.getStacktrace(),
                    log.getFkUsuario());
        } catch (DataAccessException e) {
            System.err.println("Erro ao inserir log no banco");
        }
    }
}
