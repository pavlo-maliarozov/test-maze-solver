import com.company.maze.evement.Coordinate;
import com.company.maze.evement.Maze;
import com.company.maze.solver.BFSMazeSolver;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File maze1 = new File(Main.class.getResource("maze1.txt").getFile());
        File maze2 = new File(Main.class.getResource("maze2.txt").getFile());
        File maze3 = new File(Main.class.getResource("maze3.txt").getFile());

        execute(maze1);
        execute(maze2);
        execute(maze3);
    }

    private static void execute(File file) throws Exception {
        Maze maze = new Maze(file);
        bfs(maze);
    }

    private static void bfs(Maze maze) {
        BFSMazeSolver bfs = new BFSMazeSolver();
        List<Coordinate> path = bfs.solve(maze);
        maze.printPath(path);
        maze.reset();
    }

}

