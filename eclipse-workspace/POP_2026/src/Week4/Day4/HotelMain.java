package Week4.Day4;

class HotelRoom {
    int roomNumber;
    String roomType;
    boolean isAvailable;
    double pricePerNight;

    HotelRoom(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    void bookRoom() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Room booked successfully.");
        } else {
            System.out.println("Room is not available.");
        }
    }

    void checkOut() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Check-out completed. Room is now available.");
        } else {
            System.out.println("Room was already available.");
        }
    }

    void displayRoomDetails() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price per Night: " + pricePerNight);
        System.out.println("Available: " + isAvailable);
    }
}

public class HotelMain {
    public static void main(String[] args) {
        HotelRoom r1 = new HotelRoom(101, "Deluxe", 3500);

        r1.displayRoomDetails();
        r1.bookRoom();
        r1.checkOut();
        r1.displayRoomDetails();
    }
}

