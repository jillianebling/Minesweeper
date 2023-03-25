package Minesweeper.view;
import Minesweeper.model.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GuiUpdater implements MinesweeperObserver {

    private MinesweeperConfig game;
    private MinesweeperGUI gui;

    /**
     * Instance of guiUpdater 
     * @param gui
     *      gui that is bein changed
     */
    public GuiUpdater (MinesweeperGUI gui) {
        this.gui = gui;
        game = gui.game;
    }

	@Override
	public void cellUpdated(Location location) {
        gui.moves.setText("Moves: " + game.getMoveCount());

		if (game.getGameState() != Gamestate.IN_PROGRESS && game.getGameState() != Gamestate.NOT_STARTED) {
            gui.upper.getChildren().clear();
            if (game.getGameState() == Gamestate.WON) {
                gui.message.setText("YOU WON!!");
                gui.message.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else {
                gui.message.setText("YOU LOST LMAO!");
                gui.message.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            }     
        }
	}

}