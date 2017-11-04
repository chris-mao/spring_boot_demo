/**
 * 
 */
package com.jrsoft.config.datasource;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

/**
 * com.jrsoft.config.datasource DataSourceConfiguration
 * 
 * 数据源配置类
 * 
 * TODO: 没有实现，将来也许会用到多数据源的连接，需要实现之
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
//@Configuration
public class DataSourceConfiguration {

	// @Bean(name="mysqlDS")
	// @ConfigurationProperties(prefix="spring.datasource")
	public DataSource mysqlProdDataSource() {
		return DataSourceBuilder.create().build();
	}

}
