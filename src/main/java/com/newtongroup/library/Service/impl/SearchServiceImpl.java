package com.newtongroup.library.Service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

import com.newtongroup.library.Entity.AbstractRental;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	public List<Book> searchBooks(String searchText){
		
		FullTextQuery jpaQuery = searchBooksQuery(searchText);
		List<Book> bookList = jpaQuery.getResultList();	
		
		return bookList;
	}
	
	private FullTextQuery searchBooksQuery(String searchText) {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		 
		//Create hibernate search dsl for the entity
		org.hibernate.search.query.dsl.QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(AbstractRental.class)
				.get();
		
		
		//Generate a lucene query using the builder
		org.apache.lucene.search.Query query = queryBuilder
		.keyword()
		.wildcard()
		.onFields("isbn", "title", "authorList.firstname", "authorList.lastname")
		.matching(searchText + "*")
		.createQuery();
		
		
		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Book.class);
		
		return fullTextQuery;
		
	}
	
	
}
