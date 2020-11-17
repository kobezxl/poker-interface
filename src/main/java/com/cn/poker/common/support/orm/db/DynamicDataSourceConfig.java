package com.cn.poker.common.support.orm.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.cn.poker.common.support.orm.db.DataSourceEnum;
import com.cn.poker.common.support.orm.db.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 */
@EnableTransactionManagement
@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties("spring.datasource.druid.master-data-source")
    public DataSource defaultDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource defaultDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceEnum.MASTER.getName(), defaultDataSource);
        return new DynamicDataSource(defaultDataSource, targetDataSources);
    }
    
    @Bean
	public PlatformTransactionManager mysqlTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
