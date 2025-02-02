/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package checkersgame;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class Player
{
    
    private boolean isRed;
    /*
    strategy:
    0 - human
     */
    private int strategy;
    private int depth;
    
    public Player(int strategy, boolean isRed, int depth)
    {
        this.strategy = strategy;
        this.isRed = isRed;
        this.depth = depth;
    }
    
    public int getMoveChoice(ArrayList<Board> posMoves, boolean STALEMATE_RESULTS_IN_TIE)
    {
        if (strategy == 0)
        {
            return getHumanChoice(posMoves);
        } else
        {
            return getAIChoice(posMoves, STALEMATE_RESULTS_IN_TIE);
        }
    }
    
    public int getHumanChoice(ArrayList<Board> posMoves)
    {
        Scanner input = new Scanner(System.in);
        if (isRed)
        {
            System.out.println("Your turn. (Red)");
        } else
        {
            System.out.println("Your turn. (Black)");
        }
        printMoves(posMoves);
        boolean moveChosen = false;
        int move = -1;
        while (!moveChosen)
        {
            System.out.println("Choose a move: ");
            move = input.nextInt() - 1;
            if (move >= 0 && move < posMoves.size())
            {
                moveChosen = true;
            } else
            {
                System.out.println("Invalid move. Try again.");
            }
        }
        return move;
    }
    
    public int getAIChoice(ArrayList<Board> posMoves, boolean STALEMATE_RESULTS_IN_TIE)
    {
        if (strategy < 3)
        {
            double bestScore = recursiveEvaluation(posMoves.get(0), depth, STALEMATE_RESULTS_IN_TIE);
            
            int bestMoveId = 0;
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation(posMoves.get(i), depth, STALEMATE_RESULTS_IN_TIE);
                if ((isRed && evaluation > bestScore) || (!isRed && evaluation < bestScore))
                {
                    bestScore = evaluation;
                    bestMoveId = i;
                } else if (evaluation == bestScore && Math.random() < 0.3)
                {
                    bestMoveId = i;
                }
            }
            //System.out.println("Evaluation: " + bestScore);
            return bestMoveId;
        } else if (strategy == 3)
        {
            double bestScore = recursiveEvaluation2(posMoves.get(0), depth, STALEMATE_RESULTS_IN_TIE);
            ArrayList<Board> equalMoves = new ArrayList<>();
            int bestMoveId = 0;
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation2(posMoves.get(i), depth, STALEMATE_RESULTS_IN_TIE);
                if ((isRed && evaluation > bestScore) || (!isRed && evaluation < bestScore))
                {
                    equalMoves.clear();
                    bestScore = evaluation;
                    bestMoveId = i;
                } else if (evaluation == bestScore)
                {
                    if (!posMoves.get(bestMoveId).boardListContains(equalMoves, posMoves.get(bestMoveId)))
                    {
                        equalMoves.add(posMoves.get(bestMoveId));
                    }
                    equalMoves.add(posMoves.get(i));
                    if (Math.random() < 0.3)
                    {
                        bestMoveId = i;
                    }
                    
                }
            }
            System.out.println("Evaluation: " + bestScore);
            if (!equalMoves.isEmpty())
            {
                System.out.println("Equal Moves: ");
                printMoves(equalMoves);
            }
            
            return bestMoveId;
        } else
        {
            System.out.println("Invalid strategy. Defaults to first choice.");
            return 0;
        }
    }
    
    public double evaluateBoard(Board board, boolean STALEMATE_RESULTS_IN_TIE)
    {
        switch (board.getResult(STALEMATE_RESULTS_IN_TIE))
        {
            case 0:
                double[] values;
                values = switch (strategy)
                {
                    case 1 ->
                        new double[]
                        {
                            0.0, 1.0, 1.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
                        };
                    case 2 ->
                        new double[]
                        {
                            0.0, 1.0, 1.5, 0.02, 0.0, 0.01, 0.2, 0.01, 0.01
                        };
                    default ->
                        new double[]
                        {
                            0.0, 1.0, 1.5, 0.02, 0.0, 0.01, 0.1, 0.01, 0.01
                        };
                };
                double TURN_SCORE = values[0];
                
                double PIECE_SCORE = values[1];
                double KING_SCORE = values[2];
                
                double PIECE_DISTANCE_SCORE = values[3];
                double KING_DISTANCE_SCORE = values[4];
                
                double PIECE_CENTER_SCORE = values[5];
                double KING_CENTER_SCORE = values[6];
                
                double PIECE_EDGE_SCORE = values[7];
                double KING_EDGE_SCORE = values[8];
                
                double score = 0;
                if (board.isRedTurn())
                {
                    score += TURN_SCORE;
                } else
                {
                    score -= TURN_SCORE;
                }
                
                for (int i = 0; i < board.getPosition().size(); i++)
                {
                    int piece = board.getPosition().get(i);
                    switch (piece)
                    {
                        case 1:
                            score += PIECE_SCORE;
                            score += PIECE_DISTANCE_SCORE * (7 - (i / 4));
                            score += PIECE_CENTER_SCORE * ((7.0 - Math.abs(3.5 - (i / 4))) + (7.0 - Math.abs(3.5 - (((i % 4) * 2) + 1 - ((i / 4) % 2)))));
                            if (i < 4 || i > 27 || i % 8 == 4 || i % 8 == 3)
                            {
                                score += PIECE_EDGE_SCORE;
                            }
                            break;
                        case 2:
                            score -= PIECE_SCORE;
                            score -= PIECE_DISTANCE_SCORE * (i / 4);
                            score -= PIECE_CENTER_SCORE * ((7.0 - Math.abs(3.5 - (i / 4))) + (7.0 - Math.abs(3.5 - (((i % 4) * 2) + 1 - ((i / 4) % 2)))));
                            if (i < 4 || i > 27 || i % 8 == 4 || i % 8 == 3)
                            {
                                score -= PIECE_EDGE_SCORE;
                            }
                            break;
                        case 3:
                            score += KING_SCORE;
                            score += KING_DISTANCE_SCORE * (7 - (i / 4));
                            score += KING_CENTER_SCORE * ((7.0 - Math.abs(3.5 - (i / 4))) + (7.0 - Math.abs(3.5 - (((i % 4) * 2) + 1 - ((i / 4) % 2)))));
                            if (i < 4 || i > 27 || i % 8 == 4 || i % 8 == 3)
                            {
                                score += KING_EDGE_SCORE;
                            }
                            break;
                        case 4:
                            score -= KING_SCORE;
                            score -= KING_DISTANCE_SCORE * (i / 4);
                            score -= KING_CENTER_SCORE * ((7.0 - Math.abs(3.5 - (i / 4))) + (7.0 - Math.abs(3.5 - (((i % 4) * 2) + 1 - ((i / 4) % 2)))));
                            if (i < 4 || i > 27 || i % 8 == 4 || i % 8 == 3)
                            {
                                score -= KING_EDGE_SCORE;
                            }
                            break;
                        default:
                            break;
                    }
                }
                score = Math.round(((40 - (board.getTurnNum() / 10)) / 40.0) * score * 10000.0) / 10000.0;
                return score;
            
            case 1:
                return 100.0;
            case 2:
                return -100.0;
            default:
                return 0;
        }
    }
    
    private double recursiveEvaluation(Board board, int remainingDepth, boolean STALEMATE_RESULTS_IN_TIE)
    {
        double NOW_SCORE = 0.1;
        double OPTIONS_SCORE = 0.01;
        if (remainingDepth == 0)
        {
            return evaluateBoard(board, STALEMATE_RESULTS_IN_TIE);
        }
        switch (board.getResult(STALEMATE_RESULTS_IN_TIE))
        {
            case 1:
                return 100.0;
            case 2:
                return -100.0;
            case 3:
                return 0;
            default:
                break;
        }
        ArrayList<Board> posMoves = board.getPosMoves();
        double bestScore = recursiveEvaluation(posMoves.get(0), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
        double curEval = evaluateBoard(board, STALEMATE_RESULTS_IN_TIE);
        if (Math.abs(curEval) > 7)
        {
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation(posMoves.get(i), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
                if ((board.isRedTurn() && evaluation > 90.0) || (!board.isRedTurn() && evaluation < -90.0))
                {
                    return evaluation * 0.99;
                }
                if (Math.abs(evaluation) > Math.abs(curEval))
                {
                    double bonus = curEval * NOW_SCORE;
                    if (board.isRedTurn())
                    {
                        bonus += posMoves.size() * OPTIONS_SCORE;
                    } else
                    {
                        bonus -= posMoves.size() * OPTIONS_SCORE;
                    }
                    return Math.round((evaluation + bonus) * 10000.0) / 10000.0;
                }
                if ((board.isRedTurn() && evaluation > bestScore) || (!board.isRedTurn() && evaluation < bestScore))
                {
                    bestScore = evaluation;
                }
            }
        } else
        {
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation(posMoves.get(i), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
                if ((board.isRedTurn() && evaluation > 90.0) || (!board.isRedTurn() && evaluation < -90.0))
                {
                    return evaluation * 0.99;
                }
                if ((board.isRedTurn() && evaluation > bestScore) || (!board.isRedTurn() && evaluation < bestScore))
                {
                    bestScore = evaluation;
                }
            }
        }
        
        double bonus = curEval * NOW_SCORE;
        if (board.isRedTurn())
        {
            bonus += posMoves.size() * OPTIONS_SCORE;
        } else
        {
            bonus -= posMoves.size() * OPTIONS_SCORE;
        }
        return Math.round((bestScore + bonus) * 10000.0) / 10000.0;
    }
    
    private double recursiveEvaluation2(Board board, int remainingDepth, boolean STALEMATE_RESULTS_IN_TIE)
    {
        double NOW_SCORE = 0.1;
        double OPTIONS_SCORE = 0.01;
        if (remainingDepth == 0)
        {
            return evaluateBoard(board, STALEMATE_RESULTS_IN_TIE);
        }
        switch (board.getResult(STALEMATE_RESULTS_IN_TIE))
        {
            case 1:
                return 100.0;
            case 2:
                return -100.0;
            case 3:
                return 0;
            default:
                break;
        }
        ArrayList<Board> posMoves = board.getPosMoves();
        double bestScore = recursiveEvaluation2(posMoves.get(0), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
        double curEval = evaluateBoard(board, STALEMATE_RESULTS_IN_TIE);
        if (Math.abs(curEval) > 7)
        {
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation2(posMoves.get(i), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
                if ((board.isRedTurn() && evaluation > 10.0) || (!board.isRedTurn() && evaluation < -10.0))
                {
                    return evaluation * 0.99;
                }
                if (Math.abs(evaluation) > Math.abs(curEval))
                {
                    double bonus = curEval * NOW_SCORE;
                    if (board.isRedTurn())
                    {
                        bonus += posMoves.size() * OPTIONS_SCORE;
                    } else
                    {
                        bonus -= posMoves.size() * OPTIONS_SCORE;
                    }
                    return Math.round((evaluation + bonus) * 10000.0) / 10000.0;
                }
                if ((board.isRedTurn() && evaluation > bestScore) || (!board.isRedTurn() && evaluation < bestScore))
                {
                    bestScore = evaluation;
                }
            }
        } else
        {
            for (int i = 1; i < posMoves.size(); i++)
            {
                double evaluation = recursiveEvaluation2(posMoves.get(i), remainingDepth - 1, STALEMATE_RESULTS_IN_TIE);
                if ((board.isRedTurn() && evaluation > 90.0) || (!board.isRedTurn() && evaluation < -90.0))
                {
                    return evaluation * 0.99;
                }
                if ((board.isRedTurn() && evaluation > bestScore) || (!board.isRedTurn() && evaluation < bestScore))
                {
                    bestScore = evaluation;
                }
            }
        }
        
        double bonus = curEval * NOW_SCORE;
        if (board.isRedTurn())
        {
            bonus += posMoves.size() * OPTIONS_SCORE;
        } else
        {
            bonus -= posMoves.size() * OPTIONS_SCORE;
        }
        return Math.round((bestScore + bonus) * 10000.0) / 10000.0;
    }
    
    public void printMoves(ArrayList<Board> moves)
    {
        String text;
        text = "";
        for (int i = 0; i < moves.size(); i++)
        {
            text = text + (i + 1) + ".                 ";
            if (i < 8)
            {
                text = text + " ";
            }
        }
        System.out.println(text);
        /*
        for (int i = 0; i < posMoves.size(); i++)
        {
            System.out.println("Eva: " + evaluateBoard(posMoves.get(i), isRed));
        }*/
        for (int i = 0; i < 8; i++)
        {
            text = "";
            for (int j = 0; j < moves.size(); j++)
            {
                if (i % 2 == 0)
                {
                    for (int k = 0; k < 4; k++)
                    {
                        text = text + "  " + moves.get(j).getPosition().get(i * 4 + k) + " ";
                    }
                    text = text + "";
                } else
                {
                    text = text + "";
                    for (int k = 0; k < 4; k++)
                    {
                        text = text + "" + moves.get(j).getPosition().get(i * 4 + k) + "   ";
                    }
                }
                text = text + "    ";
            }
            System.out.println(text);
        }
    }
    
}
