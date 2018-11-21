/**
 * 
 * @author AlexDerain / DING Rui
 *
 *	Contains the title and length of a movie
 */
public class Movie {

	private String title; //Title of the movie
	private int length; //Length of the movie
	
	public Movie(String title, int length){
		this.title = title;
		this.length = length;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String toString(){
		return "Title: " + title + '\n' +
				"Length: " + length;
	}
}
