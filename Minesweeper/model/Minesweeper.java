package Minesweeper.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Minesweeper.graphs.*;
import Minesweeper.view.*;

public class Minesweeper {
    private Map <Location, Character> locations;
    private List <Location> covered;
    private List <Location> uncovered;
    private int moves;
    private final int row;
    private final int col;
    private final int mines;
    private Gamestate gameState;
    private AdjacencyGraph <Location> graph;
    private MinesweeperObserver observer;
    
    /**
     * Creates instance of Minsweeper game
     */
    public Minesweeper(){
        this.locations = new HashMap<>();
        this.covered = new ArrayList<>();
        this.uncovered = new ArrayList<>();
        this.graph = new AdjacencyGraph<Location>();
        this.gameState = Gamestate.NOT_STARTED;
        this.moves = 0;
        this.col = 10;
        this.row = 10;
    // This is the closest integer based/legitimate measure for hardest 
    // difficulty minesweeper (row*col/mineCount i.e aprox 4.8 [YAY GOOGLE :D])   
        this.mines=25;
        this.observer = null;
        placeCells();
    }

    public Minesweeper(Minesweeper toCopy){
        this.locations = toCopy.locations;
        this.covered = toCopy.covered;
        this.uncovered =toCopy.uncovered;
        this.graph = toCopy.graph;
        this.gameState = toCopy.gameState;
        this.moves = toCopy.moves;
        this.col = toCopy.col;
        this.row = toCopy.row;
        this.mines=toCopy.mines;
        this.observer = null;

        
        placeCells();
    }

    //getters
    /**
     * Gets list of covered cells
     * @return
     *      covered
     */      
    public List<Location> getCovered(){
        return covered;
    }

    /**
     * gets List of uncovered cells
     * @return
     *      uncovered
     */
    public List<Location> getUncovered(){
        return uncovered;
    }
    
    /**
     * Provides collecion of possible of covered spaces.
     * @return
     *      list of covered spaces
     */
    public Collection <Location> getPossibleSelections() {
        return covered;
    }

    /**
     * Gets move count
     * @return
     *      move count
     */
    public int getMoveCount() {
        return moves;
    }

    /**
     * Gets the current state of the game
     * @return
     *      state of the game
     */
    public Gamestate getGameState() {
        return gameState;
    }

    /**
     * Gets mines boar gewnerates
     * @return
     *      number of mines on board
     */
    public int getMines(){
        return mines;
    }

    /**
     * Gets col amount
     * @return
     *      number of cols
     */
    public int getCol(){
        return col;
    }

    /**
     * Gets row amount
     * @return
     *  number of rows
     */
    public int getRow(){
        return row;
    }

    /**
     * Retrieves symbol of specific location
     * @param location
     *      location being asked for symbol of
     * @return
     *      symbol of location
     */
    public char getSymbol (Location location) {
        for (Location l: locations.keySet()) {
            if (location.equals(l))
                return locations.get(l);
        }
        return 'N'; // This is just to indicate if return is null
    }

    /**
     * returns the map of all locations and their mine/safe value
     * @return
     *      mpa of board locations
     */
    public Map <Location, Character> getLocations () {
        return locations;
    }

    /**
     * return the total neighbors of a specific cell
     * @param location
     *      location whos total neighbors are wanted
     * @return
     *      total neighbors of cell
     */
    public int getTotalNeighbors (Location location) {
        return graph.getVerticies().get(location).getNeighbors().size();
    }

    public AdjacencyGraph<Location> getGraph () {
        return graph;
    }

    /**
     * Returns if location is covered or not
     * @param location
     *      location being determined covered
     * @return
     *      true if covered, false if not
     */
    public boolean isCovered(Location location) {
        
        for (Location l: covered) {
            if (l.equals(location))
                return true;
        }    
        return false;    
    }

    /**
     * returns string of gameboard
     * @return
     *      game board 
     */
      @Override
    public String toString () {
        StringBuilder s = new StringBuilder();
        s.append("GAME BOARD | Move Count: " + moves);
        for (int i=0; i< row; i++) {
            s.append("\n");
            for (int j=0; j<col; j++) {
                for (Location l: locations.keySet()){
                    Location e = new Location (i, j);
                    if (e.equals(l) && !covered.contains(l)) {
                        s.append("[" + graph.getVerticies().get(e).getNeighbors().size() + "]");
                    }    
                        else if (e.equals(l) && covered.contains(l)) {
                            s.append("[-]");
                        }    
                }
            }
        }

    // This is just for testing the makeMove and setNeighbors method :D 
        s.append("\nTESTING BOARD");
        
        for (int i=0; i< row; i++) {
            s.append("\n");
            for (int j=0; j<col; j++) {
                for (Location l: locations.keySet()){
                    Location e = new Location (i, j);
                    if (e.equals(l))
                        s.append("[" + locations.get(e) + "]");
                }
            }
        }
        
        return s.toString();
    }


    //methods
    /**
     * Places mines in random places based on the amount of mines the game provides
     */
    private void placeCells() {
        // Build the text version of Minesweeper
        int currMines = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){ 
                Location newL = new Location(i, j);
                int random = makeRandom(100);
            // If within this range and current mines less than needed, place location as mine
                if(random <= 10 && currMines < mines) {
                    locations.put(newL, 'm');
                    graph.add(newL);
                    currMines++;
                } 
                // If not within range
                    else {
                        locations.put(newL, 's');
                        graph.add(newL);
                    }    

                covered.add (newL);
            // If the last column is placed, but not all bombs are placed, reset loop and try again. 
                if (i == row-1 && j == col-1 && currMines < mines) {
                    locations.clear();
                    covered.clear();
                    graph.getVerticies().clear();
                    currMines = 0;
                    j=-1;
                    i=0;
                }    
            }       
        } 
        
        setNeighbors();
    }

    /**
     * Sets the neighbors of every mine on the board
     */
    private void setNeighbors() {
        for (Location l: locations.keySet()) {
        // Takes every possible neighbor of newL and sees if it contains a mine    
            if (locations.get(l) == 'm') {
                
                for (Location e: locations.keySet()) {
                // Compares every possible row/col adjacent to where mine cell is    
                    if ((l.getRow()+1 == e.getRow() && l.getCol() == e.getCol()) ||
                        (l.getRow()-1 == e.getRow() && l.getCol() == e.getCol()) ||
                        (l.getRow() == e.getRow() && l.getCol()+1 == e.getCol()) ||
                        (l.getRow() == e.getRow() && l.getCol()-1 == e.getCol()) || 
                        (l.getRow()+1 == e.getRow() && l.getCol()+1 == e.getCol()) ||
                        (l.getRow()+1 == e.getRow() && l.getCol()-1 == e.getCol()) ||
                        (l.getRow()-1 == e.getRow() && l.getCol()+1 == e.getCol()) ||
                        (l.getRow()-1 == e.getRow() && l.getCol()-1 == e.getCol())) 
                            graph.connectUndirected(e, l);
                }
            } 
        }
    }        
    
    /**
     * Generates random number with a max value
     * @param max
     *      bound for random number
     * @return
     *      random number
     */
    private int makeRandom(int max){
        Random rand = new Random();
        int randReturn = rand.nextInt(max);
        return randReturn;
    }

    /**
     * Determines the actual Location of cell in game based on a imputted row and col
     * @param x
     *      col
     * @param y
     *      row
     * @return
     *      location oobject in game of that row and col
     */
    public Location coordToObj(int x, int y){
        for(Location l : locations.keySet()){
            if(x == l.getCol() && y == l.getRow()){
                return l;
            }   
        }
        return null;
    }

    /**
     * User inputs a location to reveal on board
     * @param locate
     *      cell that user wishes to reveal
     * @throws Exception
     */
    public void makeSelection (Location locate) throws MinesweeperException {
    // Made so that modifcations to the list is not done during iteration    
        List <Location> newCovered = covered;
        if (locate.getRow() >= row || locate.getCol() >= col || locate.getCol() < 0 || locate.getRow() < 0)
            throw new MinesweeperException ("This move is invalid!");

    // Checks for cell in uncoverd list that equals what the passed in location is        
        for (Location u: uncovered) {
            if (u.equals(locate) && uncovered.contains(locate)) 
                throw new MinesweeperException ("This move has already been played!");
        }
        
    // Checks all covered cells and     
        Location newL = null;
        for (Location l: covered) {
                if (locate.equals(l) && newCovered.contains(l)) {
                // Determines if user loses and game is stll in progress    
                    if (locations.get(l) == 'm') {
                        newCovered.remove(l);
                        covered.remove(l);
                        uncovered.add(l);
                        gameState = Gamestate.LOST;
                        if (observer != null)
                            notifyGUI(l);
                        break;
                    }
                        else {
                            covered.remove(l);
                            newCovered.remove(l);
                            uncovered.add(l);
                            gameState = Gamestate.IN_PROGRESS;
                            newL = l;
                            moves++;
                            if (observer != null)
                                notifyGUI(l);
                            break;
                        }
                }
        }
    // Sets list outcome of loop to list used to remove uncovered items
        covered = newCovered;

        if (moves == (row*col)-mines && newL != null) {
            gameState = Gamestate.WON;
            if (observer != null)
                notifyGUI(newL);
        }    
    }

    /**
     * resets the move count of the game
     */
    public void resetMovesCount () {
        moves = 0;
    }

    /**
     * resets the board with a newly randominzed instance
     */
    public void reset () {
        uncovered.clear();
        covered.clear();
        gameState = Gamestate.NOT_STARTED;
        placeCells();
        moves=0;
    }

    /**
     * regiters a new observer for the board 
     * @param observer
     *      observer being added to the board
     */
    public void register (MinesweeperObserver observer) {
        this.observer = observer;
    }

    /**
     * updates the guiUpdater with new changes to board
     * @param location
     *      location of game that has changed
     */
    public void notifyGUI (Location location) {
        observer.cellUpdated(location);
    }

}
