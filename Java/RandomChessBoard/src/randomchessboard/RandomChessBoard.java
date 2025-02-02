/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomchessboard;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class RandomChessBoard
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        RandomChessBoard boardMaker = new RandomChessBoard();
        boardMaker.generateBoard();
    }

    public void generateBoard()
    {
        int[] board = null;
        boolean validBoard = false;
        int runs = 0;
        while (!validBoard)
        {
            runs++;
            board = makeBoard();
            validBoard = checkBoard(board, false);

        }
        printBoard(board);
        System.out.println("runs: " + runs);
    }

    public int[] makeBoard()
    {

        int[] board = new int[64];
        double[] pieceFreq = new double[13];
        for (int i = 0; i < 64; i++)
        {
            boolean isWhite = true;
            double total = 0;
            for (int j = 0; j < 13; j++)
            {

                Piece piece = getPiece(j);

                pieceFreq[j] = 1.0 * piece.getFreq() * piece.getPosFreq(i, isWhite) / piece.getTotalPosFreq();

                total += pieceFreq[j];
                if (j == 6)
                {
                    isWhite = false;
                }
            }
            double randNum = Math.random() * total;
            int j = -1;
            while (randNum > 0)
            {
                randNum -= pieceFreq[++j];
            }
            board[i] = j;
        }
        return board;
    }

    public boolean checkBoard(int[] board, boolean allowKingInCheck)
    {
        // One king per side
        int numTypeWhite = 0;
        int numTypeBlack = 0;
        int testNum = 6;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                numTypeWhite++;
            }
            if (board[i] == testNum + 6)
            {
                numTypeBlack++;
            }
        }
        if (numTypeWhite != 1 || numTypeBlack != 1)
        {
            return false;
        }
        // no more than 8 pawns
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 1;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                numTypeWhite++;
            }
            if (board[i] == testNum + 6)
            {
                numTypeBlack++;
            }
        }
        if (numTypeWhite > 8 || numTypeBlack > 8)
        {
            return false;
        }
        //no more than 1 queen
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 5;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                numTypeWhite++;
            }
            if (board[i] == testNum + 6)
            {
                numTypeBlack++;
            }
        }
        if (numTypeWhite > 1 || numTypeBlack > 1)
        {
            return false;
        }
        //no more than 2 knights
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 2;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                numTypeWhite++;
            }
            if (board[i] == testNum + 6)
            {
                numTypeBlack++;
            }
        }
        if (numTypeWhite > 2 || numTypeBlack > 2)
        {
            return false;
        }
        //no more than 2 rooks
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 4;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                numTypeWhite++;
            }
            if (board[i] == testNum + 6)
            {
                numTypeBlack++;
            }
        }
        if (numTypeWhite > 2 || numTypeBlack > 2)
        {
            return false;
        }
        //no more than 2 bishops, 1 per color square for white
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 3;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                if (((i / 8 + i) % 2) == 0)
                {
                    numTypeWhite++;
                }
                if (((i / 8 + i) % 2) == 1)
                {
                    numTypeBlack++;
                }
            }
        }
        if (numTypeWhite > 1 || numTypeBlack > 1)
        {
            return false;
        }
        //no more than 2 bishops 1 per color square for black
        numTypeWhite = 0;
        numTypeBlack = 0;
        testNum = 9;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == testNum)
            {
                if (((i / 8 + i) % 2) == 0)
                {
                    numTypeWhite++;
                }
                if (((i / 8 + i) % 2) == 1)
                {
                    numTypeBlack++;
                }
            }
        }
        if (numTypeWhite > 1 || numTypeBlack > 1)
        {
            return false;
        }
        //check if white king is in check
        int boardChaos = 0;
        int kingPos = 0;
        boolean whiteInCheck = false;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == 6)
            {
                kingPos = i;
            }
        }

        for (int i = 0; i < 64; i++)
        {

            if (board[i] > 6)
            {
                int[] squaresAttacked = getPiece(board[i]).attacking(i, false);

                for (int j = 0; j < squaresAttacked.length; j++)
                {
                    if (board[squaresAttacked[j]] < 7)
                    {
                        boardChaos += getPiece(board[squaresAttacked[j]]).getValue();
                    }

                    if (squaresAttacked[j] == kingPos)
                    {
                        whiteInCheck = true;
                    }
                }
            }

        }
        //check if black king is in check
        kingPos = 0;
        boolean blackInCheck = false;
        for (int i = 0; i < 64; i++)
        {
            if (board[i] == 12)
            {
                kingPos = i;
            }
        }

        for (int i = 0; i < 64; i++)
        {
            if (board[i] < 7 && board[i] > 0)
            {
                int[] squaresAttacked = getPiece(board[i]).attacking(i, true);

                for (int j = 0; j < squaresAttacked.length; j++)
                {
                    if (board[squaresAttacked[j]] > 6)
                    {
                        boardChaos += getPiece(board[squaresAttacked[j]]).getValue();
                    }
                    if (squaresAttacked[j] == kingPos)
                    {
                        blackInCheck = true;
                    }
                }
            }
        }
        if (((blackInCheck && whiteInCheck) || (!allowKingInCheck && (blackInCheck || whiteInCheck))))
        {
            return false;
        } else
        {
            System.out.println("Board Chaos: " + boardChaos);
            return true;
        }
        //return !((blackInCheck && whiteInCheck) || (!allowKingInCheck && (blackInCheck || whiteInCheck)));
    }

    public void printBoard(int[] board)
    {
        String text;
        for (int i = 0; i < 8; i++)
        {
            text = "";
            for (int j = 0; j < 8; j++)
            {
                text = text + getPiece(board[i * 8 + j]).getSymbol(board[i * 8 + j] < 7) + " ";
            }
            System.out.println(text);
        }
        text = "";
        boolean emptySpaces = false;
        int numSpaces = 0;
        for (int i = 7; i > -1; i--)
        {
            if (i != 7)
            {
                text = text + "/";
            }

            for (int j = 7; j > -1; j--)
            {
                if (board[i * 8 + j] == 0)
                {
                    if (emptySpaces)
                    {
                        numSpaces++;
                    } else
                    {
                        emptySpaces = true;
                        numSpaces = 1;
                    }
                } else
                {
                    if (emptySpaces)
                    {
                        text = text + numSpaces;
                        emptySpaces = false;
                    }
                    text = text + getPiece(board[i * 8 + j]).getSymbol(board[i * 8 + j] < 7);
                }

            }
            if (emptySpaces)
            {
                text = text + numSpaces;
                emptySpaces = false;
            }

        }
        System.out.println(text);
        text = "";
        for (int i = 0; i < board.length; i++)
        {
            char value = getPiece(board[i]).getSymbol(board[i] < 7);
            if (value == '_')
            {
                text = text + 'x';
            } else if (Character.isUpperCase(value))
            {
                text = text + value;
            } else if (value == 'b')
            {
                text = text + 'V';
            } else if (value == 'r')
            {
                text = text + 'T';
            } else if (value == 'n')
            {
                text = text + 'M';
            } else if (value == 'k')
            {
                text = text + 'L';
            } else if (value == 'q')
            {
                text = text + 'W';
            } else if (value == 'p')
            {
                text = text + 'O';
            }
        }
        System.out.println(text);

    }

    public Piece getPiece(int pieceNumber)
    {
        Piece piece;
        if (pieceNumber == 0)
        {
            piece = new Piece("empty");
        } else
        {
            switch (1 + ((pieceNumber - 1) % 6))
            {
                case 1 ->
                    piece = new Piece("pawn");
                case 2 ->
                    piece = new Piece("night");
                case 3 ->
                    piece = new Piece("bishop");
                case 4 ->
                    piece = new Piece("rook");
                case 5 ->
                    piece = new Piece("queen");
                default ->
                    piece = new Piece("king");
            }
        }
        return piece;
    }

}
