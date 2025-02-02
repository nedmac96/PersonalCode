/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package unoflipgame;

/**
 *
 * @author Camden
 */
public class UnoFlipGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        UnoFlipGame game = new UnoFlipGame();
        //game.runGame();
        game.printCards();
    }

    public void printCards()
    {
        Deck deck = new Deck();
        deck.setUpRound();

        for (int i = 0; i < deck.getDrawPile().size(); i++)
        {
            deck.getDrawPile().get(i).printCard();
        }
    }

    public void runGame()
    {
        /*
        strategy
        0 - human
        1 - plays first card allowed, chooses color has most of
        2 - plays card worth most points on side, chooses color has most of
        3 - plays card worth most points on both sides, chooses color has most of
        4 - plays card worth most points on both sides weighted by current side, chooses color has most of
        5 - nice, chooses color has most of
        6 - mean, chooses color has most of
        7 - nice clock dir mean other save meanies, chooses color has most of
        8 - nice clock dir mean other play meanies on other side, chooses color has most of
        9 - nice counter-clock dir mean other save meanies, chooses color has most of
        10 - nice counter-clock dir mean other play meanies on other side, chooses color has most of
        
         */

 /*
        results
        1 vs 1 -> E,  1 vs 2 -> 1,  1 vs 3 -> 1,  1 vs 4 -> 1,  1 vs 5 -> 1,  1 vs 6 -> 6,  1 vs 7 -> 7,  1 vs 8 -> E, 
        2 vs 1 -> 2,  2 vs 2 -> E,  2 vs 3 -> 2,  2 vs 4 -> 2,  2 vs 5 -> 2,  2 vs 6 -> E,  2 vs 7 -> 7,  2 vs 8 -> 2,
        3 vs 1 -> 1,  3 vs 2 -> 2,  3 vs 3 -> E,  3 vs 4 -> 4,  3 vs 5 -> E,  3 vs 6 -> 6,  3 vs 7 -> 7,  3 vs 8 -> 8,
        4 vs 1 -> 1,  4 vs 2 -> 2,  4 vs 3 -> 4,  4 vs 4 -> E,  4 vs 5 -> 4,  4 vs 6 -> 6,  4 vs 7 -> 7,  4 vs 8 -> E,
        5 vs 1 -> 1,  5 vs 2 -> 2,  5 vs 3 -> E,  5 vs 4 -> 4,  5 vs 5 -> E,  5 vs 6 -> 6,  5 vs 7 -> 7,  5 vs 8 -> 8,
        6 vs 1 -> 6,  6 vs 2 -> E,  6 vs 3 -> 6,  6 vs 4 -> 6,  6 vs 5 -> 6,  6 vs 6 -> E,  6 vs 7 -> 7,  6 vs 8 -> 6,
        7 vs 1 -> 7,  7 vs 2 -> E,  7 vs 3 -> 7,  7 vs 4 -> 7,  7 vs 5 -> 7,  7 vs 6 -> E,  7 vs 7 -> E,  7 vs 8 -> 7,
        8 vs 1 -> 1,  8 vs 2 -> 2,  8 vs 3 -> 8,  8 vs 4 -> E,  8 vs 5 -> 8,  8 vs 6 -> 6,  8 vs 7 -> 7,  8 vs 8 -> E,
        9 vs 1 -> 9,  9 vs 2 -> E,  9 vs 3 -> 9,  9 vs 4 -> 9,  9 vs 5 -> 9,  9 vs 6 -> E,  9 vs 7 -> 7,  9 vs 8 -> 9,
       10 vs 1 -> 1, 10 vs 2 -> 2, 10 vs 3 ->10, 10 vs 4 -> E, 10 vs 5 ->10, 10 vs 6 -> 6, 10 vs 7 -> 7, 10 vs 8 -> 8,
         */
        final int numPlayers = 6;
        final int numGames = 100000;

        int invalidGames = 0;
        Deck deck = new Deck();
        Player[] players = new Player[numPlayers];
        players[0] = new Player(8);
        players[1] = new Player(7);
        players[2] = new Player(6);
        players[3] = new Player(9);
        players[4] = new Player(6);
        players[5] = new Player(6);
        /*for (int i = 0; i < numPlayers; i++)
        {
            switch (i)
            {
                case 0:
                    players[i] = new Player(5);
                    break;
                case 1:
                    players[i] = new Player(7);
                    break;
                case 2:
                    players[i] = new Player(6);
                    break;
                case 3:
                    players[i] = new Player(9);
                    break;
                case 4:
                    players[i] = new Player(1);
                    break;
                default:
                    players[i] = new Player(6);
                    break;
            }

        }*/
        for (int game = 0; game < numGames; game++)
        {
            int startingPlayer = (int) Math.floor(Math.random() * numPlayers);

            boolean winner;
            do
            {
                // set up the round
                int turn = startingPlayer;
                startingPlayer++;
                startingPlayer = startingPlayer % numPlayers;
                deck.setUpRound();
                for (Player player : players)
                {
                    player.drawHand(deck);
                }

                // deal with initial card
                while (deck.getPlayPile().isEmpty() || deck.getTopCard().getNumber(true) == 15)
                {
                    deck.play(deck.drawCard());
                    switch (deck.getTopCard().getNumber(true))
                    {
                        case 10 ->
                        {
                            players[turn].draw(deck);
                            turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                        }
                        case 11 ->
                        {
                            deck.reverse();
                            turn -= 2;
                            if (turn < 0)
                            {
                                turn += numPlayers;
                            }
                        }
                        case 12 ->
                        {
                            turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                        }
                        case 13 ->
                            deck.flip();
                        default ->
                        {
                        }
                    }
                }

                // play the round
                while (!players[turn].getHand().isEmpty())
                {
                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                    boolean played = players[turn].playTurn(deck);
                    if (played)
                    {
                        if (deck.getIsLight())
                        {
                            switch (deck.getTopCard().getNumber(true))
                            {
                                case 10 ->
                                {
                                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                                    players[turn].draw(deck);
                                }
                                case 11 ->
                                {
                                    deck.reverse();
                                }
                                case 12 ->
                                {
                                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                                }
                                case 13 ->
                                    deck.flip();
                                case 14 ->
                                    deck.getTopCard().setColor(players[turn].chooseColor(deck), deck.getIsLight());
                                case 15 ->
                                {
                                    deck.getTopCard().setColor(players[turn].chooseColor(deck), deck.getIsLight());
                                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                                    players[turn].draw(deck);
                                    players[turn].draw(deck);
                                }
                                default ->
                                {
                                }
                            }
                        } else
                        {
                            switch (deck.getTopCard().getNumber(false))
                            {
                                case 10 ->
                                {
                                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                                    for (int i = 0; i < 5; i++)
                                    {
                                        players[turn].draw(deck);
                                        if (deck.getDrawPile().isEmpty())
                                        {
                                            break;
                                        }
                                    }
                                    if (deck.getDrawPile().isEmpty())
                                    {
                                        break;
                                    }
                                }
                                case 11 ->
                                {
                                    deck.reverse();
                                }
                                case 12 ->
                                {
                                    turn = adjustTurn(turn, !deck.isClockDir(), numPlayers);
                                }
                                case 13 ->
                                    deck.flip();
                                case 14 ->
                                    deck.getTopCard().setColor(players[turn].chooseColor(deck), deck.getIsLight());
                                case 15 ->
                                {
                                    deck.getTopCard().setColor(players[turn].chooseColor(deck), deck.getIsLight());
                                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                                    do
                                    {
                                        players[turn].draw(deck);
                                        if (deck.getDrawPile().isEmpty())
                                        {
                                            break;
                                        }
                                    } while (players[turn].getHand().get(players[turn].getHand().size() - 1).getColor(false) != deck.getTopCard().getColor(false));
                                }
                                default ->
                                {
                                }
                            }
                            if (deck.getDrawPile().isEmpty())
                            {
                                break;
                            }
                            int smallestHandSize = 100;
                            for (int i = 0; i < players.length; i++)
                            {
                                if (players[i].getHand().size() < smallestHandSize)
                                {
                                    smallestHandSize = players[i].getHand().size();
                                }
                            }
                            deck.setSmallestHandSize(smallestHandSize);
                        }
                    }
                }
                if (deck.getDrawPile().isEmpty())
                {
                    break;
                }

                // count up points
                if (deck.getTopCard().getNumber(deck.getIsLight()) == 10 || deck.getTopCard().getNumber(deck.getIsLight()) == 15 || (deck.getTopCard().getNumber(deck.getIsLight()) == 12 && deck.getIsLight()))
                {
                    turn = adjustTurn(turn, !deck.isClockDir(), numPlayers);
                } else if (deck.getTopCard().getNumber(deck.getIsLight()) == 12)
                {
                    turn = adjustTurn(turn, deck.isClockDir(), numPlayers);
                }
                int points = 0;
                for (Player player : players)
                {
                    points += player.getHandPoints(deck.getIsLight());
                }
                players[turn].increaseScore(points);

                winner = false;
                for (int i = 0; i < players.length; i++)
                {
                    if (players[i].getScore() >= 500)
                    {
                        winner = true;
                        break;
                    }
                }
                /*System.out.println("");
                for (int i = 0; i < players.length; i++)
                {
                    System.out.println("Player " + (i + 1) + " Score: " + players[i].getScore());
                }*/
            } while (!winner);
            if (deck.getDrawPile().isEmpty())
            {
                invalidGames++;
                game--;
            } else
            {
                int highestScore = 499;
                int winningPlayer = -1;
                for (int i = 0; i < players.length; i++)
                {
                    if (players[i].getScore() > highestScore)
                    {
                        winningPlayer = i;
                        highestScore = players[i].getScore();
                    }
                    players[i].resetScore();
                }
                players[winningPlayer].increaseWins();
            }
        }
        /*long total = 0;
        System.out.println("");
        for (int i = 0; i < players.length; i++)
        {
            System.out.println("Player " + (i + 1) + " Score: " + players[i].getScore());
            total += players[i].getScore();
        }
        System.out.println("Average score: " + (total / 6));
        System.out.println("Player 1 Score / Average Score: " + (players[0].getScore() / (total / 6.0)));*/
        for (int i = 0; i < players.length; i++)
        {
            System.out.println("Player " + (i + 1) + " Wins: " + players[i].getWins());
        }
        System.out.println("Invalid Games: " + invalidGames);
    }

    public int adjustTurn(int turn, boolean clockwiseDir, int numPlayers)
    {
        int nextTurn;
        if (clockwiseDir)
        {
            nextTurn = turn + 1;
            nextTurn = nextTurn % numPlayers;
        } else
        {
            nextTurn = turn - 1;
            if (nextTurn < 0)
            {
                nextTurn += numPlayers;
            }
        }
        return nextTurn;
    }

}
