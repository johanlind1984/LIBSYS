package com.newtongroup.library.Config;

import org.springframework.context.annotation.Bean;

import com.newtongroup.library.Bean.LuceneIndexServiceBean;

import javax.persistence.EntityManagerFactory;

public class LuceneIndexConfig {

    @Bean
    public LuceneIndexServiceBean luceneIndexServiceBean(EntityManagerFactory EntityManagerFactory){
        LuceneIndexServiceBean luceneIndexServiceBean = new LuceneIndexServiceBean(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }

}