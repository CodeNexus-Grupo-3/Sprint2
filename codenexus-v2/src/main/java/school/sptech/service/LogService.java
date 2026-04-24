package school.sptech.service;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.Log;

import java.time.LocalDateTime;

public class LogService {

    private final JdbcTemplate jdbcTemplate;

    public LogService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void sucesso(String status, String evento, String servico) {
         saveLog(new Log(LocalDateTime.now(), status, evento, servico));
    }

    public void erro(String status, String evento, String servico, String mensagemErro, String stacktrace) {
        saveLog(new Log(LocalDateTime.now(), status, evento, servico, mensagemErro, stacktrace));
    }

    public void saveLog(Log log) {
        jdbcTemplate.update(
                "INSERT INTO Log VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)",
                log.getDataHora(),
                log.getStatus(),
                log.getEvento(),
                log.getServico(),
                log.getMensagemErro(),
                log.getStacktrace(),
                log.getFkUsuario());
    }
}