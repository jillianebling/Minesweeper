package Minesweeper.view;
import Minesweeper.model.Location;

public interface MinesweeperObserver {
    public void cellUpdated (Location location);
}