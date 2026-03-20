package Week5.Day2;
class Animal {


    String name;

public void makeSound() {
        System.out.println("Animal makes a sound.");
    }
}

class Dog extends Animal {

    public void bark() {
        System.out.println("Dog barks.");
    }
}