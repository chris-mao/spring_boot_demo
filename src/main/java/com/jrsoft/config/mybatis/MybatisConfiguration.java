/**
 * 
 */
package com.jrsoft.config.mybatis;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * com.jrsoft.config.mybatis PageHelperConfiguration
 * 
 * Mybatis配置类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Configuration
public class MybatisConfiguration {

	private final static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

	@Bean
	public PageHelper pageHelper() {
		logger.info("设置并加载Mybatis分页助手");
		PageHelper ph = new PageHelper();
		Properties p = new Properties();
		p.setProperty("dialect", "mysql");
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		p.setProperty("logImpl", "STDOUT_LOGGING");// print sql statements
		ph.setProperties(p);
		return ph;
	}

}
