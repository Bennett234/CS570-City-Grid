package city;

import actions.Action;
import city.City;

public abstract class Car {
    public int x, y;
    public City.Direction dirFacing;

    private Action currentAction;
    private boolean actionIsRunning;

    public Car(int x, int y, City.Direction dirFacing) {
        this.x = x;
        this.y = y;
        this.dirFacing = dirFacing;
    }

    public void runActionNext(Action action) {
        this.currentAction = action;
        this.actionIsRunning = true;
    }

    public void run(City city) {
        if (actionIsRunning) actionIsRunning = !currentAction.run(city);
        else step(city);
    }

    public abstract void step(City city);

//    public void step(city.City city) {
//        boolean shouldTurn = new Random().nextBoolean();
//
//        switch (city.grid.grid[x][y]) {
//            case EMPTY:
//                break;
//            case BUILDING:
//                break;
//            case SIDEWALK:
//                break;
//
//            case INTERSECTION_TOP_LEFT:
//                switch (dirFacing) {
//                    case Down:
//                        if (x == city.grid.w - 1 || y == city.grid.h - 1) shouldTurn = true;
//                        if (shouldTurn) {
//                            this.x -= 1;
//                            this.dirFacing = city.City.Direction.Left;
//                        } else {
//                            this.y += 1;
//                        }
//                        break;
//                    case Left:
//                        this.x -= 1;
//                        break;
//                }
//                break;
//            case INTERSECTION_TOP_RIGHT:
//                switch (dirFacing) {
//                    case Up:
//                        y -= 1;
//                        break;
//                    case Left:
//                        if (x == 0 || y == city.grid.h - 1) shouldTurn = true;
//                        if (shouldTurn) {
//                            y -= 1;
//                            dirFacing = city.City.Direction.Up;
//                        } else {
//                            x -= 1;
//                        }
//                        break;
//                }
//                break;
//            case INTERSECTION_BOTTOM_LEFT:
//                switch (dirFacing) {
//                    case Down:
//                        y += 1;
//                        break;
//                    case Right:
//                        if (x == city.grid.w - 1 || y == 0) shouldTurn = true;
//                        if (shouldTurn) {
//                            dirFacing = city.City.Direction.Down;
//                            y += 1;
//                        } else {
//                            x += 1;
//                        }
//                        break;
//                }
//                break;
//            case INTERSECTION_BOTTOM_RIGHT:
//                switch (dirFacing) {
//                    case Up:
//                        if (x == 0 || y == 0) shouldTurn = true;
//                        if (shouldTurn) {
//                            this.x += 1;
//                            this.dirFacing = city.City.Direction.Right;
//                        } else {
//                            this.y -= 1;
//                        }
//                        break;
//                    case Right:
//                        x += 1;
//                        break;
//                }
//                break;
//            case ROAD_UP:
//                this.dirFacing = city.City.Direction.Up;
//                this.y -= 1;
//                break;
//            case ROAD_DOWN:
//                this.dirFacing = city.City.Direction.Down;
//                this.y += 1;
//                break;
//            case ROAD_LEFT:
//                this.dirFacing = city.City.Direction.Left;
//                this.x -= 1;
//                break;
//            case ROAD_RIGHT:
//                this.dirFacing = city.City.Direction.Right;
//                this.x += 1;
//                break;
//        }
//
////        turning = shouldTurn && city.grid.isIntersection(x, y);
//    }
}
