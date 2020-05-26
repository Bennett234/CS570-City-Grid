package city;

import actions.CrossIntersection;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import static util.CityUtil.*;

public class TestCar extends Car {
    public PImage image;

    public TestCar(int x, int y, Direction dirFacing, PImage image) {
        super(x, y, dirFacing);
        this.image = image;
    }

    @Override
    public void step(City city) {
        if (city.grid.grid[x][y].isRoad()) { // We are on road
            Direction moveDir = city.grid.grid[x][y].direction(); // Get direction road is going

            followRoad(moveDir, city); // Move along road

            if (city.grid.grid[x][y].isIntersection()) { // If we've just moved to an intersection
                Intersection intersection = city.grid.getIntersection(x, y); // Get the intersection

                move(moveDir.opposite()); // Move back to the road

                Direction from = dirFacing.opposite(); // Direction entering intersection from
                Direction to; // Direction going

                // Pick random direction to turn
//                do to = from.randomExcludingThis();
//                while (!intersection.isAvailable(to));

                if (intersection.isAvailable(Direction.RIGHT) && !intersection.isAvailable(Direction.DOWN)) {
                    to = Direction.RIGHT;
                } else if (intersection.isAvailable(Direction.DOWN) && !intersection.isAvailable(Direction.RIGHT)) {
                    to = Direction.DOWN;
                } else {
                    int timeRight = intersection.timeUntilAvailable(from, Direction.RIGHT);
                    int timeDown = intersection.timeUntilAvailable(from, Direction.DOWN);

                    if (timeRight < timeDown) {
                        to = Direction.RIGHT;
                    } else {
                        to = Direction.DOWN;
                    }
                }

                // Turn on intersection
                runActionNext(new CrossIntersection(this, intersection, to, from));
            }
        }
    }

    @Override
    public void drawCar(PApplet applet, float x, float y, float w, float h) {
        applet.fill(0, 255, 255);
        applet.rect(x, y, w, h);
    }

    public void followRoad(Direction roadDirection, City city) {
        if (!city.isOccupied(x + roadDirection.dx(), y + roadDirection.dy(), this)) {
            move(roadDirection);
        }

        dirFacing = roadDirection;
    }

    public void move(Direction direction) {
        x += direction.dx();
        y += direction.dy();
    }
}
