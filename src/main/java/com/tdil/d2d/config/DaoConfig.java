package com.tdil.d2d.config;

import com.tdil.d2d.dao.*;
import com.tdil.d2d.dao.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {


    @Bean(name = "userDAO")
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }
    
}
