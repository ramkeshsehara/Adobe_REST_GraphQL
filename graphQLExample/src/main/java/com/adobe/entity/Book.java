package com.adobe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Book {
	@Id
	@Column(name="book_id")
	private int id;
	
	private String title;
	
	@Column(name="total_pages")
	private Integer totalPages;
	
	private double rating;
	private String isbn;
	
	private Date publishedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="publisher_id")
	private Publisher publisher; // book.getPublisher().getId()

	// map to FK
	@Column(name="publisher_id", insertable = false, updatable = false)
	private Integer publisherId; // book.getPublisherId();
}
