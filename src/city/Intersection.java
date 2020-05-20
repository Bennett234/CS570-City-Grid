package city;

import static util.CityUtil.*;

public class Intersection {
    public int x, y;
    public TrafficState state;
    private boolean[] availableDirections = new boolean[4];

    public enum TrafficState {
        PHASE_1,
        PHASE_2,
        PHASE_3,
        PHASE_4
    }

    public Intersection(int x, int y) {
        this(x, y, TrafficState.PHASE_1);
    }

    public Intersection(int x, int y, TrafficState state) {
        this.x = x;
        this.y = y;
        this.state = state;

        availableDirections[Direction.UP.ordinal()] = true;
        availableDirections[Direction.DOWN.ordinal()] = true;
        availableDirections[Direction.LEFT.ordinal()] = true;
        availableDirections[Direction.RIGHT.ordinal()] = true;
    }

    public void setAvailable(Direction direction, boolean available) {
        availableDirections[direction.ordinal()] = available;
    }

    public boolean isAvailable(Direction direction) {
        return availableDirections[direction.ordinal()];
    }

    public void nextState() {
        switch (state) {
            case PHASE_1: state = TrafficState.PHASE_2; break;
            case PHASE_2: state = TrafficState.PHASE_3; break;
            case PHASE_3: state = TrafficState.PHASE_4; break;
            case PHASE_4: state = TrafficState.PHASE_1; break;
        }
    }

    public boolean checkAction(Direction from, Direction to) {
        if (!isAvailable(to)) return false;
        return canDoDuringPhase(state, from, to);
    }

    private static boolean canDoDuringPhase(TrafficState state, Direction from, Direction to) {
        assert from != to;

        switch (state) {
            case PHASE_1: switch (from) {
                case UP: return to == Direction.DOWN || to == Direction.LEFT;
                case DOWN: return to == Direction.UP || to == Direction.RIGHT;
                case LEFT: case RIGHT: return false;
            }
            case PHASE_2: switch (from) {
                case LEFT: return to == Direction.DOWN || to == Direction.RIGHT;
                case RIGHT: return to == Direction.UP || to == Direction.LEFT;
                case UP: case DOWN: return false;
            }
            case PHASE_3: switch (from) {
                case UP: return to == Direction.RIGHT;
                case DOWN: return to == Direction.LEFT;
                case LEFT: case RIGHT: return false;
            }
            case PHASE_4: switch (from) {
                case LEFT: return to == Direction.UP;
                case RIGHT: return to == Direction.DOWN;
                case UP: case DOWN: return false;
            }
        }

        return false;
    }
}
