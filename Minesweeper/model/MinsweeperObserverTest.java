package Minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

import Minesweeper.view.MinesweeperObserver;

@Testable
public class MinsweeperObserverTest{
    //ObserverTest is in model package for simplicity
    
    public class FakeObs implements MinesweeperObserver{
        private Location l;

        @Override
        public void cellUpdated(Location l){
            this.l=l;
        }
    }

    @Test
    public void testObserver() throws Exception{
        //setup
        Minesweeper sweeper = new Minesweeper();
        MinesweeperObserver observer = new FakeObs();
        Gamestate expState = Gamestate.IN_PROGRESS;
        Gamestate expIfMine = Gamestate.LOST;
        Location testLoc = new Location(1, 1);
        
        //change
        sweeper.makeSelection(testLoc);
        observer.cellUpdated(testLoc);

        //check
        if(sweeper.getLocations().get(testLoc)=='m'){
            assertEquals(expIfMine, sweeper.getGameState()); //if location is mine, state should be LOST
        }
        else if(sweeper.getLocations().get(testLoc)=='s'){
            assertEquals(expState, sweeper.getGameState()); //if location is safe, state should be IN_PROGRESS
        }
        
    }

    
    
}
