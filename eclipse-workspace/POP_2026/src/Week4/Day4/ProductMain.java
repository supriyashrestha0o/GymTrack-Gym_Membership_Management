package Week4.Day4;

class Product {
    int productID;
    String name;
    double price;
    int stockQuantity;

    Product(int productID, String name, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    void purchaseProduct(int quantity) {
        if (quantity <= stockQuantity) {
            stockQuantity -= quantity;
            System.out.println("Purchase successful");
        } else {
            System.out.println("Insufficient stock");
        }
    }

    void displayProductDetails() {
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stockQuantity);
    }
}

public class ProductMain {
    public static void main(String[] args) {
        Product p1 = new Product(1, "Laptop", 80000, 10);
        p1.displayProductDetails();

        p1.purchaseProduct(2);
        p1.addStock(5);
        p1.displayProductDetails();
    }
}
