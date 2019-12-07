package com.hong.py.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述
 *
 * @ProductName: HONGPY
 * @ProjectName: springbootdemo
 * @Package: com.hong.py.config
 * @Description: note
 * @Author: hongpy21691
 * @CreateDate: 2019/11/8 10:35
 * @UpdateUser: hongpy21691
 * @UpdateDate: 2019/11/8 10:35
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2019 hongpy Technologies Inc. All Rights Reserved
 **/
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("first")
    //@ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource dataSource() {
        //DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        //dynamicDataSource.setDefaultTargetDataSource(dataSource);
        return dataSource;
    }

    @Bean(name = "second")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource secodDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "firstSqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier(value = "dynamicdatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/hong/py/mapper/*xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier(value = "dynamicdatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/hong/py/mapper/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "firstSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(value = "firstSqlSessionFactory") SqlSessionFactory sqlSessionFactoryqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactoryqlSessionFactory);
    }

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier(value = "secondSqlSessionFactory") SqlSessionFactory sqlSessionFactoryqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactoryqlSessionFactory);
    }

    @Bean("dynamicdatasource")
    public DynamicRoutingDataSource dynamicRoutingDataSource(@Qualifier(value = "first") DataSource dataSource1,@Qualifier(value = "second") DataSource dataSource2) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSource1);
        Map<Object, Object> map = new HashMap<>();
        map.putIfAbsent("first",dataSource1);
        map.putIfAbsent("second", dataSource2);
        dynamicRoutingDataSource.setTargetDataSources(map);
        //默認為first
        DynamicDataSourceContextHolder.setDataSourceRouterKey("first");
        return dynamicRoutingDataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(value = "dynamicdatasource") DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

}