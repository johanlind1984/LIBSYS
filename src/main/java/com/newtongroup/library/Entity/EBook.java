package com.newtongroup.library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="e_books")
public class EBook extends AbstractBook {
	

	@Column(name="download_link")
	private String downloadLink;

	public EBook() {
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
}
