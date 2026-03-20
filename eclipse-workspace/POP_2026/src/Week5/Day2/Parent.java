package Week5.Day2;

public class Parent {

    static void showMessage() {
        System.out.println("Message from Parent");
    }
}

class Child extends Parent {

    static void showMessage() {
        System.out.println("Message from Child");
    }

}