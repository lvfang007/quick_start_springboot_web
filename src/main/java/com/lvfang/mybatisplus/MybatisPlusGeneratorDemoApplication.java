package com.lvfang.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.mybatis.spring.annotation.MapperScan;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({"com.lvfang.mybatisplus.mapper"})
public class MybatisPlusGeneratorDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusGeneratorDemoApplication.class, args);
	}

}
