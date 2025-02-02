/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package checkersgame;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class CheckersGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        CheckersGame game = new CheckersGame();
        game.runGame();
    }

    public void runGame()
    {
        final boolean STALEMATE_RESULTS_IN_TIE = true;

        Player player1 = new Player(3, true, 7);
        Player player2 = new Player(3, false, 7);
        int blackWins = 0;
        int redWins = 0;
        int ties = 0;
        boolean redGoesFirst = false;
        for (int i = 0; i < 2; i++)
        {

            Board board;
            ArrayList<Board> posMoves;

            redGoesFirst = !redGoesFirst;
            board = new Board(redGoesFirst);

            while (board.getResult(STALEMATE_RESULTS_IN_TIE) == 0 && board.getTurnNum() < 100)
            {
                posMoves = board.getPosMoves();
                if (board.isRedTurn())
                {
                    board = posMoves.get(player1.getMoveChoice(posMoves, STALEMATE_RESULTS_IN_TIE));
                } else
                {
                    board = posMoves.get(player2.getMoveChoice(posMoves, STALEMATE_RESULTS_IN_TIE));
                }
                System.out.println("Turn: " + board.getTurnNum() + "   Current Board:");
                board.printBoard();
                System.out.println("");
            }
            System.out.println("Final Board:");
            board.printBoard();
            System.out.println("Turn: " + board.getTurnNum());
            switch (board.getResult(STALEMATE_RESULTS_IN_TIE))
            {
                case 1:
                    System.out.println("Red Wins!");
                    redWins++;
                    break;
                case 2:
                    System.out.println("Black Wins!");
                    blackWins++;
                    break;
                default:
                    System.out.println("Tie");
                    ties++;
                    break;
            }
        }
        System.out.println("Red Wins: " + redWins);
        System.out.println("Black Wins " + blackWins);
        System.out.println("Ties " + ties);
    }

}
