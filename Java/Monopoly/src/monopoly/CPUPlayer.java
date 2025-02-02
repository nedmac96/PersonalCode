package monopoly;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by fjricci on 6/22/2015. A CPU player.
 */
public class CPUPlayer implements Player
{

    private final int TO_JAIL = 30;
    private final int IN_JAIL = 10;
    private final Queue<Square> properties;
    private final String playerName;
    private int money;
    private int position;
    private boolean inJail;
    private int jailTurn;
    private int numJailFree;
    private boolean chanceFree;
    private final int index;
    private int stratagy;

    public CPUPlayer(int index)
    {
        money = 1500;
        properties = new LinkedList<>();
        position = 0;
        this.playerName = "CPU " + (index + 1);
        inJail = false;
        numJailFree = 0;
        chanceFree = false;
        this.index = index;
        if (index == 0)
        {
            stratagy = 3;
        } else
        {
            stratagy = 1;
        }

        // 0 is mortgage
        // 1 is Uncle Grant
        // 2 is Aunt Elena
        // 3 is modified Aunt Elena
    }

    public void addProperty(Square square)
    {
        if (!square.isOwnable())
        {
            throw new IllegalArgumentException("This property cannot be purchased!");
        }
        properties.add(square);
        square.purchase(this);
    }

    public void move(int numSpaces)
    {
        position += numSpaces;
        int BOARD_SIZE = 40;
        if (position >= BOARD_SIZE)
        {
            position -= BOARD_SIZE;
            excMoney(200);
        }

        if (position == TO_JAIL)
        {
            position = IN_JAIL;
            toJail();
        }
    }

    public void moveTo(int pos)
    {
        if (pos < position && !inJail)
        {
            excMoney(200);
        }
        position = pos;

        if (position == TO_JAIL)
        {
            position = IN_JAIL;
            toJail();
        }
    }

    public int position()
    {
        return position;
    }

    public Queue<Square> properties()
    {
        return properties.stream().collect(Collectors.toCollection(LinkedList::new));
    }

    public String name()
    {
        return playerName;
    }

    public int getMoney()
    {
        return money;
    }

    public void excMoney(int money)
    {
        this.money += money;
    }

    public void toJail()
    {
        inJail = true;
        move(40);
        jailTurn = 0;
    }

    public boolean stayJail()
    {
        jailTurn++;
        if (jailTurn == 3)
        {
            inJail = false;
            return false;
        }
        return true;
    }

    public void sellProp(Square sq)
    {
        properties.remove(sq);
    }

    public void leaveJail()
    {
        inJail = false;
        moveTo(10);
    }

    public boolean inJail()
    {
        return inJail;
    }

    public void addJailFree(boolean chance)
    {
        numJailFree++;
        chanceFree = chance;
    }

    public boolean useJailFree()
    {
        if (numJailFree < 1)
        {
            throw new RuntimeException("You do not have any cards!");
        }

        numJailFree--;
        boolean deck = chanceFree;
        chanceFree = !chanceFree;
        return deck;
    }

    public int numJailFree()
    {
        return numJailFree;
    }

    public int getAssets()
    {
        int assets = this.money;
        for (Square s : properties)
        {
            assets += s.cost();
            if (s instanceof Property)
            {
                assets += getHouseVal((Property) s);
            }
        }
        return assets;
    }

    private int getHouseVal(Property prop)
    {
        int numHouses = prop.numHouses();
        int houseCost = prop.houseCost();

        return numHouses * houseCost;
    }

    //TODO input stuff
    public boolean inputBool(Monopoly.State state)
    {
        switch (state.state)
        {
            case PURCHASE:
                return handlePurchase(state);
            case AUCTION:
                return handleAuction(state);
            case BUY_JAIL:
                return handleOutJail(state);
            case BUY_HOUSE:
                return handleBuyHouses(state);
            case MORTGAGE:
                return handleMortgage(state);
            case UNMORTGAGE:
                return handleUnmortgage(state);
            case TRADE_MONEY:
                return handleTradeMoney(state);
            case GIVE_PROPS:
                return handleGiveProps(state);
            case GET_PROPS:
                return handleGetProps(state);
            case SELL_HOUSE:
                return false;
            default:
                throw new IllegalArgumentException("Uh Oh. Forgot to implement a use case for boolean CPU!");
        }
    }

    public int inputInt(Monopoly.State state)
    {
        switch (state.state)
        {
            case ADDITIONAL:
                return handleAdditional(state);
            case FUNDS:
                return handleFunds(state);
            case BUY_HOUSE:
                return handleBuyHouse(state);
            case UNMORTGAGE:
                return handleUnmortgageInt(state);
            default:
                //throw new IllegalArgumentException("Uh Oh. Forgot to implement a use case for integer CPU!");
                System.out.println(state.state);
                System.out.println("Uh Oh. Forgot to implement a use case for integer CPU!");
                return 4;
        }
    }

    public int inputDecision(Monopoly.State state, String[] choices)
    {
        return 0;
    }

    public Player inputPlayer(Monopoly.State state, Player notAllowed)
    {
        return notAllowed;
    }

    //Input handlers TODO improvements
    //Boolean handlers
    private boolean handlePurchase(Monopoly.State state)
    {

        switch (stratagy)
        {
            case 0:
                if (state.board.square(position).cost() > getAssets())
                {
                    return false;
                } else if (state.board.square(position).cost() > money)
                {
                    return state.board.square(position).cost() < money + 400;
                } else
                {
                    return true;
                }
            case 1:
                if (state.board.square(position).cost() > getAssets())
                {
                    return false;
                } else
                {
                    return state.board.square(position).cost() <= money;
                }
            case 2:
                if (state.board.square(position).cost() > getAssets())
                {
                    return false;
                } else if (state.board.square(position).cost() > money)
                {
                    return false;
                } else
                {
                    return state.board.square(position).cost() > 201;
                }
            case 3:
                if (state.board.square(position).cost() > getAssets())
                {
                    return false;
                } else if (state.board.square(position).cost() > money)
                {
                    return false;
                } else
                {
                    return (state.board.square(position).cost() > 200 || money > 1000);
                }
            default:
                return false;
        }

    }

    private boolean handleAuction(Monopoly.State state)
    {
        int cost = state.board.square(state.current.position()).cost();
        return (state.val <= 2 * cost);
    }

    private boolean handleOutJail(Monopoly.State state)
    {
        //buy way out of jail if any squares are still in the bank
        for (Square sq : state.board.getBoard())
        {
            if (sq.isOwnable() && !sq.isOwned())
            {
                return true;
            }
        }
        return false;
    }

    private boolean handleBuyHouses(Monopoly.State state)
    {
        Queue<Property> purchaseable = new LinkedList<>();
        for (Square sq : properties)
        {
            if (!(sq instanceof Property))
            {
                continue;
            }

            Property prop = (Property) sq;
            if (prop.groupBuild() && prop.numHouses() < 5)
            {
                purchaseable.add(prop);
            }
        }

        for (Property p : purchaseable)
        {
            if (p.houseCost() < money)
            {
                return true;
            }
        }

        return false;
    }

    private boolean handleMortgage(Monopoly.State state)
    {
        return false;
    }

    private boolean handleUnmortgage(Monopoly.State state)
    {
        for (Square sq : state.current.properties())
        {
            if (sq.isMortgaged() && sq.mortgageCost() < money)
            {
                return true;
            }
        }

        return false;
    }

    private boolean handleTradeMoney(Monopoly.State state)
    {
        return false;
    }

    private boolean handleGiveProps(Monopoly.State state)
    {
        return false;
    }

    private boolean handleGetProps(Monopoly.State state)
    {
        return false;
    }

    private int handleAdditional(Monopoly.State state)
    {
        if (money < 500)
        {
            return 4;
        } else
        {
            if (handleUnmortgage(state))
            {
                return 2;
            } else if (handleBuyHouses(state))
            {
                return 1;
            } else
            {
                return 4;
            }

        }
    }

    private int handleBuyHouse(Monopoly.State state)
    {
        int highestPosition = 0;
        int counter = 0;
        int result = 0;
        for (Square sq : properties)
        {
            if (sq instanceof Property)
            {
                Property prop = (Property) sq;
                if (!sq.isMortgaged() && sq.cost() < money && prop.monopoly() && prop.groupBuild() && prop.numHouses() < 5)
                {
                    counter++;
                    if (sq.position() > highestPosition)
                    {
                        highestPosition = sq.position();
                        result = counter;
                    }

                }
            }
        }
        return result;
    }

    private int handleUnmortgageInt(Monopoly.State state)
    {
        int highestCost = 0;
        int counter = 0;
        int result = 0;
        for (Square sq : properties)
        {
            if (sq.isMortgaged())
            {
                counter++;
                if (sq.cost() > highestCost)
                {
                    highestCost = sq.cost();
                    result = counter;
                }

            }

        }
        return result;
    }

    private int handleFunds(Monopoly.State state)
    {
        int lowestCost = 500;
        int counter = 0;
        int result = 0;
        for (Square sq : properties)
        {
            if (!sq.isMortgaged())
            {
                counter++;
                if (sq.cost() < lowestCost)
                {
                    lowestCost = sq.cost();
                    result = counter;
                }

            }

        }
        return result;
    }

    public int getIndex()
    {
        return index;
    }
    //Int handlers
    //Decision handlers
    //Player handlers
}
