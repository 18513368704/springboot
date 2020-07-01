package com.yy.config;

import com.yy.comm.CommonConst;
import com.yy.comm.DataSourceContextHolder;
import com.yy.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig  {
    @Bean
    @ConfigurationProperties("spring.datasource.base")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource myRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(CommonConst.DB_MASTER, masterDataSource());
        targetDataSources.put(CommonConst.DB_SLAVE, slave1DataSource());
        DataSourceContextHolder myRoutingDataSource = new DataSourceContextHolder();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource());
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

}