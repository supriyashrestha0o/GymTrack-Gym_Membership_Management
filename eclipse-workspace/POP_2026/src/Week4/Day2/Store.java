package Week4.Day2;

public class Store {
    private int storedSum;

    // saving in memory
    public void save(int sum) {
        this.storedSum = sum;
    }

    // retrieving result
    public int getStoredSum() {
        return storedSum;
    }
}

