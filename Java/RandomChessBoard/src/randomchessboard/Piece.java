/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomchessboard;

import java.util.Arrays;

/**
 *
 * @author Camden
 */
public class Piece
{

    private String type;
    private int[] posFreq;
    private int freq;
    private int totalPosFreq;
    private int value;

    public Piece(String type)
    {
        this.type = type;
        if (null != type)
        {
            switch (type)
            {
                case "empty" ->
                {
                    freq = 24;
                    posFreq = new int[]
                    {
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1
                    };
                    totalPosFreq = 64;
                    value = 0;
                }
                case "pawn" ->
                {
                    freq = 8;
                    posFreq = new int[]
                    {
                        0, 0, 0, 0, 0, 0, 0, 0,
                        7, 7, 7, 7, 7, 7, 7, 7,
                        3, 4, 4, 5, 5, 4, 4, 3,
                        4, 5, 5, 6, 6, 5, 5, 4,
                        2, 3, 3, 3, 3, 3, 3, 2,
                        2, 3, 3, 3, 3, 3, 3, 2,
                        2, 3, 3, 3, 3, 3, 3, 2,
                        0, 0, 0, 0, 0, 0, 0, 0
                    };
                    totalPosFreq = 194;
                    value = 1;
                }
                case "night" ->
                {
                    freq = 2;
                    posFreq = new int[]
                    {
                        2, 5, 4, 4, 4, 4, 5, 2,
                        3, 4, 6, 7, 7, 6, 4, 3,
                        5, 6, 9, 8, 8, 9, 6, 5,
                        4, 6, 8, 8, 8, 8, 6, 4,
                        4, 6, 8, 8, 8, 8, 6, 4,
                        4, 6, 8, 8, 8, 8, 6, 4,
                        3, 4, 6, 6, 6, 6, 4, 3,
                        2, 3, 4, 4, 4, 4, 3, 2
                    };
                    totalPosFreq = 346;
                    value = 3;
                }
                case "bishop" ->
                {
                    freq = 2;
                    posFreq = new int[]
                    {
                        7, 7, 10, 7, 7, 10, 7, 7,
                        7, 11, 9, 11, 11, 9, 11, 7,
                        8, 9, 11, 13, 13, 11, 9, 8,
                        7, 9, 13, 13, 13, 13, 9, 7,
                        7, 11, 11, 13, 13, 11, 11, 7,
                        8, 9, 11, 11, 11, 11, 9, 8,
                        7, 9, 9, 9, 9, 9, 9, 7,
                        7, 7, 7, 7, 7, 7, 7, 7
                    };
                    totalPosFreq = 590;
                    value = 3;
                }
                case "rook" ->
                {
                    freq = 2;
                    posFreq = new int[]
                    {
                        20, 17, 19, 17, 18, 17, 17, 20,
                        16, 14, 14, 14, 14, 14, 14, 16,
                        15, 14, 14, 14, 14, 14, 14, 15,
                        14, 14, 14, 14, 14, 14, 14, 14,
                        14, 14, 14, 14, 14, 14, 14, 14,
                        14, 14, 14, 14, 14, 14, 14, 14,
                        14, 14, 14, 14, 14, 14, 14, 14,
                        14, 14, 14, 14, 14, 14, 14, 14
                    };
                    totalPosFreq = 935;
                    value = 5;
                }
                case "queen" ->
                {
                    freq = 1;
                    posFreq = new int[]
                    {
                        21, 21, 26, 31, 46, 31, 26, 21,
                        21, 23, 23, 33, 33, 33, 23, 21,
                        21, 23, 35, 25, 31, 25, 33, 21,
                        21, 33, 25, 27, 30, 25, 23, 31,
                        31, 23, 25, 27, 27, 25, 23, 21,
                        21, 23, 25, 25, 25, 25, 23, 21,
                        21, 23, 23, 23, 23, 23, 23, 21,
                        21, 21, 21, 21, 21, 21, 21, 21
                    };
                    totalPosFreq = 1600;
                    value = 9;
                }
                case "king" ->
                {
                    freq = 1;
                    posFreq = new int[]
                    {
                        3, 8, 7, 10, 7, 7, 5, 3,
                        5, 8, 10, 10, 10, 8, 8, 5,
                        5, 8, 8, 8, 8, 8, 8, 5,
                        5, 8, 8, 8, 8, 8, 8, 5,
                        5, 8, 8, 8, 8, 8, 8, 5,
                        5, 8, 8, 8, 8, 8, 8, 5,
                        5, 8, 8, 8, 8, 8, 8, 5,
                        3, 5, 5, 5, 5, 5, 5, 3
                    };
                    totalPosFreq = 440;
                    value = 1000;

                }
                default ->
                {
                }
            }
        }

    }

    public int getPosFreq(int pos, boolean isWhite)
    {
        if (isWhite)
        {
            return posFreq[pos];
        } else
        {
            return posFreq[((7 - (pos / 8)) * 8) + (pos % 8)];
        }
    }

    public int[] attacking(int pos, boolean isWhite)
    {
        int[] attacking;
        if (null != type)
        {
            switch (type)
            {
                case "empty" ->
                {
                    attacking = new int[0];
                }
                case "pawn" ->
                {
                    attacking = pawnAttacking(pos, isWhite);
                }
                case "night" ->
                {
                    attacking = knightAttacking(pos);
                }
                case "bishop" ->
                {
                    attacking = bishopAttacking(pos);
                }
                case "rook" ->
                {
                    attacking = rookAttacking(pos);
                }
                case "queen" ->
                {
                    attacking = queenAttacking(pos);
                }
                default ->
                {
                    attacking = kingAttacking(pos);
                }
            }
        } else
        {
            attacking = null;
        }
        return attacking;
    }

    public int[] pawnAttacking(int pos, boolean isWhite)
    {
        int[] attacking;
        switch (pos % 8)
        {
            case 0 ->
            {
                if (isWhite)
                {
                    attacking = new int[]
                    {
                        pos + 9
                    };
                } else
                {
                    attacking = new int[]
                    {
                        pos - 7
                    };
                }
            }
            case 7 ->
            {
                if (isWhite)
                {
                    attacking = new int[]
                    {
                        pos + 7
                    };
                } else
                {
                    attacking = new int[]
                    {
                        pos - 9
                    };
                }
            }
            default ->
            {
                if (isWhite)
                {
                    attacking = new int[]
                    {
                        pos + 7, pos + 9
                    };
                } else
                {
                    attacking = new int[]
                    {
                        pos - 7, pos - 9
                    };
                }
            }
        }
        return attacking;
    }

    public int[] knightAttacking(int pos)
    {
        int[] attacking;
        switch (pos % 8)
        {
            case 0 ->
            {
                switch (pos / 8)
                {
                    case 0 ->
                    {
                        attacking = new int[]
                        {
                            pos + 10, pos + 17
                        };
                    }
                    case 1 ->
                    {
                        attacking = new int[]
                        {
                            pos - 6, pos + 10, pos + 17
                        };
                    }
                    case 6 ->
                    {
                        attacking = new int[]
                        {
                            pos - 15, pos - 6, pos + 10
                        };
                    }
                    case 7 ->
                    {
                        attacking = new int[]
                        {
                            pos - 15, pos - 6
                        };
                    }
                    default ->
                    {
                        attacking = new int[]
                        {
                            pos - 15, pos - 6, pos + 10, pos + 17
                        };
                    }
                }
            }
            case 1 ->
            {
                switch (pos / 8)
                {
                    case 0 ->
                    {
                        attacking = new int[]
                        {
                            pos + 10, pos + 15, pos + 17
                        };
                    }
                    case 1 ->
                    {
                        attacking = new int[]
                        {
                            pos - 6, pos + 10, pos + 15, pos + 17
                        };
                    }
                    case 6 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 6, pos + 10
                        };
                    }
                    case 7 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 6
                        };
                    }
                    default ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 6, pos + 10, pos + 15, pos + 17
                        };
                    }
                }
            }
            case 6 ->
            {
                switch (pos / 8)
                {
                    case 0 ->
                    {
                        attacking = new int[]
                        {
                            pos + 6, pos + 15, pos + 17
                        };
                    }
                    case 1 ->
                    {
                        attacking = new int[]
                        {
                            pos - 10, pos + 6, pos + 15, pos + 17
                        };
                    }
                    case 6 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10, pos + 6
                        };
                    }
                    case 7 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10
                        };
                    }
                    default ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10, pos + 6, pos + 15, pos + 17
                        };
                    }
                }
            }
            case 7 ->
            {
                switch (pos / 8)
                {
                    case 0 ->
                    {
                        attacking = new int[]
                        {
                            pos + 6, pos + 15
                        };
                    }
                    case 1 ->
                    {
                        attacking = new int[]
                        {
                            pos - 10, pos + 6, pos + 15
                        };
                    }
                    case 6 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 10, pos + 6
                        };
                    }
                    case 7 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 10
                        };
                    }
                    default ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 10, pos + 6, pos + 15
                        };
                    }
                }
            }
            default ->
            {
                switch (pos / 8)
                {
                    case 0 ->
                    {
                        attacking = new int[]
                        {
                            pos + 6, pos + 10, pos + 15, pos + 17
                        };
                    }
                    case 1 ->
                    {
                        attacking = new int[]
                        {
                            pos - 10, pos - 6, pos + 6, pos + 10, pos + 15, pos + 17
                        };
                    }
                    case 6 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10, pos - 6, pos + 6, pos + 10
                        };
                    }
                    case 7 ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10, pos - 6
                        };
                    }
                    default ->
                    {
                        attacking = new int[]
                        {
                            pos - 17, pos - 15, pos - 10, pos - 6, pos + 6, pos + 10, pos + 15, pos + 17
                        };
                    }
                }
            }
        }
        return attacking;
    }

    public int[] bishopAttacking(int pos)
    {
        int[] attacking;
        int down = pos / 8;
        int right = pos % 8;
        int length;
        if (Math.abs(down - 3.5) > Math.abs(right - 3.5))
        {
            length = (int) (14 - 2 * Math.abs(down - 3.5));
        } else
        {
            length = (int) (14 - 2 * Math.abs(right - 3.5));
        }
        attacking = new int[length];
        boolean[] moves = new boolean[28];
        Arrays.fill(moves, true);
        for (int i = 0; i < 7; i++)
        {
            if (!(down > 6 - i))
            {
                moves[i * 2] = false;
                moves[(i * 2) + 1] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(down < i + 1))
            {
                moves[(i * 2) + 14] = false;
                moves[(i * 2) + 15] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right > 6 - i))
            {
                moves[(i * 2)] = false;
                moves[(i * 2) + 14] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right < i + 1))
            {
                moves[(i * 2) + 1] = false;
                moves[(i * 2) + 15] = false;
            }
        }
        int places = 0;
        for (int i = 0; i < 14; i += 2)
        {
            if (moves[i])
            {
                attacking[places] = pos - ((14 - i) * 9 / 2);
                places++;
            }
            if (moves[i + 1])
            {
                attacking[places] = pos - ((14 - i) * 7 / 2);
                places++;
            }
        }
        for (int i = 0; i < 14; i += 2)
        {
            if (moves[i + 14])
            {
                attacking[places] = pos + ((14 - i) * 7 / 2);
                places++;
            }
            if (moves[i + 15])
            {
                attacking[places] = pos + ((14 - i) * 9 / 2);
                places++;
            }
        }
        return attacking;
    }

    public int[] rookAttacking(int pos)
    {
        int[] attacking;
        int down = pos / 8;
        int right = pos % 8;
        attacking = new int[14];
        boolean[] moves = new boolean[28];
        Arrays.fill(moves, true);

        for (int i = 0; i < 7; i++)
        {
            if (!(down > 6 - i))
            {
                moves[i] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(down < i + 1))
            {
                moves[i + 7] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right > 6 - i))
            {
                moves[i + 14] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right < i + 1))
            {
                moves[i + 21] = false;
            }
        }
        int places = 0;
        for (int i = 0; i < 7; i++)
        {
            if (moves[i])
            {
                attacking[places] = pos - ((7 - i) * 8);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 7])
            {
                attacking[places] = pos + ((7 - i) * 8);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 14])
            {
                attacking[places] = pos - ((7 - i) * 1);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 21])
            {
                attacking[places] = pos + ((7 - i) * 1);
                places++;
            }
        }
        return attacking;
    }

    public int[] queenAttacking(int pos)
    {
        int[] attacking;
        int down = pos / 8;
        int right = pos % 8;
        int length;
        if (Math.abs(down - 3.5) > Math.abs(right - 3.5))
        {
            length = (int) (14 - 2 * Math.abs(down - 3.5));
        } else
        {
            length = (int) (14 - 2 * Math.abs(right - 3.5));
        }
        attacking = new int[length + 14];
        boolean[] moves = new boolean[56];
        Arrays.fill(moves, true);
        // Bishop moves
        for (int i = 0; i < 7; i++)
        {
            if (!(down > 6 - i))
            {
                moves[i * 2] = false;
                moves[(i * 2) + 1] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(down < i + 1))
            {
                moves[(i * 2) + 14] = false;
                moves[(i * 2) + 15] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right > 6 - i))
            {
                moves[(i * 2)] = false;
                moves[(i * 2) + 14] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right < i + 1))
            {
                moves[(i * 2) + 1] = false;
                moves[(i * 2) + 15] = false;
            }
        }
        int places = 0;
        for (int i = 0; i < 14; i += 2)
        {
            if (moves[i])
            {
                attacking[places] = pos - ((14 - i) * 9 / 2);
                places++;
            }
            if (moves[i + 1])
            {
                attacking[places] = pos - ((14 - i) * 7 / 2);
                places++;
            }
        }
        for (int i = 0; i < 14; i += 2)
        {
            if (moves[i + 14])
            {
                attacking[places] = pos + ((14 - i) * 7 / 2);
                places++;
            }
            if (moves[i + 15])
            {
                attacking[places] = pos + ((14 - i) * 9 / 2);
                places++;
            }
        }
        // Rook moves
        for (int i = 0; i < 7; i++)
        {
            if (!(down > 6 - i))
            {
                moves[i + 28] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(down < i + 1))
            {
                moves[i + 35] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right > 6 - i))
            {
                moves[i + 42] = false;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (!(right < i + 1))
            {
                moves[i + 49] = false;
            }
        }

        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 28])
            {
                attacking[places] = pos - ((7 - i) * 8);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 35])
            {
                attacking[places] = pos + ((7 - i) * 8);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 42])
            {
                attacking[places] = pos - ((7 - i) * 1);
                places++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            if (moves[i + 49])
            {
                attacking[places] = pos + ((7 - i) * 1);
                places++;
            }
        }
        return attacking;

    }

    public int[] kingAttacking(int pos)
    {
        int[] attacking;
        switch (pos)
        {
            case 0 ->
                attacking = new int[]
                {
                    1, 8, 9
                };
            case 7 ->
                attacking = new int[]
                {
                    6, 14, 15
                };
            case 56 ->
                attacking = new int[]
                {
                    48, 49, 57
                };
            case 63 ->
                attacking = new int[]
                {
                    54, 55, 62
                };
            default ->
            {
                if (pos / 8 == 0)
                {
                    attacking = new int[]
                    {
                        pos - 1, pos + 1, pos + 7, pos + 8, pos + 9
                    };
                } else if (pos / 8 == 7)
                {
                    attacking = new int[]
                    {
                        pos - 9, pos - 8, pos - 7, pos - 1, pos + 1
                    };
                } else if (pos % 8 == 0)
                {
                    attacking = new int[]
                    {
                        pos - 8, pos - 7, pos + 1, pos + 8, pos + 9
                    };
                } else if (pos % 8 == 7)
                {
                    attacking = new int[]
                    {
                        pos - 9, pos - 8, pos - 1, pos + 7, pos + 8
                    };
                } else
                {
                    attacking = new int[]
                    {
                        pos - 9, pos - 8, pos - 7, pos - 1, pos + 1, pos + 7, pos + 8, pos + 9
                    };
                }
            }
        }
        return attacking;
    }

    public int getFreq()
    {
        return freq;
    }

    public int getValue()
    {
        return value;
    }

    public int getTotalPosFreq()
    {
        return totalPosFreq;
    }

    public char getSymbol(boolean isWhite)
    {
        if (type.equals("empty"))
        {
            return '_';
        } else if (isWhite)
        {
            return type.toUpperCase().charAt(0);
        } else
        {
            return type.toLowerCase().charAt(0);
        }
    }

}
