
public interface ICinema {
	/**
	 * Add movie with given title and length to the list of movies screened in the cinema
	 * 
	 * @param title		//title of the movie
	 * @param length	//length of movie in hours, e.g. 1, 2, 3...
	 */
	void addMovie(String title, int length);
	
	/**
	 * Add room with given room number and number of seats to the cinema
	 * 
	 * @param number	//room number
	 * @param row		//number of rows
	 * @param seats		//number of seats in one row
	 */
	void addRoom(int number, int row, int seats);
	
	/**
	 * Add movie screening to the cinema
	 * 
	 * @param movieTitle		//title of the movie
	 * @param screeningTime		//time of the screening
	 * @param roomNumber		//number of the room in which movie is screened
	 */
	void addScreening(String movieTitle, int screeningTime, int roomNumber);
	
	/**
	 * Buy ticket to a movie screening
	 * 
	 * @param movieTitle		//title of the movie
	 * @param screeningTime		//time of the screening
	 * @return The ticketId or -1 in case the ticket cannot be bought
	 */
	int buyTicket(String movieTitle, int screeningTime);

	/**
	 * Buy ticket to a movie screening
	 *
	 * @param movieTitle		//title of the movie
	 * @param room				//the room number
	 * @param screeningTime		//time of screening
	 * @param row				//the row number
	 * @param seat				//the seat number
	 * @return The ticketId or -1 in case the ticket cannot be bought
	 */
	int buyTicket(String movieTitle, int room, int screeningTime, int row, int seat);

	/**
	 * Cancel ticket to a movie screening
	 * 
	 * @param ticketId		//id of the ticket to be canceled
	 */
	void cancelTicket(int ticketId);
	
	/**
	 * Add customer to the waiting list of a movie screening
	 * 
	 * @param movieTitle		//title of the movie
	 * @param screeningTime		//time of the screening
	 * @param customerName		//name of the customer
	 */
	void getOnWaitingList(String movieTitle, int screeningTime, String customerName);
	
	/**
	 * Get number of sold tickets
	 * 
	 * @return number of sold tickets
	 */
	int getSoldTicketCount();
	
	/**
	 * Print all screenings in the cinema
	 */
	void printAllScreenings();
	
	/**
	 * Print waiting list of the screenings
	 * 
	 * @param movieTitle		//title of the movie
	 * @param screeningTime		//time of the movie screening
	 */
	void printWaitingList(String movieTitle, int screeningTime);
}
