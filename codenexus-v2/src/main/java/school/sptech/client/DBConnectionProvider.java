package school.sptech.client;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnectionProvider {

    private final DataSource dataSource;

    public DBConnectionProvider() {
        BasicDataSource basicDataSource = new BasicDataSource();

        String host = System.getenv("DB_HOST");
        String db = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");

        if (host == null || db == null || user == null || pass == null) {
            throw new RuntimeException("Variáveis de ambiente do banco não configuradas corretamente");
        }

        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://" + host + ":3306/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }
}
