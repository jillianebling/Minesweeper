package Minesweeper.view;

import java.util.Collection;

import Minesweeper.model.Gamestate;
import Minesweeper.model.Location;
import Minesweeper.model.MinesweeperConfig;
import Minesweeper.model.MinesweeperException;
import backtracker.Backtracker;
import backtracker.Configuration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class MinesweeperHandler implements EventHandler <ActionEvent> {

    private MinesweeperConfig game;
    private Location loc;
    private Button button;
    private String event;
    private MinesweeperGUI gui;
    private Label message;

    /**
     * Creates constructor for event handler
     * @param gui
     *      gui of the minsweeper game
     * @param game
     *      instance of minesweeper game attached to gui
     * @param loc
     *      location being referenced
     * @param button
     *      button passed in during each instance
     * @param message
     *      message lable of the current state of the game
     * @param event
     *      string of what event is to happen
     */
    public MinesweeperHandler (MinesweeperGUI gui, MinesweeperConfig game, Location loc, Button button, Label message, String event) {
        this.game = game;
        this.loc = loc;
        this.button = button;
        this.event = event;
        this.gui = gui;  
        this.message = message;
    }

    @Override
    public void handle(ActionEvent arg0){
        if (event.equals("move")) {
            button.setVisible(false);
            try {
                game.makeSelection(loc);
            }
            catch (MinesweeperException e) {
                System.out.println("Already been placed!");
            }

        }
         else if (event.equals("hint")) {
            loc = gui.checkValidRand();
            gui.hintCount++;

            if (gui.hintCount >= (game.getRow()*game.getCol())-game.getMines()){
                button.setText("No Hints Available!");
                button.setDisable(true);
            } 

            else {
            //cycle thru all the nodes, if its the right one set it to green
                for(Node node: gui.upper.getChildren()){ 
                    if((int)GridPane.getRowIndex(node)==loc.getRow()&&(int)GridPane.getColumnIndex(node)==loc.getCol()){
                        Button b = (Button)node;
                        b.setBackground((new Background(new BackgroundFill(Color.YELLOWGREEN,CornerRadii.EMPTY,Insets.EMPTY))));
                    }
                }
            }
        }
        
        else if (event.equals("reset")) {
            game.reset();
            gui.moves.setText("Moves:" + game.getMoveCount());
            gui.upper.getChildren().clear();
            gui.lower.getChildren().clear();
            gui.placeButtons();
            gui.message.setText("Make a Move!");
            gui.message.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
            gui.hint.setText("Hint");
            gui.hint.setDisable(false);
            gui.notValid.clear();
            gui.buttons.clear();
            gui.hintCount = 0;
            
        }  
        
        else if (event.equals("solve")) {
            if (game.getGameState() != Gamestate.LOST) {
                Backtracker b =  new Backtracker(true);
                Configuration sol = b.solve(game);
                MinesweeperConfig sol2 = (MinesweeperConfig) sol;
                if (sol != null)
                    game.setGamestate(Gamestate.WON);
                for(Object el: sol2.getUncovered()){
                    Location loc = (Location) el;
                    for(Node node: gui.upper.getChildren()){ 
                        if((int)GridPane.getRowIndex(node)==loc.getRow()&&(int)GridPane.getColumnIndex(node)==loc.getCol()){
                            Button button = (Button)node;
                            button.setVisible(false);
                            gui.message.setText("YOU WON!!");
                            gui.message.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                }
            }
        }
    }
}      