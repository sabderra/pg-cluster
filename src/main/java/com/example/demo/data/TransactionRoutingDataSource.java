package com.example.demo.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.example.demo.data.TransactionDataSourceType.READ_ONLY;
import static com.example.demo.data.TransactionDataSourceType.READ_WRITE;

@Slf4j
public class TransactionRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("determineCurrentLookupKey");

        boolean isSyncActive = TransactionSynchronizationManager.isSynchronizationActive();
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();

        Object dataSourceType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ?
                READ_ONLY :
                READ_WRITE;

        log.info("determineCurrentLookupKey returning: {}", dataSourceType);
        return dataSourceType;
    }
}
