/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.uniworks.groupware.admin.config.SecurityConfig;

/**
 * @author Park Chungwan
 * Application 설정 Class.
 */
@Configuration
@ComponentScan(basePackages = {"org.uniworks.groupware.admin.service"}, useDefaultFilters = false, includeFilters = {@Filter(Service.class)})
@EnableTransactionManagement
@MapperScan("org.uniworks.groupware.admin.mapper")
@PropertySource(value = "classpath:application.properties")
@Import({SecurityConfig.class})
public class AppConfig implements TransactionManagementConfigurer {	
	@Autowired
	//private Environment env;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BoneCPConfig boneCP = new BoneCPConfig();
		return boneCP.dataSource();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		return transactionManager;
		//return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory.getObject();
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager () {
		return transactionManager(); // reference the existing @Bean method above
	}
	
	@Bean
	public CacheManager cacheManager()
	{
		return new ConcurrentMapCacheManager();
	}	
		
	@Bean
	public static JavaMailSender mailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost("smtp.naver.com");
		mailSenderImpl.setPort(587);		
		mailSenderImpl.setUsername("linuxwan");
		mailSenderImpl.setPassword("sksdkfdk1");
		mailSenderImpl.setDefaultEncoding("UTF-8");
		
		Properties javaMailProps = new Properties();
		javaMailProps.put("mail.smtp.auth", true);
		javaMailProps.put("mail.smtp.starttls.enable", true);
		
		mailSenderImpl.setJavaMailProperties(javaMailProps);
		
		return mailSenderImpl;		
	}
	
}
