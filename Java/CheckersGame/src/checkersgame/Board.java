/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package checkersgame;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class Board
{

    private ArrayList<Integer> position;
    /*
    0 - empty
    1 - red
    2 - black
    3 - red king
    4 - black king
    
    Position ids:
     x 0 x 1 x 2 x 3
     4 x 5 x 6 x 7 x
     x 8 x 9 x10 x11
    12 x13 x14 x15 x
     x16 x17 x18 x19
    20 x21 x22 x23 x
     x24 x25 x26 x27
    28 x29 x30 x31 x
     */
    private boolean isRedTurn;
    private int turnNum;

    public Board(boolean isRedTurn)
    {
        this.isRedTurn = isRedTurn;
        turnNum = 0;
        position = new ArrayList<>();
        for (int i = 0; i < 12; i++)
        {
            position.add(2);
        }
        for (int i = 0; i < 8; i++)
        {
            position.add(0);
        }
        for (int i = 0; i < 12; i++)
        {
            position.add(1);
        }
    }

    public Board(Board boardToCopy)
    {
        this.isRedTurn = boardToCopy.isRedTurn();
        this.position = new ArrayList<>();
        this.turnNum = boardToCopy.turnNum + 1;
        for (int i = 0; i < boardToCopy.getPosition().size(); i++)
        {
            this.position.add(boardToCopy.getPosition().get(i));
        }
    }

    public int getTurnNum()
    {
        return turnNum;
    }

    public void printBoard()
    {
        String text;
        for (int i = 0; i < 4; i++)
        {
            text = "";
            for (int j = 0; j < 4; j++)
            {
                text = text + "   ";
                text = text + position.get(i * 8 + j);
            }
            System.out.println(text);
            text = " ";
            for (int j = 0; j < 4; j++)
            {
                text = text + position.get(i * 8 + j + 4);
                text = text + "   ";
            }
            System.out.println(text);
        }
    }

    public boolean isRedTurn()
    {
        return isRedTurn;
    }

    public ArrayList<Integer> getPosition()
    {
        return position;
    }

    public ArrayList<Board> getPosMoves()
    {
        ArrayList<Board> posMoves = new ArrayList<>();
        Board posPosition;

        for (int i = 0; i < position.size(); i++)
        {
            if (isRedTurn)
            {
                if (position.get(i) == 3)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        int neighborId = getNeighborId(i, j);
                        if (neighborId != -1)
                        {
                            if (position.get(neighborId) == 0)
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 3);
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);
                            } else if (canJump(isRedTurn, i, j, position))
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 0);
                                posPosition.getPosition().set(getNeighborId(neighborId, j), 3);
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);

                                ArrayList<Board> positionsToAdd = new ArrayList<>();
                                recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, j), true);
                                for (int k = 0; k < positionsToAdd.size(); k++)
                                {
                                    posMoves.add(positionsToAdd.get(k));
                                }
                            }
                        }
                    }
                } else if (position.get(i) == 1)
                {
                    for (int j = 0; j < 2; j++)
                    {
                        int neighborId = getNeighborId(i, j);
                        if (neighborId != -1)
                        {
                            if (position.get(neighborId) == 0)
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                if (neighborId < 4)
                                {
                                    posPosition.getPosition().set(neighborId, 3);
                                } else
                                {
                                    posPosition.getPosition().set(neighborId, 1);
                                }
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);
                            } else if (canJump(isRedTurn, i, j, position))
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 0);
                                if (getNeighborId(neighborId, j) < 4)
                                {
                                    posPosition.getPosition().set(getNeighborId(neighborId, j), 3);
                                } else
                                {
                                    posPosition.getPosition().set(getNeighborId(neighborId, j), 1);
                                }
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);

                                ArrayList<Board> positionsToAdd = new ArrayList<>();
                                recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, j), false);
                                for (int k = 0; k < positionsToAdd.size(); k++)
                                {
                                    posMoves.add(positionsToAdd.get(k));
                                }
                            }
                        }
                    }
                }
            } else
            {
                if (position.get(i) == 4)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        int neighborId = getNeighborId(i, j);
                        if (neighborId != -1)
                        {
                            if (position.get(neighborId) == 0)
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 4);
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);
                            } else if (canJump(isRedTurn, i, j, position))
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 0);
                                posPosition.getPosition().set(getNeighborId(neighborId, j), 4);
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);

                                ArrayList<Board> positionsToAdd = new ArrayList<>();
                                recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, j), true);
                                for (int k = 0; k < positionsToAdd.size(); k++)
                                {
                                    posMoves.add(positionsToAdd.get(k));
                                }
                            }
                        }
                    }
                } else if (position.get(i) == 2)
                {
                    for (int j = 2; j < 4; j++)
                    {
                        int neighborId = getNeighborId(i, j);
                        if (neighborId != -1)
                        {
                            if (position.get(neighborId) == 0)
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                if (neighborId > 27)
                                {
                                    posPosition.getPosition().set(neighborId, 4);
                                } else
                                {
                                    posPosition.getPosition().set(neighborId, 2);
                                }
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);
                            } else if (canJump(isRedTurn, i, j, position))
                            {
                                posPosition = new Board(this);
                                posPosition.getPosition().set(i, 0);
                                posPosition.getPosition().set(neighborId, 0);
                                if (getNeighborId(neighborId, j) > 27)
                                {
                                    posPosition.getPosition().set(getNeighborId(neighborId, j), 4);
                                } else
                                {
                                    posPosition.getPosition().set(getNeighborId(neighborId, j), 2);
                                }
                                posPosition.isRedTurn = !isRedTurn;
                                posMoves.add(posPosition);

                                ArrayList<Board> positionsToAdd = new ArrayList<>();
                                recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, j), false);
                                for (int k = 0; k < positionsToAdd.size(); k++)
                                {
                                    posMoves.add(positionsToAdd.get(k));
                                }
                            }
                        }
                    }
                }
            }
        }
        return posMoves;
    }

    /*
    id - the id in the position arraylist of the tile to find the neighbor of
    dir
    up left    0
    up right   1
    down left  2
    down right 3
     */
    private Integer getNeighbor(int id, int dir)
    {
        int neighborId = getNeighborId(id, dir);
        if (neighborId == -1)
        {
            return null;
        } else
        {
            return position.get(neighborId);
        }
    }

    private int getNeighborId(int id, int dir)
    {
        int neighborId;
        if ((id / 4) % 2 == 0)
        {
            switch (dir)
            {
                case 0:
                    neighborId = id - 4;
                    break;
                case 1:
                    if (id % 4 != 3)
                    {
                        neighborId = id - 3;
                        break;
                    } else
                    {
                        return -1;
                    }
                case 2:
                    neighborId = id + 4;
                    break;
                case 3:
                    if (id % 4 != 3)
                    {
                        neighborId = id + 5;
                        break;
                    } else
                    {
                        return -1;
                    }
                default:
                    System.out.println("Invalid dir: " + dir);
                    return -1;
            }
        } else
        {
            switch (dir)
            {
                case 0:
                    if (id % 4 != 0)
                    {
                        neighborId = id - 5;
                        break;
                    } else
                    {
                        return -1;
                    }
                case 1:
                    neighborId = id - 4;
                    break;
                case 2:
                    if (id % 4 != 0)
                    {
                        neighborId = id + 3;
                        break;
                    } else
                    {
                        return -1;
                    }
                case 3:
                    neighborId = id + 4;
                    break;
                default:
                    System.out.println("Invalid dir: " + dir);
                    return -1;
            }
        }
        if (neighborId < 0 || neighborId > 31)
        {
            return -1;
        }
        return neighborId;
    }

    private boolean canJump(boolean isRed, int id, ArrayList<Integer> pos)
    {
        return (canJump(isRed, id, 0, pos) || canJump(isRed, id, 1, pos) || canJump(isRed, id, 2, pos) || canJump(isRed, id, 3, pos));
    }

    private boolean canJump(boolean isRed, int id, int dir, ArrayList<Integer> pos)
    {
        int neighborId = getNeighborId(id, dir);
        if (neighborId == -1)
        {
            return false;
        }
        Integer neighbor = pos.get(neighborId);
        return ((isRed && (neighbor == 2 || neighbor == 4)) || !isRed && (neighbor == 1 || neighbor == 3)) && getNeighbor(neighborId, dir) != null && getNeighbor(neighborId, dir).equals(0);
    }

    private void recursiveJumping(ArrayList<Board> positionsToAdd, Board curPosition, int piecePos, boolean isKing)
    {
        if (isKing)
        {
            for (int dir = 0; dir < 4; dir++)
            {
                if (canJump(isRedTurn, piecePos, dir, curPosition.getPosition()))
                {
                    Board posPosition = new Board(curPosition);
                    posPosition.getPosition().set(piecePos, 0);
                    int neighborId = getNeighborId(piecePos, dir);
                    posPosition.getPosition().set(neighborId, 0);
                    int newPosition = getNeighborId(neighborId, dir);
                    if (isRedTurn)
                    {
                        posPosition.getPosition().set(newPosition, 3);
                    } else
                    {
                        posPosition.getPosition().set(newPosition, 4);
                    }
                    posPosition.turnNum--;
                    if (!boardListContains(positionsToAdd, posPosition))
                    {
                        positionsToAdd.add(posPosition);
                        recursiveJumping(positionsToAdd, posPosition, newPosition, isKing);
                    }

                }
            }
        } else
        {
            if (isRedTurn)
            {
                for (int dir = 0; dir < 2; dir++)
                {
                    if (canJump(isRedTurn, piecePos, dir, curPosition.getPosition()))
                    {
                        Board posPosition = new Board(curPosition);
                        posPosition.getPosition().set(piecePos, 0);
                        int neighborId = getNeighborId(piecePos, dir);
                        posPosition.getPosition().set(neighborId, 0);
                        posPosition.turnNum--;
                        if (getNeighborId(neighborId, dir) < 4)
                        {
                            posPosition.getPosition().set(getNeighborId(neighborId, dir), 3);

                            positionsToAdd.add(posPosition);
                        } else
                        {
                            posPosition.getPosition().set(getNeighborId(neighborId, dir), 1);

                            positionsToAdd.add(posPosition);
                            recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, dir), isKing);
                        }
                    }
                }
            } else
            {
                for (int dir = 2; dir < 4; dir++)
                {
                    if (canJump(isRedTurn, piecePos, dir, curPosition.getPosition()))
                    {
                        Board posPosition = new Board(curPosition);
                        posPosition.getPosition().set(piecePos, 0);
                        int neighborId = getNeighborId(piecePos, dir);
                        posPosition.getPosition().set(neighborId, 0);
                        posPosition.turnNum--;
                        if (getNeighborId(neighborId, dir) > 27)
                        {
                            posPosition.getPosition().set(getNeighborId(neighborId, dir), 4);

                            positionsToAdd.add(posPosition);
                        } else
                        {
                            posPosition.getPosition().set(getNeighborId(neighborId, dir), 2);

                            positionsToAdd.add(posPosition);
                            recursiveJumping(positionsToAdd, posPosition, getNeighborId(neighborId, dir), isKing);
                        }
                    }
                }
            }
        }
    }

    public boolean boardListContains(ArrayList<Board> boardList, Board testPosition)
    {
        for (int i = 0; i < boardList.size(); i++)
        {
            boolean match = true;
            for (int j = 0; j < boardList.get(i).getPosition().size(); j++)
            {
                if (!testPosition.getPosition().get(j).equals(boardList.get(i).getPosition().get(j)))
                {
                    match = false;
                    break;
                }
            }
            if (match)
            {
                return true;
            }
        }
        return false;
    }

    private boolean canMove()
    {
        for (int i = 0; i < position.size(); i++)
        {
            if (isRedTurn)
            {
                if (position.get(i) == 3)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        if (getNeighbor(i, j) != null && (getNeighbor(i, j).equals(0)))
                        {
                            return true;
                        }
                    }
                    if (canJump(isRedTurn, i, position))
                    {
                        return true;
                    }
                } else if (position.get(i) == 1)
                {
                    for (int j = 0; j < 2; j++)
                    {
                        if (getNeighbor(i, j) != null && (getNeighbor(i, j).equals(0) || canJump(isRedTurn, i, j, position)))
                        {
                            return true;
                        }
                    }
                }
            } else
            {
                if (position.get(i) == 4)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        if (getNeighbor(i, j) != null && (getNeighbor(i, j).equals(0)))
                        {
                            return true;
                        }
                    }
                    if (canJump(isRedTurn, i, position))
                    {
                        return true;
                    }
                } else if (position.get(i) == 2)
                {
                    for (int j = 2; j < 4; j++)
                    {
                        if (getNeighbor(i, j) != null && (getNeighbor(i, j).equals(0) || canJump(isRedTurn, i, j, position)))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
    0 - undetermined
    1 - red wins
    2 - black wins
    3 - tie
     */
    public int getResult(boolean STALEMATE_RESULTS_IN_TIE)
    {
        if (turnNum >= 400)
        {
            return 3;
        }

        boolean redFound = false;
        boolean blackFound = false;

        for (int i = 0; i < position.size(); i++)
        {
            if (position.get(i) == 1 || position.get(i) == 3)
            {
                redFound = true;
                if (blackFound)
                {
                    break;
                }
            } else if (position.get(i) == 2 || position.get(i) == 4)
            {
                blackFound = true;
                if (redFound)
                {
                    break;
                }
            }
        }
        if (redFound && blackFound)
        {
            if (canMove())
            {
                return 0;
            } else
            {
                if (STALEMATE_RESULTS_IN_TIE)
                {
                    return 3;
                } else
                {
                    if (isRedTurn)
                    {
                        return 2;
                    } else
                    {
                        return 1;
                    }
                }
            }
        } else
        {
            if (redFound)
            {
                return 1;
            } else
            {
                return 2;
            }
        }
    }
}
