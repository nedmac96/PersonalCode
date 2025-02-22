/**
 * ***************************************************************************
 * <p>
 * Author: Francis Ricci File: Monopoly.java
 * <p>
 * Source code file dependencies: Board.java Card.java Cards.java
 * RandomDeck.java ProbDice.java Inactive.java Jail.java HumanPlayer.java
 * Prob.java Property.java Railroad.java Shuffle.java Square.java Taxes.java
 * Utility.java
 * <p>
 * Execution: monopoly.Monopoly
 * <p>
 * Purpose: Run a text-based Monopoly game emulator.
 * <p>
 * ***************************************************************************
 */
package monopoly;

import monopoly.Jail.JailType;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

class Monopoly
{

    private final boolean deterministic;
    private final Dice dice; //two six-sided dice
    private final Deck chance;
    private final Deck community;
    private State state;
    private boolean chanceBoost = false;
    private ValueEstimator valueEstimator;
    private boolean lost = false;

    private Monopoly()
    {
        deterministic = false;
        dice = new ProbDice(); //two dice, six sided
        chance = new RandomDeck();
        community = new RandomDeck();
    }

    public static void main(String[] args)
    {
        Monopoly monopoly = new Monopoly();
        monopoly.run();
    }

    private void run()
    {
        int maxTurns = 3000;
        int players = 2;
        int[] wins1 =
        {
            0, 0, 0, 0, 0, 0, 0, 0
        };
        int[] wins2 =
        {
            0, 0, 0, 0, 0, 0, 0, 0
        };
        int[] endTurn =
        {
            0, 0, 0, 0
        };
        int[] tileScores = new int[41];
        for (int i = 0; i < 10; i++)
        {

            for (int k = 0; k < 1000; k++)
            {
                state = new State();
                state.players = new LinkedList<>();
                state.current = null;
                state.state = DecisionState.NONE;

                //System.out.println("Would you like to provide your own dice and card input?");
                //deterministic = input.inputBool();

                /*if (deterministic)
        {
            dice = new InputDice(input);
            chance = new InputDeck();
            community = new InputDeck();
        } else
        {
            dice = new ProbDice(); //two dice, six sided
            chance = new RandomDeck();
            community = new RandomDeck();
        }*/
                state.board = new Board(chance, community, false); //create new board
                Input input = new Input();

                initialize(input, players);
                /*state.current = state.players.remove();
                state.players.add(state.current);
                while (state.current.getIndex() != 0)
                {
                    state.current = state.players.remove();
                    state.players.add(state.current);
                }*/
                int turn = 0;
                while (state.players.size() > 1 && turn < maxTurns)
                {
                    try
                    {
                        state.current = state.players.remove();
                        turn++;
                        //System.out.println("Turn: " + turn);

                        turn(tileScores);

                        if (!lost)
                        {
                            state.players.add(state.current);
                        }
                        lost = false;
                    } catch (NoSuchElementException e)
                    {
                        System.out.println("Early Termination initiated.");
                        return;
                    } finally
                    {
                        //printState();
                    }
                }
                Player winner = null;
                if (turn < maxTurns)
                {
                    winner = state.players.remove();
                    wins1[winner.getIndex()]++;
                } else
                {
                    int winnerMoney = 0;
                    /*state.current = state.players.remove();
                    state.players.add(state.current);*/
                    int playersLeft = state.players.size();
                    for (int j = 0; j < playersLeft; j++)
                    {
                        state.current = state.players.remove();
                        if (state.current.getMoney() == winnerMoney)
                        {
                            System.out.println("Tie");
                        }
                        if (state.current.getMoney() > winnerMoney)
                        {
                            winner = state.current;
                            winnerMoney = state.current.getMoney();
                        }

                    }
                    if (winnerMoney != 0)
                    {
                        wins2[winner.getIndex()]++;
                    }

                }
                endTurn[turn / 1000]++;
                /*System.out.println("----------------------------------------");
            System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            System.out.println();
            System.out.println();
            System.out.println();*/
                //System.out.println("THE WINNER IS " + winner.name() + "!!!");
                /*System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("////////////////////////////////////////");
            System.out.println("----------------------------------------");*/
            }
            System.out.println("Game: " + (i * 1000));
        }

        for (int i = 0; i < players; i++)
        {
            System.out.println("CPU " + (i + 1) + " -> " + wins1[i]);
        }
        System.out.println("");
        for (int i = 0; i < players; i++)
        {
            System.out.println("CPU " + (i + 1) + " -> " + wins2[i]);
        }
        System.out.println("");
        for (int i = 0; i < 4; i++)
        {
            System.out.println((i + 1) + " -> " + endTurn[i]);
        }
        /*System.out.println("");
        for (int i = 0; i < 40; i++)
        {
            System.out.println(tileScores[i]);
        }*/
    }

    private void initialize(Input input, int players)
    {
        //System.out.println("How many total players?");
        //int N = input.inputInt();
        int N = players;
        while (N < 2 || N > 8)
        {
            System.out.println("Must have between 2 and 8 players. Please try again.");
            N = input.inputInt();
        }

        //System.out.println("How many human players?");
        //int H = input.inputInt();
        int H = 0;
        while (H < 0 || H > N)
        {
            if (H < 1)
            {
                System.out.println("Must have at least one human player. Please try again.");
            }
            if (H > N)
            {
                System.out.println("Cannot have more human players than total players. Please try again.");
            }
            H = input.inputInt();
        }

        int[] order = new int[N];
        for (int i = 0; i < H; i++)
        {
            //System.out.println("Player " + (i + 1) + " name?");
            //state.players.add(new HumanPlayer(input.inputString()));
            //state.players.add(new HumanPlayer("Human " + (i + 1)));
            state.players.add(new HumanPlayer(i));
        }

        for (int i = H; i < N; i++)
        {
            state.players.add(new CPUPlayer(i - H));
        }

        valueEstimator = new ValueEstimator(state.board, state.players, new ProbDice(), (Cards) state.board.square(7));

        if (deterministic)
        {
            return;
        }

        boolean tie = true;
        boolean[] ties = new boolean[N];
        for (int i = 0; i < N; i++)
        {
            ties[i] = true;
        }
        int first = -1;

        while (tie)
        {
            for (int i = 0; i < N; i++)
            {
                if (ties[i])
                {
                    order[i] = dice.roll().val;
                }
            }

            int maxRoll = 0;

            for (int i = 0; i < N; i++)
            {
                if (ties[i])
                {
                    if (order[i] > maxRoll)
                    {
                        maxRoll = order[i];
                        first = i;
                    }
                }
            }

            tie = false;
            for (int i = 0; i < N; i++)
            {
                ties[i] = false;
            }

            for (int i = 0; i < N; i++)
            {
                if (order[i] == maxRoll && i != first)
                {
                    ties[i] = true;
                    tie = true;
                }
            }
        }

        for (int i = 0; i < first; i++)
        {
            state.players.add(state.players.remove());
        }
        //printState();
    }

    private void turn(int[] tileScores)
    {
        //System.out.println("It's " + state.current.name() + "'s turn");
        int double_count = 0;
        while (true)
        {
            tileScores[state.current.position()]++;
            if (state.current.inJail())
            {
                //System.out.println("Would you like to get out of jail using cash or card?");
                state.state = DecisionState.BUY_JAIL;
                if (state.current.inputBool(state))
                {
                    //System.out.println("Select cash or card.");
                    state.state = DecisionState.CASH_CARD;
                    int choice = state.current.inputDecision(state, new String[]
                    {
                        "cash", "card"
                    });
                    if (choice == 0)
                    {
                        state.current.excMoney(-50);
                        state.current.leaveJail();
                    } else if (state.current.numJailFree() > 0)
                    {
                        if (state.current.useJailFree())
                        {
                            chance.returnOutOfJail();
                        } else
                        {
                            community.returnOutOfJail();
                        }
                        state.current.leaveJail();
                    } else
                    {
                        System.out.println("You don't have any cards.");
                    }
                }
            }

            Dice.Roll roll = dice.roll();
            if (roll.is_double)
            {
                double_count++;
            }

            if (state.current.inJail())
            {
                if (roll.is_double)
                {
                    state.current.leaveJail();
                    roll.is_double = false; //we don't re-roll if the double was used to escape jail
                } else
                {
                    //System.out.println("You did not roll a double.");
                    if (!state.current.stayJail())
                    {
                        leaveJail(state.current);
                    } else
                    {
                        break;
                    }
                }
            }

            if (double_count == 3)
            {
                toJail(state.current);
                break;
            }

            //System.out.print("You rolled a " + roll.val);
            if (roll.is_double)
            {
                //System.out.print(" (double)");
            }
            Square[] square = state.board.getBoard();
            //System.out.println(" and landed on " + square[(state.current.position() + roll.val) % 40].name());
            state.current.move(roll.val);

            handleSquare(state.current, square[state.current.position()], roll.val);

            if (!roll.is_double || state.current.inJail())
            {
                break;
            }
        }

        boolean additional = true;
        while (additional && !lost)
        {
            /*System.out.println("Would you like to take any additional actions on this turn?");
            System.out.println("Please select choice");
            System.out.println("1) Buy/sell houses");
            System.out.println("2) Mortgage/unmortgage properties");
            System.out.println("3) Trade with another player");
            System.out.println("4) Nothing");*/
            state.state = DecisionState.ADDITIONAL;
            int decision = state.current.inputInt(state);

            switch (decision)
            {
                case 1:
                    handleHouses(state.current);
                    break;
                case 2:
                    handleMortgages(state.current);
                    break;
                case 3:
                    handleTrade(state.current);
                    break;
                case 4:
                    additional = false;
                    break;
                default:
                    System.out.println("Please enter a valid decision.");
            }
        }

        //System.out.println();
    }

    private void handleSquare(Player player, Square sq, int roll)
    {
        boolean owned = sq.isOwned();
        boolean ownable = sq.isOwnable();

        if (!owned && ownable)
        {
            unowned(player, sq);
        } else if (ownable && !sq.isMortgaged())
        {
            owned(player, sq, roll);
        } else if (sq instanceof Taxes)
        {
            payTax(player, (Taxes) sq, sq);
        } else if (sq instanceof Cards)
        {
            drawCard(player, (Cards) sq);
        } else if (sq instanceof Jail)
        {
            jailInteraction(player, (Jail) sq);
        }
    }

    private void buyHouses(Player player)
    {
        /*System.out.println("Expected Values:");
        for (Square sq : player.properties())
        {
            Property prop;
            if (sq instanceof Property)
            {
                prop = (Property) sq;
            } else
            {
                continue;
            }

            double val = valueEstimator.expectedValue(sq.position(), prop.rentDiff());

            System.out.println(prop.name() + ": " + val);
        }*/
        //do
        //{
        //System.out.println("On which property would you like to purchase a house?");
        Property prop = propertySelect(player);
        if (prop.numHouses() == 5 || !prop.monopoly())
        {
            System.out.println("You cannot buy houses on " + prop.name());
            System.out.println("Would you like to buy any more houses?");
            //continue;
        } else if (player.getMoney() < prop.houseCost())
        {
            System.out.println("You cannot afford to buy houses on " + prop.name());
            System.out.println("Would you like to buy any more houses?");
            //continue;
        } else if (!prop.groupBuild())
        {
            System.out.println("You must build evenly. Select another property.");
            System.out.println("Would you like to buy any more houses?");
            //continue;
        } else
        {

            prop.build(1);
            player.excMoney(-1 * prop.houseCost());

            //System.out.println("You now own " + prop.numHouses() + " houses on " + prop.name());
        }
        //System.out.println("Would you like to buy any more houses?");
        //} while (player.inputBool(state));
    }

    private int sellHouses(Player player)
    {
        int value = 0;
        do
        {
            System.out.println("On which property would you like to sell a house?");
            Property prop = propertySelect(player);
            if (prop.numHouses() == 0)
            {
                System.out.println("You cannot sell houses on " + prop.name());
                System.out.println("Would you like to sell any more houses?");
                continue;
            }

            if (!prop.groupSell())
            {
                System.out.println("You must build evenly. Select another property.");
                System.out.println("Would you like to sell any more houses?");
                continue;
            }

            prop.build(-1);
            value += prop.houseCost() / 2;

            System.out.println("You now own " + prop.numHouses() + " houses on " + prop.name());
            System.out.println("Would you like to sell any more houses?");
        } while (player.inputBool(state));
        player.excMoney(value);
        return value;
    }

    private void handleHouses(Player player)
    {
        //System.out.println("Would you like to buy houses?");
        state.state = DecisionState.BUY_HOUSE;
        if (player.inputBool(state))
        {
            buyHouses(player);
        }

        //System.out.println("Would you like to sell houses?");
        state.state = DecisionState.SELL_HOUSE;
        if (player.inputBool(state))
        {
            sellHouses(player);
        }
    }

    private void mortgage(Player player)
    {

        //System.out.println("Which property would you like to mortgage?");
        Square sq = squareSelect(player, false);

        if (sq.isMortgaged())
        {
            System.out.println("This property is already mortgaged.");
            System.out.println("Would you like to mortgage a different property?");
        }

        player.excMoney(sq.mortgage());

    }

    private void unmortgage(Player player)
    {

        //System.out.println("Which property would you like to unmortgage?");
        Square sq = squareSelect(player, true);
        player.excMoney(sq.mortgage());

    }

    private void handleMortgages(Player player)
    {
        //System.out.println("Would you like to mortgage properties?");
        state.state = DecisionState.MORTGAGE;
        if (player.inputBool(state))
        {
            mortgage(player);
        }
        //System.out.println("Would you like to unmortgage properties?");
        state.state = DecisionState.UNMORTGAGE;
        if (player.inputBool(state))
        {
            unmortgage(player);
        }
    }

    private void handleTrade(Player player)
    {
        System.out.println("With which player would you like to trade?");
        state.state = DecisionState.TRADE;
        Player other = player.inputPlayer(state, player);

        System.out.println("Would you like to exchange money?");
        state.state = DecisionState.TRADE_MONEY;
        if (player.inputBool(state))
        {
            System.out.println("Money exchange value? (Negative if you give them money)");
            int val = player.inputInt(state);
            player.excMoney(val);
            other.excMoney(-1 * val);
        }

        System.out.println("Would you like to give them properties?");
        state.state = DecisionState.GIVE_PROPS;
        while (player.inputBool(state))
        {
            Square sq = squareSelect(player);
            sq.purchase(other);
            player.sellProp(sq);
            other.addProperty(sq);
            System.out.println("Any more properties to give?");
        }

        System.out.println("Would they like to give you properties?");
        state.state = DecisionState.GET_PROPS;
        while (player.inputBool(state))
        {
            Square sq = squareSelect(other);
            sq.purchase(player);
            other.sellProp(sq);
            player.addProperty(sq);
            System.out.println("Any more properties to give?");
        }
    }

    private void leaveJail(Player player)
    {
        int JAIL_COST = 50;
        if (player.numJailFree() > 0)
        {
            player.useJailFree();
            //System.out.println("You used a Get Out Of Jail Free Card!");
        } else if (player.getMoney() >= JAIL_COST)
        {
            player.excMoney(JAIL_COST * -1);
            //System.out.println("You paid $50 to get out of jail!");
        } else
        {
            int cost = JAIL_COST;
            Player bank = new CPUPlayer(-1);
            while (true)
            {
                cost = additionalFunds(cost, player, bank);
                if (cost == Integer.MIN_VALUE)
                {
                    return;
                }
                if (cost <= 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }

    private void unowned(Player player, Square square)
    {
        int cost = square.cost();

        if (totalVal(availableAssets(player)) + player.getMoney() < cost)
        {
            //System.out.println("You cannot afford to purchase " + square.name());
            //purchase(auction(player, square), square);
            return;
        }

        boolean additional = false;
        //System.out.println("Would you like to purchase " + square.name() + " for " + cost + " (Yes/No)?");
        state.state = DecisionState.PURCHASE;
        if (player.getMoney() < cost)
        {
            additional = true;
            //System.out.println("This transaction will require additional funds.");
        }

        if (player.inputBool(state))
        {
            if (!additional)
            {
                player.excMoney(-1 * cost);
            } else
            {
                Player bank = new CPUPlayer(-1);
                while (true)
                {
                    cost = additionalFunds(cost, player, bank);
                    if (cost == Integer.MIN_VALUE)
                    {
                        return;
                    }
                    if (cost <= 0)
                    {
                        player.excMoney(cost * -1);
                        break;
                    }
                }
            }

            purchase(player, square);
        }
        /*else
        {
            purchase(auction(player, square), square);
        }*/

    }

    private void purchase(Player player, Square square)
    {
        if (player == null || square == null)
        {
            return;
        }

        if (!square.isOwnable())
        {
            return;
        }

        player.addProperty(square);
        square.purchase(player);
    }

    private Player auction(Player player, Square square)
    {
        System.out.println("Auctioning off " + square.name() + ".");
        int currentBid = -10;
        final int BID_INCREMENT = 10;

        Player winner = null;
        while (true)
        {
            int minBid = currentBid + BID_INCREMENT;
            System.out.println("Would anyone like to place a bid? Minimum bid: $" + minBid);
            state.state = DecisionState.AUCTION;
            state.val = minBid;
            if (!player.inputBool(state))
            {
                break;
            }

            System.out.println("Please enter player name"); //TODO has to be changed for CPU
            winner = player.inputPlayer(state, player);
            System.out.println(winner.name() + ", please enter your bid.");
            int bid = player.inputInt(state);
            if (bid < minBid)
            {
                System.out.println("Bid is below minimum bid. Please try again.");
                continue;
            }

            System.out.println("Bid accepted. Current highest bid - " + winner.name() + " for $" + bid);
            currentBid = bid;
        }

        if (winner != null)
        {
            winner.excMoney(-1 * currentBid);
            System.out.println(winner.name() + " wins auction, for $" + currentBid);
        } else
        {
            System.out.println("No player wins auction.");
        }

        return winner;
    }

    private void owned(Player player, Square square, int val)
    {
        int cost = square.rent(val);
        if (square instanceof Utility && chanceBoost)
        {
            cost = ((Utility) square).increasedRent();
        } else if (square instanceof Railroad && chanceBoost)
        {
            cost *= 2;
        }
        chanceBoost = false;
        Player owner = square.owner();
        if (player.name().equals(owner.name()))
        {
            return;
        }
        boolean additional = false;
        //System.out.println("You have landed on " + square.name() + " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            //System.out.println("This transaction will require additional funds.");
        }

        if (!additional)
        {
            player.excMoney(-1 * cost);
            owner.excMoney(cost);
        } else
        {
            while (true)
            {
                cost = additionalFunds(cost, player, owner);
                if (cost == Integer.MIN_VALUE)
                {
                    return;
                }
                if (cost <= 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }

    private void payTax(Player player, Taxes tax, Square square)
    {
        int cost;
        if (square.position() == 4)
        {
            //System.out.println("Would you like to pay 10% or 200 (10%/200)?");
            state.state = DecisionState.INCOME_TAX;
            if (player.inputDecision(state, new String[]
            {
                "10%", "200"
            }) == 0)
            {
                cost = tax.tax(player.getAssets());
            } else
            {
                cost = tax.tax();
            }
        } else
        {
            cost = tax.tax();
        }
        boolean additional = false;
        //System.out.println("You have landed on " + square.name() + " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            //System.out.println("This transaction will require additional funds.");
        }

        if (!additional)
        {
            player.excMoney(-1 * cost);
        } else
        {
            Player bank = new CPUPlayer(-1);
            while (true)
            {
                cost = additionalFunds(cost, player, bank);
                if (cost == Integer.MIN_VALUE)
                {
                    return;
                }
                if (cost <= 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }

    private void drawCard(Player player, Cards cards)
    {
        int numString = 3;
        Card card = cards.draw();
        String[] string = new String[numString];
        if (card.textA() != null)
        {
            string[0] = card.textA();
        }
        if (card.textB() != null)
        {
            string[1] = card.textB();
        }
        if (card.textC() != null)
        {
            string[2] = card.textC();
        }
        for (int i = 0; i < numString; i++)
        {
            if (string[i] == null)
            {
                break;
            }
            //System.out.println(string[i]);
        }

        int initialPos = player.position();

        switch (card.action())
        {
            case BANK_MONEY:
                player.excMoney(card.value());
                break;
            case PLAYER_MONEY:
                allPlayers(-1 * card.eachPlayer(), player);
                break;
            case MOVE:
                player.move(card.travel());
                break;
            case MOVE_TO:
                player.moveTo(card.travelTo());
                break;
            case MOVE_NEAREST:
                if (card.travelRail())
                {
                    railMove(player);
                } else
                {
                    utilMove(player);
                }
                break;
            case STREET_REPAIRS:
                streetRepairs(player, card.house(), card.hotel());
                break;
            case OUT_JAIL:
                player.addJailFree(card.type() == Card.CardType.CHANCE);
                break;
            default:
                break;
        }

        chanceBoost = card.increased();

        if (initialPos == player.position())
        {
            return;
        }

        Square sq = state.board.square(player.position());
        handleSquare(player, sq, 0);
    }

    private void railMove(Player player)
    {
        int pos = player.position();

        for (int i = pos; i < state.board.size(); i++)
        {
            if (state.board.square(i) instanceof Railroad)
            {
                player.moveTo(i);
                return;
            }
        }

        for (int i = 0; i < pos; i++)
        {
            if (state.board.square(i) instanceof Railroad)
            {
                player.moveTo(i);
                return;
            }
        }

        throw new RuntimeException("Problem finding railroad");
    }

    private void utilMove(Player player)
    {
        int pos = player.position();

        for (int i = pos; i < state.board.size(); i++)
        {
            if (state.board.square(i) instanceof Utility)
            {
                player.moveTo(i);
                return;
            }
        }

        for (int i = 0; i < pos; i++)
        {
            if (state.board.square(i) instanceof Utility)
            {
                player.moveTo(i);
                return;
            }
        }

        throw new RuntimeException("Problem finding utility");
    }

    private void streetRepairs(Player player, int house, int hotel)
    {
        int val = 0;

        for (Square sq : player.properties())
        {
            if (sq instanceof Property)
            {
                Property prop = (Property) sq;
                if (prop.numHouses() < 5)
                {
                    val += house * prop.numHouses();
                } else
                {
                    val += hotel;
                }
            }
        }

        boolean additional = false;
        //System.out.println("You owe " + val + " for street repairs.");
        if (player.getMoney() < val)
        {
            additional = true;
            //System.out.println("This transaction will require additional funds.");
        }

        if (!additional)
        {
            player.excMoney(-1 * val);
        } else
        {
            while (true)
            {
                val = additionalFunds(val, player, new CPUPlayer(-1));
                if (val == Integer.MIN_VALUE)
                {
                    return;
                }
                if (val <= 0)
                {
                    player.excMoney(val * -1);
                    break;
                }
            }
        }
    }

    private void allPlayers(int value, Player player)
    {
        player.excMoney(-1 * (state.players.size() - 1) * value);
        state.players.stream().forEach(p -> p.excMoney(value));
    }

    private void jailInteraction(Player player, Jail jail)
    {
        Jail.JailType type = jail.getType();
        if (type == JailType.TO_JAIL)
        {
            intoJail(player);
        }
    }

    private void toJail(Player player)
    {
        //System.out.println("Go to Jail!");
        player.moveTo(40);
        Square[] square = state.board.getBoard();
        Jail jail = (Jail) square[40];
        jailInteraction(player, jail);
    }

    private void intoJail(Player player)
    {
        player.toJail();
    }

    private int additionalFunds(int cost, Player player, Player owner)
    {
        Queue<Square> props = availableAssets(player);
        int availableAssets = mortVal(props) + player.getMoney();

        if (cost <= player.getMoney())
        {
            player.excMoney(-1 * cost);
            owner.excMoney(cost);
            return 0;
        }

        if (availableAssets < cost)
        {
            lose(player, owner);
            return Integer.MIN_VALUE;
        } else
        {
            //System.out.println("You need additional funds!");
            //System.out.println("How will you obtain necessary funds (Mortgage/Sell Houses)?");
            state.state = DecisionState.FUNDS;
            int choice = player.inputDecision(state, new String[]
            {
                "Mortgage", "Sell Houses"
            });

            if (choice == 0)
            {
                //System.out.println("Which property would you like to mortgage?");
                //System.out.println("Please enter number.");
                Square sq = squareSelect(player, false);

                cost -= sq.mortgage();
            } else
            {
                cost -= sellHouses(player);
            }
        }

        return cost;
    }

    private Property propertySelect(Player player)
    {
        Queue<Square> props = new LinkedList<>();
        for (Square sq : player.properties())
        {
            if (!(sq instanceof Property))
            {
                continue;
            }
            Property prop = (Property) sq;
            if (!sq.isMortgaged() && sq.cost() < player.getMoney() && prop.monopoly() && prop.groupBuild() && prop.numHouses() < 5)
            {

                props.add(sq);
            }
        }
        return (Property) propertySelect(props, player);
    }

    private Square squareSelect(Player player, boolean mort)
    {
        Queue<Square> props = new LinkedList<>();
        for (Square sq : player.properties())
        {
            if (sq.isMortgaged() == mort)
            {
                props.add(sq);
            }
        }
        return propertySelect(props, player);
    }

    private Square squareSelect(Player player)
    {
        return propertySelect(player.properties(), player);
    }

    private Square propertySelect(Iterable<Square> props, Player player)
    {
        //System.out.println("You may update the following properties:");

        int counter = 1;
        /*for (Square sq : props)
        {
            System.out.println(counter++ + ") " + sq.name());
        }*/

        while (true)
        {
            int propNum = player.inputInt(state);
            int propState = 1;

            for (Square sq : props)
            {
                if (propState++ == propNum)
                {
                    return sq;
                }
            }

            System.out.println("Please select a valid property.");
        }
    }

    private Queue<Square> availableAssets(Player player)
    {
        Iterable<Square> props = player.properties();
        Queue<Square> avail = new LinkedList<>();
        for (Square sq : props)
        {
            if (!sq.isMortgaged())
            {
                avail.add(sq);
            }
        }
        return avail;
    }

    private int totalVal(Queue<Square> props)
    {
        int totalMoney = 0;
        for (Square sq : props)
        {
            totalMoney += sq.cost();
            if (sq instanceof Property)
            {
                Property prop = (Property) sq;
                totalMoney += prop.numHouses() * prop.houseCost();
            }
        }
        return totalMoney;
    }

    private int mortVal(Queue<Square> props)
    {
        int totalMoney = 0;
        for (Square sq : props)
        {
            totalMoney += sq.mortgageCost();
            if (sq instanceof Property)
            {
                Property prop = (Property) sq;
                totalMoney += prop.numHouses() * prop.houseCost() / 2;
            }
        }
        return totalMoney;
    }

    private void lose(Player loser, Player winner)
    {
        Iterable<Square> squares = loser.properties();
        for (Square sq : squares)
        {
            winner.addProperty(sq);
        }
        winner.excMoney(loser.getMoney());
        while (loser.numJailFree() > 0)
        {
            winner.addJailFree(loser.useJailFree());
        }

        lost = true;
        //System.out.println(loser.name() + " has LOST!");
    }

    private void printState()
    {
        int counter = 1;
        for (Player player : state.players)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("Player " + counter++);
            System.out.printf("%-10s%40s%n", "Name", player.name());
            System.out.printf("%-10s%40s%n", "Money", player.getMoney());
            System.out.printf("%-10s%40s%n", "Position", player.position());
            System.out.printf("%-10s", "Properties");
            Iterable<Square> owned = player.properties();

            boolean first = true;
            for (Square s : owned)
            {
                if (first)
                {
                    System.out.printf("%40s%n", s);
                } else
                {
                    System.out.printf("%50s%n", s);
                }
                first = false;
            }

            if (first)
            {
                System.out.printf("%40s%n", "none");
            }

            if (player.inJail())
            {
                System.out.println("In jail");
            }

            if (player.numJailFree() > 0)
            {
                System.out.println(player.numJailFree() + " out of jail free cards");
            }
            System.out.println("--------------------------------------------------");
        }
    }

    public enum DecisionState
    {
        NONE, BUY_JAIL, CASH_CARD, BUY_HOUSE, SELL_HOUSE, MORTGAGE, UNMORTGAGE, TRADE,
        TRADE_MONEY, GIVE_PROPS, GET_PROPS, PURCHASE, AUCTION, INCOME_TAX, FUNDS, ADDITIONAL
    }

    public class State
    {

        public DecisionState state;
        public Queue<Player> players;
        public Board board; //game board
        public Player current;
        public int val = 0;
    }
}
