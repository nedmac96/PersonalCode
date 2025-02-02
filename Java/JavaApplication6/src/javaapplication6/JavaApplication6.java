/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.util.Scanner;

/**
 *
 * @author Camden
 */
public class JavaApplication6
{

    public static void main(String[] args)
    {
        /*String code;
        int score = 0;

        while (score == 0)
        {
            System.out.println("Enter your code: ");
            Scanner scanner = new Scanner(System.in);
            code = scanner.next();
            if (code.length() == 6)
            {
                for (int j = 0; j < code.length() - 1; j++)
                {
                    try
                    {
                        if (Integer.parseInt(code.substring(j, j + 1)) != 0)
                        {
                            score += (Integer.parseInt(code.substring(j, j + 1)) + 27) * Math.pow(35, (code.length() - j) - 2);
                        } else
                        {
                            score += Math.pow(36, code.length() - j - 2);
                        }
                    } catch (Exception e)
                    {
                        if (code.substring(j, j + 1).compareTo("O") > 0)
                        {
                            score += (code.substring(j, j + 1).compareTo("A") + 1) * Math.pow(35, (code.length() - j) - 2);
                        } else
                        {
                            score += (code.substring(j, j + 1).compareTo("A") + 2) * Math.pow(35, (code.length() - j) - 2);
                        }

                    }

                }
            } else
            {
                System.out.println("Invalid code. Please try again");
            }
        }
        System.out.println("The code's value is " + score + ".");*/

        String codes[] =
        {
            "000000", "ALNL9V", "C0PR9Y", "CK96R9", "CLV7KH", "C7D6ZF", "DFPCRQ", "DHHGHM", "E0YLEF", "EZEPJ4", "E2ZX7T", "E7PRUP", "F0NQNT", "FIVXAV", "F3AQWZ", "GH4W32", "GKYNP3", "GTGML2", "G273HH", "G41TZJ", "G960GC", "G6CCSD", "G6YY3F", "G69YR9", "G67IRY", "H0UFGM", "HACCNP", "HASAP6", "HA9SDZ", "HCCFIZ", "HCQM7S", "HCVNE3", "HDFS9S", "HFMXFT", "AXGHMN", "FLJ174", "DLNEDJ", "GCHCHX", "EG9UIY", "G273HH", "GY3GCU", "G7TPMO", "ATW7WN", "013PLZ", "ATT1KX", "HEXHPK", "GVSHKX", "HEXPHD", "E7PRUP", "GKYNP3", "F0NQNT", "G73JCQ", "G74J03", "G0YIC7", "G4ANL3", "FIPYMW", "H0QH31",
            "E34URY",
            "FYFPIM",
            "GNKHNN",
            "GZGVDV",
            "G9XAVT",
            "F42LEQ",
            "FN3I3J",
            "HFCSDK",
            "F9SF6Q",
            "GNEMQQ",
            "G6SR9I",
            "HGNK7Y",
            "HAMW6K",
            "GZ37DS",
            "G6T4XN", "G112IT",
            "DJCVMV",
            "G6S4VT",
            "HGQASC"

        };
        getScoresNormal(codes);

    }

    public static void getScoresInvertedNums(String[] codes)
    {
        int score;

        for (int i = 0; i < codes.length; i++)
        {
            score = 0;
            for (int j = 0; j < codes[i].length() - 1; j++)
            {
                try
                {
                    if (Integer.parseInt(codes[i].substring(j, j + 1)) != 0)
                    {
                        score += (-1 * Integer.parseInt(codes[i].substring(j, j + 1)) + 35) * Math.pow(35, (codes[i].length() - j) - 2);
                    } else
                    {
                        score += Math.pow(36, codes[i].length() - j - 2) * 0;
                    }
                } catch (Exception e)
                {
                    if (codes[i].substring(j, j + 1).compareTo("O") > 0)
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 0) * Math.pow(35, (codes[i].length() - j) - 2);
                    } else
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 1) * Math.pow(35, (codes[i].length() - j) - 2);
                    }

                }

            }
            System.out.println(score);
        }
    }

    public static void getScoresNormal(String[] codes)
    {
        int score;
        int numCharactors = 34;
        for (int i = 0; i < codes.length; i++)
        {
            score = 0;
            for (int j = 0; j < codes[i].length() - 1; j++)
            {
                try
                {
                    if (Integer.parseInt(codes[i].substring(j, j + 1)) == 9)
                    {
                        score += (5 + 26) * Math.pow(numCharactors, (codes[i].length() - j) - 2);
                    } else if (Integer.parseInt(codes[i].substring(j, j + 1)) != 0)
                    {
                        score += (Integer.parseInt(codes[i].substring(j, j + 1)) + 26) * Math.pow(numCharactors, (codes[i].length() - j) - 2);
                    } else
                    {
                        score += Math.pow(36, codes[i].length() - j - 2) * 0;
                    }
                } catch (Exception e)
                {
                    if (codes[i].substring(j, j + 1).compareTo("O") > 0)
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") - 1) * Math.pow(numCharactors, (codes[i].length() - j) - 2);
                    } else if (codes[i].substring(j, j + 1).compareTo("B") > 0)
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 0) * Math.pow(numCharactors, (codes[i].length() - j) - 2);
                    } else
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 1) * Math.pow(numCharactors, (codes[i].length() - j) - 2);
                    }

                }

            }
            //System.out.println(codes[i] + " - " + score);
            System.out.println(score);
        }
    }

    public static void getScoresThreeDigits(String[] codes)
    {
        int score;
        int numCharactors = 34;
        for (int i = 0; i < codes.length; i++)
        {
            score = 0;
            for (int j = 0; j < codes[i].length() - 3; j++)
            {
                try
                {
                    if (Integer.parseInt(codes[i].substring(j, j + 1)) == 9)
                    {
                        score += (5 + 26) * Math.pow(numCharactors, (codes[i].length() - j) - 4);
                    } else if (Integer.parseInt(codes[i].substring(j, j + 1)) != 0)
                    {
                        score += (Integer.parseInt(codes[i].substring(j, j + 1)) + 26) * Math.pow(numCharactors, (codes[i].length() - j) - 4);
                    } else
                    {
                        score += Math.pow(36, codes[i].length() - j - 3) * 0;
                    }
                } catch (Exception e)
                {
                    if (codes[i].substring(j, j + 1).compareTo("O") > 0)
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") - 1) * Math.pow(numCharactors, (codes[i].length() - j) - 4);
                    } else if (codes[i].substring(j, j + 1).compareTo("B") > 0)
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 0) * Math.pow(numCharactors, (codes[i].length() - j) - 4);
                    } else
                    {
                        score += (codes[i].substring(j, j + 1).compareTo("A") + 1) * Math.pow(numCharactors, (codes[i].length() - j) - 4);
                    }

                }

            }
            System.out.println(codes[i] + " - " + score);
            //System.out.println(score);
        }
    }
}
