/**
 * 
 */
package br.com.facilpay.infra;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Renan F Rodrigues
 *
 */

@Configuration
@EnableTransactionManagement
public class DBConfig {
	
	@Value("${entityPackage}")
	private String entityPackage;
	
	@Value("${sqlDialect}")
	private String sqlDialect;
	
	@Primary
	@Bean(name = "rds-datasource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, 
			@Qualifier("rds-datasource") DataSource dataSource) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.dialect", sqlDialect);
		return builder
				.dataSource(dataSource)
				.properties(properties)
				.packages("br.com.facilpay.shared.entities", entityPackage)
				.persistenceUnit("facilpay-rds")
				.build();
	}
	
	@Primary
	@Bean(name = "facilpay-jdbc-template")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}	

}
