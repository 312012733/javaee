package com.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages= {"com.dao","com.dao2"})
public class MyBatisConfiguration
{}
