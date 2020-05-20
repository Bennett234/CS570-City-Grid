package city;

import actions.Action;
import javafx.util.Pair;
import processing.core.PApplet;
import util.AnimationUtil;

import static util.CityUtil.*;

public abstract class Car {
    public int x, y;
    public Direction dirFacing;

    private int lastX, lastY;
    private Direction lastDirFacing;

    private Action currentAction;
    private boolean actionIsRunning;

    public Car(int x, int y, Direction dirFacing) {
        this.x = x;
        this.y = y;
        this.dirFacing = dirFacing;
    }

    public void runActionNext(Action action) {
        this.currentAction = action;
        this.actionIsRunning = true;
    }

    public void run(City city) {
        lastDirFacing = dirFacing;
        lastX = x;
        lastY = y;

        if (actionIsRunning) actionIsRunning = !currentAction.run(city);
        else step(city);
    }

    public void draw(PApplet applet, City city, float animationState) {
        Pair<Float, Float> pos = AnimationUtil.linearApprox(lastX, lastY, x, y, animationState);

        float x = pos.getKey();
        float y = pos.getValue();

        switch (dirFacing) {
            case UP:
                applet.fill(255, 0, 255);
                applet.rect((x + 0.25f) * city.grid.size, (y + 0.25f) * city.grid.size, city.grid.size * 0.5f, city.grid.size * 0.5f);
                break;
            case DOWN:
                applet.fill(0, 255, 0);
                applet.rect((x + 0.25f) * city.grid.size, (y + 0.25f) * city.grid.size, city.grid.size * 0.5f, city.grid.size * 0.5f);
                break;
            case LEFT:
                applet.fill(0, 0, 255);
                applet.rect((x + 0.25f) * city.grid.size, (y + 0.25f) * city.grid.size, city.grid.size * 0.5f, city.grid.size * 0.5f);
                break;
            case RIGHT:
                applet.fill(0, 255, 255);
                applet.rect((x + 0.25f) * city.grid.size, (y + 0.25f) * city.grid.size, city.grid.size * 0.5f, city.grid.size * 0.5f);
                break;
        }
    }

    public abstract void step(City city);

//    public void step(city.City city) {
//        boolean shouldTurn = new Random().nextBoolean();
//
//        switch (city.city.grid.grid[x][y]) {
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
//                        if (x == city.city.grid.w - 1 || y == city.city.grid.h - 1) shouldTurn = true;
//                        if (shouldTurn) {
//                            this.x -= 1;
//                            this.dirFacing = city.Direction.Left;
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
//                        if (x == 0 || y == city.city.grid.h - 1) shouldTurn = true;
//                        if (shouldTurn) {
//                            y -= 1;
//                            dirFacing = city.Direction.Up;
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
//                        if (x == city.city.grid.w - 1 || y == 0) shouldTurn = true;
//                        if (shouldTurn) {
//                            dirFacing = city.Direction.Down;
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
//                            this.dirFacing = city.Direction.Right;
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
//                this.dirFacing = city.Direction.Up;
//                this.y -= 1;
//                break;
//            case ROAD_DOWN:
//                this.dirFacing = city.Direction.Down;
//                this.y += 1;
//                break;
//            case ROAD_LEFT:
//                this.dirFacing = city.Direction.Left;
//                this.x -= 1;
//                break;
//            case ROAD_RIGHT:
//                this.dirFacing = city.Direction.Right;
//                this.x += 1;
//                break;
//        }
//
////        turning = shouldTurn && city.city.grid.isIntersection(x, y);
//    }
}
