/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connectfourgame;

import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class ConnectFourGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        ConnectFourGame game = new ConnectFourGame();
        game.runGame();
    }

    public void runGame()
    {
        boolean redIsHuman = false;
        boolean yellowIsHuman = false;
        int AIDepth = 7;

        Column[] board = new Column[7];
        for (int i = 0; i < board.length; i++)
        {
            board[i] = new Column();
        }
        //boolean isRedTurn = (Math.round(Math.random()) == 0);
        boolean isRedTurn = true;
        int turn = 0;
        while (isThereAWinner(board) == 0 && turn < 42)
        {

            turn++;
            if (turn > AIDepth)
            {
                if (turn > 21)
                {
                    AIDepth = 43 - turn;
                } else if (turn % 4 == 0)
                {
                    AIDepth++;
                }
            }
            if (isRedTurn)
            {
                if (redIsHuman)
                {
                    printBoard(board);
                    System.out.println("It is red's turn");
                    board[runHumanTurn(board, 1)].addToken(1);
                } else
                {
                    board[runAITurn(board, 1, AIDepth)].addToken(1);
                }
                isRedTurn = false;
            } else
            {
                if (yellowIsHuman)
                {
                    printBoard(board);
                    System.out.println("It is yellow's turn");
                    board[runHumanTurn(board, 2)].addToken(2);
                } else
                {
                    board[runAITurn(board, 2, AIDepth)].addToken(2);
                }
                isRedTurn = true;
            }

        }
        switch (isThereAWinner(board))
        {
            case 1 ->
                System.out.println("Red wins!");
            case 2 ->
                System.out.println("Yellow wins!");
            default ->
                System.out.println("It is a tie");
        }
        System.out.println("Final board: ");
        printBoard(board);
    }

    public int evaluateBoard(Column[] board)
    {
        // check if there is already a winner
        if (isThereAWinner(board) != 0)
        {
            if (isThereAWinner(board) == 1)
            {
                return 1000;
            } else
            {
                return -1000;
            }
        }

        int score = 0;
        int redCount;
        int yellowCount;
        // check verticle sets
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                redCount = 0;
                yellowCount = 0;
                if (board[i].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i].getSlot(j + 1) == 1)
                {
                    redCount++;
                }
                if (board[i].getSlot(j + 2) == 1)
                {
                    redCount++;
                }
                if (board[i].getSlot(j + 3) == 1)
                {
                    redCount++;
                }

                if (board[i].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i].getSlot(j + 1) == 2)
                {
                    yellowCount++;
                }
                if (board[i].getSlot(j + 2) == 2)
                {
                    yellowCount++;
                }
                if (board[i].getSlot(j + 3) == 2)
                {
                    yellowCount++;
                }

                if (yellowCount == 0)
                {
                    score += redCount * redCount;
                } else if (redCount == 0)
                {
                    score -= yellowCount * yellowCount;
                }
            }
        }

        // check horizantal sets
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                redCount = 0;
                yellowCount = 0;
                if (board[i].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i + 1].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i + 2].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i + 3].getSlot(j) == 1)
                {
                    redCount++;
                }

                if (board[i].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 1].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 2].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 3].getSlot(j) == 2)
                {
                    yellowCount++;
                }

                if (yellowCount == 0)
                {
                    if (redCount != 0)
                    {
                        score += redCount * redCount;
                        if (j < 4)
                        {
                            score += 2;
                        }
                    }
                } else if (redCount == 0)
                {
                    score -= yellowCount * yellowCount;
                    if (j < 4)
                    {
                        score -= 2;
                    }
                }
            }
        }

        // check diagnol sets
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                redCount = 0;
                yellowCount = 0;
                if (board[i].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i + 1].getSlot(j + 1) == 1)
                {
                    redCount++;
                }
                if (board[i + 2].getSlot(j + 2) == 1)
                {
                    redCount++;
                }
                if (board[i + 3].getSlot(j + 3) == 1)
                {
                    redCount++;
                }

                if (board[i].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 1].getSlot(j + 1) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 2].getSlot(j + 2) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 3].getSlot(j + 3) == 2)
                {
                    yellowCount++;
                }

                if (yellowCount == 0)
                {
                    score += redCount * redCount;
                } else if (redCount == 0)
                {
                    score -= yellowCount * yellowCount;
                }

                redCount = 0;
                yellowCount = 0;
                if (board[i + 3].getSlot(j) == 1)
                {
                    redCount++;
                }
                if (board[i + 2].getSlot(j + 1) == 1)
                {
                    redCount++;
                }
                if (board[i + 1].getSlot(j + 2) == 1)
                {
                    redCount++;
                }
                if (board[i].getSlot(j + 3) == 1)
                {
                    redCount++;
                }

                if (board[i + 3].getSlot(j) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 2].getSlot(j + 1) == 2)
                {
                    yellowCount++;
                }
                if (board[i + 1].getSlot(j + 2) == 2)
                {
                    yellowCount++;
                }
                if (board[i].getSlot(j + 3) == 2)
                {
                    yellowCount++;
                }

                if (yellowCount == 0)
                {
                    score += redCount * redCount;
                } else if (redCount == 0)
                {
                    score -= yellowCount * yellowCount;
                }
            }
        }
        return score;
    }

    public int isThereAWinner(Column[] board)
    {
        // check verticle sets
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i].getSlot(j) != 0 && board[i].getSlot(j) == board[i].getSlot(j + 1) && board[i].getSlot(j) == board[i].getSlot(j + 2) && board[i].getSlot(j) == board[i].getSlot(j + 3))
                {
                    return board[i].getSlot(j);
                }
            }
        }

        // check horizantal sets
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                if (board[i].getSlot(j) != 0 && board[i].getSlot(j) == board[i + 1].getSlot(j) && board[i].getSlot(j) == board[i + 2].getSlot(j) && board[i].getSlot(j) == board[i + 3].getSlot(j))
                {
                    return board[i].getSlot(j);
                }
            }
        }

        // check diagnol sets
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i].getSlot(j) != 0 && board[i].getSlot(j) == board[i + 1].getSlot(j + 1) && board[i].getSlot(j) == board[i + 2].getSlot(j + 2) && board[i].getSlot(j) == board[i + 3].getSlot(j + 3))
                {
                    return board[i].getSlot(j);
                }

                if (board[i + 3].getSlot(j) != 0 && board[i + 3].getSlot(j) == board[i + 2].getSlot(j + 1) && board[i + 3].getSlot(j) == board[i + 1].getSlot(j + 2) && board[i + 3].getSlot(j) == board[i].getSlot(j + 3))
                {
                    return board[i + 3].getSlot(j);
                }
            }
        }
        return 0;
    }

    public int runHumanTurn(Column[] board, int color)
    {
        // return the column number a human plays in
        boolean validAnswer = false;
        int answer = 0;
        Scanner keyboard = new Scanner(System.in);
        while (!validAnswer)
        {
            System.out.println("Enter the column: ");
            answer = keyboard.nextInt();
            if (answer > -1 && answer < 7 && board[answer].isNotFull())
            {
                validAnswer = true;
            } else
            {
                System.out.println("Invalid column");
            }
        }
        return answer;
    }

    public int runAITurn(Column[] board, int color, int depth)
    {
        // return the column number an AI plays in
        Column[] nextBoard = board;
        int nextColor;
        if (color == 1)
        {
            nextColor = 2;
        } else
        {
            nextColor = 1;
        }
        int boardScore;
        int bestScore;
        if (color == 1)
        {
            bestScore = -1001;
        } else
        {
            bestScore = 1001;
        }
        int bestCol = 0;
        for (int i = 0; i < board.length; i++)
        {
            if (nextBoard[i].isNotFull())
            {
                nextBoard[i].addToken(color);
                boardScore = calculateBoard(nextBoard, nextColor, depth - 1);
                System.out.println("Column " + i + " value is " + boardScore);
                if (color == 1)
                {
                    if (boardScore == 1000)
                    {
                        System.out.println("Red (AI) did column: " + i);
                        nextBoard[i].removeToken();
                        return i;
                    }
                    if (boardScore > bestScore)
                    {
                        bestScore = boardScore;
                        bestCol = i;
                    }
                } else
                {
                    if (boardScore == -1000)
                    {
                        System.out.println("Yellow (AI) did column: " + i);
                        nextBoard[i].removeToken();
                        return i;
                    }
                    if (boardScore < bestScore)
                    {
                        bestScore = boardScore;
                        bestCol = i;
                    }
                }
                nextBoard[i].removeToken();
            }
        }
        if (color == 1)
        {
            System.out.println("Red (AI) did column: " + bestCol);
        } else
        {
            System.out.println("Yellow (AI) did column: " + bestCol);
        }
        return bestCol;
    }

    public int calculateBoard(Column[] board, int color, int depthRemaining)
    {
        if (isThereAWinner(board) == 0 && depthRemaining > 0)
        {
            Column[] nextBoard = board;
            int nextColor;
            if (color == 1)
            {
                nextColor = 2;
            } else
            {
                nextColor = 1;
            }
            int boardScore;
            int bestScore;
            if (color == 1)
            {
                bestScore = -1001;
            } else
            {
                bestScore = 1001;
            }
            for (int i = 0; i < board.length; i++)
            {
                if (nextBoard[i].isNotFull())
                {
                    nextBoard[i].addToken(color);
                    boardScore = calculateBoard(nextBoard, nextColor, depthRemaining - 1);
                    if (color == 1)
                    {
                        if (boardScore == 1000)
                        {
                            nextBoard[i].removeToken();
                            return boardScore;
                        }
                        if (boardScore > bestScore)
                        {
                            bestScore = boardScore;
                        }
                    } else
                    {
                        if (boardScore == -1000)
                        {
                            nextBoard[i].removeToken();
                            return boardScore;
                        }
                        if (boardScore < bestScore)
                        {
                            bestScore = boardScore;
                        }
                    }
                    nextBoard[i].removeToken();
                }
            }
            return bestScore;
        } else
        {
            return evaluateBoard(board);
        }
    }

    public void printBoard(Column[] board)
    {
        String text;
        for (int j = 5; j > -1; j--)
        {
            text = "";
            for (int i = 0; i < 7; i++)
            {
                text = text + board[i].getSlot(j) + " ";
            }
            System.out.println(text);
        }
    }
}
