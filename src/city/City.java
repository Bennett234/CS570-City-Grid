package city;

import processing.core.PApplet;

import java.util.ArrayList;

public class City {
    private static final int ANIMATION_FRAMES = 5;
    private static final int FRAMES_PER_LIGHT_CHANGE = 10;

    public CityGrid grid;
    public ArrayList<Car> cars;

    public City(int grid_w, int grid_h, int grid_size) {
        this.grid = new CityGrid(grid_w, grid_h, grid_size);
        this.cars = new ArrayList<>();
    }

    public void generate() {
        this.grid.generate();
    }

    public void update(PApplet applet) {
        if (applet.frameCount % (ANIMATION_FRAMES * FRAMES_PER_LIGHT_CHANGE) == 0) {
            for (Intersection intersection : grid.intersections.values()) {
                intersection.nextState();
            }
        }

        if (applet.frameCount % ANIMATION_FRAMES == 0) step();
        draw(applet, (float) (applet.frameCount % ANIMATION_FRAMES) / ANIMATION_FRAMES);
    }

    public void step() {
        for (Car car : cars) {
            car.run(this);
        }
    }

    public boolean isOccupied(int x, int y, Car asking) {
        for (Car car : cars) {
            if (car != asking && car.x == x && car.y == y) return true;
        }

        return false;
    }

    public void draw(PApplet applet, float animationState) {
        this.grid.draw(applet);

        for (Car car : cars) {
            car.draw(applet, this, animationState);
        }
    }
}
