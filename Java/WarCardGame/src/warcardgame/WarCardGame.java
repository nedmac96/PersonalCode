/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcardgame;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class WarCardGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Players players[] = getPlayers();

        //Prints the players names
        System.out.println("The players are: \n");
        for (Players player : players)
        {
            System.out.println(player.getPlayerName());
        }

        //creates the deck of cards
        CardDeck cards[] = new CardDeck[52];
        createDeck(cards);

        //deals the deck of cards
        for (var i = 0; i < cards.length; i++)
        {
            players[i % players.length].playerCards.add(cards[i]);
        }

        //prints each players cards
        for (Players player : players)
        {
            System.out.println(player.getPlayerCards());
        }
        int winnerId = 14;
        while (winnerId > 13)
        {
            //gets the players to play their cards
            System.out.println("");
            for (var i = 0; i < players.length; i++)
            {
                if (!players[i].playerCards.isEmpty())
                {
                    if (players[i].isAI() == false)
                    {
                        System.out.println("Press enter to flip your card, " + players[i].getPlayerName() + ": ");
                        Scanner scanner = new Scanner(System.in);
                        scanner.nextLine();
                        System.out.println(players[i].getPlayerName() + " played the " + players[i].playerCards.peek());
                    } else
                    {
                        System.out.println(players[i].getPlayerName() + " played the " + players[i].playerCards.peek());
                    }
                } else
                {
                    System.out.println(players[i].getPlayerName() + " has lost");
                }
                players[i].setCardPlayed(players[i].playerCards.poll());

            }

            //Determines who played the highest card
            int highestValue = 0;
            ArrayList<Integer> highestValueHolderId = new ArrayList<>();

            for (var i = 0; i < players.length; i++)
            {
                if (!players[i].playerCards.isEmpty())
                {
                    if (players[i].getCardPlayed().getValue() >= highestValue)
                    {
                        if (players[i].getCardPlayed().getValue() > highestValue)
                        {
                            highestValue = players[i].getCardPlayed().getValue();
                            highestValueHolderId.clear();
                        }
                        highestValueHolderId.add(i);
                    }
                }
            }

            //Determines the outcome
            if (highestValueHolderId.size() == 1)
            {
                for (var i = 0; i < players.length; i++)
                {
                    if (players[i].getCardPlayed() != null)
                    {
                        players[highestValueHolderId.get(0)].playerCards.add(players[i].getCardPlayed());
                    }
                }

            } else
            {
                //War
                System.out.println("WAR!!!!!");
                ArrayList<CardDeck> cardsPlayed = new ArrayList<>();

                for (var i = 0; i < players.length; i++)
                {
                    if (players[i].getCardPlayed() != null)
                    {
                        cardsPlayed.add(players[i].getCardPlayed());
                    }
                }

                while (highestValueHolderId.size() != 1)
                {
                    for (var i = 0; i < highestValueHolderId.size(); i++)
                    {
                        if (players[highestValueHolderId.get(i)].playerCards.size() > 2)
                        {
                            for (var j = 0; j < 2; j++)
                            {
                                cardsPlayed.add(players[highestValueHolderId.get(i)].playerCards.poll());
                            }
                        } else if (players[highestValueHolderId.get(i)].playerCards.size() == 2)
                        {
                            cardsPlayed.add(players[highestValueHolderId.get(i)].playerCards.poll());
                        }
                        if (!players[highestValueHolderId.get(i)].playerCards.isEmpty())
                        {
                            cardsPlayed.add(players[highestValueHolderId.get(i)].playerCards.peek());
                            players[highestValueHolderId.get(i)].setCardPlayed(players[highestValueHolderId.get(i)].playerCards.poll());
                        }
                        System.out.println(players[highestValueHolderId.get(i)].getPlayerName() + " played the " + players[highestValueHolderId.get(i)].getCardPlayed() + " in the WAR");

                    }
                    highestValue = 0;
                    ArrayList<Integer> highestValueHolderIdWar = new ArrayList<>();
                    for (var i = 0; i < highestValueHolderId.size(); i++)
                    {
                        if (!players[highestValueHolderId.get(i)].playerCards.isEmpty())
                        {
                            if (players[highestValueHolderId.get(i)].getCardPlayed().getValue() >= highestValue)
                            {
                                if (players[highestValueHolderId.get(i)].getCardPlayed().getValue() > highestValue)
                                {
                                    highestValue = players[highestValueHolderId.get(i)].getCardPlayed().getValue();
                                    highestValueHolderId.clear();
                                }
                                highestValueHolderIdWar.add(i);
                            }
                        }
                    }
                }
                System.out.println(players[highestValueHolderId.get(0)].getPlayerName() + " won the WAR!");
                for (var i = 0; i < cardsPlayed.size(); i++)
                {
                    players[highestValueHolderId.get(0)].playerCards.add(cardsPlayed.get(i));
                }
            }
            //prints each players cards
            for (Players player : players)
            {
                System.out.println(player.getPlayerCards());
            }

            if (players[highestValueHolderId.get(0)].playerCards.size() == 52)
            {
                winnerId = highestValueHolderId.get(0);
            }

        }
        //prints each players cards
        for (Players player : players)
        {
            System.out.println(player.getPlayerCards());
        }

    }

    public static void createDeck(CardDeck cards[])
    {
        for (var i = 1; i < 5; i++)
        {
            for (var j = 2; j < 15; j++)
            {
                int randomNumber;
                do
                {

                    randomNumber = (int) Math.round(Math.random() * 51);

                } while (cards[randomNumber] != null);

                cards[randomNumber] = new CardDeck();
                cards[randomNumber].setId(((i - 1) * 13) + j - 1);
                cards[randomNumber].setValue(j);

                String suit;
                if (i == 1)
                {
                    suit = "Hearts";
                } else if (i == 2)
                {
                    suit = "Spades";
                } else if (i == 3)
                {
                    suit = "Clubs";
                } else
                {
                    suit = "Diamonds";
                }

                String name;
                if (j <= 10)
                {
                    name = Integer.toString(j);
                } else if (j == 11)
                {
                    name = "Jack";
                } else if (j == 12)
                {
                    name = "Queen";
                } else if (j == 13)
                {
                    name = "King";
                } else
                {
                    name = "Ace";
                }
                cards[randomNumber].setCardName(name + " of " + suit);
            }
        }
    }

    public static Players[] getPlayers()
    {

//Gets the number of players
        Scanner scanner = new Scanner(System.in);
        int input;
        System.out.println("Enter the number of players: ");
        input = scanner.nextInt();
        while (input < 2 || input > 13)
        {
            System.out.println("Error: Invalid number. The number must be between 2 and 13. Please enter a valid number of players: ");
            input = scanner.nextInt();
        }

        //Gets the players names and finds out if there are any AIs
        Players players[] = new Players[input];
        scanner.nextLine();
        for (var i = 0; i < players.length; i++)
        {

            players[i] = new Players();

            System.out.println("Enter player " + (i + 1) + "'s name:");
            players[i].setPlayerName(scanner.nextLine());

            System.out.println("Is " + players[i].getPlayerName() + " an AI?(y/n)");
            if ("y".equals(scanner.nextLine()))
            {
                players[i].setAI(true);
            } else
            {
                players[i].setAI(false);
            }

        }
        return players;
    }
}
