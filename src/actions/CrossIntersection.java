package actions;

import city.Car;
import city.City;
import city.Intersection;

import static util.CityUtil.*;

public class CrossIntersection extends Action {
    private Car car;
    private Intersection intersection;
    private Direction to;
    private Direction from;

    private boolean crossing = false;
    private int crossingStep = 0;

    public CrossIntersection(Car car, Intersection intersection, Direction to) {
        this(car, intersection, to, car.dirFacing.opposite());
    }

    public CrossIntersection(Car car, Intersection intersection, Direction to, Direction from) {
        assert to != from;
        assert intersection.isAvailable(to);

        this.car = car;
        this.intersection = intersection;
        this.to = to;
        this.from = from;
    }

    @Override
    public boolean run(City city) {
        if (crossing) {
            if (intersection.numRoads() == 2) switch (from) {
                case UP: switch (to) {
                    case LEFT: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y; car.dirFacing = Direction.LEFT; break;
                        case 1: car.x--; return true;
                    } break;
                    case RIGHT: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y; car.dirFacing = Direction.DOWN; break;
                        case 1: car.y++; break;
                        case 2: car.x++; car.dirFacing = Direction.RIGHT; break;
                        case 3: car.x++; return true;
                    } break;
                } break;
                case DOWN: switch (to) {
                    case RIGHT: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y + 1; car.dirFacing = Direction.RIGHT; break;
                        case 1: car.x++; return true;
                    } break;
                    case LEFT: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y + 1; car.dirFacing = Direction.UP; break;
                        case 1: car.y--; break;
                        case 2: car.x--; car.dirFacing = Direction.LEFT; break;
                        case 3: car.x--; return true;
                    } break;
                } break;
                case LEFT: switch (to) {
                    case DOWN: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y + 1; car.dirFacing = Direction.DOWN; break;
                        case 1: car.y++; return true;
                    } break;
                    case UP: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y + 1; car.dirFacing = Direction.RIGHT; break;
                        case 1: car.x++; break;
                        case 2: car.y--; car.dirFacing = Direction.UP; break;
                        case 3: car.y--; return true;
                    } break;
                } break;
                case RIGHT: switch (to) {
                    case UP: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y; car.dirFacing = Direction.UP; break;
                        case 1: car.y--; return true;
                    } break;
                    case DOWN: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y; car.dirFacing = Direction.LEFT; break;
                        case 1: car.x--; break;
                        case 2: car.y++; car.dirFacing = Direction.DOWN; break;
                        case 3: car.y++; return true;
                    } break;
                } break;
            }
            else switch (from) {
                case UP: switch (to) {
                    case DOWN: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y; car.dirFacing = Direction.DOWN; break;
                        case 1: car.y++; break;
                        case 2: car.y++; return true;
                    } break;
                    case LEFT: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y; car.dirFacing = Direction.LEFT; break;
                        case 1: car.x--; return true;
                    } break;
                    case RIGHT: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y; car.dirFacing = Direction.RIGHT; break;
                        case 1: car.x++; car.dirFacing = Direction.DOWN; break;
                        case 2: car.y++; car.x++; car.dirFacing = Direction.RIGHT; return true;
                    } break;
                } break;
                case DOWN: switch (to) {
                    case UP: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y + 1; car.dirFacing = Direction.UP; break;
                        case 1: car.y--; break;
                        case 2: car.y--; return true;
                    } break;
                    case RIGHT: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y + 1; car.dirFacing = Direction.RIGHT; break;
                        case 1: car.x++; return true;
                    } break;
                    case LEFT: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y + 1; car.dirFacing = Direction.LEFT; break;
                        case 1: car.x--; car.dirFacing = Direction.UP; break;
                        case 2: car.y--; car.x--; car.dirFacing = Direction.LEFT; return true;
                    } break;
                } break;
                case LEFT: switch (to) {
                    case RIGHT: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y + 1; car.dirFacing = Direction.RIGHT; break;
                        case 1: car.x++; break;
                        case 2: car.x++; return true;
                    } break;
                    case DOWN: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y + 1; car.dirFacing = Direction.DOWN; break;
                        case 1: car.y++; return true;
                    } break;
                    case UP: switch (crossingStep) {
                        case 0: car.x = intersection.x; car.y = intersection.y + 1; car.dirFacing = Direction.UP; break;
                        case 1: car.y--; car.dirFacing = Direction.RIGHT; break;
                        case 2: car.y--; car.x++; car.dirFacing = Direction.UP; return true;
                    } break;
                } break;
                case RIGHT: switch (to) {
                    case LEFT: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y; car.dirFacing = Direction.LEFT; break;
                        case 1: car.x--; break;
                        case 2: car.x--; return true;
                    } break;
                    case UP: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y; car.dirFacing = Direction.UP; break;
                        case 1: car.y--; return true;
                    } break;
                    case DOWN: switch (crossingStep) {
                        case 0: car.x = intersection.x + 1; car.y = intersection.y; car.dirFacing = Direction.DOWN; break;
                        case 1: car.y++; car.dirFacing = Direction.LEFT; break;
                        case 2: car.y++; car.x--; car.dirFacing = Direction.DOWN; return true;
                    } break;
                } break;
            }

            crossingStep++;
        } else {
            crossing = intersection.checkAction(from, to, city);
        }

        return false;
    }
}
