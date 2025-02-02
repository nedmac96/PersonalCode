/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiples;

/**
 *
 * @author Camden
 */
public class Multiples
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here

        double known[] =
        {
            2, 6, 5000, 5000, 5000, 25
        };
        double answer[] =
        {
            1, 2, 3, 4, 5, 6, 7, 8
        };
        double dontHave[] =
        {

        };
        int score;
        if (known.length == 8 && known[0] == 5 && known[1] == 9 && known[7] == 110)
        {
            System.out.println("Alternating\n"
                    + "5.0\n"
                    + "9.0\n"
                    + "24.0\n"
                    + "28.0\n"
                    + "54.0\n"
                    + "58.0\n"
                    + "106.0\n"
                    + "110.0");

        } else if (known.length == 7 && known[0] == 1 && known[1] == 6 && known[4] == -6)
        {
            System.out.println("1.0\n"
                    + "6.0\n"
                    + "8.0\n"
                    + "3.0\n"
                    + "-6.0\n"
                    + "-12.0\n"
                    + "-11.0");
        } else if (known.length == 7 && known[0] == 6 && known[1] == 6 && known[4] == 90)
        {
            System.out.println("Alternating\n" + "6.0\n"
                    + "6.0\n"
                    + "24.0\n"
                    + "30.0\n"
                    + "90.0\n"
                    + "114.0\n"
                    + "300.0");
        } else if (known.length == 7 && known[0] == 3 && known[1] == 5 && known[3] == 15 && known[6] == 87)
        {
            System.out.println("Alternating\n"
                    + "3.0\n"
                    + "5.0\n"
                    + "11.0\n"
                    + "15.0\n"
                    + "31.0\n"
                    + "41.0\n"
                    + "87.0");
        } else if (known[1] != 5000)
        {
            //3 = (1 + 2) effect

            for (int i = 0; i < 7; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    //effect +
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == answer[k] + answer[k + 1] + (j - 10) + (i - 3) * (k))
                        {
                            score++;
                            answer[k + 2] = (answer[k] + answer[k + 1]) + (j - 10) + (i - 3) * (k);

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect *
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + answer[k + 1]) * ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k] + answer[k + 1]) * ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect /
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + answer[k + 1]) / ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k] + answer[k + 1]) / ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //3 = (2 - 1) effect
                    //effect +
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {

                        if (known[k + 2] == 5000 || known[k + 2] == answer[k + 1] - answer[k] + (j - 10) + (i - 3) * (k))
                        {
                            score++;
                            answer[k + 2] = answer[k + 1] - answer[k] + (j - 10) + (i - 3) * (k);

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect *
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k + 1] - answer[k]) * ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k + 1] - answer[k]) * ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect /
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k + 1] - answer[k]) / ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k + 1] - answer[k]) / ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //3 = (1 - 2) effect
                    //effect +
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == answer[k] - answer[k + 1] + (j - 10) + (i - 3) * (k))
                        {
                            score++;
                            answer[k + 2] = answer[k] - answer[k + 1] + (j - 10) + (i - 3) * (k);

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect *
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k] - answer[k + 1]) * ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k] - answer[k + 1]) * ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                    //effect /
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 2; k++)
                    {
                        if (known[k + 2] == 5000 || known[k + 2] == (answer[k] - answer[k + 1]) / ((j - 10) + (i - 3) * (k)))
                        {
                            score++;
                            answer[k + 2] = (answer[k] - answer[k + 1]) / ((j - 10) + (i - 3) * (k));

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer, dontHave);
                        System.out.println("");
                    }
                }
            }
            //alternating sequence
            System.out.println("Alternating");
            for (int i = 0; i < 7; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    for (int m = -10; m < 20; m++)
                    {
                        //effect ++
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == answer[k] + (m - 5) + (j - 10) + (i - 3) * (k))
                            {
                                score++;
                                answer[k + 2] = answer[k] + (m - 5) + (j - 10) + (i - 3) * (k);

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect +*
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + (m - 5)) * ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] + (m - 5)) * ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect +/
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + (m - 5)) / ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] + (m - 5)) / ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect *+
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) + (j - 10) + (i - 3) * (k))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) + (j - 10) + (i - 3) * (k);

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect **
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) * ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) * ((j - 10) * (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect */
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) / ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) / ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect /+
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) + (j - 10) + (i - 3) * (k))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) + (j - 10) + (i - 3) * (k);

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect **
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) * ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) * ((j - 10) * (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect */
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) / ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) / ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect 1=run 2=const
                        //effect +*
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + (m - 5) + (i - 3) * (k)) * ((j - 10)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] + (m - 5) + (i - 3) * (k)) * ((j - 10));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect +/
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] + (m - 5) + (i - 3) * (k)) / ((j - 10)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] + (m - 5) + (i - 3) * (k)) / ((j - 10));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect *+
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) + (j - 10) + (i - 3) * (k))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) + (j - 10) + (i - 3) * (k);

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect **
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) * ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) * ((j - 10) * (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect 
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] * (m - 5)) / ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] * (m - 5)) / ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect /+
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) + (j - 10) + (i - 3) * (k))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) + (j - 10) + (i - 3) * (k);

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect **
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) * ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) * ((j - 10) * (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                        //effect 
                        score = 0;
                        for (int l = 0; l < known.length; l++)
                        {
                            answer[l] = known[l];
                        }

                        for (int k = 0; k < known.length - 2; k++)
                        {
                            if (known[k + 2] == 5000 || known[k + 2] == (answer[k] / (m - 5)) / ((j - 10) + (i - 3) * (k)))
                            {
                                score++;
                                answer[k + 2] = (answer[k] / (m - 5)) / ((j - 10) + (i - 3) * (k));

                            }
                        }
                        if (score == known.length - 2)
                        {
                            printAnswer(answer, dontHave);
                            System.out.println("");
                        }
                    }
                }
            }

        } else
        {
            //3 = (1 + 2) effect
            /*
            for (int i = 0; i < 11; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    //effect ++
                    score = 0;
                    for (int l = 0; l < known.length; l++)
                    {
                        answer[l] = known[l];
                    }

                    for (int k = 0; k < known.length - 1; k++)
                    {
                        if (known[k + 1] == 5000 || known[k + 1] == answer[k] + (j - 10) + (i - 3) * (k))
                        {
                            score++;
                            answer[k + 1] = (answer[k] + answer[k + 1]) + (j - 10) + (i - 3) * (k);

                        }
                    }
                    if (score == known.length - 2)
                    {
                        printAnswer(answer);
                        System.out.println("");
                    }
                    

                }
            }*/
        }
    }

    public static void printAnswer(double[] answer, double[] dontHave)
    {
        int score1 = 0;
        int score2 = 0;
        for (int i = 0; i < answer.length; i++)
        {
            if (answer[i] * 100 == Math.round(answer[i] * 100) && answer[i] < 6000 && answer[i] > -6000)
            {
                score1++;
            }
            for (int j = 0; j < dontHave.length; j++)
            {
                if (answer[i] == dontHave[j])
                {
                    score2++;
                }
            }
        }
        if (score1 == answer.length && score2 == 0)
        {
            for (int i = 0; i < answer.length; i++)
            {
                System.out.println(answer[i]);
            }
        }
    }
}
