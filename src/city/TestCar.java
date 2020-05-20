package city;

import actions.CrossIntersection;

import static util.CityUtil.*;

public class TestCar extends Car {
    public TestCar(int x, int y, Direction dirFacing) {
        super(x, y, dirFacing);
    }

    @Override
    public void step(City city) {
        // System.out.println("BEGIN STEP (" + x + ", " + y + ")");

        if (city.grid.grid[x][y].isRoad()) {
            Direction moveDir = city.grid.grid[x][y].direction();

            followRoad(moveDir, city);

            if (city.grid.grid[x][y].isIntersection()) {
                // System.out.println(" Intersection Detected");

                Intersection intersection = city.grid.getIntersection(x, y);

                move(moveDir.opposite());

                Direction from = dirFacing.opposite();
                Direction to;

                do to = from.randomExcludingThis();
                while (!intersection.isAvailable(to));

                // System.out.println(" Moving From " + to + " to " + from);

                runActionNext(new CrossIntersection(this, intersection, to, from));
            } else {
                // System.out.println(" Road Followed (" + moveDir + ")");
            }
        } else {
            // System.out.println(" Off Road (" + x + ", " + y + ")");
        }

        // System.out.println("END STEP (" + x + ", " + y + ")");
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
