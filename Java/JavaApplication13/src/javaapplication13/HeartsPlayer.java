/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class HeartsPlayer
{

    private int AIType;
    private List<Integer> cards;
    private int bid;
    private int cardPlayed;
    private int[] cardsToPass =
    {
        0, 0, 0
    };

    public HeartsPlayer(int AIType)
    {
        AIType = AIType;
    }

    public void displayCards()
    {
        System.out.println("Your cards are:");
        for (int i = 0; i < cards.size(); i++)
        {
            System.out.println((i + 1) + ". " + getCardName(cards.get(i)));

        }
    }

    public void sortCards()
    {
        List<Integer> sortedCards = new ArrayList<>();
        int smallest;
        for (int i = 0; i < cards.size(); i++)
        {
            smallest = 100;
            for (int j = 0; j < cards.size(); j++)
            {
                if (cards.get(j) < smallest && (sortedCards.isEmpty() || cards.get(j) > sortedCards.get(sortedCards.size() - 1)))
                {
                    smallest = cards.get(j);
                }
            }
            sortedCards.add(smallest);
        }
        cards = sortedCards;
    }

    public void setCards(List<Integer> newCards)
    {
        cards = newCards;
    }

    //passing method
    public int determineBid(int passDirection)
    {
        double bid = -1;
        if (AIType == 0)
        {
            Scanner scanner = new Scanner(System.in);
            int input;
            this.displayCards();
            while (bid == -1)
            {
                System.out.println("What cards do you want to pass?");
                input = scanner.nextInt();
                if (input > -1 && input < 14)
                {
                    bid = input;
                } else
                {
                    System.out.println("Invalid bid try again");
                }
            }
        } else
        {
            bid = 0;
            int numDiamonds = 0;
            int numClubs = 0;
            int numHearts = 0;
            int numSpades = 0;
            for (int i = 0; i < 13; i++)
            {
                if (cards.contains(i))
                {
                    numDiamonds++;
                }
            }
            for (int i = 13; i < 26; i++)
            {
                if (cards.contains(i))
                {
                    numClubs++;
                }
            }
            for (int i = 26; i < 39; i++)
            {
                if (cards.contains(i))
                {
                    numHearts++;
                }
            }
            for (int i = 39; i < 52; i++)
            {
                if (cards.contains(i))
                {
                    numSpades++;
                }
            }

            for (int i = 0; i < 13; i++)
            {
                if (cards.contains(i))
                {
                    bid += (100 - (11700 * 1.2788 * (12 - i) / (676 - numDiamonds))) - (66.5184 - (5.1168 * numSpades));
                }
            }
            for (int i = 13; i < 26; i++)
            {
                if (cards.contains(i))
                {
                    bid += (100 - (11700 * 1.2788 * (25 - i) / (676 - numClubs))) - (66.5184 - (5.1168 * numSpades));
                }
            }
            for (int i = 26; i < 39; i++)
            {
                if (cards.contains(i))
                {
                    bid += (100 - (11700 * 1.2788 * (38 - i) / (676 - numHearts))) - (66.5184 - (5.1168 * numSpades));
                }
            }
            for (int i = 39; i < 52; i++)
            {
                if (cards.contains(i))
                {
                    bid += (100 - (11700 * 1.2788 * (51 - i) / (676 - numSpades))) + numSpades / (numDiamonds + 1) + numSpades / (numClubs + 1) + numSpades / (numHearts + 1);
                }
            }
            bid *= 0.003;
            bid += numSpades * 0.3;
            bid = Math.floor(bid);
            bid += 5;
        }
        this.bid = (int) bid;
        return (int) bid;
    }

    public int getBid()
    {
        return bid;
    }

    public int playCard(int requiredSuit, int winningCard, List<Integer> cardsPlayed)
    {
        int card = -1;
        if (AIType == 0)
        {
            displayCards();
            Scanner scanner = new Scanner(System.in);
            int input = -5;
            while (card == -1)
            {
                System.out.print("Enter which card you want to play, you can play a");
                switch (requiredSuit)
                {
                    case 0:
                        System.out.print(" diamond");
                        break;
                    case 1:
                        System.out.print(" club");
                        break;
                    case 2:
                        System.out.print(" heart");
                        break;
                    case 3:
                        System.out.print(" spade");
                        break;
                    case -1:
                        System.out.print("ny card");
                        break;
                    default:
                        System.out.print(" non-spade");
                }
                System.out.println(" if you have one");
                input = scanner.nextInt() - 1;
                if (cards.get(input) / 13 == requiredSuit)
                {
                    card = cards.get(input);
                } else
                {
                    boolean hasSuit = false;
                    for (int i = 0; i < cards.size(); i++)
                    {
                        if (cards.get(i) / 13 == requiredSuit)
                        {
                            hasSuit = true;
                        }
                    }
                    if (hasSuit)
                    {
                        System.out.println("Invalid card try again");
                    } else
                    {
                        card = cards.get(input);
                    }
                }
            }
            cards.remove(input);
        } else
        {
            if (cardsPlayed.size() < 3 || winningCard == cardsPlayed.get(cardsPlayed.size() - 2))
            {
                for (int i = 0; i < 13; i++)
                {
                    if (cards.contains(i + requiredSuit * 13) && card == -1)
                    {
                        card = i + requiredSuit * 13;
                    }
                }
                if (card == -1)
                {
                    for (int i = 0; i < 13; i++)
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            if (cards.contains(j * 13 + i) && card == -1)
                            {
                                card = j * 13 + i;
                            }
                        }
                    }
                }
                if (card == -1)
                {
                    for (int i = 0; i < 13; i++)
                    {
                        if (cards.contains(i + 39) && card == -1)
                        {
                            card = i + 39;
                        }
                    }
                }
            } else
            {
                if (requiredSuit == 0)
                {
                    //not leading on a diamond hand

                    //checks greatest
                    if (card == -1)
                    {
                        int i = 0;
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    //checks larger
                    if (card == -1)
                    {
                        for (int i = 0; i < 12 - winningCard; i++)
                        {
                            if (cards.contains(i + 1 + winningCard) && card == -1)
                            {
                                card = i + 1 + winningCard;
                            }
                        }
                    }
                    //checks any
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            if (cards.contains(i) && card == -1)
                            {
                                card = i;
                            }
                        }
                    }
                    //checks spades
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            int j = 3;
                            if (cards.contains(j * 13 + i) && card == -1)
                            {
                                card = j * 13 + i;
                            }

                        }
                    }
                    //checks all
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 4; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }

                        }
                    }
                } else if (requiredSuit == 1)
                {
                    //not leading on a club hand

                    //checks greatest
                    if (card == -1)
                    {
                        int i = 1;
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    //checks larger
                    if (card == -1)
                    {
                        for (int i = 0; i < 25 - winningCard; i++)
                        {
                            if (cards.contains(i + 1 + winningCard) && card == -1)
                            {
                                card = i + 1 + winningCard;
                            }
                        }
                    }
                    //checks any
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            if (cards.contains(i + 13) && card == -1)
                            {
                                card = i + 13;
                            }
                        }
                    }
                    //checks spades
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            int j = 3;
                            if (cards.contains(j * 13 + i) && card == -1)
                            {
                                card = j * 13 + i;
                            }

                        }
                    }
                    //checks all
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 4; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }

                        }
                    }
                } else if (requiredSuit == 2)
                {
                    //not leading on a heart hand

                    //checks greatest
                    if (card == -1)
                    {
                        int i = 2;
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    //checks larger
                    if (card == -1)
                    {
                        for (int i = 0; i < 38 - winningCard; i++)
                        {
                            if (cards.contains(i + 1 + winningCard) && card == -1)
                            {
                                card = i + 1 + winningCard;
                            }
                        }
                    }
                    //checks any
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            if (cards.contains(i + 26) && card == -1)
                            {
                                card = i + 26;
                            }
                        }
                    }
                    //checks spades
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            int j = 3;
                            if (cards.contains(j * 13 + i) && card == -1)
                            {
                                card = j * 13 + i;
                            }

                        }
                    }
                    //checks all
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 4; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }

                        }
                    }

                } else if (requiredSuit == 3)
                {
                    //not leading on a spade hand

                    if (card == -1)
                    {
                        int i = 3;
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 51 - winningCard; i++)
                        {
                            if (cards.contains(i + 1 + winningCard) && card == -1)
                            {
                                card = i + 1 + winningCard;
                            }
                        }
                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            if (cards.contains(i + 39) && card == -1)
                            {
                                card = i + 39;
                            }
                        }
                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 4; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }

                        }
                    }
                } else if (requiredSuit == -1)
                {
                    //Leading when spades are broken

                    for (int i = 0; i < 4; i++)
                    {
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 4; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }
                        }
                    }

                } else
                {
                    //Leading when spades are not broken

                    for (int i = 0; i < 3; i++)
                    {
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }
                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            for (int j = 0; j < 3; j++)
                            {
                                if (cards.contains(j * 13 + i) && card == -1)
                                {
                                    card = j * 13 + i;
                                }
                            }
                        }
                    }
                    if (card == -1)
                    {
                        int i = 3;
                        boolean greaterCardsPlayed = true;
                        int j = 0;
                        while (greaterCardsPlayed)
                        {
                            if (cards.contains(i * 13 + 12 - j) && card == -1)
                            {
                                card = i * 13 + 12 - j;
                            } else if (!cardsPlayed.contains(i * 13 + 12 - j) || j >= 7)
                            {
                                greaterCardsPlayed = false;
                            }
                            j++;
                        }

                    }
                    if (card == -1)
                    {
                        for (int i = 0; i < 13; i++)
                        {
                            int j = 3;
                            if (cards.contains(j * 13 + i) && card == -1)
                            {
                                card = j * 13 + i;
                            }

                        }
                    }
                }
            }
            cards.remove(cards.indexOf(card));
        }
        cardPlayed = card;
        return card;
    }

    public String getCardName(int card)
    {
        String first;
        String second;
        if (card % 13 < 9)
        {
            first = String.valueOf((card % 13) + 2);
        } else if (card % 13 == 9)
        {
            first = "Jack";
        } else if (card % 13 == 10)
        {
            first = "Queen";
        } else if (card % 13 == 11)
        {
            first = "King";
        } else
        {
            first = "Ace";
        }
        if (card / 13 == 0)
        {
            second = " of Diamonds";
        } else if (card / 13 == 1)
        {
            second = " of Clubs";
        } else if (card / 13 == 2)
        {
            second = " of Hearts";
        } else
        {
            second = " of Spades";
        }
        return first + second;
    }

    public String sayCardPlayed()
    {

        return getCardName(cardPlayed);

    }
}
