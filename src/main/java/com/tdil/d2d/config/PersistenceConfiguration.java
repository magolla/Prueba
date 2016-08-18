package com.tdil.d2d.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources(@PropertySource({"classpath:/conf/app/${spring.profiles.active}/jdbc.properties"}))
public class PersistenceConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    public PersistenceConfiguration() {
        super();
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(this.dataSource());
        sessionFactory.setPackagesToScan(new String[] {"com.tdil.d2d.persistence"});
        sessionFactory.setHibernateProperties(this.hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.getEnv().getProperty("jdbc.driverClassName"));
        dataSource.setUrl(this.getEnv().getProperty("jdbc.url"));
        dataSource.setUsername(this.getEnv().getProperty("jdbc.user"));
        dataSource.setPassword(this.getEnv().getProperty("jdbc.pass"));

        return dataSource;
    }


    @Bean
    public HibernateTransactionManager transactionManager() {
        final HibernateTransactionManager txManager = new HibernateTransactionManager();

        SessionFactory sessionFactory = (SessionFactory) this.getApplicationContext().getBean("sessionFactory");
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties hibernateProperties() {
        return new Properties() {

            private static final long serialVersionUID = -513755169441733678L;

            {
                this.setProperty("hibernate.dialect", PersistenceConfiguration.this.getEnv()
                    .getProperty("hibernate.dialect"));
                this.setProperty("hibernate.show_sql",
                    PersistenceConfiguration.this.getEnv().getProperty("hibernate.show_sql"));
                if (PersistenceConfiguration.this.getEnv().getProperty("hibernate.hbm2ddl.auto") != null) {
                	this.setProperty("hibernate.hbm2ddl.auto",
                        PersistenceConfiguration.this.getEnv().getProperty("hibernate.hbm2ddl.auto"));
                }
            }
        };
    }


    /*
     * Dao beans
     */
    /*
     * @Bean(name = "userDao") public GenericDAO<User> userDaoConfigurer() { GenericHibernateDAOImpl<User> bean = new
     * GenericHibernateDAOImpl<User>();
     * 
     * bean.setType(User.class);
     * 
     * return bean; }
     */

    /*
     * Getters & Setters
     */

    /**
     * @return the env
     */
    public Environment getEnv() {
        return this.env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(Environment env) {
        this.env = env;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
