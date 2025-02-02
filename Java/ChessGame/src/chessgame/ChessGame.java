/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jdk.jfr.Timespan;

/**
 *
 * @author Camden
 */
public class ChessGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        //String position = "rnbqkbnrppppppppxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxPPPPPPPPRNBQKBNR";
        String position = "rnbqkbnrppppppppxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxPPPPPPPPRNBQKBNR";
        System.out.println("your position is: ");
        printPosition(position);
        String blackPieces = "rnbqkp";
        String whitePieces = "RNBQKP";
        boolean whiteIsHuman = true;
        boolean blackIsHuman = false;
        int AIDepth = 2;
        boolean giveEva = true;
        boolean whiteToMove = true;
        boolean whiteCanCastle = true;
        boolean blackCanCastle = true;
        boolean winner = false;
        double turnNumber = 0;
        List<String> posiblePositions;
        while (!winner)
        {

            posiblePositions = getPosiblePositions(position, whiteCanCastle, blackCanCastle, whitePieces, blackPieces, whiteToMove, checkForCheck(position, whiteToMove, whiteCanCastle, blackCanCastle, whitePieces, blackPieces));
            int positionsRemoved = 0;
            int currentSize = posiblePositions.size();

            for (int i = 0; i < currentSize; i++)
            {
                if (checkForCheck(posiblePositions.get(i - positionsRemoved), whiteToMove, whiteCanCastle, blackCanCastle, whitePieces, blackPieces))
                {
                    posiblePositions.remove(i - positionsRemoved);
                    positionsRemoved++;

                }
            }

            if (posiblePositions.isEmpty())
            {
                if (checkForCheck(position, whiteToMove, whiteCanCastle, blackCanCastle, whitePieces, blackPieces))
                {
                    if (whiteToMove)
                    {
                        System.out.println("Checkmate Black Wins!");
                    } else
                    {
                        System.out.println("Checkmate White Wins!");
                    }

                } else
                {
                    System.out.println("The game is a draw by stalemate");

                }
                System.out.println("The final position is: ");
                printPosition(position);
                winner = true;
            } else
            {
                System.out.println("you have " + posiblePositions.size() + " legal moves");
            }

            if (!winner)
            {
                System.out.println("This is turn number " + turnNumber);
                if (whiteToMove)
                {
                    if (whiteIsHuman)
                    {
                        if (checkForCheck(position, whiteToMove, whiteCanCastle, blackCanCastle, whitePieces, blackPieces))
                        {
                            System.out.println("You are in Check!");
                        }
                        String currentPosition = position;
                        position = runHumanTurn(position, posiblePositions);
                        if (position.equals("AI"))
                        {
                            whiteIsHuman = false;
                            position = runAITurn(currentPosition, posiblePositions, AIDepth, whiteToMove, whiteCanCastle, blackCanCastle, giveEva);
                        }

                    } else
                    {
                        position = runAITurn(position, posiblePositions, AIDepth, whiteToMove, whiteCanCastle, blackCanCastle, giveEva);
                    }
                    whiteToMove = false;
                } else
                {
                    if (blackIsHuman)
                    {
                        if (checkForCheck(position, whiteToMove, whiteCanCastle, blackCanCastle, whitePieces, blackPieces))
                        {
                            System.out.println("You are in Check!");
                        }
                        String currentPosition = position;
                        position = runHumanTurn(position, posiblePositions);
                        if (position.equals("AI"))
                        {
                            blackIsHuman = false;
                            position = runAITurn(currentPosition, posiblePositions, AIDepth, whiteToMove, whiteCanCastle, blackCanCastle, giveEva);
                        }
                    } else
                    {
                        position = runAITurn(position, posiblePositions, AIDepth, whiteToMove, whiteCanCastle, blackCanCastle, giveEva);
                    }
                    whiteToMove = true;
                }
                turnNumber += 0.5;
                if (turnNumber == 60.0)
                {
                    AIDepth += 1;
                }

                if (position.charAt(60) != 'K')
                {
                    whiteCanCastle = false;
                }
                if (position.charAt(4) != 'k')
                {
                    blackCanCastle = false;
                }
            }

        }

    }

    public static void printPosition(String position)
    {
        System.out.println("");
        for (int i = 0; i < 64; i++)
        {
            System.out.print(position.charAt(i));
            System.out.print(" ");
            if ((i + 1) % 8 == 0)
            {
                System.out.println("");
            }
        }
        System.out.println("");

    }

    public static int getPositionEvaluation(String position)
    {
        int evaluation = 0;
        for (int i = 0; i < 64; i++)
        {
            switch (position.charAt(i))
            {
                case 'K':
                    evaluation += 1000;
                    break;
                case 'Q':
                    evaluation += 9;
                    break;
                case 'R':
                    evaluation += 5;
                    break;
                case 'N':
                    evaluation += 3;
                    break;
                case 'B':
                    evaluation += 3;
                    break;
                case 'P':
                    evaluation += 1;
                    break;
                case 'k':
                    evaluation -= 1000;
                    break;
                case 'q':
                    evaluation -= 9;
                    break;
                case 'r':
                    evaluation -= 5;
                    break;
                case 'b':
                    evaluation -= 3;
                    break;
                case 'n':
                    evaluation -= 3;
                    break;
                case 'p':
                    evaluation -= 1;
                    break;
                default:
                    break;
            }

        }
        return evaluation;
    }

    public static int getPositionEvaluationAI(String position)
    {
        double evaluation = 0;
        for (int i = 0; i < 64; i++)
        {
            switch (position.charAt(i))
            {
                case 'K':
                    evaluation += 1000;
                    break;
                case 'Q':
                    evaluation += 9;
                    evaluation += (0.9 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'R':
                    evaluation += 5;
                    evaluation += (0.5 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'N':
                    evaluation += 3;
                    evaluation += (0.3 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'B':
                    evaluation += 3.1;
                    evaluation += (0.3 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'P':
                    evaluation += 1;
                    break;
                case 'k':
                    evaluation -= 1000;
                    break;
                case 'q':
                    evaluation -= 9;
                    evaluation -= (0.9 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'r':
                    evaluation -= 5;
                    evaluation -= (0.5 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'b':
                    evaluation -= 3.1;
                    evaluation -= (0.3 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'n':
                    evaluation -= 3;
                    evaluation -= (0.3 * i * (-i / 64 + 1)) / 48;
                    break;
                case 'p':
                    evaluation -= 1;
                    break;
                default:
                    break;
            }

        }
        return (int) (evaluation);
    }

    public static String runHumanTurn(String position, List<String> posiblePositions)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The current position is: ");
        printPosition(position);
        System.out.println("white has " + getPositionEvaluation(position) + " more material than black");
        List<String> legalMoves = getLegalMoves(position, posiblePositions);
        System.out.println("Enter your move: ");
        String move = "";
        while (!legalMoves.contains(move))
        {
            move = scanner.next();
            if (!legalMoves.contains(move))
            {
                System.out.println("Invalid move try again");
            }
            if ("?".equals(move))
            {
                System.out.println("the legal moves are: ");
                for (int i = 0; i < legalMoves.size(); i++)
                {
                    System.out.println(legalMoves.get(i));
                }
            }

            if ("AI".equals(move))
            {
                break;
            }
        }
        if (!"AI".equals(move))
        {
            return posiblePositions.get(legalMoves.indexOf(move));
        } else
        {
            return "AI";
        }
    }

    public static String runAITurn(String position, List<String> posiblePositions, int depth, boolean whiteToMove, boolean whiteCanCastle, boolean blackCanCastle, boolean giveEva)
    {
        System.out.println("The current position is: ");
        printPosition(position);
        boolean whitesMove = whiteToMove;
        List<Integer> trail = new ArrayList<>();
        List<Integer> bestLine = new ArrayList<>();
        int bestValue = getPositionEvaluation(posiblePositions.get(0));
        int bestValueLocation = 0;
        if (depth <= 0)
        {

            for (int i = 1; i < posiblePositions.size(); i++)
            {
                if (whiteToMove)
                {
                    if (getPositionEvaluation(posiblePositions.get(i)) > bestValue)
                    {
                        bestValue = getPositionEvaluation(posiblePositions.get(i));
                        bestValueLocation = i;
                    }
                } else
                {
                    if (getPositionEvaluation(posiblePositions.get(i)) < bestValue)
                    {
                        bestValue = getPositionEvaluation(posiblePositions.get(i));
                        bestValueLocation = i;
                    }
                }
            }

        } else
        {
            System.out.println("Estimated AI think time: " + (int) (1 + posiblePositions.size() * posiblePositions.size() * posiblePositions.size() * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) * (depth / 2.0) / 5400) + " sec");
            trail = AIGoDeeper((depth * 2) - 1, (depth * 2) - 1, posiblePositions, !whitesMove, whiteCanCastle, blackCanCastle, false);
            //System.out.println(trail);
            //System.out.println(trail);
            if (giveEva)
            {
                if (whitesMove)
                {
                    System.out.println("The AI thinks the evaluation of this position is " + getLargest(trail));
                } else
                {
                    System.out.println("The AI thinks the evaluation of this position is " + getSmallest(trail));
                }
            }
            if (whitesMove)
            {
                bestValueLocation = trail.indexOf(getLargest(trail));
            } else
            {
                bestValueLocation = trail.indexOf(getSmallest(trail));
            }
            //System.out.println(bestValueLocation);
            //System.out.println(posiblePositions.size());
            System.out.println(getLegalMoves(position, posiblePositions).get(bestValueLocation));
        }

        return posiblePositions.get(bestValueLocation);
    }

    public static List<Integer> AIGoDeeper(int initialDepth, int remainingDepth, List<String> posiblePositions, boolean whitesMove, boolean whiteCanCastle, boolean blackCanCastle, boolean yourMove)
    {
        //System.out.println("start");
        List<Integer> values = new ArrayList<>();
        if (remainingDepth > 0)
        {
            for (int i = 0; i < posiblePositions.size(); i++)
            {

                if (whitesMove)
                {
                    values.add(getLargest(AIGoDeeper(initialDepth, remainingDepth - 1, getPosiblePositions(posiblePositions.get(i), whiteCanCastle, blackCanCastle, "RNBQKP", "rnbqkp", whitesMove, false), !whitesMove, true, true, !yourMove)));
                } else
                {
                    values.add(getSmallest(AIGoDeeper(initialDepth, remainingDepth - 1, getPosiblePositions(posiblePositions.get(i), whiteCanCastle, blackCanCastle, "RNBQKP", "rnbqkp", whitesMove, false), !whitesMove, true, true, !yourMove)));
                }

            }
        } else
        {
            for (int i = 0; i < posiblePositions.size(); i++)
            {
                values.add(getPositionEvaluation(posiblePositions.get(i)));
            }
        }
        return values;
    }

    public static int getLargest(List<Integer> list)
    {
        int largest = -2000;
        if (list.size() % 2 == 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i) > largest)
                {
                    largest = list.get(i);
                }
            }
        } else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(list.size() - i - 1) > largest)
                {
                    largest = list.get(list.size() - i - 1);
                }
            }
        }

        return largest;
    }

    public static int getSmallest(List<Integer> list)
    {
        int smallest = 2000;
        if (list.size() % 2 == 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i) < smallest)
                {
                    smallest = list.get(i);
                }
            }
        } else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(list.size() - i - 1) < smallest)
                {
                    smallest = list.get(list.size() - i - 1);
                }
            }
        }
        return smallest;
    }

    public static List<String> getPosiblePositions(String currentPosition, boolean whiteCanCastle, boolean blackCanCastle, String whitePieces, String blackPieces, boolean whiteToMove, boolean inCheck)
    {

        List<String> posiblePositions = new ArrayList<>();
        int numPieces = 0;
        String yourPieces;
        String thierPieces;
        if (whiteToMove)
        {
            yourPieces = whitePieces;
            thierPieces = blackPieces;
        } else
        {
            yourPieces = blackPieces;
            thierPieces = whitePieces;
        }

        for (int i = 0; i < 64; i++)
        {
            if (yourPieces.contains(String.valueOf(currentPosition.charAt(i))))
            {
                numPieces++;
            }

        }
        int lastPiecePosition = -1;
        int currentPiecePosition = -1;
        for (int i = 0; i < numPieces; i++)
        {

            int j = lastPiecePosition;
            while (currentPiecePosition == lastPiecePosition)
            {
                j++;
                if (yourPieces.contains(String.valueOf(currentPosition.charAt(j))))
                {

                    currentPiecePosition = j;
                }

            }
            boolean keepGoing = true;
            if (currentPosition.charAt(j) == yourPieces.charAt(0))
            {
                //if the piece is a rook
                findRookMoves(currentPosition, yourPieces, thierPieces, posiblePositions, keepGoing, currentPiecePosition, inCheck);

            } else if (currentPosition.charAt(j) == yourPieces.charAt(1))
            {
                //if the piece is a knight
                findKnightMoves(currentPosition, yourPieces, posiblePositions, currentPiecePosition, inCheck);

            } else if (currentPosition.charAt(j) == yourPieces.charAt(2))
            {
                //if the piece is a bishop
                findBishopMoves(currentPosition, yourPieces, thierPieces, posiblePositions, keepGoing, currentPiecePosition, inCheck);

            } else if (currentPosition.charAt(j) == yourPieces.charAt(3))
            {
                //if the piece is the queen
                findQueenMoves(currentPosition, yourPieces, thierPieces, posiblePositions, keepGoing, currentPiecePosition, inCheck);

            } else if (currentPosition.charAt(j) == yourPieces.charAt(4))
            {
                //if the piece is the king
                findKingMoves(currentPosition, yourPieces, posiblePositions, whiteToMove, whiteCanCastle, blackCanCastle, currentPiecePosition, inCheck);
                if (j != 60 && whiteToMove)
                {
                    whiteCanCastle = false;
                }
                if (j != 4 && !whiteToMove)
                {
                    blackCanCastle = false;
                }
            } else
            {
                //if the piece is a pawn
                findPawnMoves(currentPosition, posiblePositions, thierPieces, currentPiecePosition, whiteToMove, inCheck);
            }

            lastPiecePosition = currentPiecePosition;
        }

        return posiblePositions;
    }

    public static List<String> getLegalMoves(String currentPosition, List<String> posiblePositions)
    {
        List<String> legalMoves = new ArrayList<>();
        for (int i = 0; i < posiblePositions.size(); i++)
        {
            String move = "";
            List<Integer> changeLocations = new ArrayList<>();
            for (int j = 0; j < 64; j++)
            {
                if (posiblePositions.get(i).charAt(j) != currentPosition.charAt(j))
                {
                    changeLocations.add(j);
                }
            }
            String files = "hgfedcba";
            //int change1 = 71 - changeLocations.get(0);
            //int change2 = 71 - changeLocations.get(1);
            if (changeLocations.size() == 2)
            {
                int fromPosition;
                int toPosition;
                if (posiblePositions.get(i).charAt(changeLocations.get(0)) == 'x')
                {
                    fromPosition = changeLocations.get(0);
                    toPosition = changeLocations.get(1);
                } else
                {
                    fromPosition = changeLocations.get(1);
                    toPosition = changeLocations.get(0);
                }
                if (!"P".equals(String.valueOf(currentPosition.charAt(fromPosition)).toUpperCase()))
                {
                    //the piece is not a pawn
                    move = String.valueOf(currentPosition.charAt(fromPosition)).toUpperCase();
                    if (currentPosition.charAt(toPosition) != 'x')
                    {
                        move = move + "x";
                    }
                    move = move + files.charAt(7 - (toPosition % 8));
                    move = move + ((71 - toPosition) / 8);

                } else
                {
                    //the piece is a pawn
                    move = move + files.charAt(7 - (fromPosition % 8));
                    if (currentPosition.charAt(toPosition) != 'x')
                    {
                        move = move + "x";
                        move = move + files.charAt(7 - (toPosition % 8));
                    }
                    move = move + ((71 - toPosition) / 8);
                    if (posiblePositions.get(i).charAt(toPosition) != currentPosition.charAt(fromPosition))
                    {
                        move = move + "=" + posiblePositions.get(i).charAt(toPosition);
                    }
                }

                /*if (posiblePositions.get(i).charAt(changeLocations.get(0)) == 'x')
                {
                
                if (!"p".equals(String.valueOf(currentPosition.charAt(changeLocations.get(0))).toLowerCase()))
                {
                move = String.valueOf(posiblePositions.get(i).charAt(changeLocations.get(1))).toUpperCase();
                if (currentPosition.charAt(changeLocations.get(1)) != 'x')
                {
                move = move + "x";
                }
                move = move + files.charAt(change1 % 8);
                move = move + (change1 / 8);
                } else
                {
                
                move = "" + files.charAt(change1 % 8);
                if (currentPosition.charAt(changeLocations.get(1)) != 'x')
                {
                move = move + "x";
                move = move + files.charAt(change2 % 8);
                }
                if (currentPosition.charAt(changeLocations.get(0)) == posiblePositions.get(i).charAt(changeLocations.get(1)))
                {
                //move = move + files.charAt(change2 % 8);
                move = move + (change2 / 8);
                } else
                {
                move = move + "=" + posiblePositions.get(i).charAt(changeLocations.get(1));
                }
                }
                } else
                {
                System.out.println(String.valueOf(currentPosition.charAt(changeLocations.get(1))).toLowerCase());
                if (!"p".equals(String.valueOf(currentPosition.charAt(changeLocations.get(1))).toLowerCase()))
                {
                move = String.valueOf(posiblePositions.get(i).charAt(changeLocations.get(0))).toUpperCase();
                if (currentPosition.charAt(changeLocations.get(0)) != 'x')
                {
                move = move + "x";
                }
                move = move + files.charAt(change1 % 8);
                move = move + (change1 / 8);
                } else
                {
                move = "" + files.charAt(changeLocations.get(1) % 8);
                if (currentPosition.charAt(changeLocations.get(0)) != 'x')
                {
                move = move + "x";
                move = move + files.charAt(change2 % 8);
                }
                if (currentPosition.charAt(changeLocations.get(1)) == posiblePositions.get(i).charAt(changeLocations.get(0)))
                {
                //move = move + files.charAt(change1 % 8);
                move = move + (change1 / 8);
                } else
                {
                move = move + "=" + posiblePositions.get(i).charAt(change2);
                }
                }
                }*/
            } else if (changeLocations.size() == 3)
            {
                //en passant
            } else
            {
                //castle
                if (currentPosition.charAt(changeLocations.get(0)) == 'r' || currentPosition.charAt(changeLocations.get(0)) == 'R')
                {
                    move = "O-O-O";
                } else
                {
                    move = "O-O";
                }

            }

            //System.out.println(move);
            legalMoves.add(move);
        }

        return legalMoves;
    }

    public static List<String> getCurrentPositionList(String currentPosition)
    {

        List<String> position = new ArrayList<>();
        for (int i = 0; i < 64; i++)
        {
            position.add(String.valueOf(currentPosition.charAt(i)));
        }
        return position;
    }

    public static String getCurrentPositionString(List<String> currentPosition)
    {

        String position = "";
        for (int i = 0; i < 64; i++)
        {
            position = position + currentPosition.get(i);
        }
        return position;
    }

    public static String getNewPosition(String currentBoardPosition, int initialPosition, int finalPosition)
    {
        List<String> position = getCurrentPositionList(currentBoardPosition);
        position.set(finalPosition, String.valueOf(currentBoardPosition.charAt(initialPosition)));
        position.set(initialPosition, "x");

        return getCurrentPositionString(position);
    }

    public static String getNewPosition(String currentBoardPosition, int initialPosition, int finalPosition, String piece)
    {
        List<String> position = getCurrentPositionList(currentBoardPosition);
        position.set(finalPosition, piece);
        position.set(initialPosition, "x");

        return getCurrentPositionString(position);
    }

    public static boolean checkForCheck(String position, boolean checkWhite, boolean whiteCanCastle, boolean blackCanCastle, String whitePieces, String blackPieces)
    {
        boolean inCheck = false;
        List<String> positions = getPosiblePositions(position, whiteCanCastle, blackCanCastle, whitePieces, blackPieces, !checkWhite, false);
        if (checkWhite)
        {
            for (int i = 0; i < positions.size(); i++)
            {
                if (!positions.get(i).contains("K"))
                {
                    inCheck = true;
                }
            }

        } else
        {
            for (int i = 0; i < positions.size(); i++)
            {
                if (!positions.get(i).contains("k"))
                {
                    inCheck = true;
                }
            }
        }
        return inCheck;
    }

    public static void findPawnMoves(String currentPosition, List<String> posiblePositions, String thierPieces, int j, boolean whiteToMove, boolean inCheck)
    {
        if (whiteToMove)
        {

            if ("x".equals(String.valueOf(currentPosition.charAt(j - 8))))
            {
                if (Math.floor((j - 8) / 8) != 0)
                {
                    posiblePositions.add(getNewPosition(currentPosition, j, j - 8));
                    if (Math.floor(j / 8) == 6 && "x".equals(String.valueOf(currentPosition.charAt(j - 16))))
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 16));
                    }
                } else
                {
                    posiblePositions.add(getNewPosition(currentPosition, j, j - 8, "Q"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j - 8, "R"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j - 8, "N"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j - 8, "B"));
                }
            }

            if (j - 9 > -1 && j - 9 < 64)
            {
                if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - 9))) && (j - 9) % 8 < j % 8)
                {
                    if (Math.floor((j - 9) / 8) != 0)
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 9));
                    } else
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 9, "Q"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 9, "R"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 9, "N"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 9, "B"));
                    }
                }
            }
            if (j - 7 > -1 && j - 7 < 64)
            {
                if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - 7))) && (j - 7) % 8 > j % 8)
                {
                    if (Math.floor((j - 7) / 8) != 0)
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 7));
                    } else
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 7, "Q"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 7, "R"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 7, "N"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j - 7, "B"));
                    }
                }
            }

        } else
        {
            if ("x".equals(String.valueOf(currentPosition.charAt(j + 8))))
            {
                if (Math.floor((j + 8) / 8) != 7)
                {
                    posiblePositions.add(getNewPosition(currentPosition, j, j + 8));
                    if (Math.floor(j / 8) == 1 && "x".equals(String.valueOf(currentPosition.charAt(j + 16))))
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 16));
                    }
                } else
                {
                    posiblePositions.add(getNewPosition(currentPosition, j, j + 8, "q"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j + 8, "r"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j + 8, "n"));
                    posiblePositions.add(getNewPosition(currentPosition, j, j + 8, "b"));
                }
            }

            if (j + 9 > -1 && j + 9 < 64)
            {
                if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + 9))) && (j + 9) % 8 > j % 8)
                {
                    if (Math.floor((j + 9) / 8) != 7)
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 9));
                    } else
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 9, "q"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 9, "r"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 9, "n"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 9, "b"));
                    }
                }
            }
            if (j + 7 > -1 && j + 7 < 64)
            {
                if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + 7))) && (j + 7) % 8 < j % 8)
                {
                    if (Math.floor((j + 7) / 8) != 7)
                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 7));
                    } else

                    {
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 7, "q"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 7, "r"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 7, "n"));
                        posiblePositions.add(getNewPosition(currentPosition, j, j + 7, "b"));
                    }

                }
            }
        }
    }

    public static void findRookMoves(String currentPosition, String yourPieces, String thierPieces, List<String> posiblePositions, boolean keepGoing, int j, boolean inCheck)
    {
        for (int k = 0; k < 7; k++)
        {
            if (j + k + 1 > -1 && j + k + 1 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + k + 1))) && j % 8 < (j + k + 1) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + k + 1))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + k + 1));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - k - 1 > -1 && j - k - 1 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - k - 1))) && j % 8 > (j - k - 1) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - k - 1))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - k - 1));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 8 > -1 && j + (k + 1) * 8 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 8))) && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 8))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 8));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - (k + 1) * 8 > -1 && j - (k + 1) * 8 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 8))) && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 8))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - (k + 1) * 8));
                } else
                {
                    keepGoing = false;
                }
            }
        }
    }

    public static void findKnightMoves(String currentPosition, String yourPieces, List<String> posiblePositions, int j, boolean inCheck)
    {
        if (j - 17 > -1 && j - 17 < 64)
        {
            if ((j - 17) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 17))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 17));
            }
        }
        if (j - 15 > -1 && j - 15 < 64)
        {
            if ((j - 15) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 15))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 15));
            }
        }
        if (j - 10 > -1 && j - 10 < 64)
        {
            if ((j - 10) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 10))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 10));
            }
        }
        if (j - 6 > -1 && j - 6 < 64)
        {
            if ((j - 6) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 6))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 6));
            }
        }
        if (j + 17 > -1 && j + 17 < 64)
        {
            if ((j + 17) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 17))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 17));
            }
        }
        if (j + 15 > -1 && j + 15 < 64)
        {
            if ((j + 15) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 15))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 15));
            }
        }
        if (j + 10 > -1 && j + 10 < 64)
        {
            if ((j + 10) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 10))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 10));
            }
        }
        if (j + 6 > -1 && j + 6 < 64)
        {
            if ((j + 6) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 6))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 6));
            }
        }
    }

    public static void findBishopMoves(String currentPosition, String yourPieces, String thierPieces, List<String> posiblePositions, boolean keepGoing, int j, boolean inCheck)
    {
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 9 > -1 && j + (k + 1) * 9 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 9))) && j % 8 < (j + (k + 1) * 9) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 9))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 9));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 7 > -1 && j + (k + 1) * 7 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 7))) && j % 8 > (j + (k + 1) * 7) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 7))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 7));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - ((k + 1) * 7) > -1 && j - ((k + 1) * 7) < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - ((k + 1) * 7)))) && j % 8 < (j - ((k + 1) * 7)) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - ((k + 1) * 7)))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - ((k + 1) * 7)));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - (k + 1) * 9 > -1 && j - (k + 1) * 9 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 9))) && j % 8 > (j - (k + 1) * 9) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 9))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - (k + 1) * 9));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }

        }
    }

    public static void findQueenMoves(String currentPosition, String yourPieces, String thierPieces, List<String> posiblePositions, boolean keepGoing, int j, boolean inCheck)
    {
        for (int k = 0; k < 7; k++)
        {
            if (j + k + 1 > -1 && j + k + 1 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + k + 1))) && j % 8 < (j + k + 1) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + k + 1))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + k + 1));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - k - 1 > -1 && j - k - 1 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - k - 1))) && j % 8 > (j - k - 1) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - k - 1))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - k - 1));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 8 > -1 && j + (k + 1) * 8 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 8))) && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 8))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 8));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - (k + 1) * 8 > -1 && j - (k + 1) * 8 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 8))) && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 8))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - (k + 1) * 8));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 9 > -1 && j + (k + 1) * 9 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 9))) && j % 8 < (j + (k + 1) * 9) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 9))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 9));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j + (k + 1) * 7 > -1 && j + (k + 1) * 7 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 7))) && j % 8 > (j + (k + 1) * 7) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j + (k + 1) * 7))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j + (k + 1) * 7));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - (k + 1) * 7 > -1 && j - (k + 1) * 7 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 7))) && j % 8 < (j - (k + 1) * 7) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 7))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - (k + 1) * 7));
                } else
                {
                    keepGoing = false;
                }
            } else
            {
                keepGoing = false;
            }
        }
        keepGoing = true;
        for (int k = 0; k < 7; k++)
        {
            if (j - (k + 1) * 9 > -1 && j - (k + 1) * 9 < 64)
            {
                if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 9))) && j % 8 > (j - (k + 1) * 9) % 8 && keepGoing)
                {
                    if (thierPieces.contains(String.valueOf(currentPosition.charAt(j - (k + 1) * 9))))
                    {
                        keepGoing = false;
                    }
                    posiblePositions.add(getNewPosition(currentPosition, j, j - (k + 1) * 9));
                } else
                {
                    keepGoing = false;
                }
            }
        }
    }

    public static void findKingMoves(String currentPosition, String yourPieces, List<String> posiblePositions, boolean whiteToMove, boolean whiteCanCastle, boolean blackCanCastle, int j, boolean inCheck)
    {
        if (j - 9 > -1 && j - 9 < 64)
        {
            if ((j - 9) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 9))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 9));
            }
        }
        if (j - 8 > -1 && j - 8 < 64)
        {
            if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j - 8))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 8));
            }
        }
        if (j - 7 > -1 && j - 7 < 64)
        {
            if ((j - 7) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 7))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 7));
            }
        }
        if (j - 1 > -1 && j - 1 < 64)
        {
            if ((j - 1) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j - 1))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j - 1));
            }
        }
        if (j + 1 > -1 && j + 1 < 64)
        {
            if ((j + 1) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 1))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 1));
            }
        }
        if (j + 7 > -1 && j + 7 < 64)
        {
            if ((j + 7) % 8 < j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 7))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 7));
            }
        }
        if (j + 8 > -1 && j + 8 < 64)
        {
            if (!yourPieces.contains(String.valueOf(currentPosition.charAt(j + 8))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 8));
            }
        }
        if (j + 9 > -1 && j + 9 < 64)
        {
            if ((j + 9) % 8 > j % 8 && !yourPieces.contains(String.valueOf(currentPosition.charAt(j + 9))))
            {
                posiblePositions.add(getNewPosition(currentPosition, j, j + 9));
            }
        }

        if (j == 60 && "x".equals(String.valueOf(currentPosition.charAt(61))) && "x".equals(String.valueOf(currentPosition.charAt(62))) && "R".equals(String.valueOf(currentPosition.charAt(63))) && whiteToMove && whiteCanCastle)
        {
            posiblePositions.add(getNewPosition(getNewPosition(currentPosition, 60, 62), 63, 61));
        }
        if (j == 60 && "x".equals(String.valueOf(currentPosition.charAt(59))) && "x".equals(String.valueOf(currentPosition.charAt(58))) && "x".equals(String.valueOf(currentPosition.charAt(57))) && "R".equals(String.valueOf(currentPosition.charAt(56))) && whiteToMove && whiteCanCastle)
        {
            posiblePositions.add(getNewPosition(getNewPosition(currentPosition, 60, 58), 56, 59));
        }
        if (j == 4 && "x".equals(String.valueOf(currentPosition.charAt(5))) && "x".equals(String.valueOf(currentPosition.charAt(6))) && "r".equals(String.valueOf(currentPosition.charAt(7))) && !whiteToMove && blackCanCastle)
        {
            posiblePositions.add(getNewPosition(getNewPosition(currentPosition, 4, 6), 7, 5));
        }
        if (j == 4 && "x".equals(String.valueOf(currentPosition.charAt(3))) && "x".equals(String.valueOf(currentPosition.charAt(2))) && "x".equals(String.valueOf(currentPosition.charAt(1))) && "r".equals(String.valueOf(currentPosition.charAt(0))) && !whiteToMove && blackCanCastle)
        {
            posiblePositions.add(getNewPosition(getNewPosition(currentPosition, 4, 2), 0, 3));
        }
    }
}
