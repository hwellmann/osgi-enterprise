/*
 * Copyright 2011 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.osgienterprise.library.client.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.osgienterprise.library.client.BookService;
import com.googlecode.osgienterprise.library.model.Book;


/**
 * Example of a persistence bundle client.
 * 
 * @author Harald Wellmann
 */
public class BookServiceImpl implements BookService {
    private static Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private EntityManagerFactory emf;

    /**
     * Used by Service Component Runtime to inject EntityManagerFactory service.
     * 
     * @param entityManagerFactory
     */
    protected void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    @Override
    public void createBooks() {
        log.info("creating books");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Book book17 = new Book(17, "Jane Eyre", "Charlotte Bronte");
        Book book31 = new Book(31, "Oliver Twist", "Charles Dickens");
        em.persist(book17);
        em.persist(book31);
        tx.commit();
        em.close();
        log.info("done");
    }

    @Override
    public List<Book> findBooks() {
        log.info("finding books");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        List<Book> books = query.getResultList();
        tx.commit();
        em.close();
        log.info("done");
        return books;
    }
}
