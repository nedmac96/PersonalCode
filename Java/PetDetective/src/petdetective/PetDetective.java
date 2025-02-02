/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petdetective;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class PetDetective
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        //Size 6row 4col
        int rows = 6;
        int cols = 4;
        int board[] =
        {

            12, 1, 8, 16,
            10, 3, 5, 4,
            20, 14, -1, 7,
            9, 6, 11, 13,
            0, 18, 15, 17,
            22, 19, 2, 21
        };
        /*
        0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
         */
        String connections[] =
        {
            "0-1", "0-4",
            "x1-2", "1-5",
            "2-3", "2-6",
            "3-7", "4-5",
            "4-8", "5-6",
            "x5-9", "6-7",
            "6-10", "7-11",
            "8-9", "8-12",
            "9-10", "9-13",
            "10-11", "10-14",
            "x11-15", "12-13",
            "x12-16", "13-14",
            "13-17", "14-15",
            "x14-18", "15-19",
            "16-17", "16-20",
            "17-18", "17-21",
            "x18-19", "18-22",
            "19-23", "x20-21",
            "21-22", "22-23"
        };
        /*
            "0-1", "0-4", 
            "1-2", "1-5", 
            "2-3", "2-6",
            "3-7", "4-5", 
            "4-8", "5-6", 
            "5-9", "6-7",
            "6-10", "7-11", 
            "8-9", "8-12", 
            "9-10", "9-13",
            "10-11", "10-14", 
            "11-15", "12-13", 
            "12-16", "13-14",
            "13-17", "14-15", 
            "14-18", "15-19", 
            "16-17", "16-20",
            "17-18", "17-21", 
            "18-19", "18-22", 
            "19-23", "20-21",
            "21-22", "22-23"
         */
        int currentBoard[] = new int[board.length];
        int totalFuel = 34;
        int carX = -1;
        int carY = -1;

        for (int j = 0; j < board.length; j++)
        {

            if (board[j] == -1)
            {
                carX = j % cols;
                carY = j / cols;
                currentBoard[j] = 0;
            } else
            {
                currentBoard[j] = board[j];
            }
        }
        int[] inCar =
        {
            -1, -1, -1, -1
        };
        solve(currentBoard, totalFuel, carX, carY, rows, cols, connections, new ArrayList<>(), inCar);
    }

    public static int move(int[] board, int fuel, int carX, int carY, int destination, int rows, int cols, String[] connections, ArrayList<Integer> usedLocations, int[] inCar)
    {
        int newFuel = -2;
        int destX = -1;
        int destY = -1;
        for (int i = 0; i < board.length; i++)
        {
            if (board[i] == destination)
            {
                destX = i % cols;
                destY = i / cols;
            }
        }

        int testFuel;
        if (!usedLocations.contains(carX + (carY * cols)))
        {
            ArrayList<Integer> copy = new ArrayList<>();
            for (int i = 0; i < usedLocations.size(); i++)
            {
                copy.add(usedLocations.get(i));
            }
            copy.add(carX + (carY * cols));
            if (!(carX == destX && carY == destY) && fuel > -1)
            {

                if ((inCar[0] != -1 && board[carX + (carY * cols)] == inCar[0] + 1) || (inCar[1] != -1 && board[carX + (carY * cols)] == inCar[1] + 1) || (inCar[2] != -1 && board[carX + (carY * cols)] == inCar[2] + 1) || (inCar[3] != -1 && board[carX + (carY * cols)] == inCar[3] + 1))
                {
                    return -1;
                } else
                {
                    newFuel = -1;
                    for (String connection : connections)
                    {
                        //right
                        if (connection.equals((carX + (carY * cols)) + "-" + (carX + (carY * cols) + 1)))
                        {
                            testFuel = move(board, fuel - 1, carX + 1, carY, destination, rows, cols, connections, copy, inCar);

                            if (testFuel > newFuel)
                            {
                                newFuel = testFuel;
                            }
                        }
                        //down
                        if (newFuel < fuel - (Math.abs(carX - destX) + Math.abs(carY - destY)))
                        {
                            if (connection.equals((carX + (carY * cols)) + "-" + (carX + ((carY + 1) * cols))))
                            {
                                testFuel = move(board, fuel - 1, carX, carY + 1, destination, rows, cols, connections, copy, inCar);
                                if (testFuel > newFuel)
                                {
                                    newFuel = testFuel;
                                }
                            }
                            if (newFuel < fuel - (Math.abs(carX - destX) + Math.abs(carY - destY)))
                            {
                                //left
                                if (connection.equals((carX + (carY * cols) - 1) + "-" + (carX + (carY * cols))))
                                {
                                    testFuel = move(board, fuel - 1, carX - 1, carY, destination, rows, cols, connections, copy, inCar);
                                    if (testFuel > newFuel)
                                    {
                                        newFuel = testFuel;
                                    }
                                }
                                if (newFuel < fuel - (Math.abs(carX - destX) + Math.abs(carY - destY)))
                                {
                                    //up
                                    if (connection.equals((carX + ((carY - 1) * cols)) + "-" + (carX + (carY * cols))))
                                    {
                                        testFuel = move(board, fuel - 1, carX, carY - 1, destination, rows, cols, connections, copy, inCar);
                                        if (testFuel > newFuel)
                                        {
                                            newFuel = testFuel;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (newFuel == -2)
            {
                newFuel = fuel;
            }
            return newFuel;

        } else
        {
            return -1;
        }
    }

    public static void solve(int[] board, int fuel, int carX, int carY, int rows, int cols, String[] connections, ArrayList<Integer> solutionPath, int[] inCar)
    {
        int score = 0;
        if (solutionPath.size() == 3)
        {
            System.out.println(solutionPath.get(0) + " " + solutionPath.get(1) + " " + solutionPath.get(2));
        }
        for (int i = 0; i < board.length; i++)
        {
            if (board[i] != 0)
            {
                score++;
            }
        }
        if (fuel > 0 && (score < fuel || score == fuel))
        {
            if (!(inCar[0] > 0 && inCar[1] > 0 && inCar[2] > 0 && inCar[3] > 0))
            {
                for (int i = 0; i < board.length; i++)
                {
                    if (board[i] % 2 == 1)
                    {

                        ArrayList<Integer> solution = new ArrayList<>();
                        for (int j = 0; j < solutionPath.size(); j++)
                        {
                            solution.add(solutionPath.get(j));
                        }
                        solution.add(board[i]);
                        int[] newCar =
                        {
                            -1, -1, -1, -1
                        };
                        System.arraycopy(inCar, 0, newCar, 0, inCar.length);
                        if (newCar[0] == -1)
                        {
                            newCar[0] = board[i];
                        } else if (newCar[1] == -1)
                        {
                            newCar[1] = board[i];
                        } else if (newCar[2] == -1)
                        {
                            newCar[2] = board[i];
                        } else if (newCar[3] == -1)
                        {
                            newCar[3] = board[i];
                        } else
                        {
                            System.out.println("New car ERROR!");
                            newCar[7] = 5;
                        }
                        int[] newBoard = new int[board.length];
                        System.arraycopy(board, 0, newBoard, 0, board.length);
                        newBoard[i] = 0;
                        solve(newBoard, move(board, fuel, carX, carY, board[i], rows, cols, connections, new ArrayList<>(), inCar), i % cols, i / cols, rows, cols, connections, solution, newCar);
                    }

                }
            }
            for (int i = 0; i < board.length; i++)
            {
                if (board[i] != 0 && (board[i] == inCar[0] + 1 || board[i] == inCar[1] + 1 || board[i] == inCar[2] + 1 || board[i] == inCar[3] + 1))
                {
                    ArrayList<Integer> solution = new ArrayList<>();
                    for (int j = 0; j < solutionPath.size(); j++)
                    {
                        solution.add(solutionPath.get(j));
                    }
                    solution.add(board[i]);
                    int[] newCar =
                    {
                        -1, -1, -1, -1
                    };
                    for (int j = 0; j < inCar.length; j++)
                    {
                        newCar[j] = inCar[j];
                        if (board[i] == newCar[j] + 1)
                        {
                            newCar[j] = -1;
                        }
                    }

                    int[] newBoard = new int[board.length];
                    System.arraycopy(board, 0, newBoard, 0, board.length);
                    newBoard[i] = 0;
                    solve(newBoard, move(board, fuel, carX, carY, board[i], rows, cols, connections, new ArrayList<>(), inCar), i % cols, i / cols, rows, cols, connections, solution, newCar);
                }
            }
        } else
        {
            if (fuel == 0)
            {
                score = 0;
                for (int i = 0; i < board.length; i++)
                {
                    if (board[i] == 0)
                    {
                        score++;
                    }
                }
                if (score == board.length)
                {
                    System.out.println(solutionPath);
                    System.exit(0);
                }
            }
        }

    }

}
