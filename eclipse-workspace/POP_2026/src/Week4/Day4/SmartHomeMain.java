package Week4.Day4;

class SmartDevice {
    int deviceID;
    String deviceName;
    boolean status;
    int batteryLevel;

    SmartDevice(int deviceID, String deviceName, int batteryLevel) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.batteryLevel = batteryLevel;
        this.status = false;
    }

    void turnOn() {
        status = true;
        System.out.println(deviceName + " is ON");
    }

    void turnOff() {
        status = false;
        System.out.println(deviceName + " is OFF");
    }

    void checkBattery() {
        System.out.println(deviceName + " Battery Level: " + batteryLevel + "%");
    }
}

public class SmartHomeMain {
    public static void main(String[] args) {
        SmartDevice d1 = new SmartDevice(101, "Smart Light", 80);
        SmartDevice d2 = new SmartDevice(102, "Smart Fan", 60);

        d1.turnOn();
        d1.checkBattery();

        d2.turnOn();
        d2.turnOff();
        d2.checkBattery();
    }
}

