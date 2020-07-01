package com.yy.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceContextHolder extends AbstractRoutingDataSource {
    private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> local = new InheritableThreadLocal<String>();
    public static void set(String db) {
        local.set(db);
    }

    @Override
    protected Object determineCurrentLookupKey() {
            return local.get();
    }

    public static void clearDataSource() {
        local.remove();
    }

}
