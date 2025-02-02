/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chickenfootgame;

/**
 *
 * @author Camden
 */
public class ChickenFootGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ChickenFootGame game = new ChickenFootGame();
        game.runGame();
    }

    public void runGame()
    {
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
        
        Average score: 2923812
        Player 1 Score / Average Score: 0.9519432149516869
        
        Average score: 29335547
        Player 1 Score / Average Score: 0.9347274273375022
         */
        final int numPlayers = 6;
        final int highestDomino = 11;
        final int numGames = 100000;

        Board board = new Board();
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++)
        {
            if (i == 0 || i == 1)
            {
                players[i] = new Player(8);
            } else
            {
                players[i] = new Player(6);
            }
        }
        for (int game = 0; game < numGames; game++)
        {
            int startingPlayer = (int) Math.floor(Math.random() * numPlayers);

            for (int startingNum = highestDomino; startingNum >= 0; startingNum--)
            {
                // set up the round
                int turn = -1;
                board.setUpRound(highestDomino);
                for (Player player : players)
                {
                    player.drawHand(board);
                }

                // find the double
                for (int i = 0; i < players.length; i++)
                {
                    if (players[i].hasDouble(startingNum))
                    {
                        turn = i;
                        break;
                    }
                }
                if (turn == -1)
                {
                    turn = startingPlayer;
                    startingPlayer++;
                    startingPlayer = startingPlayer % numPlayers;
                    boolean doubleFound = false;
                    while (!doubleFound)
                    {
                        players[turn].draw(board);
                        if (players[turn].hasDouble(startingNum))
                        {
                            doubleFound = true;
                        } else
                        {
                            turn++;
                            turn = turn % numPlayers;
                        }
                    }
                    Domino dominoToPlay = players[turn].getHand().get(players[turn].getHand().size() - 1);
                    players[turn].removeDomino(dominoToPlay);
                    board.play(dominoToPlay);
                } else
                {
                    for (int i = 0; i < players[turn].getHand().size(); i++)
                    {
                        if (players[turn].getHand().get(i).getNum1() == startingNum && players[turn].getHand().get(i).getNum2() == startingNum)
                        {
                            board.play(players[turn].getHand().get(i));
                            players[turn].removeDomino(players[turn].getHand().get(i));
                        }
                    }
                }

                // play the round
                int dominosPlayed = 0;
                while (!players[turn].getHand().isEmpty())
                {
                    turn++;
                    if (turn == numPlayers)
                    {
                        turn = turn % numPlayers;
                        if (!board.canDraw())
                        {
                            if (dominosPlayed == 0)
                            {
                                dominosPlayed = board.getDominosPlayed().size();
                            } else if (dominosPlayed == board.getDominosPlayed().size())
                            {
                                break;
                            } else
                            {
                                dominosPlayed = board.getDominosPlayed().size();
                            }
                        }
                    }
                    players[turn].playTurn(board);
                    int smallestHandSize = 100;
                    for (int i = 0; i < players.length; i++)
                    {
                        if (players[i].getHand().size() < smallestHandSize)
                        {
                            smallestHandSize = players[i].getHand().size();
                        }
                    }
                    board.setSmallestHandSize(smallestHandSize);
                }

                // count up points
                for (Player player : players)
                {
                    player.increaseScore();
                }
                /*
                // display scoreboard
                System.out.println("");
                for (int i = 0; i < players.length; i++)
                {
                    System.out.println("Player " + (i + 1) + " Score: " + players[i].getScore());
                }
                 */
            }

            // display scoreboard
            //System.out.println("Final Scores");
            /*System.out.println("");
            for (int i = 0; i < players.length; i++)
            {
                System.out.println("Player " + (i + 1) + " Score: " + players[i].getScore());
            }*/
            int lowestScore = 500;
            int winningPlayer = -1;
            for (int i = 0; i < players.length; i++)
            {
                if (players[i].getScore() < lowestScore)
                {
                    winningPlayer = i;
                    lowestScore = players[i].getScore();
                }
                players[i].resetScore();
            }
            players[winningPlayer].increaseWins();
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
    }

}
