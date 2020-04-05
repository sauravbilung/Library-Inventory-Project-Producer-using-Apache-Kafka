package com.learningKafka.domain;

/*@AllArgsConstructor  // for generating constructors with arguments
@NoArgsConstructor   // for generating constructors with no arguments 
@Data // for generating getters,setters and toString() methods
@Builder // used for fluent API style of building the Library event domain
*/
public class Book {

	private Integer bookId;
	private String bookName;
	private String bookAuthor;
	
	public Integer getBookId() {
		return bookId;
	}
	
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getBookAuthor() {
		return bookAuthor;
	}
	
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
}
