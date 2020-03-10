package com.hong.py.aspect;

import com.hong.py.annotation.DataSourceType;
import com.hong.py.config.DynamicDataSourceContextHolder;
import com.hong.py.config.DynamicRoutingDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 文件描述
 *
 * @ProductName: HONGPY
 * @ProjectName: springbootdemo
 * @Package: com.hong.py.aspect
 * @Description: note
 * @Author: hongpy21691
 * @CreateDate: 2019/11/8 14:02
 * @UpdateUser: hongpy21691
 * @UpdateDate: 2019/11/8 14:02
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2019 hongpy Technologies Inc. All Rights Reserved
 **/
@Aspect
@Component
public class DataSourceAspect  {

    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(ds)") // 切点为注解类型为DataSourceType的函数
    public void pointcut(DataSourceType ds){}

    @Before("pointcut(ds)")
    public void ChangeDataSource(JoinPoint joinPoint, DataSourceType ds){
        String dsId = ds.value();
        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            logger.debug("Use DataSource :{} >", dsId, joinPoint.getSignature());
        } else {
            logger.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, joinPoint.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        }
    }

    @After("pointcut(ds)")
    public void ChangedEnd(JoinPoint joinPoint, DataSourceType ds){
        logger.debug("Revert DataSource : " + ds.value() + " > " + joinPoint.getSignature());
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }
}
