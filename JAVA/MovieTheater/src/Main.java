/**
 * 
 * @author AlexDerain
 * @author Ding Rui
 *
 */
public class Main {
	
	
	
	public static void main(String[] args) {
		
		System.out.println("*************** Test cases for Part One ***************\n");
		
		//Initialize necessary test case
		Movie ironMan_1 = new Movie("Iron Man 1", 2);
		Room roomOne = new Room(1, 10, 10);
		MovieScreening firstScreening = new MovieScreening(ironMan_1, 15, roomOne);
		Ticket ticket = new Ticket(firstScreening, 10, 1, 1);
		System.out.println(ticket.toString());
		System.out.println();
		
		//To see if the ticket and movie screening information change when movie changes
		System.out.println("To see if the ticket and movie screening information change when movie changes");
		ironMan_1.setTitle("Iron Man 2");
		System.out.println(ticket.toString());
		System.out.println();
		
		//To see if ticket information changes when movie screening changes
		System.out.println("To see if ticket information changes when movie screening changes");
		firstScreening.setScreeningTime(20);
		System.out.println(ticket.toString());
		System.out.println();
		
		//To see if movie screening could link to corresponding movie
		System.out.println("To see if movie screening could link to corresponding movie");
		System.out.println(firstScreening.getMovie().toString());
		System.out.println();
		
		//To see if ticket and movie screening information change when room changes
		System.out.println("To see if ticket and movie screening information change when room changes");
		roomOne.setNumber(10);
		System.out.println(ticket.toString());
		System.out.println();
		System.out.println(firstScreening.toString());
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		
		System.out.println("*************** Test cases for Part Two ***************\n");
		
		Cinema cinema = new Cinema();
		
		//Add movies to the movie list
		System.out.println("Add movies to the movie list.");
		
		cinema.addMovie("Iron Man 1", 2);	//succeed
		cinema.addMovie("Iron Man 1", 3);	//fail, Iron Man 1 already exists
		cinema.addMovie("Iron Man 2", 2);	//succeed
		cinema.addMovie("Iron Man 3", 2);	//succeed
		cinema.addMovie("Iron Man 4", 2);	//succeed
		cinema.addMovie("Iron Man 5", 2);	//succeed
		cinema.addMovie("Iron Man 6", 2);	//succeed
		
		System.out.println();
		
		//Add rooms to the room list
		System.out.println("Add rooms to the room list.");
		
		cinema.addRoom(1, 10, 1);		//succeed
		cinema.addRoom(2, 2, 10);		//succeed
		cinema.addRoom(1, 20, 2);		//fail. room 1 already exists
		cinema.addRoom(2, 10, 3);		//fail, room 2 already exists
		
		System.out.println();
		
		//Add screening movie to the screening list
		System.out.println("Add screening movie to the screening list.");
		
		cinema.addScreening("Iron Man 1", 15, 1);	//succeed
		cinema.addScreening("Iron Man 1", 18, 3);	//fail, room 3 does not exist
		cinema.addScreening("Iron Man 7", 11, 1);	//fail, Iron Man 7 does not exist
		cinema.addScreening("Iron Man 2", 15, 2);	//succeed
		cinema.addScreening("Iron Man 2", 16, 2);	//fail, time overlaps
		cinema.addScreening("Iron Man 3", 17, 2);	//succeed
		cinema.addScreening("Iron Man 4", 8, 1);	//succeed
		cinema.addScreening("Iron Man 5", 10, 2);	//succeed
		cinema.addScreening("Iron Man 6", 12, 1);	//succeed
		
		System.out.println();
		
		//Buy tickets
		System.out.println("Buy tickets.");
		
		for(int i = 0; i < 12; i++){
			System.out.println(cinema.buyTicket("Iron Man 1", 15));		//10 succeed, 2 fail
		}
		cinema.buyTicket("a", 11);		//fail, a does not exist

		for(int i = 0; i < 22; i++){
			cinema.buyTicket("Iron Man 2", 15);	//20 succeed, 2 fail
		}

		System.out.println();

		//cinema.printAllTickets();

		System.out.println();
		
		//Cancel tickets
		System.out.println("Cancel tickets.");
		
		cinema.cancelTicket(10);	//succeed
		cinema.cancelTicket(10);	//succeed
		cinema.cancelTicket(10);	//fail, since nobody is on the waiting list, ticket 10 is thrown away
		cinema.cancelTicket(3000);	//fail, ticket 3000 does not exist
		
		System.out.println();
		
		//Print all screenings
		System.out.println("Print all screenings");
		cinema.printAllScreenings();
		System.out.println();
		
		//Print waiting list
		System.out.println("Print waiting list.");
		
		cinema.printWaitingList("Iron Man 2", 15);		//succeed
		cinema.printWaitingList("Iron Man 1", 11);		//fail, this screening does not exist
		cinema.printWaitingList("Iron Man 3", 17);		//fail, nobody is on the list
		
		System.out.println();

		System.out.println("*************** Test cases for Part Three ***************\n");

		cinema.buyTicket("Iron Man 3", 2, 17, 1, 1);		//succeed
		cinema.buyTicket("Iron Man 3", 2, 17, 1, 1);		//fail, this seat has already been taken
		cinema.buyTicket("Iron Man 3", 2, 17, 1, 2);		//succeed
		cinema.buyTicket("Iron Man 3", 17);		//succeed

		System.out.println();

		cinema.printScreening("Iron Man 1", 15);	//succeed
		cinema.printScreening("Iron Man 2", 15);	//succeed
		cinema.printScreening("Iron Man 1", 17);	//fail, this screening does not exist

		System.out.println();

		cinema.getFreePopcorn(1);	//succeed
		cinema.getFreePopcorn(1);	//fail, this ticket has already got popcorn
		cinema.getFreePopcorn(10);	//fail, this ticket does not exist
		cinema.getFreePopcorn(34);	//fail, this ticket does not exist

		System.out.println();

		cinema.marathonTicket();

		System.out.println();

		System.out.println(cinema);
	}

}
