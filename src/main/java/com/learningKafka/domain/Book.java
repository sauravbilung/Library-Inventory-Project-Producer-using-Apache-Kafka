package com.learningKafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor  // for generating constructors with arguments
@NoArgsConstructor   // for generating constructors with no arguments 
@Data // for generating getters,setters and toString() methods
@Builder // used for fluent API style of building the Library event domain
public class Book {

	private Integer bookId;
	private String bookName;
	private String bookAuthor;
}
