package Week5.Day2;

public class Shape {

    public void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {

    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }

    
}