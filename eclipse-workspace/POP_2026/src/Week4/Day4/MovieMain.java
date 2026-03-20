package Week4.Day4;

class MovieTicket {
    int ticketID;
    String movieName;
    int seatNumber;
    boolean isBooked;

    MovieTicket(int ticketID, String movieName, int seatNumber) {
        this.ticketID = ticketID;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    void bookTicket() {
        if (!isBooked) {
            isBooked = true;
            System.out.println("Ticket booked successfully.");
        } else {
            System.out.println("Ticket already booked.");
        }
    }

    void cancelTicket() {
        if (isBooked) {
            isBooked = false;
            System.out.println("Ticket cancelled successfully.");
        } else {
            System.out.println("Ticket is not booked yet.");
        }
    }

    void displayTicketDetails() {
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Movie Name: " + movieName);
        System.out.println("Seat Number: " + seatNumber);
        System.out.println("Booked: " + isBooked);
    }
}

public class MovieMain {
    public static void main(String[] args) {
        MovieTicket t1 = new MovieTicket(1, "Inception", 12);
        t1.displayTicketDetails();

        t1.bookTicket();
        t1.cancelTicket();
    }
}
