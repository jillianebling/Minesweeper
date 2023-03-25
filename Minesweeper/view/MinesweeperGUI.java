package Minesweeper.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import Minesweeper.model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application{

    protected MinesweeperConfig game = new MinesweeperConfig();
    protected GridPane upper = new GridPane();
    protected GridPane lower = new GridPane();
    protected Label moves = null;
    protected Label message = null;
    protected Button hint = null;
    protected Button solve=null;
    protected int hintCount = 0;
    protected List <Location> notValid = new LinkedList<>();
    protected List <Button> buttons = new LinkedList<>();


    public void start(Stage stage) throws Exception {    
    // Register gui to guiUpdater    
        game.register(new GuiUpdater(this));

    // Left side of gameboard that     
        VBox box = new VBox(); 

        message = makeLabel(90, 65, "Make a move!");
        hint = makeButton(90, 50, "Hint");

        EventHandler <ActionEvent> event = new MinesweeperHandler(this, game, null, hint, null, "hint");
        hint.setOnAction(event);

        solve= makeButton(90, 50, "Solve");
        EventHandler<ActionEvent> solveEvent= new MinesweeperHandler(this,game,null,solve,null,"solve");
        solve.setOnAction(solveEvent);

        Label mines = makeLabel(90, 45, "Mines: " + Integer.toString(game.getMines()));
        moves = makeLabel(90, 45, "Moves: " + 0);

        Button reset = makeButton(90, 50, "Reset");
        EventHandler <ActionEvent> event2 = new MinesweeperHandler(this, game, null, reset,null,"reset");
        reset.setOnAction(event2);
        box.getChildren().addAll(mines, moves, hint, reset, solve, message);
        box.setAlignment(Pos.TOP_LEFT);

    // Creates right side of board 
        StackPane stack = new StackPane();
        placeButtons();
        stack.getChildren().addAll(lower, upper);
        
    // Grid for presented gameboard    
        GridPane grid = new GridPane();
        grid.add(box, 0, 0);
        grid.add(stack, 1, 0);

        Scene scene = new Scene (grid);
        stage.setTitle("Minesweeper");
        stage.setScene (scene);
        stage.show();   
    }

    /**
     * Creates buttin of specific size and text
     * @param wide
     *      width of button
     * @param tall
     *      height of button
     * @param name
     *      text of button
     * @return
     *      new button
     */
    public static Button makeButton (int wide, int tall, String name) {
        Button button = new Button();
        button.setMinSize(wide, tall);
        button.setText(name);
        return button;
    }

    /**
     * Creates a stack for mine images on the lower grid
     * @return
     *      stack pane of mine image
     */
    public static StackPane makeMineStack () {
        StackPane pane = new StackPane();
        Image image = new Image ("Minesweeper/media/images/mine24.png");
        ImageView view = new ImageView(image);
        view.setFitHeight(30);
        view.setFitWidth(30);
        pane.getChildren().addAll(makeLabel(""), view);
        return pane; 
    }

    /**
     * Creates a lable with a specified text adn predetermined size and style. 
     * @param num
     *      String entered for label creation
     * @return
     *      new label
     */
    public static Label makeLabel(String num) {
        Label s = new Label();
        s.setText(num);
        s.setMinSize(45, 15);
        switch (num) {
        case ("1"):
        s.setTextFill(Color.BLUE);
        break;
        case ("2"):
        s.setTextFill(Color.GREEN);
        break;
        case ("3"):
        s.setTextFill(Color.RED);
        break;
        case ("4"):
        s.setTextFill(Color.GOLD);
        break;
        case ("5"):
        s.setTextFill(Color.INDIGO);
        break;
        case ("6"):
        s.setTextFill(Color.HOTPINK);
        break;
        case ("7"):
        s.setTextFill(Color.TAN);
        break;
        case ("8"):
        s.setTextFill(Color.SILVER);
        break;
        default:
        s.setTextFill(Color.PEACHPUFF);
        break;
        }

        s.setAlignment(Pos.CENTER);
        s.setFont(Font.font(35));
        s.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        return s;    
    }

    /**
     * Creates label w/ specific size and text
     * @param wide
     *      width of label
     * @param tall
     *      height of label
     * @param num
     *      text of label
     * @return
     *      new label
     */
    public static Label makeLabel(int wide, int tall, String num) {
        Label s = new Label();
        s.setText(num);
        s.setMinSize(wide, tall);
        s.setAlignment(Pos.CENTER);
        s.setFont(Font.font(10));
        s.setTextFill(Color.BLACK);
        s.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        return s;    
    }

    /**
     * Places all the buttons and images onto the minesweeper gui
     */
    public void placeButtons () {
        for (int i =0; i < game.getRow(); i++) {
            for (int j=0; j < game.getCol(); j++) {
                Button button = makeButton(45, 51, "");
                upper.add(button, j, i);
                buttons.add(button);   
                Location l = game.coordToObj(j, i);
                if (game.getSymbol(l) == 'm') 
                    lower.add(makeMineStack(), j, i);
                else if (game.getSymbol(l) != 'n') {
                    String s = "";
                    if (game.getTotalNeighbors(l) != 0)
                        s += game.getTotalNeighbors(l);
                    lower.add(makeLabel(s), j, i);     
                }   
                EventHandler <ActionEvent> event = new MinesweeperHandler(this, game, new Location(i, j), button, message, "move");
                button.setOnAction(event);        
            }  
        }
    }

    /**
     * Checks if a move being made for a hint is a valid move
     * @return
     *      returns the move being made for the hint
     */
    public Location checkValidRand(){
        Random rand = new Random();
        Location prettyRand = null;
        while(true){
            int row = rand.nextInt(game.getRow());
            int col = rand.nextInt(game.getCol());
            prettyRand = game.coordToObj(col, row);
            if (game.getPossibleSelections().size() == game.getMines())
                break;
            if(game.isCovered(prettyRand) && game.getLocations().get(prettyRand) == 's' && !notValid.contains(prettyRand)){
                notValid.add(prettyRand);
                break;
            }
        }
        return prettyRand;
    }


    public static void main (String[] args) {
        launch(args);
    }

}