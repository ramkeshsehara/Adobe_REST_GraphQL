package com.adobe.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="publishers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Publisher {
	@Id
	@Column(name="publisher_id")
	private int id;
	
	private String name;
	
	@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
	private List<Book> books = new ArrayList<>();
}
