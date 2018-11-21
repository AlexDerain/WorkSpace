import java.util.Scanner;

import Vector.*;

/**
 * 
 * @author AlexDerain / DING Rui
 *
 *	Initialize of cinema
 *
 * 	Contains 15 public methods:
 * 		addMovie
 * 		addRoom
 * 		addScreening
 * 		buyTicket(title, time)
 * 		buyTicket(title, time, row, seat)
 * 		cancelTicket
 * 		getFreePopcorn
 * 		getOnWaitingList
 * 		getSoldTicketCount
 * 		marathonTicke
 * 		printAllScreenings
 * 		printAllTickets
 * 		printScreenings
 * 		printWaitingList
 * 		toString
 *
 * 	And 8 private methods:
 * 		checkMarathonTicket
 * 		checkScreeningTimeOverlap
 * 		getMovieByTitleInScreening
 * 		getMovieIndexByScreening
 * 		getScreeningMovieById
 * 		getScreeningMovieByTitleAndTime
 * 		getTicketByID
 * 		waiting
 *
 * 	Also 9 private variables and containers:
 * 		Vector rooms: Contains Class Room, each represents one unique room
 * 		Vector movies: Contains Class Movie, each represents one unique movie
 * 		Vector movieScreenings: Contains Class LinkedList
 * 								The first element of each LinkedList is the movie, rest are the screenings of this movie
 * 								When a movie is added, it is also added to the movieScreenings automatically, without screenings
 * 		Vector tickets: Contains Class Ticket, each represents one unique ticket
 * 		Vector waitingList: Contains Class LinkedListQueue
 * 							Each queue contains poeple that waiting for a specific screening movie
 * 		Vector deletedTickets: Contains integers, each represents
 * 		int ticketId: Global variable, each integer smaller than or equal to this represents one unique ticket
 * 					  +1 after a ticket is sold
 * 		int soldTickets: Globla variable, represents the number of sold tickets
 * 						 +1 after a ticket is sold
 * 		int screeningId: Global variable, each integer smaller than or equal to this represents one unique screening movie
 * 						 +1 after a screening movie is added
 */
public class Cinema implements ICinema {

	private Vector rooms;	//Vector of rooms
	private Vector movies;	//Vector of movies
	private Vector movieScreenings;	//Vector of movie screenings
	private Vector tickets;	//Vector of tickets
	private Vector waitingList;	//Waiting list
	private Vector deletedTickets; //Contains IDs of deleted tickets, in case of accidentally calling
	private int ticketId;	//Each ticket ID will be used only once
	private int soldTickets;	//Count for sold tickets
	private int screeningId; //Each screening movie has a unique ID, for identifying waiting list

	
	public Cinema(){
		movies = new Vector(1000);
		rooms = new Vector(1000);
		movieScreenings = new Vector(1000);
		tickets = new Vector(10000);
		waitingList = new Vector(1000);
		deletedTickets = new Vector(10000);
		ticketId = 0;
		soldTickets = 0;
		screeningId = 0;
	}

	//Private methods
	/**
	 *
	 * @param waitingScreeningMovie
	 * @param screeningTime
	 *
	 * When buyTicket method returns -1, ask for the name of the customer and add him to the waiting list
	 */
	private void waiting(MovieScreening waitingScreeningMovie, int screeningTime){
			System.out.println("Sorry, but the room is full, we will put you in the waiting list.");
			System.out.println("Would you like to leave your name?");
			Scanner s = new Scanner(System.in);
			String name = s.next();

			//Put this customer on the waiting list of the specific screening movie
			getOnWaitingList(waitingScreeningMovie.getTitle(), screeningTime, name);
			System.out.println("Thank you " + name + ", you are on the waiting list now.");
	}

	/**
	 *
	 * @param title
	 * @param time
	 * @return the ID of the screening movie
	 *
	 * Searching for the screening movie with given title and time
	 * If the screening movie does not exist, then return -1
	 */
	private int getScreeningMovieByTitleAndTime(String title, int time){
		int index = -1;

		//Firstly get the LinkedList of the specific movie
		LinkedList tempMovie = getMovieByTitleInScreening(title);
		if(tempMovie != null) {
			for (int i = 1; i < tempMovie.size(); i++) {
				if (((MovieScreening) tempMovie.get(i)).getScreeningTime() == time) {
					index = ((MovieScreening) tempMovie.get(i)).getScreeningId();
				}
			}
		}
		return index;
	}

	/**
	 *
	 * @param Id
	 * @return the require MovieScreening
	 *
	 * Searching for the specific screening movie with given screening ID
	 */
	private MovieScreening getScreeningMovieById(int Id){
		for(int i = 0; i < movieScreenings.size(); i++){

			//Firstly get the LinkedList of the specific movie
			LinkedList tempMovie = (LinkedList)movieScreenings.get(i);
			for(int j = 1; j < tempMovie.size(); j++){
				if(((MovieScreening)tempMovie.get(j)).getScreeningId() == Id){
					return (MovieScreening)tempMovie.get(j);
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param title
	 * @return the LinkedList of screenings of the specific movie
	 *
	 * Get all screening of the specific movie with a given title
	 */
	private LinkedList getMovieByTitleInScreening(String title){
		for(int i = 0; i < movieScreenings.size(); i++){
			if(((Movie)((LinkedList)movieScreenings.get(i)).getFirst()).getTitle().equals(title)){
				return (LinkedList)movieScreenings.get(i);
			}
		}
		return null;
	}

	/**
	 *
	 * @param ticketId
	 * @return Ticket
	 *
	 * Search for the specific ticket with the given ticket ID
	 */
	private Ticket getTicketByID(int ticketId){
		if(deletedTickets.contain(ticketId)){
			return null;
		}
		else {
			for (int i = 0; i < tickets.size(); i++) {
				if (ticketId == ((Ticket) tickets.get(i)).getTicketId()) {
					return (Ticket) tickets.get(i);
				}
			}
			return null;
		}
	}

	/**
	 *
	 * @param number
	 * @param time
	 * @param length
	 * @return true or false
	 *
	 * Check whether a screening movie has a time overlapping with other existing screening movies
	 * True for time overlapping occurs
	 * False for no time overlapping
	 */
	private boolean checkScreeningTimeOverlap(int number, int time, int length){
		for(int i = 0; i < screeningId; i++){

			//Compares with every existing screening movie
			MovieScreening tempScreening = getScreeningMovieById(i);

			//Time overlapping only occurs when the two movies screened in the same room
			if(tempScreening.getRoom().getNumber() == number){

				/*
				Time overlapping occurs when:
				1. One starts when another is still on
				2. Two screenings start at the same time
				 */
				if(time > tempScreening.getScreeningTime() && time < (tempScreening.getScreeningTime() + tempScreening.getLength()) ||
						time < tempScreening.getScreeningTime() && (time + length) > tempScreening.getScreeningTime() ||
						time == tempScreening.getScreeningTime()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param current
	 * @param index: Contains the ID of the movies that has already been added
	 * @param endTime
	 * @return 1. Done with one movie, checking for the rest
	 * 		   2. When no movie can be added, then the marathon is over
	 *
	 * Using greedy algorithm, recursively find the best solution
	 */
	private LinkedList checkMarathonTicket(LinkedList current, Vector index, int endTime){
		int tempStartTime = 25;
		int tempId = -1;
		int tempIndex = -1;
		for(int j = 0; j < movieScreenings.size(); j++){

			//If the movie with this ID has already been added, then skip this movie
			if(index.contain(j)){
				continue;
			}
			LinkedList tempMovie = (LinkedList) movieScreenings.get(j);
			for(int i = 1; i < tempMovie.size(); i++){

				//Finding the best solution for the current situation
				//Starts earliest after the current movie is the best solution for the current state
				if(((MovieScreening)tempMovie.get(i)).getScreeningTime() < tempStartTime && ((MovieScreening)tempMovie.get(i)).getScreeningTime() >= endTime){
					tempStartTime = ((MovieScreening)tempMovie.get(i)).getScreeningTime();
					tempId = ((MovieScreening)tempMovie.get(i)).getScreeningId();
					tempIndex = getMovieIndexByScreeningId(tempId);
				}
			}
		}

		//When no screening movie can be added, the marathon is done
		if(tempId == -1){
			return current;
		}
		else {

			//Add the best movie to the marathon list
			current.addLast(getScreeningMovieById(tempId));

			//Put the movie of this screening to the added list, leave it in the next loop
			index.addLast(tempIndex);

			//The new end time is the end time of the new last movie
			int newEndTime = getScreeningMovieById(tempId).getLength() + tempStartTime;
			return checkMarathonTicket(current, index, newEndTime);
		}
	}

	/**
	 *
	 * @param id
	 * @return the specific movie
	 *
	 * Search for the movie with given screening ID
	 */
	private int getMovieIndexByScreeningId(int id){

		//Firstly get the movie from the specific screening
		Movie tempMovie = getScreeningMovieById(id).getMovie();
		for(int i = 0; i < movieScreenings.size(); i++){

			//Only need to check the title of movies and the given screening
			if(((Movie)((LinkedList)movieScreenings.get(i)).getFirst()).getTitle().equals(tempMovie.getTitle())){
				return i;
			}
		}
		return -1;
	}

	@Override
	public void addMovie(String title, int length){
		//If the movie already exists, then addition fails
		boolean flag = false;
		for (int i = 0; i < movies.size(); i++){
			if(title.equals(((Movie) movies.get(i)).getTitle())){
				System.out.println("This movie already exists!");
				flag = true;
				break;
			}
		}

		if(!flag){

			//Add this movie to the Vector movies
			Movie newMovie = new Movie(title, length);
			movies.addLast(newMovie);

			//Also add a new LinkedList to the Vector movieScreenings at the same time
			//First element of the list is the movie
			//Rest are screenings of this movie (if there are)
			LinkedList movie = new LinkedList();
			movie.addLast(newMovie);
			movieScreenings.addLast(movie);

			//Testing out print
			System.out.println("Addition succeed!");
		}
	}

	@Override
	public void addRoom(int number, int row, int seats){
		//If the room already exists, then this addition fails
		boolean flag = false;
		for (int i = 0; i < rooms.size(); i++){
			if(number == ((Room) rooms.get(i)).getNumber()){
				System.out.println("This room already exists!");
				flag = true;
				break;
			}
		}

		if(!flag){
			Room newRoom = new Room(number, row, seats);
			rooms.addLast(newRoom);

			//Testing out print
			System.out.println("Addition succeed!");
		}
	}

	@Override
	public void addScreening(String movieTitle, int screeningTime, int roomNumber){
		boolean flag1 = false;
		boolean flag2 = false;
		int index1 = 0, index2 = 0;
		Movie tempMovie = new Movie("1", 0);

		//If the movie does not exist, then addition fails
		for(int i = 0; i < movies.size(); i++){
			if(movieTitle.equals(((Movie) movies.get(i)).getTitle())){
				flag1 = true;
				index1 = i;
				tempMovie = (Movie)movies.get(i);
				break;
			}
		}
		if(!flag1){
			System.out.println("This movie does not exist!");
		}

		//If the room does not exist, then addition fails
		for(int i = 0; i < rooms.size(); i++){
			if(roomNumber == ((Room) rooms.get(i)).getNumber()){
				flag2 = true;
				index2 = i;
				break;
			}
		}
		if(!flag2) {
			System.out.println("This room does not exist!");
		}


		//Detecting time overlapping in the same room
		if(flag1) {
			for (int i = 0; i < screeningId; i++) {
				if (checkScreeningTimeOverlap(roomNumber, screeningTime, tempMovie.getLength())) {
					flag1 = false;
					System.out.println("Time overlap! Screening movie addition fails!");
					break;
				}
			}
		}

		/*
		If: No time overlapping, room and movie exist
		Add this screening to the screening list
		Create a new waiting list for this screening movie
		 */
		if(flag1 && flag2){

			//Create a new screening movie, with current screening ID
			MovieScreening newMovieScreening = new MovieScreening(((Movie) movies.get(index1)), screeningTime, ((Room) rooms.get(index2)));
			newMovieScreening.setScreeningId(screeningId);
			screeningId++;

			//Add this screening to the movie list in the movieScreenings
			//Rather than add to movieScreenings directly
			getMovieByTitleInScreening(movieTitle).addLast(newMovieScreening);

			//Add a new waiting list at the same time
			//Using screeningId to identify
			LinkedListQueue waitings = new LinkedListQueue();
			waitingList.addLast(waitings);

			//Testing out print
			System.out.println("Addition succeed!");
		}
	}

	@Override
	public int buyTicket(String movieTitle, int screeningTime){
		int index = getScreeningMovieByTitleAndTime(movieTitle, screeningTime);

		if(index >= 0){
			MovieScreening tempScreening = getScreeningMovieById(index); //For simplifying

			//If this screening still has vacant seats, then sells the ticket
			if(tempScreening.getVacantSeat() > 0){
				ticketId++;

				//Using default row and seat pointer as row and seat number for the new ticket
				Ticket newTicket = new Ticket(tempScreening, ticketId, tempScreening.getRowNumber(), tempScreening.getSeatNumber());
				tickets.addLast(newTicket);
				newTicket.ticketSold();
				soldTickets++;
				return ticketId;
			}
			else{
				waiting(tempScreening, screeningTime);
				return -1;
			}
		}

		//If the screening movie does not exist, then sale fails
		else{
			System.out.println("This screening movie does not exist!");
			return 0;
		}
	}

	@Override
	public int buyTicket(String movieTitle, int room, int screeningTime, int row, int seat) {
		int index = getScreeningMovieByTitleAndTime(movieTitle, screeningTime);

		if(index >= 0){
			MovieScreening tempScreening = getScreeningMovieById(index); //For simplifying

			//If required seat is still vacant, then sells the ticket
			if(tempScreening.ticketSoldBySeat(row, seat) != -1){
				ticketId++;
				Ticket newTicket = new Ticket(tempScreening, ticketId, row, seat);
				tickets.addLast(newTicket);
				soldTickets++;
				return ticketId;
			}
			else{
				waiting(tempScreening, screeningTime);
				return -1;
			}
		}

		//If the screening movie does not exist, then sale fails
		else{
			System.out.println("This screening movie does not exist!");
			return 0;
		}
	}

	@Override
	public void cancelTicket(int ticketId){

		//If the ticket does not exist, then cancel fails
		boolean flag = false;
		if(ticketId > this.ticketId){
			System.out.println("This ticket does not exist!");
			flag = true;
		}

		//Cancel the ticket, release a seat, and inform the first customer of this screening on the waiting list
		else{
			Ticket thisTicket = getTicketByID(ticketId);
			thisTicket.ticketCanceled();

			//If a ticket is canceled while there's people on the waiting list, then give him/her this ticket
			LinkedListQueue waitings = (LinkedListQueue)waitingList.get(thisTicket.getMovieScreening().getScreeningId());
			if(!waitings.isEmpty()){
				System.out.println("Dear " + waitings.top() + ", now your get your ticket, the ticketID is " + ticketId);
				waitings.pop();
				thisTicket.ticketSold();
			}

			//Otherwise just throw this ticket
			else{
				System.out.println("Nobody on the waiting list, this ticket is deleted.");

				//Add this ticket ID to delete list, in case of reuse
				deletedTickets.addLast(ticketId);
				soldTickets --;
				flag = true;
			}
		}
		
		//Testing print
		if(!flag){
			System.out.println("Cancel succeed!");
		}
	}

	@Override
	public void getOnWaitingList(String movieTitle, int screeningTime, String customerName) {

		//Save the information of the screening movie, and add the customer to the waiting list
		int index = getScreeningMovieByTitleAndTime(movieTitle, screeningTime);
		MovieScreening waitingScreeningMovie = getScreeningMovieById(index);
		((LinkedListQueue)waitingList.get(waitingScreeningMovie.getScreeningId())).push(customerName);
	}

	/**
	 *
	 * @param ticketId
	 *
	 * Get free popcorn by given ticket ID
	 */
	public void getFreePopcorn(int ticketId){
		boolean flag = false;
		Ticket thisTicket = getTicketByID(ticketId);

		//Only a ticket exists and is being used can it gets popcorn
		if(thisTicket != null && thisTicket.isTaken()){
			flag = true;
			thisTicket.getFreePopcorn();
		}

		//If the ticket does not exist , has already got popcorn or is not being used, then cannot get popcorn
		if(!flag){
			System.out.println("This ticket does not exist!");
		}
	}

	@Override
	public int getSoldTicketCount() {
		return soldTickets;
	}

	@Override
	public void printAllScreenings() {
		for(int j = 0; j < screeningId; j++){
			MovieScreening printing = getScreeningMovieById(j);
			System.out.println(printing);
		}
	}

	/**
	 *
	 * @param title
	 * @param screeningTime
	 *
	 * Print the room state of the given screening movie
	 * Represents as a matrix
	 */
	public void printScreening(String title, int screeningTime){
		int index = getScreeningMovieByTitleAndTime(title, screeningTime);
		if(index != -1){
			MovieScreening printing = getScreeningMovieById(index);
			System.out.println(printing);
			printing.printSeats();
		}
		else{
			System.out.println("Screening movie does not exit!");
		}

	}

	/**
	 * Print all tickets in the cinema today
	 * Only for the tickets that has been used
	 */
	public void printAllTickets(){
		for(int i = 0; i < tickets.size(); i++){
			if(((Ticket)tickets.get(i)).isTaken()) {
				System.out.println(tickets.get(i));
				System.out.println();
			}
		}
	}

	/**
	 *	Check the marathon ticket today
	 */
	public void marathonTicket(){
		LinkedList list = new LinkedList();  //List of all marathon movies
		Vector index = new Vector(1000);  //Contains IDs of all the movie in the marathon
		list = checkMarathonTicket(list, index, 0);
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
			System.out.println();
		}
	}

	@Override
	public void printWaitingList(String movieTitle, int screeningTime){
		int index = getScreeningMovieByTitleAndTime(movieTitle, screeningTime);
		if(index >= 0) {
			MovieScreening printScreening = getScreeningMovieById(index);	//For simplifying
			LinkedListQueue printList = (LinkedListQueue) waitingList.get(printScreening.getScreeningId());

			//If nobody is on the waiting list, then printing fails
			if (printList.isEmpty()) {
				System.out.println("Nobody is waiting for this screening movie!");
			} else {
				printList.print();
			}
		}

		//If the screening does not exist, then printing fails
		else{
			System.out.println("This screening movie does not exist, or does not have a waiting list!");
		}
	}

	public String toString(){
		String s = "***************TODAY'S PROGRAM***************" + '\n';
		for(int i = 0; i < movieScreenings.size(); i++){
			if(((LinkedList)movieScreenings.get(i)).size() > 1) {
				LinkedList tempMovie = (LinkedList) movieScreenings.get(i);
				s += ((Movie) tempMovie.getFirst()).getTitle() + ":" + '\n';
				for(int j = 1; j < tempMovie.size(); j++){
					s += "		Starts from: " + ((MovieScreening)tempMovie.get(j)).getScreeningTime() + "h, Room: " + ((MovieScreening)tempMovie.get(j)).getRoom().getNumber() + '\n';
				}
			}
		}

		s += '\n' + "***************TICKET***************" + '\n';
		for(int i = 0; i < ticketId; i++){
			if(getTicketByID(i + 1) != null) {
				Ticket thisTicket = getTicketByID(i + 1);
				s += thisTicket.getMovieScreening().getTitle() + " (" + thisTicket.getMovieScreening().getLength() + "h)" + '\n' +
						"Playing at: " + thisTicket.getMovieScreening().getScreeningTime() + "h" + '\n' +
						"Room: " + thisTicket.getMovieScreening().getRoom().getNumber() + '\n' +
						"Row: " + thisTicket.getRow() + "	Seat: " + thisTicket.getSeat() + '\n' +
						"ID: " + thisTicket.getTicketId() + '\n' + '\n';
			}
		}
		s += "*************************************";

		return s;
	}
}