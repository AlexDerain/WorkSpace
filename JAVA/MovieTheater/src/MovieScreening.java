/**
 * 
 * @author AlexDerain / DING Rui
 *
 *	Contains screening time, a movie and screening room
 *
 * Most important class except Cinema
 * Each screening movie contains a room matrix represents the taken state of seats
 * Connecting ticket and movie and room
 */
public class MovieScreening{

	private int screeningTime; //Time of screening of the movie
	private Movie movie; //Movie on screening
	private Room room; //Screening room
	private int vacantSeat; //Number of vacant seats of a screening movie
	private int screeningId; //Each screening movie has a unique ID, for identifying waiting list
	private int rowNumber;  //Default row pointer, for buyTicket(title, time) method
	private int seatNumber;  //Default seat pointer, for buyTicket(title, time) method
	private int[][] theRoom;  //Room matrix, 0 for vacant seat, 1 for taken seat
	
	public MovieScreening(Movie movie, int screeningTime, Room room){
		this.movie = movie;
		this.screeningTime = screeningTime;
		this.room = room;
		this.vacantSeat = room.getTotalSeats();
		this.screeningId = -1;
		this.rowNumber = 0;
		this.seatNumber = 0;

		//initialize the room as a matrix
		//0 for vacant
		//1 for taken
		theRoom = new int[room.getRow()][room.getSeats()];
		for(int i = 0; i < room.getRow(); i++){
			for(int j = 0; j < room.getSeats(); j++){
				theRoom[i][j] = 0;
			}
		}
	}
	
	public Movie getMovie(){
		return movie;
	}
	
	public int getScreeningTime() {
		return screeningTime;
	}

	public void setScreeningTime(int screeningTime) {
		this.screeningTime = screeningTime;
	}

	public void setScreeningId(int id){
		this.screeningId = id;
	}

	public String getTitle(){
		return movie.getTitle();
	}
	
	public int getLength(){
		return movie.getLength();
	}
	
	public Room getRoom(){
		return room;
	}

	/*
	Return current default row pointer
	For the numbers on tickets start from 1, so +1
	 */
	public int getRowNumber(){
		return rowNumber + 1;
	}

	/*
	Return current default seat pointer
	For the numbers on tickets start from 1, so +1
	 */
	public int getSeatNumber(){
		return seatNumber + 1;
	}
	
	public int getVacantSeat(){
		return vacantSeat;
	}
	
	public int getScreeningId(){
		return screeningId;
	}

	/*
	For buyTicket(title, time) method
	Return an array, contains row number and seat number shown on ticket
	 */
	public int[] ticketSold(){
		boolean flag = false;

		//Scanning for vacant seat
		//Always starts from the 0,0
		//In case a ticket is cancelled, and the seat before pointer is vacant
		for(int i = 0; i < room.getRow(); i++){
			for(int j = 0; j < room.getSeats(); j++){
				if(theRoom[i][j] == 0){
					theRoom[i][j] = 1;
					rowNumber = i;
					seatNumber = j;
					vacantSeat--;
					flag = true;
					break;
				}
			}
			if(flag){
				break;
			}
		}
		int[] arr = {rowNumber + 1, seatNumber + 1};
		return arr;
	}

	/*
	For buyTicket(title, time. row, seat) method
	If the required seat is vacant, then sold this seat
	Otherwise failed
	 */
	public int ticketSoldBySeat(int row, int seat) {
		if(theRoom[row - 1][seat - 1] == 1){
			return -1;
		}
		else{
			theRoom[row - 1][seat - 1] = 1;
			vacantSeat--;
			return 0;
		}
	}

	/*
	The seat on the cancelled ticket becomes vacant again
	 */
	public void ticketCanceled(int row, int seat){
		theRoom[row - 1][seat - 1] = 0;
		vacantSeat++;
	}

	/*
	Print the room matrix
	0 for vacant, 1 for taken
	The seat on the top left is 1st row, 1st seat
	 */
	public void printSeats(){
		System.out.println("Free seats: " + vacantSeat);
		for(int i = 0; i < room.getRow(); i++){
			for(int j = 0; j < room.getSeats(); j++){
				System.out.print(theRoom[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public String toString(){
		return "Title: " + movie.getTitle() + '\n' +
				"Screening Time: " + screeningTime + " (" + getLength() + "h)" + '\n' +
				"Room: " + room.getNumber();
	}
}
