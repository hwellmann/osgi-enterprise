package com.googlecode.osgienterprise.geminitx.library.service;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class LibrarySpringConfig
{
    @Inject
    private EntityManagerFactory entityManagerFactory;
    
    @Bean
    public LibraryService simpleLibraryService() {
        return new PersistentLibraryService();
    }

    
    @Bean
    public PlatformTransactionManager transactionManager() {
            return new JpaTransactionManager(entityManagerFactory);
    }
}
