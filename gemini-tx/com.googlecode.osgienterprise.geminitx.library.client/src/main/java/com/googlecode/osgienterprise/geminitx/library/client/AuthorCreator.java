package com.googlecode.osgienterprise.geminitx.library.client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.googlecode.osgienterprise.geminitx.library.model.Author;

public class AuthorCreator
{
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void createAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        em.persist(author);
    }
}
