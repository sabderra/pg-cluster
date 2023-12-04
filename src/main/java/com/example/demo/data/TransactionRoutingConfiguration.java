package com.example.demo.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

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
    @FlywayDataSource
    public DataSource primaryDataSource() {
        return new HikariDataSource(primaryConfiguration());
    }

    @Bean
    public DataSource replicaDataSource() {
        return new HikariDataSource(replicaConfiguration());
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("primaryDataSource") DataSource primaryDataSource,
            @Qualifier("replicaDataSource") DataSource replicaDataSource
    ) {
        log.info("routingDataSource() called");
        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();

        Map<Object, Object> dataSourceMap = Map.of(
                READ_WRITE, primaryDataSource,
                READ_ONLY, replicaDataSource
        );

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(primaryDataSource);

        // Without this flyway initialization fails
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }


    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        log.info("dataSource() called, returning LazyConnectionDataSourceProxy");
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}
