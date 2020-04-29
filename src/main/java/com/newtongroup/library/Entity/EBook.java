package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="e_books")
public class EBook extends AbstractBook {

	@Column(name="download_link")
	private String downloadLink;

	@ManyToMany()
	@JoinTable(
			name="ebook_author",
			joinColumns = {@JoinColumn(name="idebook_author_ebook_id")},
			inverseJoinColumns = {@JoinColumn(name="idebook_author_author_id")})
	private List<Author> authorList;

	public EBook() {
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}
}
