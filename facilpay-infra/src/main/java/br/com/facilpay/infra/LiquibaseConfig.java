/**
 * 
 */
package br.com.facilpay.infra;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.configuration.GlobalConfiguration;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.integration.spring.SpringLiquibase;

/**
 * @author Renan F Rodrigues
 *
 */

@Configuration
public class LiquibaseConfig {
	
	@Autowired
	@Qualifier(value = "rds-datasource")
	private DataSource dataSource;
	
	@Value("${config.liquibase.database-change-log-table}")
	private String changelogTable;

	@Value("${config.liquibase.database-change-log-lock-table}")
	private String changelogLockTable;
	
	@Value("${config.liquibase.change-log}")
	private String changelog;
	
	@Bean
	public SpringLiquibase liquibase() {
		GlobalConfiguration globalConfiguration = LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration.class);
		globalConfiguration.setDatabaseChangeLogTableName(changelogTable);
		globalConfiguration.setDatabaseChangeLogLockTableName(changelogLockTable);
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(changelog);
		return liquibase;
	}

}
