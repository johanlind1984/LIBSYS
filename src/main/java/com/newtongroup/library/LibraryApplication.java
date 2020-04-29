package com.newtongroup.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.newtongroup.library.Config.LuceneIndexConfig;

@SpringBootApplication
@Import(LuceneIndexConfig.class)
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
