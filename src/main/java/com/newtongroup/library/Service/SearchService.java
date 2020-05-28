package com.newtongroup.library.Service;

import java.util.List;

import com.newtongroup.library.Entity.Author;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.EBook;

public interface SearchService {

	List<Book> searchBooks(String searchText);
	
	List<EBook> searchEBooks(String searchText);
	
	List<Book> filterBooksOnCategories(String searchText, List<String> categories);
	
	List<EBook> filterEBooksOnCategories(String searchText, List<String> categories);
	
	List<Author> searchAuthor(String searchText);

}
