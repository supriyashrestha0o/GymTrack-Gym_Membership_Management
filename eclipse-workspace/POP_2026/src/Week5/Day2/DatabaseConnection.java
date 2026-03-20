package Week5.Day2;

class DatabaseConnection {
 static {
     System.out.println("Database connected successfully.");
 }
}

class Main { 
 public static void main(String[] args) {
     DatabaseConnection obj1 = new DatabaseConnection();
     DatabaseConnection obj2 = new DatabaseConnection();
 }
}