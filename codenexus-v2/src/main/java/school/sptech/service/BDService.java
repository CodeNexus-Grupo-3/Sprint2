package school.sptech.service;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.Dados;

import java.util.List;

public class BDService {

    private final JdbcTemplate jdbcTemplate;

    public BDService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(List<Dados> dados) {
        System.out.println("Início da inserção no banco");
        for (Dados dado : dados) {
            jdbcTemplate.update("INSERT INTO Dashboard VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)", dado.getDuracao(), dado.getTotalBaron(), dado.getTotalDrag(), dado.getTotalTorres(), dado.getTotalAbates(), dado.getTotalMortes(), dado.getTotalAssistencias(), dado.getTotalGold(), dado.getTotalDano());
        }
        System.out.println("Fim da inserção no banco");
    }
}
