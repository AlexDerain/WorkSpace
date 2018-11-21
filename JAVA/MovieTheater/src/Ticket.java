/**
 * 
 * @author AlexDerain / DING Rui
 *
 *	Contains a actual movie screening and an ID
 *
 *  Contains the actual row number and seat number
 *	When a ticket is being used, it means someone holds it, and not gonna cancel it
 *	When a ticket is cancelled, the taken is false, means it is not being used, until it is resold or thrown away
 */
public class Ticket{

	private MovieScreening movieScreening; //Each ticket represents one exact movie screening
	private int ticketId; //Each ticket has an ID
	private boolean popcorn; //Each ticket can get free popcorn only once
	private boolean taken; //To check if this ticket is sold and using or not
	private int row;  //The row number shown on ticket, always 1 bigger than that in movie screening
	private int seat;  //The seat number shown on ticket, always 1 bigger than that in movie screening

	public Ticket(MovieScreening movieScreening, int ticketId, int row, int seat){
		this.movieScreening = movieScreening;
		this.ticketId = ticketId;
		this.popcorn = true;
		this.taken = true;
		this.row = row;
		this.seat = seat;
	}
	
	public MovieScreening getMovieScreening() {
		return movieScreening;
	}

	public void setMovieScreening(MovieScreening movieScreening) {
		this.movieScreening = movieScreening;
	}
	
	public int getTicketId() {
		return ticketId;
	}

	public int getRow(){
		return row;
	}

	public int getSeat(){
		return seat;
	}

	/*
	For buyTicket(title, time) method
	Receiving default row number and seat number
	And this ticket is being used
	 */
	public void ticketSold(){
		int[] arr= movieScreening.ticketSold();
		row = arr[0];
		seat = arr[1];
		taken = true;
	}

	/*
	 When a ticket is canceled, a seat is vacant again
	 And this ticket is not taken (not being used)
	 */
	public void ticketCanceled(){
		movieScreening.ticketCanceled(row, seat);
		taken = false;
	}
	
	/*
	 When a ticket get a free popcorn, it cannot get this twice
	 */
	public void getFreePopcorn(){
		if(popcorn) {
			popcorn = false;
			System.out.println("Enjoy your popcorn!");
		}
		else{
			System.out.println("This ticket has already been used!");
		}
	}

	/*
	Only if a ticket is being used, one can get free popcorn with it
	 */
	public boolean isTaken(){
		return taken;
	}

	public String toString(){
		return "Title: " +  movieScreening.getTitle() + '\n' +
				"Time of Screening: " + movieScreening.getScreeningTime() + '\n' +
				"Length: " + movieScreening.getLength() + '\n' +
				"Room: " + movieScreening.getRoom().getNumber() + '\n' +
				"Row: " + row + '\n' +
				"Seat: " + seat;
	}
}
