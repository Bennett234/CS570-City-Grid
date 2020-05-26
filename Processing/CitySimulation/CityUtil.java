package util;

import java.util.List;
import java.util.Random;

public class CityUtil {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private Direction opposite;
        private int dx, dy;

        private static final int SIZE = 4;
        private static final Random RANDOM = new Random();

        static {
            UP.opposite    = DOWN;
            UP.dx          = 0;
            UP.dy          = -1;

            DOWN.opposite  = UP;
            DOWN.dx        = 0;
            DOWN.dy        = 1;

            LEFT.opposite  = RIGHT;
            LEFT.dx        = -1;
            LEFT.dy        = 0;

            RIGHT.opposite = LEFT;
            RIGHT.dx       = 1;
            RIGHT.dy       = 0;
        }

        public Direction opposite() {
            return opposite;
        }

        public int dx() {
            return dx;
        }

        public int dy() {
            return dy;
        }

        public static Direction random() {
            switch (RANDOM.nextInt(SIZE)) {
                case 0:
                    return UP;
                case 1:
                    return DOWN;
                case 2:
                    return LEFT;
                case 3:
                    return RIGHT;
            }
            
            return UP;
        }

        public static Direction randomExcluding(Direction direction)  {
            Direction dir;

            do dir = random();
            while (dir == direction);

            return dir;
        }

        public Direction randomExcludingThis()  {
            return randomExcluding(this);
        }
    }
}
