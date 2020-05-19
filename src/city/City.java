package city;

import processing.core.PApplet;

import java.util.ArrayList;

public class City {
    public CityGrid grid;
    public ArrayList<Car> cars;

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    public City(int grid_w, int grid_h, int grid_size) {
        this.grid = new CityGrid(grid_w, grid_h, grid_size);
        this.cars = new ArrayList<>();
    }

    public void generate() {
        this.grid.generate();
    }

    public void step() {
        for (Car car : cars) {
            car.run(this);
        }
    }

    public void draw(PApplet applet) {
        this.grid.draw(applet);

        for (Car car : cars) {
            switch (car.dirFacing) {
                case Up:
                    applet.fill(255, 0, 255);
                    applet.rect(car.x * grid.size, car.y * grid.size, grid.size, grid.size);
                    break;
                case Down:
                    applet.fill(0, 255, 0);
                    applet.rect(car.x * grid.size, car.y * grid.size, grid.size, grid.size);
                    break;
                case Left:
                    applet.fill(0, 0, 255);
                    applet.rect(car.x * grid.size, car.y * grid.size, grid.size, grid.size);
                    break;
                case Right:
                    applet.fill(0, 255, 255);
                    applet.rect(car.x * grid.size, car.y * grid.size, grid.size, grid.size);
                    break;
            }
        }
    }
}
