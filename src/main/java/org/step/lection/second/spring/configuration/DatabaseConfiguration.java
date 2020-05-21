package org.step.lection.second.spring.configuration;

import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "org.step")
public class DatabaseConfiguration {

    @Bean("devDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl("jdbc:postgresql://localhost:5433/social");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName(Driver.class.getName());

        return dataSource;
    }

    @Bean("prodDataSource")
    public DataSource newDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl("jdbc:postgresql://localhost:5432/social");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName(Driver.class.getName());

        return dataSource;
    }

    @Bean
//    @DependsOn(value = "devDataSource")
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier("devDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public JdbcTemplate jdbcTemplate(@Qualifier("devDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}
