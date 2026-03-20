package Week4.Day1;

class stuuu {

    int id;

    // Parent Parameterized Constructor
    stuuu(int id) {
        this.id = id;
    }

    void role() {
        System.out.println("Student ID: " + id);
    }
}

class CollegeStuuu extends stuuu {
    String major;

    // Child Parameterized Constructor
    CollegeStuuu(int id, String major) {
        super(id); // Pass 'id' to the Student class
        this.major = major;
    }

 
    void role() {
        super.role(); // Calls the Parent's role() to print the ID
        System.out.println("Major: " + major);
        System.out.println("Status: I am a college student.");
    }
}