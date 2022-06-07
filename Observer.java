import java.util.ArrayList;

//The classes and/or objects participating in this pattern are:
//	1. Subject  (Device)
//		. Key reader application observes the keyboard device and mouse cursor position reader application observers Mouse device.
//	    . Provides an interface for attaching and detaching Observer objects which are Keyboard and Mouse.
//	2. ConcreteSubject  (Mouse and Keyboard)
//	    . Stores state of interest to ConcreteObserver sends a notification to its observers when its state changes.
//	    . The keyboard device notifies the Key reader application and The mouse device notifies the mouse cursor position reader application.
//	3. Observer  (Application)
//   . Defines an updating interface for objects that should be notified
//     of changes in a subject.
//	4. ConcreteObserver  (Application)
//   . Maintains a reference to a ConcreteSubject object
//   . Stores state that should stay consistent with the subject's
//   . Implements the Observer updating interface to keep its state
//     consistent with the subject's

//subject ==> device
abstract class Device {
    protected String _name;
    protected double _mouseCursorXCoordinate;  // Internal Subject state for mouse device
    protected double _mouseCursorYCoordinate;  // Internal Subject state for mouse device
    protected char _pressedKey; // Internal Subject state for keyboard device


    protected ArrayList<Application> applications = new ArrayList<Application>();


    public Device(String name, double xCoordinate, double yCoordinate) {
        _name = name;
        _mouseCursorXCoordinate = xCoordinate;
        _mouseCursorYCoordinate = yCoordinate;

    }

    public Device(String name, char pressedKey) {
        _name = name;
        _pressedKey = pressedKey;
    }

    //Register the Observers to the related devices
    //Mouse attaches Mouse cursor position reader application.
    //Keyboard attaches key reader application.
    public void Attach(Application application) {
        applications.add(application);
    }

    //Unregister from the list of Observers.
    public void Detach(Application application) {
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getName().equals(application.getName())) {
                applications.remove(i);
                return;
            }
        }
    }

    //Notify the Observers which are Key reader and Mouse cursor position reader application.
    public void Notify() {

        for (int i = 0; i < applications.size(); i++) {
            applications.get(i).Update(this);
        }
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_xCoordinate() {
        return _mouseCursorXCoordinate;
    }

    public void set_xCoordinate(double _xCoordinate) {
        this._mouseCursorXCoordinate = _xCoordinate;
    }

    public double get_yCoordinate() {
        return _mouseCursorYCoordinate;
    }

    public void set_yCoordinate(double _yCoordinate) {
        this._mouseCursorYCoordinate = _yCoordinate;
    }

    public char get_pressedKey() {
        return _pressedKey;
    }

    public void set_pressedKey(char _pressedKey) {
        this._pressedKey = _pressedKey;
    }

}
//'ConcreteSubject' ==> Mouse
// Mouse is sending its changed cursor coordinates information to notify the observer which is mouse cursor position reader.

class Mouse extends Device {

    //Constructor
    public Mouse(String name, double xCoordinate, double yCoordinate) {
        super(name, xCoordinate, yCoordinate);
    }

    public double get_xCoordinate() {
        return _mouseCursorXCoordinate;
    }

    public void set_xCoordinate(double _xCoordinate) {
        this._mouseCursorXCoordinate = _xCoordinate;

        // Whenever a change happens to _xCoordinate,
        // notify the mouse cursor position reader observer.
        Notify();
    }

    public double get_yCoordinate() {
        return _mouseCursorYCoordinate;
    }

    public void set_yCoordinate(double _yCoordinate) {
        this._mouseCursorYCoordinate = _yCoordinate;

        // Whenever a change happens to _yCoordinate,
        // notify the mouse cursor position reader observer.
        Notify();
    }
}

//'ConcreteSubject' ==> Mouse and Keyboard
// Keyboard is sending its pressed key interruption to notify the observer which is key reader application.

class Keyboard extends Device {
    public Keyboard(String name, char pressedKey) {
        super(name, pressedKey);
    }

    public char get_pressedKey() {
        return _pressedKey;
    }

    public void set_pressedKey(char pressedKey) {
        this._pressedKey = pressedKey;

        // Whenever a change happens to _pressedKey, notify Key reader observer.
        Notify();
    }


}


//'Observer'  ==> Abstract Observer.
public interface Observer {
    void Update(Device device);
}

//'ConcreteObserver' ==> Application
class Application implements Observer {
    private Device _device;

    private String _application_name;
    private String _device_name;
    private double _mouse_device_x_interruption;                // Internal Observer state
    private double _mouse_device_y_interruption;                // Internal Observer state
    private char _keyboard_device_pressedKey_interruption;      // Internal Observer state

    // Constructor
    public Application(String name) {
        _application_name = name;
    }

    public void Update(Device device) {
        _device = device;  // Reference to Subject

        _mouse_device_x_interruption = device.get_xCoordinate();
        _mouse_device_y_interruption = device.get_yCoordinate();
        _keyboard_device_pressedKey_interruption = device.get_pressedKey();
        _device_name = device.get_name();

        //Applications update messages.
        if (_device_name.equals("Mouse")) {
            System.out.println("Notified " + _application_name + "! " + _device_name +
                    "'s cursor coordinates " + "change to (" + _mouse_device_x_interruption + "px, " + _mouse_device_y_interruption + "px)");
        } else if (_device_name.equals("Keyboard")) {
            System.out.println("Notified " + _application_name + "! " + _device_name +
                    "'s pressed key " + "change to " + _keyboard_device_pressedKey_interruption);
        }

    }


    public void setDevice(Device value) {
        _device = value;
    }


    public String getName() {
        return _application_name;
    }
}

//MainApp test application
class ObserverPattern {
    public static void main(String[] args) {
        // Create applications
        Application mouseCursorPositionReader = new Application("MouseCursorPositionReader");
        Application keyReader = new Application("Key Reader");

        // Create mouse and keyboard devices and attach applications
        Mouse mouse = new Mouse("Mouse", 120.00, 23);
        Keyboard keyboard = new Keyboard("Keyboard", 'a');
        mouseCursorPositionReader.setDevice(keyboard);
        keyReader.setDevice(keyboard);
        mouse.Attach(mouseCursorPositionReader);
        keyboard.Attach(keyReader);

        // Change mouse cursor coordinates and press keyboard's keys , which notifies MouseCursorPositionReader and Key Reader applications
        mouse.set_xCoordinate(120.10);
        keyboard.set_pressedKey('b');
        mouse.set_yCoordinate(90.20);
        mouse.set_xCoordinate(420.50);
        keyboard.set_pressedKey('t');
        mouse.set_yCoordinate(340.75);
        keyboard.set_pressedKey('t');
        keyboard.set_pressedKey('o');
        keyboard.set_pressedKey('m');

    }
}