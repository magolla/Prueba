package com.tdil.d2d.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@PropertySource(ignoreResourceNotFound = true, value = {"classpath:/conf/app/${spring.profiles.active}/environment.properties",
		"classpath:/conf/app/${spring.profiles.active}/passwords.properties"})
@ComponentScan(basePackages = {"com.tdil.d2d"})
@Configuration
@EnableAsync
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.mappingJackson2HttpMessageConverter());
        this.addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);

        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean(name="messageSource")
	public ReloadableResourceBundleMessageSource messageSource(){
    	ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
    	bean.setBasename("classpath:locale/messages");
		return bean;
	}

//    @Bean
//    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
//        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
//        methodInvokingFactoryBean.setTargetClass(org.springframework.util.Log4jConfigurer.class);
//        methodInvokingFactoryBean.setTargetMethod("initLogging");
//        methodInvokingFactoryBean.setArguments(new Object[] {this.log4jLocation});
//        return methodInvokingFactoryBean;
//    }

    /**
     * Chequeo de declaracion de variables de entorno
     * Si hay una Variable NO definida, El Tomcat NO levanta
     */
    @PostConstruct
    public void checkStartup() {
        if (StringUtils.isEmpty(System.getProperty("log4j.configurationFile"))) {
            System.out.println("NO SE DECLARO :log4j.configurationFile");
            System.exit(-1);
        }

        if (StringUtils.isEmpty(System.getProperty("spring.profiles.active"))) {
            System.out.println("NO SE DECLARO :spring.profiles.active");
            System.exit(-1);
        }
    }

}
