package com.newtongroup.library.Service;

import java.util.List;

import com.newtongroup.library.Entity.Book;

public interface SearchService {

	List<Book> searchBooks(String searchText);

}
