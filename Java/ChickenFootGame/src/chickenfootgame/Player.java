/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chickenfootgame;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class Player
{

    private ArrayList<Domino> hand;
    private int strategy;
    private int score;
    private int wins;

    /*
    strategy = 1 -> play the first legal domino
    strategy = 2 -> play the domino worth the most
    strategy = 3 -> play the domino worth the least
    strategy = 4 -> play highest double, then highest
    strategy = 5 -> play highest if smallest hand size > 5 else play highest double, then highest
    strategy = 6 -> plat highest double, then highest block 0s, then highest
    strategy = 7 -> block 0s, then play double, then highest
    strategy = 8 -> play highest double, then by calcScore based on doubles played and matching dominos in hand
    strategy = 9 -> same as 8 but with direction
    
    play highest/lowest
    play double
    save for other doubles
    give you more options
    give you options for doubles
    make more options/remove options
    block 0s
    someone close to getting out
    
     */
    public Player(int strategy)
    {
        this.strategy = strategy;
        hand = new ArrayList<>();
        score = 0;
        wins = 0;
    }

    public void addDomino(Domino domino)
    {
        hand.add(domino);
    }

    public void removeDomino(Domino domino)
    {
        hand.remove(domino);
    }

    public boolean hasDouble(int num)
    {
        boolean hasDouble = false;
        for (int i = 0; i < hand.size(); i++)
        {
            if (hand.get(i).getNum1() == num && hand.get(i).getNum2() == num)
            {
                hasDouble = true;
            }
        }
        return hasDouble;
    }

    public void draw(Board board)
    {
        Domino domino = board.drawDomino();
        if (domino.getNum1() != -1)
        {
            hand.add(domino);
        }
    }

    public void drawHand(Board board)
    {
        hand.clear();
        for (int i = 0; i < 7; i++)
        {
            draw(board);
        }
    }

    public ArrayList<Domino> getHand()
    {
        return hand;
    }

    public void increaseScore()
    {
        for (int i = 0; i < hand.size(); i++)
        {
            score += hand.get(i).getScore();
        }
    }

    public int getScore()
    {
        return score;
    }

    public void playTurn(Board board)
    {
        if (strategy == 0)
        {
            runHumanTurn(board);
        } else
        {
            runAITurn(board);
        }
    }

    public void runAITurn(Board board)
    {
        int sameAsHandDoubleValue = 600;
        int sameAsHandOtherValue = 400;
        int sameAsAlreadyPlayedDoubleValue = 600;

        if (board.getMustPlayRemaining() > 0)
        {
            // must play on a double
            boolean canPlay = false;
            for (int i = 0; i < hand.size(); i++)
            {
                if (hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType())
                {
                    canPlay = true;
                    break;
                }
            }
            if (canPlay)
            {
                // choose a domino to play on the double based on strategy
                if (strategy == 1)
                {
                    int dominoNum = -1;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType())
                        {
                            dominoNum = i;
                            break;
                        }
                    }
                    board.play(hand.get(dominoNum));
                    hand.remove(dominoNum);
                } else if (strategy == 2 || strategy == 4 || strategy == 5 || strategy == 6 || strategy == 7)
                {
                    int dominoNum = -1;
                    int highestValue = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if ((hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType()) && hand.get(i).getScore() > highestValue)
                        {
                            dominoNum = i;
                            highestValue = hand.get(i).getScore();
                        }
                    }
                    board.play(hand.get(dominoNum));
                    hand.remove(dominoNum);
                } else if (strategy == 3)
                {
                    int dominoNum = -1;
                    int lowestValue = 100;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if ((hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType()) && hand.get(i).getScore() < lowestValue)
                        {
                            dominoNum = i;
                            lowestValue = hand.get(i).getScore();
                        }
                    }
                    board.play(hand.get(dominoNum));
                    hand.remove(dominoNum);
                } else if (strategy == 8 || strategy == 9)
                {
                    int dominoNum = -1;
                    int highestCalcScore = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if ((hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType()))
                        {
                            Domino domino = hand.get(i);
                            int calcScore = 10000;
                            calcScore += domino.getScore() * 100;
                            for (int j = 0; j < hand.size(); j++)
                            {
                                if (i != j)
                                {
                                    if (hand.get(j).isDouble() && (domino.getNum1() == hand.get(j).getNum1() || domino.getNum2() == hand.get(j).getNum1()))
                                    {
                                        calcScore += sameAsHandDoubleValue;
                                    } else
                                    {
                                        if (hand.get(j).getNum1() == domino.getNum1())
                                        {
                                            calcScore += sameAsHandOtherValue;
                                        }
                                        if (hand.get(j).getNum1() == domino.getNum2())
                                        {
                                            calcScore += sameAsHandOtherValue;
                                        }
                                        if (hand.get(j).getNum2() == domino.getNum1())
                                        {
                                            calcScore += sameAsHandOtherValue;
                                        }
                                        if (hand.get(j).getNum2() == domino.getNum2())
                                        {
                                            calcScore += sameAsHandOtherValue;
                                        }
                                    }
                                }
                            }
                            for (int k = 0; k < board.getDominosPlayed().size(); k++)
                            {
                                if (board.getDominosPlayed().get(k).isDouble() && board.getDominosPlayed().get(k).getNum1() != board.getMustPlayType() && (board.getDominosPlayed().get(k).getNum1() == domino.getNum1() || board.getDominosPlayed().get(k).getNum1() == domino.getNum1()))
                                {
                                    calcScore += sameAsAlreadyPlayedDoubleValue;
                                }
                            }

                            if (calcScore > highestCalcScore)
                            {
                                dominoNum = i;
                                highestCalcScore = calcScore;
                            }
                        }
                    }
                    board.play(hand.get(dominoNum));
                    hand.remove(dominoNum);
                } else
                {
                    System.out.println("Invalid strategy");
                }
            } else
            {
                // must draw
                if (board.canDraw())
                {
                    draw(board);
                    if (hand.get(hand.size() - 1).getNum1() == board.getMustPlayType() || hand.get(hand.size() - 1).getNum2() == board.getMustPlayType())
                    {
                        board.play(hand.get(hand.size() - 1));
                        hand.remove(hand.size() - 1);
                    }
                }
            }
        } else
        {
            // can play anywhere
            boolean canPlay = false;
            for (int i = 0; i < hand.size(); i++)
            {
                if (board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2()))
                {
                    canPlay = true;
                    break;
                }
            }
            if (canPlay)
            {
                // choose a domino to play based on strategy
                int domino = -1;
                if (strategy == 1)
                {
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2()))
                        {
                            domino = i;
                            break;
                        }
                    }
                } else if (strategy == 2)
                {
                    int highestValue = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                        {
                            domino = i;
                            highestValue = hand.get(i).getScore();
                        }
                    }
                } else if (strategy == 3)
                {
                    int lowestValue = 1000;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() < lowestValue)
                        {
                            domino = i;
                            lowestValue = hand.get(i).getScore();
                        }
                    }
                } else if (strategy == 4)
                {
                    int highestValue = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (hand.get(i).isDouble() && board.getOptions().contains(hand.get(i).getNum1()) && hand.get(i).getScore() > highestValue)
                        {
                            domino = i;
                            highestValue = hand.get(i).getScore();
                        }
                    }
                    if (highestValue == 0)
                    {
                        for (int i = 0; i < hand.size(); i++)
                        {
                            if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                            {
                                domino = i;
                                highestValue = hand.get(i).getScore();
                            }
                        }
                    }
                } else if (strategy == 5)
                {
                    if (board.getSmallestHandSize() > 5)
                    {
                        int highestValue = 0;
                        for (int i = 0; i < hand.size(); i++)
                        {
                            if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                            {
                                domino = i;
                                highestValue = hand.get(i).getScore();
                            }
                        }
                    } else
                    {
                        int highestValue = 0;
                        for (int i = 0; i < hand.size(); i++)
                        {
                            if (hand.get(i).isDouble() && board.getOptions().contains(hand.get(i).getNum1()) && hand.get(i).getScore() > highestValue)
                            {
                                domino = i;
                                highestValue = hand.get(i).getScore();
                            }
                        }
                        if (highestValue == 0)
                        {
                            for (int i = 0; i < hand.size(); i++)
                            {
                                if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                                {
                                    domino = i;
                                    highestValue = hand.get(i).getScore();
                                }
                            }
                        }
                    }
                } else if (strategy == 6)
                {
                    int highestValue = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (hand.get(i).isDouble() && board.getOptions().contains(hand.get(i).getNum1()) && hand.get(i).getScore() > highestValue)
                        {
                            domino = i;
                            highestValue = hand.get(i).getScore();
                        }
                    }
                    if (highestValue == 0)
                    {
                        if (board.getOptions().contains(0))
                        {
                            for (int i = 0; i < hand.size(); i++)
                            {
                                if ((hand.get(i).getNum1() == 0 || hand.get(i).getNum2() == 0) && hand.get(i).getScore() > highestValue)
                                {
                                    domino = i;
                                    highestValue = hand.get(i).getScore();
                                }
                            }
                        }
                        if (highestValue == 0)
                        {
                            for (int i = 0; i < hand.size(); i++)
                            {
                                if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                                {
                                    domino = i;
                                    highestValue = hand.get(i).getScore();
                                }
                            }
                        }
                    }
                } else if (strategy == 7)
                {
                    int highestValue = 0;
                    if (board.getOptions().contains(0))
                    {
                        for (int i = 0; i < hand.size(); i++)
                        {
                            if (hand.get(i).getNum1() == 0 && hand.get(i).getNum2() == 0)
                            {
                                highestValue = 50;
                                domino = i;
                            }
                        }
                    }

                    if (highestValue == 0)
                    {
                        if (board.getOptions().contains(0))
                        {
                            for (int i = 0; i < hand.size(); i++)
                            {
                                if ((hand.get(i).getNum1() == 0 || hand.get(i).getNum2() == 0) && hand.get(i).getScore() > highestValue)
                                {
                                    domino = i;
                                    highestValue = hand.get(i).getScore();
                                }
                            }
                        }
                        if (highestValue == 0)
                        {
                            for (int i = 0; i < hand.size(); i++)
                            {
                                if (hand.get(i).isDouble() && board.getOptions().contains(hand.get(i).getNum1()) && hand.get(i).getScore() > highestValue)
                                {
                                    domino = i;
                                    highestValue = hand.get(i).getScore();
                                }
                            }
                            if (highestValue == 0)
                            {
                                for (int i = 0; i < hand.size(); i++)
                                {
                                    if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && hand.get(i).getScore() > highestValue)
                                    {
                                        domino = i;
                                        highestValue = hand.get(i).getScore();
                                    }
                                }
                            }
                        }
                    }
                } else if (strategy == 8 || strategy == 9)
                {
                    int highestValue = 0;
                    for (int i = 0; i < hand.size(); i++)
                    {
                        if (hand.get(i).isDouble() && board.getOptions().contains(hand.get(i).getNum1()) && hand.get(i).getScore() > highestValue)
                        {
                            domino = i;
                            highestValue = hand.get(i).getScore();
                        }
                    }
                    if (highestValue == 0)
                    {
                        domino = -1;
                        int highestCalcScore = 0;
                        for (int i = 0; i < hand.size(); i++)
                        {
                            if ((board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2())) && !hand.get(i).isDouble())
                            {
                                Domino dominoToTest = hand.get(i);
                                int calcScore = 1000000;
                                calcScore += dominoToTest.getScore() * 100;
                                for (int j = 0; j < hand.size(); j++)
                                {
                                    if (i != j)
                                    {
                                        if (hand.get(j).isDouble() && (dominoToTest.getNum1() == hand.get(j).getNum1() || dominoToTest.getNum2() == hand.get(j).getNum1()))
                                        {
                                            calcScore += sameAsHandDoubleValue;
                                        } else
                                        {
                                            if (hand.get(j).getNum1() == dominoToTest.getNum1())
                                            {
                                                calcScore += sameAsHandOtherValue;
                                            }
                                            if (hand.get(j).getNum1() == dominoToTest.getNum2())
                                            {
                                                calcScore += sameAsHandOtherValue;
                                            }
                                            if (hand.get(j).getNum2() == dominoToTest.getNum1())
                                            {
                                                calcScore += sameAsHandOtherValue;
                                            }
                                            if (hand.get(j).getNum2() == dominoToTest.getNum2())
                                            {
                                                calcScore += sameAsHandOtherValue;
                                            }
                                        }
                                    }
                                }
                                for (int k = 0; k < board.getDominosPlayed().size(); k++)
                                {
                                    if (board.getDominosPlayed().get(k).isDouble() && board.getDominosPlayed().get(k).getNum1() != board.getMustPlayType() && (board.getDominosPlayed().get(k).getNum1() == dominoToTest.getNum1() || board.getDominosPlayed().get(k).getNum1() == dominoToTest.getNum1()))
                                    {
                                        calcScore += sameAsAlreadyPlayedDoubleValue;
                                    }
                                }

                                if (calcScore > highestCalcScore)
                                {
                                    domino = i;
                                    highestCalcScore = calcScore;
                                }
                            }
                        }
                    }
                } else
                {
                    System.out.println("Invalid strategy");
                }
                Domino newDomino = hand.get(domino);
                int choice;
                if (board.getOptions().contains(newDomino.getNum1()) && board.getOptions().contains(newDomino.getNum2()))
                {
                    // choose the direction based on stratagy
                    if (strategy == 1 || strategy == 2 || strategy == 3 || strategy == 4 || strategy == 5)
                    {
                        choice = newDomino.getNum1();
                    } else if (strategy == 6 || strategy == 7 || strategy == 8)
                    {
                        if (board.getOptions().contains(0) && (newDomino.getNum1() == 0 || newDomino.getNum2() == 0))
                        {
                            choice = 0;
                        } else
                        {
                            choice = newDomino.getNum1();
                        }
                    } else if (strategy == 9)
                    {
                        if (board.getOptions().contains(0) && (newDomino.getNum1() == 0 || newDomino.getNum2() == 0))
                        {
                            choice = 0;
                        } else
                        {
                            choice = newDomino.getNum1();
                        }
                    } else
                    {
                        System.out.println("Invalid strategy");
                        choice = 0;
                    }
                } else
                {
                    if (board.getOptions().contains(newDomino.getNum1()))
                    {
                        choice = newDomino.getNum1();
                    } else
                    {
                        choice = newDomino.getNum2();
                    }
                }
                board.play(newDomino, choice);
                hand.remove(domino);
            } else
            {
                // must draw
                if (board.canDraw())
                {
                    draw(board);
                    Domino newDomino = hand.get(hand.size() - 1);
                    if (board.getOptions().contains(newDomino.getNum1()) || board.getOptions().contains(newDomino.getNum2()))
                    {
                        int choice;
                        if (board.getOptions().contains(newDomino.getNum1()) && board.getOptions().contains(newDomino.getNum2()))
                        {
                            // choose the direction based on stratagy
                            if (strategy == 1 || strategy == 2 || strategy == 3 || strategy == 4 || strategy == 5)
                            {
                                choice = newDomino.getNum1();
                            } else if (strategy == 6 || strategy == 7 || strategy == 8)
                            {
                                if (board.getOptions().contains(0) && (newDomino.getNum1() == 0 || newDomino.getNum2() == 0))
                                {
                                    choice = 0;
                                } else
                                {
                                    choice = newDomino.getNum1();
                                }
                            } else if (strategy == 9)
                            {
                                if (board.getOptions().contains(0) && (newDomino.getNum1() == 0 || newDomino.getNum2() == 0))
                                {
                                    choice = 0;
                                } else
                                {
                                    choice = newDomino.getNum1();
                                }
                            } else
                            {
                                System.out.println("Invalid strategy");
                                choice = 0;
                            }
                        } else
                        {
                            if (board.getOptions().contains(newDomino.getNum1()))
                            {
                                choice = newDomino.getNum1();
                            } else
                            {
                                choice = newDomino.getNum2();
                            }
                        }
                        board.play(newDomino, choice);
                        hand.remove(hand.size() - 1);
                    }
                }
            }
        }
    }

    public void runHumanTurn(Board board)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Your hand:");
        printHand();
        System.out.println("");
        if (board.getMustPlayRemaining() > 0)
        {
            System.out.println("You must play a " + board.getMustPlayType());
            boolean canPlay = false;
            for (int i = 0; i < hand.size(); i++)
            {
                if (hand.get(i).getNum1() == board.getMustPlayType() || hand.get(i).getNum2() == board.getMustPlayType())
                {
                    canPlay = true;
                    break;
                }
            }
            if (canPlay)
            {
                System.out.println("Which domino do you want to play?");
                int domino = input.nextInt() - 1;
                while (domino < 0 || domino >= hand.size() || (hand.get(domino).getNum1() != board.getMustPlayType() && hand.get(domino).getNum2() != board.getMustPlayType()))
                {
                    System.out.println("Invalid choice please try again");
                    System.out.println("Which domino do you want to play?");
                    domino = input.nextInt() - 1;
                }
                board.play(hand.get(domino));
                hand.remove(domino);
            } else
            {
                System.out.println("You must draw");
                if (board.canDraw())
                {
                    draw(board);
                    System.out.println("You drew " + hand.get(hand.size() - 1).getNum1() + ", " + hand.get(hand.size() - 1).getNum2());
                    if (hand.get(hand.size() - 1).getNum1() == board.getMustPlayType() || hand.get(hand.size() - 1).getNum2() == board.getMustPlayType())
                    {
                        System.out.println("You can play it");
                        board.play(hand.get(hand.size() - 1));
                        hand.remove(hand.size() - 1);
                    } else
                    {
                        System.out.println("You cannot play it. You must pass");
                    }
                } else
                {
                    System.out.println("The draw pile is empty. You must pass");
                }
            }
        } else
        {
            board.printOptions();
            boolean canPlay = false;
            for (int i = 0; i < hand.size(); i++)
            {
                if (board.getOptions().contains(hand.get(i).getNum1()) || board.getOptions().contains(hand.get(i).getNum2()))
                {
                    canPlay = true;
                    break;
                }
            }
            if (canPlay)
            {
                System.out.println("Which domino do you want to play?");
                int domino = input.nextInt() - 1;
                while (domino < 0 || domino >= hand.size() || (!board.getOptions().contains(hand.get(domino).getNum1()) && !board.getOptions().contains(hand.get(domino).getNum2())))
                {
                    System.out.println("Invalid choice please try again");
                    System.out.println("Which domino do you want to play?");
                    domino = input.nextInt() - 1;
                }
                Domino newDomino = hand.get(domino);
                int choice;
                if (board.getOptions().contains(newDomino.getNum1()) && board.getOptions().contains(newDomino.getNum2()))
                {
                    System.out.println("On what do you want to play it?");
                    choice = input.nextInt();
                    while (choice != newDomino.getNum1() && choice != newDomino.getNum2())
                    {
                        System.out.println("On what do you want to play it?");
                        choice = input.nextInt();
                    }
                } else
                {
                    if (board.getOptions().contains(newDomino.getNum1()))
                    {
                        choice = newDomino.getNum1();
                    } else
                    {
                        choice = newDomino.getNum2();
                    }
                }
                board.play(newDomino, choice);
                hand.remove(domino);
            } else
            {
                System.out.println("You must draw");
                if (board.canDraw())
                {
                    draw(board);
                    Domino newDomino = hand.get(hand.size() - 1);
                    System.out.println("You drew " + newDomino.getNum1() + ", " + newDomino.getNum2());
                    if (board.getOptions().contains(newDomino.getNum1()) || board.getOptions().contains(newDomino.getNum2()))
                    {
                        System.out.println("You can play it");
                        int choice;
                        if (board.getOptions().contains(newDomino.getNum1()) && board.getOptions().contains(newDomino.getNum2()))
                        {
                            System.out.println("On what do you want to play it?");
                            choice = input.nextInt();
                            while (choice != newDomino.getNum1() && choice != newDomino.getNum2())
                            {
                                System.out.println("On what do you want to play it?");
                                choice = input.nextInt();
                            }
                        } else
                        {
                            if (board.getOptions().contains(newDomino.getNum1()))
                            {
                                choice = newDomino.getNum1();
                            } else
                            {
                                choice = newDomino.getNum2();
                            }
                        }
                        board.play(newDomino, choice);
                        hand.remove(hand.size() - 1);
                    } else
                    {
                        System.out.println("You cannot play it. You must pass");
                    }
                } else
                {
                    System.out.println("The draw pile is empty. You must pass");
                }
            }
        }
    }

    public void printHand()
    {
        for (int i = 0; i < hand.size(); i++)
        {
            System.out.println((i + 1) + ") " + hand.get(i).getNum1() + ", " + hand.get(i).getNum2());
        }
    }

    public void resetScore()
    {
        score = 0;
    }

    public void increaseWins()
    {
        wins++;
    }

    public int getWins()
    {
        return wins;
    }

}
