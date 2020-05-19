package city;

import java.util.Random;

public class TestCar extends Car {
    public TestCar(int x, int y, City.Direction dirFacing) {
        super(x, y, dirFacing);
    }

    @Override
    public void step(City city) {
        boolean shouldTurn = new Random().nextBoolean();

        switch (city.grid.grid[x][y]) {
            case EMPTY:
            case BUILDING_1:
            case BUILDING_2:
            case SIDEWALK:
                break;

            case INTERSECTION_TOP_LEFT:
                switch (dirFacing) {
                    case Down:
                        if (x == city.grid.w - 1 || y == city.grid.h - 1) shouldTurn = true;
                        if (shouldTurn) {
                            this.x -= 1;
                            this.dirFacing = City.Direction.Left;
                        } else {
                            this.y += 1;
                        }
                        break;
                    case Left:
                        this.x -= 1;
                        break;
                }
                break;
            case INTERSECTION_TOP_RIGHT:
                switch (dirFacing) {
                    case Up:
                        y -= 1;
                        break;
                    case Left:
                        if (x == 0 || y == city.grid.h - 1) shouldTurn = true;
                        if (shouldTurn) {
                            y -= 1;
                            dirFacing = City.Direction.Up;
                        } else {
                            x -= 1;
                        }
                        break;
                }
                break;
            case INTERSECTION_BOTTOM_LEFT:
                switch (dirFacing) {
                    case Down:
                        y += 1;
                        break;
                    case Right:
                        if (x == city.grid.w - 1 || y == 0) shouldTurn = true;
                        if (shouldTurn) {
                            dirFacing = City.Direction.Down;
                            y += 1;
                        } else {
                            x += 1;
                        }
                        break;
                }
                break;
            case INTERSECTION_BOTTOM_RIGHT:
                switch (dirFacing) {
                    case Up:
                        if (x == 0 || y == 0) shouldTurn = true;
                        if (shouldTurn) {
                            this.x += 1;
                            this.dirFacing = City.Direction.Right;
                        } else {
                            this.y -= 1;
                        }
                        break;
                    case Right:
                        x += 1;
                        break;
                }
                break;
            case ROAD_UP:
                this.dirFacing = City.Direction.Up;
                this.y -= 1;
                break;
            case ROAD_DOWN:
                this.dirFacing = City.Direction.Down;
                this.y += 1;
                break;
            case ROAD_LEFT:
                this.dirFacing = City.Direction.Left;
                this.x -= 1;
                break;
            case ROAD_RIGHT:
                this.dirFacing = City.Direction.Right;
                this.x += 1;
                break;
        }
    }
}
