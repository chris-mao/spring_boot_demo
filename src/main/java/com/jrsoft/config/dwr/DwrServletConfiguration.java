/**
 * 
 */
package com.jrsoft.config.dwr;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.extend.Configurator;
import org.directwebremoting.spring.CreatorConfig;
import org.directwebremoting.spring.DwrSpringServlet;
import org.directwebremoting.spring.SpringConfigurator;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.jrsoft.config.servlet ServletConfiguration
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Configuration
// @ImportResource(locations = { "classpath:config/dwr.xml" })
public class DwrServletConfiguration {

	// @Bean
	// public DwrController dwrController() {
	// DwrController dwrController = new DwrController();
	//
	// Map<String,String> configParams = new HashMap<String, String>();
	// configParams.put("debug", "true");
	// configParams.put("crossDomainSessionSecurity", "false"); //
	// 设置为false可以支持跨域
	// configParams.put("allowScriptTagRemoting", "true");//
	// 设置为true可以启用script标签的远程访问
	// configParams.put("activeReverseAjaxEnable", "true");// 设置为true启用轮询和长连接
	// dwrController.setConfigParams(configParams);
	//
	// List<Configurator> configurators = new ArrayList<Configurator>();
	// configurators.add(new AnnotationsConfigurator());
	// dwrController.setConfigurators(configurators);
	//
	// return dwrController;
	// }

	@Bean(name = "__dwrConfiguration")
	public Configurator __dswrConfiguration() {

		SpringCreator creator = new SpringCreator();
		creator.setBeanName("authUserServiceImpl");
		creator.setJavascript("authUserService12345");
		creator.setScope("application");

		CreatorConfig creatorConfig = new CreatorConfig();
		creatorConfig.setCreator(creator);

		Map<String, CreatorConfig> creators = new HashMap<String, CreatorConfig>();
		creators.put("Demo", creatorConfig);

		Map<String, String> converterTypes = new HashMap<String, String>();
		converterTypes.put("authUser", "com.jrsoft.auth.entity.AuthUser");

		SpringConfigurator cfg = new SpringConfigurator();
		cfg.setCreators(creators);
		cfg.setConverterTypes(converterTypes);
		return cfg;
	}

	@Bean
	public ServletRegistrationBean dwrSpringServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new DwrSpringServlet());
		registration.addUrlMappings("/dwr-invoke/*");
		registration.addInitParameter("debug", "true");
		registration.addInitParameter("crossDomainSessionSecurity", "false"); // 设置为false可以支持跨域
		registration.addInitParameter("allowScriptTagRemoting", "true");// 设置为true可以启用script标签的远程访问
		registration.addInitParameter("activeReverseAjaxEnable", "true");// 设置为true启用轮询和长连接
		// registration.addInitParameter("classes",
		// "com.jrsoft.auth.service.impl.AuthUserServiceImpl");
		return registration;
	}
}
