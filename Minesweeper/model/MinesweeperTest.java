package Minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

import backtracker.Configuration;

@Testable
public class MinesweeperTest {

    @Test
    public void testCoveredList() {
        List <Location> list = new ArrayList<>();
        for (int i=0; i<5;i++) {
            for (int j=0; j<5; j++) {
                list.add(new Location (i, j));
            }
        }
        String actual = list.toString();
        String expected = "[ROW:0 | COL:0, ROW:0 | COL:1, ROW:0 | COL:2, ROW:0 | COL:3, ROW:0 | COL:4, ROW:1 | COL:0, ROW:1 | COL:1, ROW:1 | COL:2, ROW:1 | COL:3, ROW:1 | COL:4, ROW:2 | COL:0, ROW:2 | COL:1, ROW:2 | COL:2, ROW:2 | COL:3, ROW:2 | COL:4, ROW:3 | COL:0, ROW:3 | COL:1, ROW:3 | COL:2, ROW:3 | COL:3, ROW:3 | COL:4, ROW:4 | COL:0, ROW:4 | COL:1, ROW:4 | COL:2, ROW:4 | COL:3, ROW:4 | COL:4]";

        assertEquals(expected, actual);
    }

    @Test
    public void testDuplicateMoves() throws Exception {
        Minesweeper m = new Minesweeper();
        try {
        m.makeSelection(new Location (0,0));
        m.makeSelection(new Location (0,0));
        assert(false);

        }
        catch (MinesweeperException e) {
            assert(true);
        }
    }

    @Test
    public void testInvalidMove() throws Exception {
        Minesweeper m = new Minesweeper();
        try {
        m.makeSelection(new Location(10, 10));
        assert(false);
        }
        catch (MinesweeperException e) {
            assert(true);
        }
    }

    @Test
    public void testCoordToObj(){
        Minesweeper sweeper = new Minesweeper();
        Location coord= sweeper.coordToObj(1, 2);
        Location exp= new Location(1, 2);
        coord.equals(exp);

    }
    
    @Test
    public void testValidMove() throws Exception{
        Minesweeper sweeper = new Minesweeper();
        Location location = new Location(1, 2);
        sweeper.makeSelection(location);
        assert(sweeper.isCovered(location)==false);
    }

    @Test
    public void testresetMovesCount() throws Exception{
        Minesweeper sweeper = new Minesweeper();
        sweeper.makeSelection(new Location(1,1));
        assert(sweeper.getMoveCount()==1); // was move count updated?
        sweeper.resetMovesCount();
        assert(sweeper.getMoveCount()==0);// was the moce count reset
    }

    @Test
    public void testReset() throws Exception{
        Minesweeper sweeper = new Minesweeper();
        Location l = new Location(1, 1);

        sweeper.makeSelection(l); //move made to change game state from default
        sweeper.reset();
        assert(sweeper.getMoveCount()==0);

        List<Location> covered= sweeper.getCovered();
        assert(covered.size()==sweeper.getCol()*sweeper.getRow()); //checks that all elements are present

        List<Location> uncovered= sweeper.getUncovered();
        assert(uncovered.isEmpty()==true); //checks that no tiles are uncovered

        assert(sweeper.getGameState()==Gamestate.NOT_STARTED);

    }   

    @Test
    public void testIsCovered() throws Exception{
        Minesweeper sweeper = new Minesweeper();
        Location l = new Location(1, 1);
        sweeper.makeSelection(l);
        assert(sweeper.isCovered(l)==false);

    }

    @Test
    public void testGetSuccessors(){
        MinesweeperConfig c = new MinesweeperConfig();
        Collection<Configuration> cofig =c.getSuccessors();
        assert(cofig!=null);
    }

    @Test
    public void testIsGoal(){
        MinesweeperConfig c = new MinesweeperConfig();
        c.setGamestate(Gamestate.WON);
        Boolean b =c.isGoal();
        assertTrue(b);
    }

    @Test
    public void testIsValid() throws MinesweeperException{
        MinesweeperConfig c = new MinesweeperConfig();
        Map<Location,Character> q = c.getLocations();

        Location l = new Location(1, 1);
        c.makeSelection(l);

        Boolean b =c.isValid();

        if(q.get(l)=='m'){
            assert(b==false);
        } else{
            assert(b==true);
        }
    }
}


