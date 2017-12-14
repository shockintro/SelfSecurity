package com.lihf.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 此类用于配置pgsql数据源
 * @author lihf
 * @date 2017-12-14
 */
@Configuration
public class PostgresConfig {

    @Value("${spring.datasource.driver}")
    private String driverName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.url}")
    private String url;

    /**
     * 注册datasource Bean
     * @return datasource pgsql数据源
     */
    @Bean
    public DataSource postgresDataSource() {
        return  DataSourceBuilder.create()
                .driverClassName(driverName)
                .password(password)
                .url(url)
                .username(username)
                .build();
    }
}
