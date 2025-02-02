/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unoflipgame;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class Player
{

    private ArrayList<Card> hand;
    private int strategy;
    private int score;
    private int wins;

    public Player(int strategy)
    {
        this.strategy = strategy;
        hand = new ArrayList<>();
        score = 0;
        wins = 0;
    }

    public void addCard(Card card)
    {
        hand.add(card);
    }

    public void draw(Deck deck)
    {
        Card card = deck.drawCard();
        if (card.getColor(true) != -1)
        {
            hand.add(card);
            if (strategy == 0)
            {
                System.out.print("You DREW ");
                card.printCard();
            }
        }

    }

    public void drawHand(Deck deck)
    {
        hand.clear();
        for (int i = 0; i < 7; i++)
        {
            draw(deck);
        }
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public int getHandPoints(boolean isLight)
    {
        int points = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            points += hand.get(i).getScore(isLight);
        }
        return points;
    }

    public int getScore()
    {
        return score;
    }

    public int chooseColor(Deck deck)
    {
        if (strategy == 0)
        {
            Scanner input = new Scanner(System.in);
            if (deck.getIsLight())
            {
                System.out.println("1) Red");
                System.out.println("2) Green");
                System.out.println("3) Blue");
                System.out.println("4) Yellow");
            } else
            {
                System.out.println("1) Pink");
                System.out.println("2) Teal");
                System.out.println("3) Purple");
                System.out.println("4) Orange");
            }
            System.out.println("Choose a color: ");
            int color = input.nextInt() - 1;
            while (color < 0 || color > 3)
            {
                System.out.println("Invalid color. Try again");
                System.out.println("Choose a color: ");
                color = input.nextInt();
            }
            return color;
        } else
        {
            return AIColorChoice(deck);
        }
    }

    public boolean playTurn(Deck deck)
    {
        if (strategy == 0)
        {
            return runHumanTurn(deck);
        } else
        {
            return runAITurn(deck);
        }
    }

    public boolean runHumanTurn(Deck deck)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Your hand:");
        printHand();
        System.out.println("");

        deck.printTopCard();
        boolean canPlay = false;
        boolean hasColor = false;
        for (int i = 0; i < hand.size(); i++)
        {
            if (hand.get(i).getColor(deck.getIsLight()) == deck.getTopCard().getColor(deck.getIsLight()) && hand.get(i).getColor(deck.getIsLight()) != 4)
            {
                canPlay = true;
                hasColor = true;
                break;
            } else if (hand.get(i).getNumber(deck.getIsLight()) > 13 || hand.get(i).getNumber(deck.getIsLight()) == deck.getTopCard().getNumber(deck.getIsLight()) || deck.getTopCard().getColor(deck.getIsLight()) == 4)
            {
                canPlay = true;
            }
        }
        if (canPlay)
        {
            System.out.println("Which card do you want to play?");
            int cardNum = input.nextInt() - 1;
            while ((cardNum < 0 || cardNum >= hand.size() || (hand.get(cardNum).getColor(deck.getIsLight()) != deck.getTopCard().getColor(deck.getIsLight()) && hand.get(cardNum).getNumber(deck.getIsLight()) < 14 && hand.get(cardNum).getNumber(deck.getIsLight()) != deck.getTopCard().getNumber(deck.getIsLight())) || (hand.get(cardNum).getNumber(deck.getIsLight()) == 15 && hasColor)) && deck.getTopCard().getColor(deck.getIsLight()) != 4)
            {
                System.out.println("Invalid choice please try again");
                System.out.println("Which card do you want to play?");
                cardNum = input.nextInt() - 1;
            }
            Card newCard = hand.get(cardNum);
            deck.play(newCard);
            hand.remove(cardNum);
        } else
        {
            System.out.println("You must draw");
            draw(deck);
            System.out.print("You drew ");
            hand.get(hand.size() - 1).printCard();
            if (hand.get(hand.size() - 1).getColor(deck.getIsLight()) == deck.getTopCard().getColor(deck.getIsLight()) || hand.get(hand.size() - 1).getNumber(deck.getIsLight()) > 13 || hand.get(hand.size() - 1).getNumber(deck.getIsLight()) == deck.getTopCard().getNumber(deck.getIsLight()))
            {
                System.out.println("You can play it");
                deck.play(hand.get(hand.size() - 1));
                hand.remove(hand.size() - 1);
            } else
            {
                System.out.println("You cannot play it. You must pass");
                return false;
            }
        }
        return true;
    }

    public boolean runAITurn(Deck deck)
    {
        boolean canPlay = false;
        boolean hasColor = false;
        for (int i = 0; i < hand.size(); i++)
        {
            if (hand.get(i).getColor(deck.getIsLight()) == deck.getTopCard().getColor(deck.getIsLight()) && hand.get(i).getColor(deck.getIsLight()) != 4)
            {
                canPlay = true;
                hasColor = true;
                break;
            } else if (hand.get(i).getNumber(deck.getIsLight()) > 13 || hand.get(i).getNumber(deck.getIsLight()) == deck.getTopCard().getNumber(deck.getIsLight()) || deck.getTopCard().getNumber(deck.getIsLight()) == 4)
            {
                canPlay = true;
            }
        }
        if (canPlay)
        {
            // choose a card to play based on strategy
            if (strategy == 1)
            {
                int cardNum = -1;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        cardNum = i;
                        break;
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 2)
            {
                int cardNum = -1;
                int highestScore = 0;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        if (hand.get(i).getScore(deck.getIsLight()) > highestScore)
                        {
                            cardNum = i;
                            highestScore = hand.get(i).getScore(deck.getIsLight());
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 3)
            {
                int cardNum = -1;
                int highestScore = 0;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        if (hand.get(i).getScore(true) + hand.get(i).getScore(false) > highestScore)
                        {
                            cardNum = i;
                            highestScore = hand.get(i).getScore(true) + hand.get(i).getScore(false);
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 4)
            {
                int cardNum = -1;
                int highestScore = 0;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        if (hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight()) > highestScore)
                        {
                            cardNum = i;
                            highestScore = hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight());
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 5) // nice
            {
                int cardNum = -1;
                int highestScore = -100;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        int cardScore = hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight());
                        if (hand.get(i).getNumber(!deck.getIsLight()) == 10)
                        {
                            cardScore += 90;
                        }
                        if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                        {
                            cardScore -= 40;
                        } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                        {
                            cardScore -= 150;
                        }
                        if (cardScore > highestScore)
                        {
                            cardNum = i;
                            highestScore = cardScore;
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 6) // mean
            {
                int cardNum = -1;
                int highestScore = -100;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        int cardScore = hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight());
                        if (hand.get(i).getNumber(!deck.getIsLight()) == 10)
                        {
                            cardScore -= 90;
                        } else if (hand.get(i).getNumber(!deck.getIsLight()) == 15)
                        {
                            cardScore -= 90;
                        }
                        if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                        {
                            cardScore += 40;
                        } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                        {
                            cardScore += 150;
                        }
                        if (cardScore > highestScore)
                        {
                            cardNum = i;
                            highestScore = cardScore;
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 7 || strategy == 9) // nice one dir mean other save meanies
            {
                int cardNum = -1;
                int highestScore = -100;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        int cardScore = hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight());
                        if (hand.get(i).getNumber(!deck.getIsLight()) == 10)
                        {
                            cardScore -= 90;
                        } else if (hand.get(i).getNumber(!deck.getIsLight()) == 15)
                        {
                            cardScore -= 90;
                        }
                        if ((!deck.isClockDir() && strategy == 7) || (deck.isClockDir() && strategy == 9))
                        {
                            if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                            {
                                cardScore -= 40;
                            } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                            {
                                cardScore -= 150;
                            }
                        } else
                        {
                            if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                            {
                                cardScore += 40;
                            } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                            {
                                cardScore += 150;
                            }
                        }
                        if (cardScore > highestScore)
                        {
                            cardNum = i;
                            highestScore = cardScore;
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else if (strategy == 8 || strategy == 10) // nice one dir mean other play meanies on other side
            {
                int cardNum = -1;
                int highestScore = -100;
                for (int i = 0; i < hand.size(); i++)
                {
                    if (isPlayable(hand.get(i), deck, hasColor))
                    {
                        int cardScore = hand.get(i).getScore(deck.getIsLight()) * 2 + hand.get(i).getScore(!deck.getIsLight());
                        if (hand.get(i).getNumber(!deck.getIsLight()) == 10)
                        {
                            cardScore += 90;
                        }
                        if ((!deck.isClockDir() && strategy == 8) || (deck.isClockDir() && strategy == 10))
                        {

                            if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                            {
                                cardScore -= 40;
                            } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                            {
                                cardScore -= 150;
                            }
                        } else
                        {
                            if (hand.get(i).getNumber(deck.getIsLight()) == 10)
                            {
                                cardScore += 40;
                            } else if (hand.get(i).getNumber(deck.getIsLight()) == 15)
                            {
                                cardScore += 150;
                            }
                        }
                        if (cardScore > highestScore)
                        {
                            cardNum = i;
                            highestScore = cardScore;
                        }
                    }
                }
                Card newCard = hand.get(cardNum);
                deck.play(newCard);
                hand.remove(cardNum);
            } else
            {
                System.out.println("Error invalid strategy");
            }
        } else
        {
            draw(deck);
            if (hand.get(hand.size() - 1).getColor(deck.getIsLight()) == deck.getTopCard().getColor(deck.getIsLight()) || hand.get(hand.size() - 1).getNumber(deck.getIsLight()) > 13 || hand.get(hand.size() - 1).getNumber(deck.getIsLight()) == deck.getTopCard().getNumber(deck.getIsLight()))
            {
                deck.play(hand.get(hand.size() - 1));
                hand.remove(hand.size() - 1);
            } else
            {
                return false;
            }
        }
        return true;
    }

    public int AIColorChoice(Deck deck)
    {
        // choose a color based on strategy
        if (strategy == 1 || strategy == 2 || strategy == 3 || strategy < 20)
        {
            int color = 0;
            int maxAmount = 0;
            for (int i = 0; i < 4; i++)
            {
                int count = 0;
                for (int j = 0; j < hand.size(); j++)
                {
                    if (hand.get(j).getColor(deck.getIsLight()) == i)
                    {
                        count++;
                    }
                }
                if (count > maxAmount)
                {
                    maxAmount = count;
                    color = i;
                }
            }
            return color;
        } else
        {
            System.out.println("Error invalid strategy");
            return 0;
        }
    }

    public void printHand()
    {
        for (int i = 0; i < hand.size(); i++)
        {
            System.out.print((i + 1) + ") ");
            hand.get(i).printCard();
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

    public void increaseScore(int points)
    {
        score += points;
    }

    public boolean isPlayable(Card card, Deck deck, boolean hasColor)
    {
        return ((card.getColor(deck.getIsLight()) == deck.getTopCard().getColor(deck.getIsLight()) || card.getNumber(deck.getIsLight()) > 13 || card.getNumber(deck.getIsLight()) == deck.getTopCard().getNumber(deck.getIsLight())) || deck.getTopCard().getNumber(deck.getIsLight()) == 4) && !(card.getNumber(deck.getIsLight()) == 15 && hasColor);
    }
}
