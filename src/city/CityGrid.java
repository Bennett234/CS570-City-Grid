package city;

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Random;

public class CityGrid {
    public int w, h, size;

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
        ROAD_RIGHT
    }

    public Cell[][] grid;

    public CityGrid(int w, int h, int size) {
        this.w = w;
        this.h = h;
        this.size = size;
        this.grid = new Cell[w][h];
    }

    public void generate() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Cell buildingType = new Random().nextBoolean() ? Cell.BUILDING_1 : Cell.BUILDING_2;

                switch (i % 6) {
                    case 0:
                        switch (j % 6) {
                            case 0: grid[i][j] = Cell.INTERSECTION_BOTTOM_RIGHT; break;
                            case 1: case 2: case 3: case 4: grid[i][j] = Cell.ROAD_UP; break;
                            case 5: grid[i][j] = Cell.INTERSECTION_TOP_RIGHT; break;
                        } break;
                    case 1: case 4:
                        switch (j % 6) {
                            case 0: grid[i][j] = Cell.ROAD_RIGHT; break;
                            case 1: case 4: case 2: case 3: grid[i][j] = Cell.SIDEWALK; break;
                            case 5: grid[i][j] = Cell.ROAD_LEFT; break;
                        } break;
                    case 2: case 3:
                        switch (j % 6) {
                            case 0: grid[i][j] = Cell.ROAD_RIGHT; break;
                            case 1: case 4: grid[i][j] = Cell.SIDEWALK; break;
                            case 2: case 3: grid[i][j] = buildingType; break;
                            case 5: grid[i][j] = Cell.ROAD_LEFT; break;
                        } break;
                    case 5:
                        switch (j % 6) {
                            case 0: grid[i][j] = Cell.INTERSECTION_BOTTOM_LEFT; break;
                            case 1: case 2: case 3: case 4: grid[i][j] = Cell.ROAD_DOWN; break;
                            case 5: grid[i][j] = Cell.INTERSECTION_TOP_LEFT; break;
                        } break;
                }
            }
        }
    }

    public boolean isIntersection(int x, int y) {
        return (grid[x][y] == Cell.INTERSECTION_BOTTOM_LEFT) ||
                (grid[x][y] == Cell.INTERSECTION_BOTTOM_RIGHT) ||
                (grid[x][y] == Cell.INTERSECTION_TOP_LEFT) ||
                (grid[x][y] == Cell.INTERSECTION_TOP_RIGHT);
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
                applet.fill(255);
//                applet.rect(x, y, w, 2);
                break;
            case INTERSECTION_TOP_RIGHT:
                applet.fill(255);
//                applet.rect(x + w - 2, y, 2, h);
                break;
            case INTERSECTION_BOTTOM_LEFT:
                applet.fill(255);
//                applet.rect(x, y, 2, h);
                break;
            case INTERSECTION_BOTTOM_RIGHT:
                applet.fill(255);
//                applet.rect(x, y + h - 2, w, 2);
                break;

            case ROAD_UP:
                applet.fill(255, 255, 0);
                applet.rect(x, y + h * 0.25f, 2, h * 0.5f);
                break;
            case ROAD_DOWN:
                applet.fill(255, 255, 0);
                applet.rect(x + w - 2, y + h * 0.25f, 2, h * 0.5f);
                break;
            case ROAD_LEFT:
                applet.fill(255, 255, 0);
                applet.rect(x + w * 0.25f, y + h - 2, w * 0.5f, 2);
                break;
            case ROAD_RIGHT:
                applet.fill(255, 255, 0);
                applet.rect(x + w * 0.25f, y, w * 0.5f, 2);
                break;
        }
    }
}
