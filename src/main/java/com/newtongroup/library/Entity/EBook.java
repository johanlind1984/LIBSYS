package com.newtongroup.library.Entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;

@Entity
@Indexed
@Table(name="e_books")
public class EBook extends AbstractBook {

	@Column(name="download_link")
	private String downloadLink;

	@JsonManagedReference
	@IndexedEmbedded
	@ManyToMany()
	@JoinTable(
			name="ebook_author",
			joinColumns = {@JoinColumn(name="idebook_author_ebook_id")},
			inverseJoinColumns = {@JoinColumn(name="idebook_author_author_id")})
	private List<Author> authorList;
	

	@JsonIgnore
	@OneToMany(mappedBy = "ebook", cascade = CascadeType.ALL)
	private List<EbookLoan> loanedEbooks;
	
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "placement_id")
	private Placement placement;
	
	

	public Placement getPlacement() {
		return placement;
	}


	public void setPlacement(Placement placement) {
		this.placement = placement;
	}


	public EBook() {
	}
	
	
	public List<EbookLoan> getLoanedEbooks() {
		return loanedEbooks;
	}


	public void setLoanedEbooks(List<EbookLoan> loanedEbooks) {
		this.loanedEbooks = loanedEbooks;
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
