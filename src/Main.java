import city.Car;
import city.City;
import city.TestCar;
import processing.core.PApplet;
import util.CityUtil.Direction;

@SuppressWarnings("WeakerAccess")
public class Main extends PApplet {
    private static final int SCREEN_W = 1800;
    private static final int SCREEN_H = 900;

    City city;
    Car car;

    boolean update = true;

    public void setup() {
        city = new City(36 * 2, 36, 25);
        city.generate();

        for (int i = 7; i < city.grid.w - 6; i += 6) {
            for (int j = 6; j < city.grid.h - 6; j += 6) {
                for (int k = 0; k < 4; k++) {
                    car = new TestCar(i, j, Direction.RIGHT);
                    city.cars.add(car);
                }
            }
        }

        frameRate(60);
    }

    public void draw() {
        if (update) {
            background(0);
            city.update(this);
//            update = false;
        }
    }

    @Override
    public void keyPressed() {
        update = true;
    }

    public void settings(){
        size(SCREEN_W, SCREEN_H, JAVA2D);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
