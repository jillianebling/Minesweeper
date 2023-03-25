package Minesweeper.model;

public class MinesweeperException extends Exception{
    
    /**
     * Thorws exceptions with the passed in message
     * @param message
     *      message to throw when error is thrown
     * @throws Exception
     */
    public MinesweeperException(String message) {
        super(message);
    }
}
