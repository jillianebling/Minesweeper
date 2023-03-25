package backtracker;

import java.util.List;

import Minesweeper.model.Gamestate;
import Minesweeper.model.Location;
import Minesweeper.model.Minesweeper;

public class RecursiveBacktracking {
    public boolean debug;
    public Minesweeper masterBoard;
    
    public RecursiveBacktracking(boolean debug, Minesweeper masterBoard){
        this.debug = debug;
        this.masterBoard = masterBoard;

        if(debug){
            System.out.println("Backtracking debugging active");
        }
    }

    
    private void debugPrint(String msg, Minesweeper config) {
        if (this.debug) {
            System.out.println(msg + ":\n" + config);
        }
    }

    Minesweeper board = masterBoard;
    List<Location> covered = masterBoard.getCovered();
    List<Location> uncovered = masterBoard.getUncovered();
    public Minesweeper recursiveBacktracking(Minesweeper board, List<Location> covered, List<Location> uncovered){
        debugPrint("Current board", board);
        if(board.getGameState() == Gamestate.WON){
            return board;
        }else{
            for(Location selectedTile : covered){
                if(!(board.getLocations().get(selectedTile) == 'm')){
                 
                    debugPrint("Valid move", board);
                    covered.remove(selectedTile);
                    uncovered.add(selectedTile);
                    return recursiveBacktracking(board, covered, uncovered);
              
                }else{
              
                    debugPrint("Invalid move", board);
                    //will remove and then add back invalid moves
                    //that way when the game is won the covered list will contain only invalid moves
                    Location temp = selectedTile;
                    covered.remove(selectedTile);
                    covered.add((covered.size() - 1), temp);
                    return recursiveBacktracking(board, covered, uncovered);
                } 
            }
            
        }
        
        return null;
    }

    public static void main(String[] args){
        Minesweeper board = new Minesweeper();

        RecursiveBacktracking b = new RecursiveBacktracking(true, board);
        b.recursiveBacktracking(board, board.getCovered(), board.getUncovered());
    }
}
