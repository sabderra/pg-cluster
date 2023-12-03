package com.example.demo.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import javax.sql.DataSource;
import java.util.Map;

import static com.example.demo.data.TransactionDataSourceType.READ_ONLY;
import static com.example.demo.data.TransactionDataSourceType.READ_WRITE;

@Configuration
@Slf4j
public class TransactionRoutingConfiguration extends AbstractJdbcConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.primary")
    public HikariConfig primaryConfiguration() {
        return new HikariConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.replica")
    public HikariConfig replicaConfiguration() {
        return new HikariConfig();
    }

    @Bean
    public DataSource routingDataSource() {
        log.info("routingDataSource");
        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();

        DataSource primaryDataSource = new HikariDataSource(primaryConfiguration());
        DataSource replicaDataSource = new HikariDataSource(replicaConfiguration());

        Map<Object, Object> dataSourceMap = Map.of(
                READ_WRITE, primaryDataSource,
                READ_ONLY, replicaDataSource
        );

        routingDataSource.setTargetDataSources(dataSourceMap);
        return routingDataSource;
    }

}
