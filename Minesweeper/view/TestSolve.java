package Minesweeper.view;
import Minesweeper.model.MinesweeperConfig;
import backtracker.Backtracker;
import backtracker.Configuration;


public class TestSolve {

    public static void main(String[] args) {
        Backtracker b =  new Backtracker(false);
        MinesweeperConfig m = new MinesweeperConfig();
        
        Configuration sol = b.solve(m);
        System.out.println(sol);
    }
}
