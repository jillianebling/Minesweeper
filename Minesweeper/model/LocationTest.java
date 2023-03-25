package Minesweeper.model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class LocationTest {
    
    @Test
    public void testEquals(){
        Location l = new Location(0, 0);
        Location b = new Location(0,0);
        Location o = new Location(1,1);
        assert(l.equals(o)==false);
        assert(l.equals(b)==true);
    }

    @Test
    public void testHashCode(){
        Location l = new Location(0, 0);
        int code= l.hashCode();
        assert(code==1);
    }

}
