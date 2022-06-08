package cn.angers.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
表示这是一个 spring boot应用
@SpringBootApplication 是一个符复合注解
它组合了以下几个注解
@SpringBootConfiguration 将这个类声明为配置类
@EnableAutoConfiguration 启用 Spring Boot 自动配置
@ComponentScan 启用组件扫描
当你将其它类声明为 @Component @Controller @Service
spring 会将这些类自动注册为 spring 应用上下文中的组件
 */
@SpringBootApplication
public class SpringInActionApplication {

	public static void main(String[] args) {
		/*
		运行 spring 应用
		这是执行 jar 文件时运行的方法
		调用 SpringApplication.run() 方法
		执行实际的应用程序引导动作
		创建 spring 应用上下文
		run() 方法中的两个参数
		SpringInActionApplication.class 配置类
		args 命令行参数
		 */
		SpringApplication.run(SpringInActionApplication.class, args);
	}

}
