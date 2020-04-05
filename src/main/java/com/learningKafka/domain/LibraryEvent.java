package com.learningKafka.domain;

/*@AllArgsConstructor  // for generating constructors with arguments
@NoArgsConstructor   // for generating constructors with no arguments 
@Data // for generating getters,setters and toString() methods
@Builder // used for fluent API style of building the Library event domain
*/
public class LibraryEvent {

	private Integer libraryEventId;
	private Book book;
	
	public Integer getLibraryEventId() {
		return libraryEventId;
	}
	
	public void setLibraryEventId(Integer libraryEventId) {
		this.libraryEventId = libraryEventId;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
}
