package com.company.maze.evement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Maze {

    private MazeElement[][] maze;
    private boolean[][] visited;
    private Coordinate start;
    private Coordinate end;

    public Maze(File maze) throws FileNotFoundException {
        StringBuilder fileText = new StringBuilder();
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText.append(input.nextLine())
                        .append("\n");
            }
        }

        initializeMaze(fileText.toString());
    }

    public void printPath(List<Coordinate> path) {

        MazeElement[][] tempMaze = Arrays.stream(maze)
                .map(MazeElement[]::clone)
                .toArray(MazeElement[][]::new);

        path.stream().filter(coordinate -> !isStart(coordinate.getX(), coordinate.getY())
                && !isExit(coordinate.getX(), coordinate.getY()))
                .forEach(coordinate ->
                        tempMaze[coordinate.getX()][coordinate.getY()] = MazeElement.PATH);

        System.out.println(representAsString(tempMaze));
    }

    public void reset() {
        for (boolean[] aVisited : visited) Arrays.fill(aVisited, false);
    }

    public Coordinate getEntry() {
        return start;
    }

    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    private boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == MazeElement.WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        return row >= 0 && row < getHeight() && col >= 0 && col < getWidth();
    }

    private String representAsString(MazeElement[][] maze) {
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        result.append('\n');
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col] == MazeElement.ROAD) {
                    result.append(' ');
                } else if (maze[row][col] == MazeElement.WALL) {
                    result.append('#');
                } else if (maze[row][col] == MazeElement.START) {
                    result.append('S');
                } else if (maze[row][col] == MazeElement.EXIT) {
                    result.append('E');
                } else {
                    result.append('.');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    private int getHeight() {
        return maze.length;
    }

    private int getWidth() {
        return maze[0].length;
    }

    private void initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] lines = text.split("[\r]?\n");
        maze = new MazeElement[lines.length][lines[0].length()];
        visited = new boolean[lines.length][lines[0].length()];

        int mazeHeight = getHeight();
        int mazeWidth = getWidth();

        for (int row = 0; row < mazeHeight; row++) {
            if (lines[row].length() != mazeWidth) {
                throw new IllegalArgumentException(
                        String.format("line %d wrong length (was %d but should be %d)",
                        (row + 1),
                        lines[row].length(),
                        mazeWidth)
                );
            }

            for (int col = 0; col < mazeWidth; col++) {
                if (lines[row].charAt(col) == '#')
                    maze[row][col] = MazeElement.WALL;
                else if (lines[row].charAt(col) == 'S') {
                    maze[row][col] = MazeElement.START;
                    start = new Coordinate(row, col);
                } else if (lines[row].charAt(col) == 'E') {
                    maze[row][col] = MazeElement.EXIT;
                    end = new Coordinate(row, col);
                } else
                    maze[row][col] = MazeElement.ROAD;
            }
        }
    }
}
