import city.City;
import city.Car;
import city.TestCar;

import static util.CityUtil.*;

private static final int SCREEN_W = 1800;
private static final int SCREEN_H = 900;

City city;
Car car;

boolean update = true;

public void setup() {
  city = new City(36 * 2, 36, 25);
  city.generate();
  
  car = new TestCar(7, 6, Direction.RIGHT, null);
  city.cars.add(car);
  
  frameRate(60);
}

public void draw() {
  if (update) {
    background(0);
    city.update(this);
  }
}

public void keyPressed() {
  update = true;
}

public void settings(){
  size(SCREEN_W, SCREEN_H, JAVA2D);
}

public static void main(String[] args) {
  PApplet.main("Main");
}
