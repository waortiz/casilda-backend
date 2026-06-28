package co.edu.udea.casilda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
public class CasildaApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CasildaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CasildaApplication.class, args);
    }

    @Bean
    public CommandLineRunner executeSql(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute("ALTER TABLE registrolinealma ALTER COLUMN idpersona DROP NOT NULL");
                stmt.execute("ALTER TABLE registrolinealma ALTER COLUMN ididentidadgenero DROP NOT NULL");
                System.out.println("====== DB ALTER CONSTRAINTS EXECUTED SUCCESSFULLY ======");
            } catch (Exception e) {
                System.err.println("DB ALTER CONSTRAINTS FAILED: " + e.getMessage());
            }
        };
    }
}
