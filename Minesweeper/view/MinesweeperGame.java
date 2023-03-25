package Minesweeper.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Minesweeper.model.Gamestate;
import Minesweeper.model.Location;
import Minesweeper.model.MinesweeperConfig;
import Minesweeper.model.MinesweeperException;
import backtracker.Backtracker;
import backtracker.Configuration;

public class MinesweeperGame {    
    public static void main (String [] args) throws Exception {
    
        Scanner scan = new Scanner (System.in);
        System.out.println();
        MinesweeperConfig m = new MinesweeperConfig();
        String selection = "";

        while (!selection.equals("quit")) {
            System.out.println(m.toString() + "\n");
            System.out.println(MinesweeperGame.menuPrompt());

            try {
            selection = scan.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("You must enter a word!");
            }

            switch (selection) {
                case ("help"):
                    System.out.println(MinesweeperGame.menuPrompt());
                    break;
                case ("move"):
                    System.out.print("Please enter a single row/col you wish to choose: ");            
                    try {
                        int newRow = scan.nextInt();
                        int newCol = scan.nextInt();
                        scan.nextLine();
                        m.makeSelection(new Location (newRow, newCol));

                        if (m.getGameState() == Gamestate.WON) {
                            System.out.println("YOU WON!!");
                            System.exit(0);
                        } 
                            
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must enter a valid row and col");
                    }
                    catch (MinesweeperException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case ("hint"):
                    Random r = new Random ();
                    System.out.println("Here's a possible guess:");
                    List <Location> list =  (List<Location>) m.getPossibleSelections();
                    Location l = list.get(r.nextInt(list.size()));

                    if (m.getMoveCount() == (m.getRow()*m.getCol())-m.getMines()-1) 
                        System.out.println("Cannot give any more hints!");
                        else if (m.getLocations().get(l) == 's')
                            System.out.println(l);
                    break;
                case ("reset"):
                    m = new MinesweeperConfig ();
                    System.out.println("Your board has been reset!"); 
                    break;
                case ("quit"):
                    System.out.println("Goodbeye!");
                    System.exit(0);
                    break;
                case ("solve"):
                    Backtracker b =  new Backtracker(false);
                    Configuration sol = b.solve(m);
                    if (sol != null) {
                        m.setGamestate(Gamestate.WON);
                        System.out.println("YOU WON!!");
                        System.exit(0);
                    }
                    System.out.println(sol);
                break;    
                default:
                    System.out.println("You mush choose from the avialble menu options!\n");           
            }

            if (m.getGameState() == Gamestate.LOST) {
                System.out.println("GAME OVER D:");
                System.exit(0);
            }
        }

        scan.close();
    }

    /**
     * Provides a menu for command line interaction
     * @return
     *      menu for game
     */
    private static String menuPrompt () {
        StringBuilder s = new StringBuilder();
        s.append("Please choose from the following options:\n");
        s.append("TYPE help - list of commands\n");
        s.append("TYPE move - make your board move\n");
        s.append("TYPE hint - shows all your available guess\n");
        s.append("TYPE reset - resets board with same bpard parameters\n");
        s.append("TYPE quit - exit game\n");
        s.append("TYPE solve - solve game\n");
        return s.toString();
    }

}
