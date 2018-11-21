/**
 * 
 * @author AlexDerain / DING Rui
 * 
 *	Contains room number, number of rows and number of seats in one row
 */
public class Room {

	private int seats; //Number of seats in one row
	private int number; //Room number
	private int row; //Number of rows
	
	public Room(int number, int row, int seats){
		this.seats = seats;
		this.number = number;
		this.row = row;
	}

	public int getSeats() {
		return seats;
	}

	public int getRow(){
		return row;
	}

	public int getTotalSeats(){
		return row * seats;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number){
		this.number = number;
	}

	public String toString(){
		return "Number of Seats: " + seats * row + '\n' +
				"Room Number: " + number;
	}
}
