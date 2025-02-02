/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Camden
 */
public class JavaApplication13
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        // TODO code application logic here
        HeartsPlayer bottomPlayer = new HeartsPlayer(0);
        HeartsPlayer leftPlayer = new HeartsPlayer(1);
        HeartsPlayer topPlayer = new HeartsPlayer(1);
        HeartsPlayer rightPlayer = new HeartsPlayer(1);
        List<Integer> deck = new ArrayList<>();
        int verticalScore = 0;
        int horizantalScore = 0;
        int playerHands[];

        while (verticalScore < 500 && horizantalScore < 500 || horizantalScore == verticalScore)
        {
            //generates a random deck
            deck.clear();
            for (int i = 0; i < 52; i++)
            {
                addCardToDeck((int) (Math.random() * 52), deck);
            }
            //gives out the cards       
            bottomPlayer.setCards(deck.subList(0, 13));
            leftPlayer.setCards(deck.subList(13, 26));
            topPlayer.setCards(deck.subList(26, 39));
            rightPlayer.setCards(deck.subList(39, 52));

            bottomPlayer.sortCards();
            leftPlayer.sortCards();
            topPlayer.sortCards();
            rightPlayer.sortCards();

            //runs the bidding
            System.out.println("");
            int bidder = (int) (Math.random() * 4);
            for (int i = 0; i < 4; i++)
            {
                bidder++;
                bidder = bidder % 4;
                switch (bidder)
                {
                    case 0:
                        System.out.println("The bottom player bid: " + bottomPlayer.determineBid());
                        break;
                    case 1:
                        System.out.println("The left player bid: " + leftPlayer.determineBid());
                        break;
                    case 2:
                        System.out.println("The top player bid: " + topPlayer.determineBid());
                        break;
                    default:
                        System.out.println("The right player bid: " + rightPlayer.determineBid());
                        break;
                }
            }

            //runs rounds
            playerHands = new int[]
            {
                0, 0, 0, 0
            };
            List<Integer> cardsPlayed = new ArrayList<>();
            int player = (bidder + 1) % 4;
            int winningCard;
            boolean spadesBroken = false;
            int startingSuit = -2;
            for (int i = 0; i < 13; i++)
            {
                winningCard = -1;
                for (int j = 0; j < 4; j++)
                {

                    if (j == 0)
                    {
                        if (spadesBroken)
                        {
                            switch (player)
                            {
                                case 0:
                                    cardsPlayed.add(bottomPlayer.playCard(-1, winningCard, cardsPlayed));
                                    System.out.println("The bottom player played the " + bottomPlayer.sayCardPlayed());
                                    break;
                                case 1:
                                    cardsPlayed.add(leftPlayer.playCard(-1, winningCard, cardsPlayed));
                                    System.out.println("The left player played the " + leftPlayer.sayCardPlayed());
                                    break;
                                case 2:
                                    cardsPlayed.add(topPlayer.playCard(-1, winningCard, cardsPlayed));
                                    System.out.println("The top player played the " + topPlayer.sayCardPlayed());
                                    break;
                                default:
                                    cardsPlayed.add(rightPlayer.playCard(-1, winningCard, cardsPlayed));
                                    System.out.println("The right player played the " + rightPlayer.sayCardPlayed());
                                    break;
                            }
                        } else
                        {
                            switch (player)
                            {
                                case 0:
                                    cardsPlayed.add(bottomPlayer.playCard(4, winningCard, cardsPlayed));
                                    System.out.println("The bottom player played the " + bottomPlayer.sayCardPlayed());
                                    break;
                                case 1:
                                    cardsPlayed.add(leftPlayer.playCard(4, winningCard, cardsPlayed));
                                    System.out.println("The left player played the " + leftPlayer.sayCardPlayed());
                                    break;
                                case 2:
                                    cardsPlayed.add(topPlayer.playCard(4, winningCard, cardsPlayed));
                                    System.out.println("The top player played the " + topPlayer.sayCardPlayed());
                                    break;
                                default:
                                    cardsPlayed.add(rightPlayer.playCard(4, winningCard, cardsPlayed));
                                    System.out.println("The right player played the " + rightPlayer.sayCardPlayed());
                                    break;
                            }
                        }
                        winningCard = cardsPlayed.get(cardsPlayed.size() - 1);
                        startingSuit = winningCard / 13;
                    } else
                    {
                        switch (player)
                        {
                            case 0:
                                cardsPlayed.add(bottomPlayer.playCard(startingSuit, winningCard, cardsPlayed));
                                System.out.println("The bottom player played the " + bottomPlayer.sayCardPlayed());
                                break;
                            case 1:
                                cardsPlayed.add(leftPlayer.playCard(startingSuit, winningCard, cardsPlayed));
                                System.out.println("The left player played the " + leftPlayer.sayCardPlayed());
                                break;
                            case 2:
                                cardsPlayed.add(topPlayer.playCard(startingSuit, winningCard, cardsPlayed));
                                System.out.println("The top player played the " + topPlayer.sayCardPlayed());
                                break;
                            default:
                                cardsPlayed.add(rightPlayer.playCard(startingSuit, winningCard, cardsPlayed));
                                System.out.println("The right player played the " + rightPlayer.sayCardPlayed());
                                break;
                        }
                        if ((cardsPlayed.get(cardsPlayed.size() - 1) / 13 == winningCard / 13 || cardsPlayed.get(cardsPlayed.size() - 1) / 13 == 3) && cardsPlayed.get(cardsPlayed.size() - 1) > winningCard)
                        {
                            winningCard = cardsPlayed.get(cardsPlayed.size() - 1);
                        }
                    }
                    if (cardsPlayed.get(cardsPlayed.size() - 1) > 38)
                    {
                        spadesBroken = true;
                    }
                    player = ++player % 4;
                }
                player = player - (cardsPlayed.size() - cardsPlayed.indexOf(winningCard));
                if (player > 3)
                {
                    playerHands[player - 4] += 1;
                } else if (player < -1)
                {
                    playerHands[player + 4] += 1;
                    player += 4;
                } else
                {
                    playerHands[(player + 4) % 4] += 1;
                }

                switch (player)
                {
                    case 0:
                        System.out.println("The bottom player won the hand with the " + bottomPlayer.sayCardPlayed());
                        break;
                    case 1:
                        System.out.println("The left player won the hand with the " + leftPlayer.sayCardPlayed());
                        break;
                    case 2:
                        System.out.println("The top player won the hand with the " + topPlayer.sayCardPlayed());
                        break;
                    default:
                        System.out.println("The right player won the hand with the " + rightPlayer.sayCardPlayed());
                        break;
                }

            }
            System.out.println("The vertical team bid " + (bottomPlayer.getBid() + topPlayer.getBid()) + " and won " + (playerHands[0] + playerHands[2]) + " hands.");
            if (playerHands[0] + playerHands[2] >= bottomPlayer.getBid() + topPlayer.getBid())
            {
                System.out.println("So they get " + ((bottomPlayer.getBid() + topPlayer.getBid()) * 10 + playerHands[0] + playerHands[2] - (bottomPlayer.getBid() + topPlayer.getBid())) + " points");
                verticalScore += ((bottomPlayer.getBid() + topPlayer.getBid()) * 10 + playerHands[0] + playerHands[2] - (bottomPlayer.getBid() + topPlayer.getBid()));
            } else
            {
                System.out.println("So they lose " + ((bottomPlayer.getBid() + topPlayer.getBid()) * 10) + " points");
                verticalScore -= (bottomPlayer.getBid() + topPlayer.getBid()) * 10;
            }
            System.out.println("Giving them a total of " + verticalScore + " points");
            System.out.println("");
            System.out.println("The horizantal team bid " + (leftPlayer.getBid() + rightPlayer.getBid()) + " and won " + (playerHands[1] + playerHands[3]) + " hands.");
            if (playerHands[1] + playerHands[3] >= leftPlayer.getBid() + rightPlayer.getBid())
            {
                System.out.println("So they get " + ((leftPlayer.getBid() + rightPlayer.getBid()) * 10 + playerHands[1] + playerHands[3] - (leftPlayer.getBid() + rightPlayer.getBid())) + " points");
                horizantalScore += (leftPlayer.getBid() + rightPlayer.getBid()) * 10 + playerHands[1] + playerHands[3] - (leftPlayer.getBid() + rightPlayer.getBid());
            } else
            {
                System.out.println("So they lose " + ((leftPlayer.getBid() + rightPlayer.getBid()) * 10) + " points");
                horizantalScore -= (leftPlayer.getBid() + rightPlayer.getBid()) * 10;
            }
            System.out.println("Giving them a total of " + horizantalScore + " points");
        }
        if (verticalScore < horizantalScore)
        {
            System.out.println("The horizantal team won with " + horizantalScore + " points!");
        } else
        {
            System.out.println("The verticle team won with " + verticalScore + " points!");
        }

    }

    public static void addCardToDeck(int card, List<Integer> deck)
    {
        if (!deck.contains(card))
        {
            deck.add(card);
        } else
        {
            addCardToDeck((int) (Math.random() * 52), deck);
        }
    }

}
