/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rubixcubesolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class RubixCubeSolver
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RubixCubeSolver solver = new RubixCubeSolver();
        solver.solve();
    }

    /*
    White - 0
    Yellow - 1
    Red - 2
    Orange - 3
    Green - 4
    Blue - 5
     */
    public void solve()
    {
        int[][] solvedPos =
        {
            {
                0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            {
                1, 1, 1, 1, 1, 1, 1, 1, 1
            },
            {
                2, 2, 2, 2, 2, 2, 2, 2, 2
            },
            {
                3, 3, 3, 3, 3, 3, 3, 3, 3
            },
            {
                4, 4, 4, 4, 4, 4, 4, 4, 4
            },
            {
                5, 5, 5, 5, 5, 5, 5, 5, 5
            }
        };
        Position solvedPosition = new Position(solvedPos);
        //findSolutionWithSteps(pos);
        //findNumPosMoves(solvedPosition);
        findNumPosSideMoves(solvedPosition);
//        for (Position pos : solvedPosition.getPosSideMoves())
//        {
//            pos.printPos();
//        }
    }

    public Position getStartingPosition(Position solvedPos)
    {
        Scanner scnr = new Scanner(System.in);
        /*
        0 = use coded position
        1 = get pos from keyboard
        2 = use solved position
         */
        int inputType = 1;
        int[][] startingPos;
        if (inputType == 0)
        {
            startingPos = new int[][]
            {
                {
                    4, 0, 0, 4, 0, 0, 4, 0, 0
                },
                {
                    5, 5, 5, 1, 1, 1, 1, 1, 1
                },
                {
                    1, 4, 4, 2, 2, 2, 2, 2, 2
                },
                {
                    5, 5, 0, 3, 3, 3, 3, 3, 3
                },
                {
                    3, 3, 3, 1, 4, 4, 1, 4, 4
                },
                {
                    2, 2, 2, 5, 5, 0, 5, 5, 0
                }
            };
        } else if (inputType == 1)
        {
            startingPos = new int[6][9];
            String nextLine;
            int color1;
            int color2;
            int color3;
            for (int i = 0; i < 6; i++)
            {
                switch (i)
                {
                    case 0 ->
                        System.out.println("W (G up)");
                    case 1 ->
                        System.out.println("Y (B up)");
                    case 2 ->
                        System.out.println("R (Y up)");
                    case 3 ->
                        System.out.println("O (Y up)");
                    case 4 ->
                        System.out.println("G (Y up)");
                    case 5 ->
                        System.out.println("B (Y up)");
                    default ->
                        throw new AssertionError();
                }
                System.out.println("Enter 3 letters");
                for (int j = 0; j < 3; j++)
                {
                    nextLine = scnr.nextLine();
                    if (nextLine.length() == 3)
                    {
                        color1 = converChar(nextLine.charAt(0));
                        color2 = converChar(nextLine.charAt(1));
                        color3 = converChar(nextLine.charAt(2));
                    } else
                    {
                        color1 = -1;
                        color2 = -1;
                        color3 = -1;
                    }
                    while (color1 == -1 || color2 == -1 || color3 == -1)
                    {
                        System.out.println("Invalid input try again");
                        nextLine = scnr.nextLine();
                        if (nextLine.length() == 3)
                        {
                            color1 = converChar(nextLine.charAt(0));
                            color2 = converChar(nextLine.charAt(1));
                            color3 = converChar(nextLine.charAt(2));
                        } else
                        {
                            color1 = -1;
                            color2 = -1;
                            color3 = -1;
                        }
                    }
                    startingPos[i][j * 3 + 0] = color1;
                    startingPos[i][j * 3 + 1] = color2;
                    startingPos[i][j * 3 + 2] = color3;
                }
            }
            for (int[] side : startingPos)
            {
                System.out.println(Arrays.toString(side));
            }
        } else
        {
            return solvedPos;
        }

        Position pos = new Position(startingPos);

        if (!pos.isValid())
        {
            System.out.println("Invalid Cube");
        }
        if (pos.isSolved())
        {
            System.out.println("Already Solved");
        }
        return pos;
    }

    public void findShortestSolution(Position solvedPosition, Position pos)
    {
        int maxDepth = 0;
        ArrayList<Position> solutionPath = new ArrayList<>();
        ArrayList<Position> seenPositions = new ArrayList<>();
        ArrayList<Position> solvedPositions = new ArrayList<>();

        int depthUsed;
        while (maxDepth < 20)
        {

            maxDepth++;
            depthUsed = maxDepth;
            seenPositions.clear();
            solvedPositions.clear();
            recursiveFindSolvedPositions(maxDepth, solvedPosition, null, solvedPositions, seenPositions);

            seenPositions.clear();
            solutionPath.clear();
            if (!recursiveFindSolution(maxDepth, solutionPath, pos, null, solvedPositions, seenPositions))
            {
                System.out.println("No Solution Found in " + maxDepth + " moves");
            } else
            {
                Position lastPos;
                int depth = maxDepth;
                while (depth > 1)
                {
                    lastPos = solutionPath.get(solutionPath.size() - 1);
                    depth = depth / 2 + depth % 2;
                    seenPositions.clear();
                    solvedPositions.clear();
                    recursiveFindSolvedPositions(depth, solvedPosition, null, solvedPositions, seenPositions);

                    seenPositions.clear();
                    solutionPath.clear();
                    recursiveFindSolution(depth, solutionPath, lastPos, null, solvedPositions, seenPositions);
                    depthUsed += depth - 1;
                }
                lastPos = solutionPath.get(solutionPath.size() - 1);
                recursiveFindSolution(1, solutionPath, lastPos, null, solvedPositions, seenPositions);
                System.out.println(depthUsed * 2 + " moves");
                return;
            }
        }
    }

    public boolean recursiveFindSolution(int depthRemaining, ArrayList<Position> solutionPath, Position pos, Position prevPos, ArrayList<Position> solvedPositions, ArrayList<Position> seenPositions)
    {
        solutionPath.add(pos);
        if (pos.isSolved())
        {
            return true;
        } else if (depthRemaining < 2 && solvedPositions.contains(pos))
        {
            return true;
        }
        if (depthRemaining > 0)
        {
            seenPositions.add(pos);
            ArrayList<Position> posMoves = pos.getPosMoves();
            for (int i = 0; i < posMoves.size(); i++)
            {
                if (!posMoves.get(i).equals(prevPos))
                {
                    if (recursiveFindSolution(depthRemaining - 1, solutionPath, posMoves.get(i), pos, solvedPositions, seenPositions))
                    {
                        switch (i / 2)
                        {
                            case 0 ->
                                System.out.print("W");
                            case 1 ->
                                System.out.print("Y");
                            case 2 ->
                                System.out.print("R");
                            case 3 ->
                                System.out.print("O");
                            case 4 ->
                                System.out.print("G");
                            case 5 ->
                                System.out.print("B");
                            default ->
                                throw new AssertionError();
                        }
                        if (i % 2 == 0)
                        {
                            System.out.println("R");
                        } else
                        {
                            System.out.println("L");
                        }
                        return true;
                    }
                }
            }
            seenPositions.remove(pos);
        }
        solutionPath.remove(pos);
        return false;
    }

    public boolean alreadyUsed(Position pos, ArrayList<Position> seenPositions)
    {
        for (Position seenPosition : seenPositions)
        {
            if (seenPosition.isTheSame(pos))
            {
                return true;
            }
        }
        return false;
    }

    public void printPositions(ArrayList<Position> positions)
    {
        for (Position pos : positions)
        {
            for (int[] position : pos.getPosition())
            {
                System.out.println(Arrays.toString(position));
            }
            System.out.println("");
        }
    }

    public int converChar(char color)
    {
        switch (color)
        {
            case 'W' ->
            {
                return 0;
            }
            case 'Y' ->
            {
                return 1;
            }
            case 'R' ->
            {
                return 2;
            }
            case 'O' ->
            {
                return 3;
            }
            case 'G' ->
            {
                return 4;
            }
            case 'B' ->
            {
                return 5;
            }
            default ->
            {
                return -1;
            }
        }
    }

    public void recursiveFindSolvedPositions(int depthRemaining, Position pos, Position prevPos, ArrayList<Position> solvedPositions, ArrayList<Position> seenPositions)
    {
        if (!solvedPositions.contains(pos))
        {
            solvedPositions.add(pos);
        }
        if (depthRemaining > 0)
        {
            seenPositions.add(pos);
            ArrayList<Position> posMoves = pos.getPosMoves();
            for (int i = 0; i < posMoves.size(); i++)
            {
                if (!posMoves.get(i).equals(prevPos))
                {
                    recursiveFindSolvedPositions(depthRemaining - 1, posMoves.get(i), pos, solvedPositions, seenPositions);
                }
            }
            seenPositions.remove(pos);
        }
    }

    public void findSolutionWithSteps(Position position)
    {
        int maxStepDepth = 6;
        int stepDepth;
        Position prevPos = null;
        ArrayList<Position> seenPositions = new ArrayList<>();
        int moveCount = 0;
        while (!position.isSolved())
        {
            moveCount++;
            stepDepth = maxStepDepth;
            seenPositions.add(position);
            int bestScore = 0;
            Position bestMove = null;
            int move = -1;
            ArrayList<Position> posMoves = position.getPosMoves();
            for (int i = 0; i < posMoves.size(); i++)
            {
                Position posMove = posMoves.get(i);
                if (!posMove.equals(prevPos) && !seenPositions.contains(posMove))
                {
                    int moveScore = recursiveStep(stepDepth, posMove, prevPos);
                    if (moveScore > bestScore)
                    {
                        bestMove = posMove;
                        bestScore = moveScore;
                        move = i;
                    } else if (moveScore == bestScore)
                    {
                        if (Math.random() > 0.5)
                        {
                            bestMove = posMove;
                            bestScore = moveScore;
                            move = i;
                        }
                    }

                }
            }
            if (move == -1)
            {
                System.out.println("No more moves to try");
                throw new AssertionError();
            }
            prevPos = position;
            position = bestMove;

            switch (move / 2)
            {
                case 0 ->
                    System.out.print("W");
                case 1 ->
                    System.out.print("Y");
                case 2 ->
                    System.out.print("R");
                case 3 ->
                    System.out.print("O");
                case 4 ->
                    System.out.print("G");
                case 5 ->
                    System.out.print("B");
                default ->
                    throw new AssertionError();
            }
            if (move % 2 == 0)
            {
                System.out.print("R");
            } else
            {
                System.out.print("L");
            }
            System.out.println(" Score: " + bestScore);
        }
    }

    public int recursiveStep(int depthRemaining, Position pos, Position prevPos)
    {
        int score = pos.score();
        if (depthRemaining > 0)
        {
            int bestScore = score;
            for (Position posMove : pos.getPosMoves())
            {
                if (!posMove.equals(prevPos))
                {
                    int moveScore = recursiveStep(depthRemaining - 1, posMove, pos);
                    if (moveScore >= bestScore)
                    {
                        bestScore = moveScore;
                    }

                }
            }
            score = bestScore;
        }
        return score - 1;
    }

    public void findNumPosMoves(Position solvedPosition)
    {
        ArrayList<Position> prevPositions = new ArrayList<>();
        ArrayList<Position> currentPositions;
        ArrayList<Position> allPositions = new ArrayList<>();
        prevPositions.add(solvedPosition);
        allPositions.add(solvedPosition);
        for (int i = 0; i < 10; i++)
        {
            currentPositions = new ArrayList<>();
            for (Position pos : prevPositions)
            {
                for (Position posMove : pos.getPosMoves())
                {
                    if (!allPositions.contains(posMove))
                    {
                        allPositions.add(posMove);
                        currentPositions.add(posMove);
                    }
                }
            }
            System.out.println("Turn: " + (i + 1) + ", Possible move combinations: " + currentPositions.size());
            prevPositions = currentPositions;
        }
    }

    public void findNumPosSideMoves(Position solvedPosition)
    {
        ArrayList<Position> prevPositions = new ArrayList<>();
        ArrayList<Position> currentPositions;
        ArrayList<Position> allPositions = new ArrayList<>();
        prevPositions.add(solvedPosition);
        allPositions.add(solvedPosition);
        for (int i = 0; i < 10; i++)
        {
            currentPositions = new ArrayList<>();
            for (Position pos : prevPositions)
            {
                for (Position posMove : pos.getPosSideMoves())
                {
                    if (!allPositions.contains(posMove))
                    {
                        allPositions.add(posMove);
                        currentPositions.add(posMove);
                    }
                }
            }
            System.out.println("Turn: " + (i + 1) + ", Possible move combinations: " + currentPositions.size());
            prevPositions = currentPositions;
        }
    }
}
