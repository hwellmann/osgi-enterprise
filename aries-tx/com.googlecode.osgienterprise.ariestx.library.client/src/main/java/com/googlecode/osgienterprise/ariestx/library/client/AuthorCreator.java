package com.googlecode.osgienterprise.ariestx.library.client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.googlecode.osgienterprise.ariestx.library.model.Author;

public class AuthorCreator
{
    @PersistenceContext
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void createAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        em.persist(author);
    }
}
