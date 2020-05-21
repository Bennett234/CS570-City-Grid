package city;

import actions.CrossIntersection;

import static util.CityUtil.*;

public class TestCar extends Car {
    public TestCar(int x, int y, Direction dirFacing) {
        super(x, y, dirFacing);
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
                do to = from.randomExcludingThis();
                while (!intersection.isAvailable(to));

                // Turn on intersection
                runActionNext(new CrossIntersection(this, intersection, to, from));
            }
        }
    }

    public void followRoad(Direction roadDirection, City city) {
        move(roadDirection);

        if (city.isOccupied(x, y, this)) move(roadDirection.opposite());

        dirFacing = roadDirection;
    }

    public void move(Direction direction) {
        x += direction.dx();
        y += direction.dy();
    }
}
