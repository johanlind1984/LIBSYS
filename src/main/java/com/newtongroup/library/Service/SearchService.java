package com.newtongroup.library.Service;

import java.util.List;

import com.newtongroup.library.Entity.AbstractBook;
import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.EBook;

public interface SearchService {

	List<Book> searchBooks(String searchText);
	
	List<EBook> searchEBooks(String searchText);

}
