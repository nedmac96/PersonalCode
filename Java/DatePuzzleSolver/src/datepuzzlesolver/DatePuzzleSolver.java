/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datepuzzlesolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Camden
 */
public class DatePuzzleSolver
{

    private int monthToSolve;
    private int dayToSolve;
    private int solutionsFound = 0;
    private int[][] squareUses = new int[49][11];

    public DatePuzzleSolver(int[][] squareUsesArray)
    {
        squareUses = squareUsesArray;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        List monthList = new ArrayList();
        Calendar cal = new GregorianCalendar(2023, Calendar.JANUARY, 1);

        int[][] squareUses = new int[49][11];

        List tempList;
        String tempString = null, tempString2 = null;
        int minSolutions = -1, maxSolutions = -1, solutions;
        for (int i = 0; i < 12; i++)
        {
            tempList = new ArrayList<Integer>();
            monthList.add(tempList);
            //while (i == cal.get(Calendar.MONTH))'
            for (int j = 0; j < 31; j++)
            {
                DatePuzzleSolver solver = new DatePuzzleSolver(squareUses);
                solver.setMonthToSolve(i);
                solver.setDayToSolve(j);
                solutions = solver.solvePuzzle();
                if (-1 == minSolutions || solutions < minSolutions)
                {
                    // (very bad code)
                    tempString = "Month: " + i + " Day: " + j;
                    minSolutions = solutions;
                    //System.out.println("Best Date = " + tempString + " with " + minSolutions + " solutions.");

                }
                if (-1 == maxSolutions || solutions > maxSolutions)
                {
                    // (very bad code)
                    tempString2 = "Month: " + i + " Day: " + j;
                    maxSolutions = solutions;
                    //System.out.println("Worst Date = " + tempString2 + " with " + maxSolutions + " solutions.");
                }
                tempList.add(solutions);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                System.out.println("Month: " + i + " Day: " + j + " Solutions Found: " + tempList.get(j));
            }
            /*for (int x = 0; x < tempList.size(); x++)
            {
                // print out the list
                System.out.println(" " + i + " " + x + " " + tempList.get(x));
            }*/
        }
        System.out.println("Best Date = " + tempString + " with " + minSolutions + " solutions.");
        System.out.println("Worst Date = " + tempString2 + " with " + maxSolutions + " solutions.");
    }

    public void setMonthToSolve(int newMonth)
    {
        this.monthToSolve = newMonth;
    }

    public void setDayToSolve(int newDay)
    {
        this.dayToSolve = newDay;
    }

    public int solvePuzzle()
    {
        this.solutionsFound = 0;
        Square[] board = new Square[49];
        int dayId;
        int monthId;

        if (dayToSolve < 28)
        {
            dayId = dayToSolve + 14;
        } else
        {
            dayId = dayToSolve + 16;
        }

        if (monthToSolve < 6)
        {
            monthId = monthToSolve;
        } else
        {
            monthId = monthToSolve + 1;
        }
        for (int i = 0; i < board.length; i++)
        {
            board[i] = new Square(i);
            if (i == dayId || i == monthId)
            {
                board[i].fillSquare(new Piece(10));
            }
        }
        testPiece(new Piece(0), board);
        if (dayToSolve == 30)
        {
            for (int i = 0; i < 7; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    System.out.println(Arrays.toString(squareUses[i * 7 + j]));
                }
                System.out.println("");
            }
        }
        return solutionsFound;
    }

    public Square[] testPiece(Piece piece, Square[] board)
    {

        board = testRotation(piece, board);
        if (boardIsFilled(board))
        {
            return board;
        }
        while (piece.canBeRotated())
        {
            piece.rotate();
            board = testRotation(piece, board);
        }
        if (piece.canBeFlipped())
        {
            piece.flip();
            board = testRotation(piece, board);
            while (piece.canBeRotated())
            {
                piece.rotate();
                board = testRotation(piece, board);
            }
        }
        return board;
    }

    public Square[] addPiece(Piece piece, int pos, Square[] board)
    {
        Square[] updatedBoard = board;
        for (int i = 0; i < piece.getSquaresUsed().length; i++)
        {
            updatedBoard[pos + piece.getSquaresUsed()[i]].fillSquare(piece);
        }
        return updatedBoard;
    }

    public Square[] testRotation(Piece piece, Square[] board)
    {
        for (int i = 0; i < 8 - piece.getWidth(); i++)
        {
            for (int j = 0; j < 8 - piece.getHeight(); j++)
            {
                int pos = (j * 7) + i;
                int blockedSquares = 0;
                for (int k = 0; k < piece.getSquaresUsed().length; k++)
                {
                    if (board[pos + piece.getSquaresUsed()[k]].getCurrentStatus().equals("blocked"))
                    {
                        blockedSquares++;
                    }
                }
                if (blockedSquares == 0)
                {
                    board = addPiece(piece, pos, board);
                    if (boardIsFilled(board) && piece.getId() == 8)
                    {
                        solutionsFound++;
                        for (int m = 0; m < board.length; m++)
                        {
                            squareUses[m][board[m].getPiece().getId()]++;
                        }
                        //printBoard(board);
                    } else
                    {
                        board = testPiece(new Piece(piece.getId() + 1), board);
                    }

                    board = deletePiece(piece, pos, board);
                }
            }
        }
        return board;
    }

    public Square[] deletePiece(Piece piece, int pos, Square[] board)
    {
        Square[] updatedBoard = board;
        for (int i = 0; i < piece.getSquaresUsed().length; i++)
        {
            updatedBoard[pos + piece.getSquaresUsed()[i]].emptySquare();
        }
        return updatedBoard;
    }

    public boolean boardIsFilled(Square[] board)
    {
        int squaresLeft = 49;
        for (Square board1 : board)
        {
            if (board1.getCurrentStatus().equals("blocked"))
            {
                squaresLeft--;
            }
        }
        return squaresLeft == 0;
    }

    public void printBoard(Square[] board)
    {
        System.out.println("Solution number " + solutionsFound);
        for (int i = 0; i < 7; i++)
        {
            String text = "";
            for (int j = 0; j < 7; j++)
            {
                text = text + board[(i * 7) + j].getPiece();
            }
            System.out.println(text);
        }
        System.out.println("");
    }

}
