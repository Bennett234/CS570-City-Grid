import city.Car;
import city.City;
import city.TestCar;
import processing.core.PApplet;

@SuppressWarnings("WeakerAccess")
public class Main extends PApplet {
    private static final int SCREEN_W = 900;
    private static final int SCREEN_H = 900;

    City city;
    Car car;

    public void setup() {
        city = new City(36, 36, 25);
        city.generate();

        car = new TestCar(1, 0, City.Direction.Right);
        city.cars.add(car);

        frameRate(8);
    }

    public void draw() {
        background(0);
        city.draw(this);
        city.step();
    }

    public void settings(){
        size(SCREEN_W, SCREEN_H, JAVA2D);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
