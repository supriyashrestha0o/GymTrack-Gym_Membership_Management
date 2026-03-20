package Week7.Day1;

abstract class Animal {

    void eat() {
        System.out.println("Bird is eating");
    }

    void sleep() {
        System.out.println("Bird is sleeping");
    }

    abstract void makeSound();
}

interface Flyable {
    void fly();
}

class Bird extends Animal implements Flyable {

    public void makeSound() {
        System.out.println("Bird is chirping");
    }

    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Fly {

    public static void main(String[] args) {

        Bird b = new Bird();

        b.eat();
        b.sleep();
        b.makeSound();
        b.fly();
    }
}