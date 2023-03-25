package Minesweeper.model;

public class Location { 

    private int row;
    private int col;

    public Location (int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * returns row integer
     * @return
     *      row
     */
    public int getRow () {
        return row;
    }

    /**
     * returns column integer
     * @return
     *      col
     */
    public int getCol () {
        return col;
    }

    /**
     * checks if two objects are equal based on
     * rows and columns
     */
    @Override
    public boolean equals (Object o) {
        if (o instanceof Location) {
            Location newL = (Location) o;

            if (newL.row == this.row && newL.col == this.col) {
                return true;
            }
            
            else {
                return false;
            }
        }

        return false;
    }

    /**
     * @return
     *      hashcode for a location
     */
    @Override
    public int hashCode(){
        int hash = (int)Math.pow(Math.pow(row, 27), Math.pow(col, 31));
        return hash;
    }

    public String toString () {
        return "ROW:" + row + " | COL:" + col;
    }
}