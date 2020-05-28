package com.newtongroup.library.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.springframework.stereotype.Service;

import com.newtongroup.library.Entity.AbstractRental;
import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.EBook;
import com.newtongroup.library.Service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	private static final String[] BOOK_SEARCH_FIELDS = new String[] 
			{ "isbn", "title", "authorList.firstname",
			"authorList.lastname" };
	
	private static final String[] AUTHOR_SEARCH_FIELDS = new String[] 
			{"firstname", "lastname"};

	@PersistenceContext
	private EntityManager entityManager;


	public List<Book> filterBooksOnCategories(String searchText, List<String> categories) {

		List<Book> results = searchBooks(searchText);
		boolean hasCategoryFilter = categories != null && !categories.isEmpty();

		if (hasCategoryFilter) {
			List<Book> filteredResults = new ArrayList<Book>();

			for (Book book : results) {

				long categoryId = book.getPlacement().getPlacementId();

				if (categories.contains(String.valueOf(categoryId))) {
					filteredResults.add(book);
				}
			}
			results = filteredResults;
		}
		return results;
	}

	public List<EBook> filterEBooksOnCategories(String searchText, List<String> categories) {

		List<EBook> results = searchEBooks(searchText);
		boolean hasCategoryFilter = categories != null && !categories.isEmpty();

		if (hasCategoryFilter) {
			List<EBook> filteredResults = new ArrayList<EBook>();

			for (EBook book : results) {

				long categoryId = book.getPlacement().getPlacementId();

				if (categories.contains(String.valueOf(categoryId))) {
					filteredResults.add(book);
				}
			}
			results = filteredResults;
		}
		return results;
	}

	public List<Book> searchBooks(String searchText) {

		FullTextQuery jpaQuery = searchQuery(searchText, Book.class, BOOK_SEARCH_FIELDS);
		List<Book> bookList = jpaQuery.getResultList();

		return bookList;
	}

	public List<EBook> searchEBooks(String searchText) {

		FullTextQuery jpaQuery = searchQuery(searchText, EBook.class, BOOK_SEARCH_FIELDS);
		List<EBook> bookList = jpaQuery.getResultList();

		return bookList;
	}
	
	public List<Author> searchAuthor(String searchText) {
		FullTextQuery jpaQuery = searchQuery(searchText, AUTHOR_SEARCH_FIELDS);
		List<Author> authorList = jpaQuery.getResultList();
		
		return authorList;
	
	}
	
	private FullTextQuery searchQuery(String searchText , String[] fieldParams) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		// Create hibernate search dsl for the entity
		org.hibernate.search.query.dsl.QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Author.class).get();
		Query query;
		if (null != searchText && !searchText.trim().isEmpty() && null != fieldParams) {

			BooleanJunction<?> bj = queryBuilder.bool();

			for (String field : fieldParams) {
				for (String string : searchText.split(" ")) {
					bj.should(new WildcardQuery(new Term(field, "*" + string.trim().toLowerCase() + "*")));
				}
			}
			query = bj.createQuery();

		} else {
			query = queryBuilder.all().createQuery();
		}

		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Author.class);

		return fullTextQuery;

	}

	private FullTextQuery searchQuery(String searchText, Class<? extends AbstractRental> clazz, String[] fieldParams) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		// Create hibernate search dsl for the entity
		org.hibernate.search.query.dsl.QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(clazz).get();
		Query query;
		if (null != searchText && !searchText.trim().isEmpty() && null != fieldParams) {

			BooleanJunction<?> bj = queryBuilder.bool();

			for (String field : fieldParams) {
				for (String string : searchText.split(" ")) {
					bj.should(new WildcardQuery(new Term(field, "*" + string.trim().toLowerCase() + "*")));
				}
			}
			query = bj.createQuery();

		} else {
			query = queryBuilder.all().createQuery();
		}

		org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, clazz);

		return fullTextQuery;

	}

}
