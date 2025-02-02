/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseballsimulation;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */

/*
Events:
1-Single/Error
2-Single Runners move 2 bases
3-Double
4-Double Runners move 3 bases
5-Triple
6-Home Run
7-Strikeout/Pop Out
8-Ground Out
9-GIDP
10-Fly Out
11-Sac Fly
12-Walk/Hit by Pitch
13-Stolen Base
14-Caught Stealing

InningStates:
# - 1 2 3 Outs
0 - 0 0 0  0
1 - 1 0 0  0
2 - 0 1 0  0
3 - 0 0 1  0
4 - 1 1 0  0
5 - 1 0 1  0
6 - 0 1 1  0
7 - 1 1 1  0
8 - 0 0 0  1
9 - 1 0 0  1
10- 0 1 0  1
11- 0 0 1  1
12- 1 1 0  1
13- 1 0 1  1
14- 0 1 1  1
15- 1 1 1  1
16- 0 0 0  2
17- 1 0 0  2
18- 0 1 0  2
19- 0 0 1  2
20- 1 1 0  2
21- 1 0 1  2
22- 0 1 1  2
23- 1 1 1  2
24- 0 0 0  3

 */
public class InningState
{

    private int id;
    private int outs;
    private int manOnFirst; // 0 for false  1 for true
    private int manOnSecond;
    private int manOnThird;
    private int runChange;
    private boolean batterChange;

    public InningState(int id)
    {
        this.id = id;
        this.outs = id / 8;
        int bases = id % 8;
        if (bases == 1 || bases == 4 || bases == 5 || bases == 7)
        {
            this.manOnFirst = 1;
        } else
        {
            this.manOnFirst = 0;
        }
        if (bases == 2 || bases == 4 || bases == 6 || bases == 7)
        {
            this.manOnSecond = 1;
        } else
        {
            this.manOnSecond = 0;
        }
        if (bases == 3 || bases == 5 || bases == 6 || bases == 7)
        {
            this.manOnThird = 1;
        } else
        {
            this.manOnThird = 0;
        }
        this.runChange = 0;
        batterChange = false;
    }

    public InningState getNextState(ArrayList<InningState> inningStates, int event)
    {
        InningState nextState;
        switch (event)
        {
            case 1:
            {
                if (id % 8 != 0)
                {
                    nextState = inningStates.get(calcId(outs, 1, manOnFirst, manOnSecond));
                    nextState.runChange = manOnThird;
                } else
                {
                    nextState = inningStates.get((id / 8) * 8 + 1);
                }

                nextState.batterChange = true;
                return nextState;
            }
            case 2:
            {
                if (id % 8 == 0)
                {
                    System.out.println("Invalid single runners move 2 bases (no base runners)");
                    return this;
                } else
                {
                    if (manOnFirst == 1)
                    {
                        nextState = inningStates.get((id / 8) * 8 + 5);
                    } else
                    {
                        nextState = inningStates.get((id / 8) * 8 + 1);
                    }
                    nextState.runChange = manOnSecond + manOnThird;
                    nextState.batterChange = true;
                    return nextState;
                }

            }
            case 3:
            {

                nextState = inningStates.get((id / 8) * 8 + 6);
                nextState.batterChange = true;
                nextState.runChange = getNumMenOnBase() - nextState.manOnThird;
                return nextState;
            }
            case 4:
            {
                nextState = inningStates.get((id / 8) * 8 + 2);
                nextState.batterChange = true;
                nextState.runChange = getNumMenOnBase() - nextState.manOnThird;
                return nextState;
            }
            case 5:
            {
                nextState = inningStates.get((id / 8) * 8 + 3);
                nextState.batterChange = true;
                nextState.runChange = getNumMenOnBase();
                return nextState;
            }
            case 6:
            {

                nextState = inningStates.get((id / 8) * 8);
                nextState.batterChange = true;
                nextState.runChange = getNumMenOnBase() + 1;
                return nextState;
            }
            case 7:
            {
                if (outs == 2)
                {
                    return inningStates.get(inningStates.size() - 1);
                }
                nextState = inningStates.get(id + 8);
                nextState.batterChange = true;
                nextState.runChange = 0;
                return nextState;
            }
            case 8:
            {
                if (outs == 2)
                {
                    return inningStates.get(inningStates.size() - 1);
                }
                if (manOnFirst == 1)
                {
                    nextState = inningStates.get(calcId(outs + 1, 1, 0, manOnSecond));
                    if (manOnThird == 1)
                    {
                        nextState.runChange = 1;
                    }
                } else
                {
                    nextState = inningStates.get(id + 8);
                }
                nextState.batterChange = true;
                return nextState;
            }
            case 9:
            {
                if (manOnFirst == 0 || outs == 2)
                {
                    System.out.println("Invalid GIDP. Two outs or no man on first");
                    return this;
                } else
                {
                    if (outs == 1)
                    {
                        nextState = inningStates.get(inningStates.size() - 1);
                        nextState.batterChange = true;
                        return nextState;
                    }
                    nextState = inningStates.get(calcId(outs + 2, 0, 0, manOnSecond));
                    if (manOnThird == 1)
                    {
                        nextState.runChange = 1;
                    }
                    nextState.batterChange = true;
                    return nextState;
                }

            }
            case 10:
            {
                // Fly Out
                if (outs == 2)
                {
                    nextState = inningStates.get(inningStates.size() - 1);
                    nextState.batterChange = true;
                    return nextState;
                }
                nextState = inningStates.get(id + 8);
                nextState.batterChange = true;
                return nextState;
            }
            case 11:
            {
                if (outs == 2)
                {
                    nextState = inningStates.get(inningStates.size() - 1);
                    nextState.batterChange = true;
                    return nextState;
                }
                if (manOnSecond == 0 && manOnThird == 0)
                {
                    System.out.println("Invalid Sac fly. No man on second or third");
                    return this;
                } else
                {
                    nextState = inningStates.get(calcId(outs + 1, manOnFirst, 0, manOnSecond));
                    if (manOnThird == 1)
                    {
                        nextState.runChange = 1;
                    }
                }
                nextState.batterChange = true;
                return nextState;
            }
            case 12:
            {
                //Walk
                if (manOnFirst == 1)
                {
                    if (manOnSecond == 1)
                    {
                        nextState = inningStates.get(calcId(outs, 1, 1, 1));
                        if (manOnThird == 1)
                        {
                            nextState.runChange = 1;
                        }
                    } else
                    {
                        nextState = inningStates.get(calcId(outs, 1, 1, manOnThird));
                    }
                } else
                {
                    nextState = inningStates.get(calcId(outs, 1, manOnSecond, manOnThird));
                }
                nextState.batterChange = true;
                return nextState;
            }
            case 13:
            {
                //Stolen Base
                if (manOnFirst != 1 || manOnSecond != 0)
                {
                    System.out.println("Invalid Stolen Base. No man on first or a man on second");
                    return this;
                } else
                {
                    nextState = inningStates.get(calcId(outs, 0, 1, manOnThird));
                    nextState.batterChange = false;
                    return nextState;
                }
            }
            case 14:
            {
                //Caught Stealing
                if (manOnFirst != 1 || manOnSecond != 0)
                {
                    System.out.println("Invalid Caught Stealing. No man on first or a man on second");
                    return this;
                } else if (outs == 2)
                {
                    nextState = inningStates.get(inningStates.size() - 1);
                    nextState.batterChange = true;
                    return nextState;
                } else
                {

                    nextState = inningStates.get(calcId(outs + 1, 0, 0, manOnThird));
                    nextState.batterChange = false;
                    return nextState;
                }
            }
            default:
                return null;
        }

    }

    public int getNumMenOnBase()
    {
        return manOnFirst + manOnSecond + manOnThird;
    }

    public void resetState()
    {
        runChange = 0;
        batterChange = false;
    }

    private int calcId(int numOuts, int manOnFirst, int manOnSecond, int manOnThird)
    {
        int idNum = numOuts * 8;
        if (manOnFirst == 1)
        {
            if (manOnThird == 1)
            {
                if (manOnSecond == 1)
                {
                    idNum += 7;
                } else
                {
                    idNum += 5;
                }
            } else
            {
                if (manOnSecond == 1)
                {
                    idNum += 4;
                } else
                {
                    idNum += 1;
                }
            }
        } else
        {
            if (manOnThird == 1)
            {
                if (manOnSecond == 1)
                {
                    idNum += 6;
                } else
                {
                    idNum += 3;
                }
            } else
            {
                if (manOnSecond == 1)
                {
                    idNum += 2;
                }
            }
        }
        return idNum;
    }

    public void printState()
    {
        String info = "";
        if (manOnFirst == 1)
        {
            info = info + "1B ";
        } else
        {
            info = info + "__ ";
        }
        if (manOnSecond == 1)
        {
            info = info + "2B ";
        } else
        {
            info = info + "__ ";
        }
        if (manOnThird == 1)
        {
            info = info + "3B ";
        } else
        {
            info = info + "__ ";
        }
        info = info + outs;
        info = info + " Run Change: " + runChange;
        System.out.println(info);
    }

    public int getId()
    {
        return id;
    }
}
