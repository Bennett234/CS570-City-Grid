package city;

import city.Intersection.TrafficState;
import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.Point;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static util.CityUtil.*;

public class CityGrid {
    public int w, h, size;

    public HashMap<Point, Intersection> intersections;

    private static final Cell EPTY = Cell.EMPTY;
    private static final Cell BDG1 = Cell.BUILDING_1;
    private static final Cell BDG2 = Cell.BUILDING_2;
    private static final Cell SDWK = Cell.SIDEWALK;
    private static final Cell I_TL = Cell.INTERSECTION_TOP_LEFT;
    private static final Cell I_TR = Cell.INTERSECTION_TOP_RIGHT;
    private static final Cell I_BL = Cell.INTERSECTION_BOTTOM_LEFT;
    private static final Cell I_BR = Cell.INTERSECTION_BOTTOM_RIGHT;
    private static final Cell RD_U = Cell.ROAD_UP;
    private static final Cell RD_D = Cell.ROAD_DOWN;
    private static final Cell RD_L = Cell.ROAD_LEFT;
    private static final Cell RD_R = Cell.ROAD_RIGHT;

    enum Cell {
        EMPTY,

        BUILDING_1,
        BUILDING_2,
        SIDEWALK,

        INTERSECTION_TOP_LEFT,
        INTERSECTION_TOP_RIGHT,
        INTERSECTION_BOTTOM_LEFT,
        INTERSECTION_BOTTOM_RIGHT,

        ROAD_UP,
        ROAD_DOWN,
        ROAD_LEFT,
        ROAD_RIGHT;
        
        private CellType type;
        private Direction direction;
        
        static {
            EMPTY.type      = CellType.OBSTACLE;
            BUILDING_1.type = CellType.OBSTACLE;
            BUILDING_2.type = CellType.OBSTACLE;
            SIDEWALK.type   = CellType.OBSTACLE;
            
            INTERSECTION_TOP_LEFT.type     = CellType.INTERSECTION;
            INTERSECTION_TOP_RIGHT.type    = CellType.INTERSECTION;
            INTERSECTION_BOTTOM_LEFT.type  = CellType.INTERSECTION;
            INTERSECTION_BOTTOM_RIGHT.type = CellType.INTERSECTION;
            
            ROAD_UP.type    = CellType.ROAD;
            ROAD_DOWN.type  = CellType.ROAD;
            ROAD_LEFT.type  = CellType.ROAD;
            ROAD_RIGHT.type = CellType.ROAD;
            
            ROAD_UP.direction    = Direction.UP;
            ROAD_DOWN.direction  = Direction.DOWN;
            ROAD_LEFT.direction  = Direction.LEFT;
            ROAD_RIGHT.direction = Direction.RIGHT;
        }

        public Direction direction() {
            return direction;
        }

        public CellType type() {
            return type;
        }

        public boolean isRoad() {
            return type == CellType.ROAD;
        }

        public boolean isIntersection() {
            return type == CellType.INTERSECTION;
        }
    }
    
    enum CellType {
        OBSTACLE,
        ROAD, 
        INTERSECTION
    }

    public Cell[][] grid;

    public CityGrid(int w, int h, int size) {
        this.w = w;
        this.h = h;
        this.size = size;
        this.grid = new Cell[w][h];
        this.intersections = new HashMap<>();
    }

    public void generate() {
        for (int i = 5; i < w; i += 6) {
            for (int j = 5; j < h; j += 6) {
                Intersection intersection = new Intersection(i, j);

                for (int k = 0; k < new Random().nextInt(3); k++) {
                    intersection.nextState();
                }

                intersections.put(new Point(i, j), intersection);
            }
        }

        for (int i = 0; i + 5 < w; i += 6) {
            for (int j = 0; j + 5 < h; j += 6) {
                Cell buildingType = new Random().nextBoolean() ? BDG1 : BDG2;

                if (genCornerCell(i, j, buildingType)) continue;
                if (genEdgeCell(i, j, buildingType)) continue;

                genNormalCell(i, j, buildingType);
            }
        }
    }

    public Intersection getIntersection(int x, int y) {
        assert grid[x][y].type == CellType.INTERSECTION;

        switch (grid[x][y]) {
            case INTERSECTION_TOP_LEFT:     return intersections.get(new Point(x, y));
            case INTERSECTION_TOP_RIGHT:    return intersections.get(new Point(x - 1, y));
            case INTERSECTION_BOTTOM_LEFT:  return intersections.get(new Point(x, y - 1));
            case INTERSECTION_BOTTOM_RIGHT: return intersections.get(new Point(x - 1, y - 1));
        }

        return null;
    }

    public void draw(PApplet applet) {
        applet.rectMode(PConstants.CORNER);
        applet.noStroke();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this.drawCell(applet, i, j, i * size, j * size, size, size);
            }
        }
    }

    public void drawCell(PApplet applet, int i, int j, int x, int y, int w, int h) {
        Intersection i2;
        TrafficState s;

        switch (grid[i][j]) {
            case EMPTY:
                break;

            case BUILDING_1:
                applet.fill(255, 0, 0);
                applet.rect(x, y, w, h);
                break;
            case BUILDING_2:
                applet.fill(255, 128, 0);
                applet.rect(x, y, w, h);
                break;
            case SIDEWALK:
                applet.fill(128);
                applet.rect(x, y, w, h);
                break;

            case INTERSECTION_TOP_LEFT:
                i2 = getIntersection(i, j);
                s = i2.state;
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.UP, i2,
                        s == TrafficState.PHASE_1 || s == TrafficState.PHASE_3);
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.LEFT, i2,
                        s != TrafficState.PHASE_4);
                break;
            case INTERSECTION_TOP_RIGHT:
                i2 = getIntersection(i, j);
                s = i2.state;
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.UP, i2,
                        s != TrafficState.PHASE_3);
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.RIGHT, i2,
                        s == TrafficState.PHASE_2 || s == TrafficState.PHASE_4);
                break;
            case INTERSECTION_BOTTOM_LEFT:
                i2 = getIntersection(i, j);
                s = i2.state;
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.DOWN, i2,
                        s != TrafficState.PHASE_3);
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.LEFT, i2,
                        s == TrafficState.PHASE_2 || s == TrafficState.PHASE_4);
                break;
            case INTERSECTION_BOTTOM_RIGHT:
                i2 = getIntersection(i, j);
                s = i2.state;
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.DOWN, i2,
                        s == TrafficState.PHASE_1 || s == TrafficState.PHASE_3);
                drawIntersectionLineWithState(applet, x, y, w, h, Direction.RIGHT, i2,
                        s != TrafficState.PHASE_4);
                break;
                
            case ROAD_UP:
                if (grid[i - 1][j].type != CellType.ROAD) return;
                if (grid[i - 1][j] == Cell.ROAD_RIGHT) return;
                applet.fill(255, 255, 0);
                applet.rect(x, y + h * 0.25f, 2, h * 0.5f);
                break;
            case ROAD_DOWN:
                if (grid[i + 1][j].type != CellType.ROAD) return;
                if (grid[i + 1][j] == Cell.ROAD_LEFT) return;
                applet.fill(255, 255, 0);
                applet.rect(x + w - 2, y + h * 0.25f, 2, h * 0.5f);
                break;
            case ROAD_LEFT:
                if (grid[i][j + 1].type != CellType.ROAD) return;
                if (grid[i][j + 1] == Cell.ROAD_UP) return;
                applet.fill(255, 255, 0);
                applet.rect(x + w * 0.25f, y + h - 2, w * 0.5f, 2);
                break;
            case ROAD_RIGHT:
                if (grid[i][j - 1].type != CellType.ROAD) return;
                if (grid[i][j - 1] == Cell.ROAD_DOWN) return;
                applet.fill(255, 255, 0);
                applet.rect(x + w * 0.25f, y, w * 0.5f, 2);
                break;
        }
    }


    public void drawIntersectionLineWithState(PApplet applet, int x, int y, int w, int h, Direction dir, Intersection intersection, boolean state) {
        if (!intersection.isAvailable(dir)) return;

        if (state) applet.fill(0, 255, 0);
        else applet.fill(255, 0, 0);

        if (intersection.numRoads() == 2) applet.fill(0, 255, 0);

        switch (dir) {
            case UP:    applet.rect(x + 2,         y,         w - 4, 2);     break;
            case DOWN:  applet.rect(x + 2,     y + h - 2, w - 4, 2);     break;
            case LEFT:  applet.rect(    x,         y + 2,     2,     h - 4); break;
            case RIGHT: applet.rect(x + w - 2, y + 2,     2,     h - 4); break;
        }
    }


    public void genCellTemplate(int i, int j, Cell[][] template) {
        assert template.length == 6;
        assert template[0].length == 6;

        for (int tX = 0; tX < 6; tX++) {
            for (int tY = 0; tY < 6; tY++) {
                grid[i + tX][j + tY] = template[tY][tX];
            }
        }
    }

    public void genNormalCell(int x, int y, Cell BLDG) {
        genCellTemplate(x, y, new Cell[][]{
                { I_BR, RD_R, RD_R, RD_R, RD_R, I_BL },

                { RD_U, SDWK, SDWK, SDWK, SDWK, RD_D },

                { RD_U, SDWK, BLDG, BLDG, SDWK, RD_D },

                { RD_U, SDWK, BLDG, BLDG, SDWK, RD_D },

                { RD_U, SDWK, SDWK, SDWK, SDWK, RD_D },

                { I_TR, RD_L, RD_L, RD_L, RD_L, I_TL }
        });
    }

    public boolean genEdgeCell(int x, int y, Cell BLDG) {
        if (x == 0) {
            genCellTemplate(x, y, new Cell[][]{
                    { EPTY, EPTY, EPTY, EPTY, SDWK, I_BL },

                    { EPTY, EPTY, EPTY, EPTY, SDWK, RD_D },

                    { EPTY, EPTY, EPTY, EPTY, SDWK, RD_D },

                    { EPTY, EPTY, EPTY, EPTY, SDWK, RD_D },

                    { EPTY, EPTY, EPTY, EPTY, SDWK, RD_D },

                    { EPTY, EPTY, EPTY, EPTY, SDWK, I_TL }
            });

            getIntersection(x + 5, y - 1).setAvailable(Direction.LEFT, false);
            getIntersection(x + 5, y + 5).setAvailable(Direction.LEFT, false);
        }
        else if (x + 6 >= w) {
            genCellTemplate(x, y, new Cell[][]{
                    { I_BR, SDWK, EPTY, EPTY, EPTY, EPTY },

                    { RD_U, SDWK, EPTY, EPTY, EPTY, EPTY },

                    { RD_U, SDWK, EPTY, EPTY, EPTY, EPTY },

                    { RD_U, SDWK, EPTY, EPTY, EPTY, EPTY },

                    { RD_U, SDWK, EPTY, EPTY, EPTY, EPTY },

                    { I_TR, SDWK, EPTY, EPTY, EPTY, EPTY }
            });

            getIntersection(x - 1, y - 1).setAvailable(Direction.RIGHT, false);
            getIntersection(x - 1, y + 5).setAvailable(Direction.RIGHT, false);
        }
        else if (y == 0) {
            genCellTemplate(x, y, new Cell[][]{
                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { SDWK, SDWK, SDWK, SDWK, SDWK, SDWK },

                    { I_TR, RD_L, RD_L, RD_L, RD_L, I_TL }
            });

            getIntersection(x - 1, y + 5).setAvailable(Direction.UP, false);
            getIntersection(x + 5, y + 5).setAvailable(Direction.UP, false);
        }
        else if (y + 6 >= h) {
            genCellTemplate(x, y, new Cell[][]{
                    { I_BR, RD_R, RD_R, RD_R, RD_R, I_BL },

                    { SDWK, SDWK, SDWK, SDWK, SDWK, SDWK },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                    { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY }
            });

            getIntersection(x - 1, y - 1).setAvailable(Direction.DOWN, false);
            getIntersection(x + 5, y - 1).setAvailable(Direction.DOWN, false);
        }
        else return false;
        return true;
    }

    public boolean genCornerCell(int x, int y, Cell BLDG) {
        if (x == 0 && y == 0) genCellTemplate(x, y, new Cell[][]{
                /* Top Left */

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, SDWK, SDWK },

                { EPTY, EPTY, EPTY, EPTY, SDWK, I_TL }
        });
        else if (x + 6 >= w && y == 0) genCellTemplate(x, y, new Cell[][]{
                /* Top Right */

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { SDWK, SDWK, EPTY, EPTY, EPTY, EPTY },

                { I_TR, SDWK, EPTY, EPTY, EPTY, EPTY }
        });
        else if (x == 0 && y + 6 >= h) genCellTemplate(x, y, new Cell[][]{
                /* Bottom Left */

                { EPTY, EPTY, EPTY, EPTY, SDWK, I_BL },

                { EPTY, EPTY, EPTY, EPTY, SDWK, SDWK },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY }
        });
        else if (x + 6 >= w && y + 6 >= h) genCellTemplate(x, y, new Cell[][]{
                /* Bottom Right */

                { I_BR, SDWK, EPTY, EPTY, EPTY, EPTY },

                { SDWK, SDWK, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY },

                { EPTY, EPTY, EPTY, EPTY, EPTY, EPTY }
        });
        else return false;
        return true;
    }
}
