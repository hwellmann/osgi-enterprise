/*
 * Copyright 2010 Harald Wellmann
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.googlecode.osgienterprise.geminitx.library.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.googlecode.osgienterprise.geminitx.library.model.Author;
import com.googlecode.osgienterprise.geminitx.library.model.Book;

/**
 * NOTE: By the EJB 3.1 specification, all public business methods are transactional by default,
 * with a REQUIRED transaction type, so the <code>@TransactionAttribute</code> annotations on the
 * methods of this class are redundant.
 * <p>
 * However, by adding these annotations, we can use this class both in a Java EE 6 and in a Spring
 * 3.0.x container. Of course, the preferred solution would be if Spring supported the
 * <code>@Stateless</code> and other EJB annotations <a
 * href="https://jira.springframework.org/browse/SPR-3858">out-of-the-box</a>.
 * 
 * @author hwellmann
 * 
 */
public class SimpleLibraryService implements LibraryService {
    
    private static final long serialVersionUID = 1L;

    private List<Book> books = new ArrayList<Book>();
    
    @PostConstruct
    public void fillLibrary()
    {
        if (getNumBooks() != 0)
            return;
        
        Author mann = new Author();
        mann.setFirstName("Thomas");
        mann.setLastName("Mann");
        
        Author steinbeck = new Author();
        steinbeck.setFirstName("John");
        steinbeck.setLastName("Steinbeck");
        
        Book buddenbrooks = new Book();
        buddenbrooks.setTitle("Buddenbrooks");
        buddenbrooks.setAuthor(mann);
        mann.getBooks().add(buddenbrooks);
        
        Book eden = new Book();
        eden.setTitle("East of Eden");
        eden.setAuthor(steinbeck);
        steinbeck.getBooks().add(eden);

        books.add(buddenbrooks);
        books.add(eden);
    }
    
    public List<Book> findBooksByAuthor(String lastName)
    {
        List<Book> results = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getAuthor().getLastName().equals(lastName)) {
                results.add(book);
            }
        }
        return results;       
    }
    
    public List<Book> findBooksByTitle(String title)
    {
        List<Book> results = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                results.add(book);
            }
        }
        return books;       
    }
    
    public long getNumBooks()
    {
        return books.size();
    }
}
