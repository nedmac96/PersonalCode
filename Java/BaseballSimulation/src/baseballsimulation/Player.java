/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseballsimulation;

/**
 *
 * @author Camden
 */
public class Player
{

    private double[] eventOdds;
    private boolean canSteal;
    private double probTotal;

    public Player()
    {
        eventOdds = new double[9];
        eventOdds[0] = 0.159;
        eventOdds[1] = 0.050;
        eventOdds[2] = 0.004;
        eventOdds[3] = 0.036;
        eventOdds[4] = 0.230;
        eventOdds[5] = 0.290;
        eventOdds[6] = 0.214;
        eventOdds[7] = 0.100;
        eventOdds[8] = 0.098;
        canSteal = true;
        probTotal = 0;
        for (int i = 0; i < eventOdds.length; i++)
        {
            probTotal += eventOdds[i];
        }

    }

    public Player(int id)
    {
        eventOdds = new double[9];

        switch (id)
        {
            case 0:
                // Julio Rodriguez
                eventOdds[0] = 0.160;
                eventOdds[1] = 0.046;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.043;
                eventOdds[4] = 0.256;
                eventOdds[5] = 0.230;
                eventOdds[6] = 0.169;
                eventOdds[7] = 0.072;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 1:
                // Ty France
                eventOdds[0] = 0.162;
                eventOdds[1] = 0.046;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.024;
                eventOdds[4] = 0.170;
                eventOdds[5] = 0.281;
                eventOdds[6] = 0.208;
                eventOdds[7] = 0.100;
                eventOdds[8] = 0.098;
                canSteal = false;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 2:
                // JP Crawford
                eventOdds[0] = 0.145;
                eventOdds[1] = 0.045;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.020;
                eventOdds[4] = 0.167;
                eventOdds[5] = 0.273;
                eventOdds[6] = 0.201;
                eventOdds[7] = 0.140;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 3:
                // Cal Raleigh
                eventOdds[0] = 0.099;
                eventOdds[1] = 0.039;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.059;
                eventOdds[4] = 0.289;
                eventOdds[5] = 0.234;
                eventOdds[6] = 0.172;
                eventOdds[7] = 0.096;
                eventOdds[8] = 0.098;
                canSteal = false;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 4:
                // Mitch Haniger
                eventOdds[0] = 0.128;
                eventOdds[1] = 0.036;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.047;
                eventOdds[4] = 0.279;
                eventOdds[5] = 0.245;
                eventOdds[6] = 0.181;
                eventOdds[7] = 0.078;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 5:
                // Dylan Moore
                eventOdds[0] = 0.095;
                eventOdds[1] = 0.045;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.030;
                eventOdds[4] = 0.304;
                eventOdds[5] = 0.204;
                eventOdds[6] = 0.151;
                eventOdds[7] = 0.154;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 6:
                // Sam Haggerty
                eventOdds[0] = 0.151;
                eventOdds[1] = 0.038;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.025;
                eventOdds[4] = 0.220;
                eventOdds[5] = 0.250;
                eventOdds[6] = 0.184;
                eventOdds[7] = 0.113;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 7:
                // Josh Rojas
                eventOdds[0] = 0.166;
                eventOdds[1] = 0.048;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.017;
                eventOdds[4] = 0.202;
                eventOdds[5] = 0.261;
                eventOdds[6] = 0.193;
                eventOdds[7] = 0.098;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 8:
                // Jorge Polanco
                eventOdds[0] = 0.128;
                eventOdds[1] = 0.038;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.038;
                eventOdds[4] = 0.242;
                eventOdds[5] = 0.240;
                eventOdds[6] = 0.177;
                eventOdds[7] = 0.132;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 9:
                // Dominic Canzone
                eventOdds[0] = 0.101;
                eventOdds[1] = 0.065;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.041;
                eventOdds[4] = 0.198;
                eventOdds[5] = 0.300;
                eventOdds[6] = 0.221;
                eventOdds[7] = 0.055;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 10:
                // Cade Marlowe
                eventOdds[0] = 0.130;
                eventOdds[1] = 0.030;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.030;
                eventOdds[4] = 0.330;
                eventOdds[5] = 0.196;
                eventOdds[6] = 0.144;
                eventOdds[7] = 0.120;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 11:
                // Luke Raley
                eventOdds[0] = 0.117;
                eventOdds[1] = 0.048;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.039;
                eventOdds[4] = 0.317;
                eventOdds[5] = 0.206;
                eventOdds[6] = 0.152;
                eventOdds[7] = 0.108;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 12:
                // Mitch Garver
                eventOdds[0] = 0.117;
                eventOdds[1] = 0.036;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.047;
                eventOdds[4] = 0.257;
                eventOdds[5] = 0.231;
                eventOdds[6] = 0.171;
                eventOdds[7] = 0.126;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 13:
                // Clase Jonatan
                eventOdds[0] = 0.16;
                eventOdds[1] = 0.04;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.03;
                eventOdds[4] = 0.28;
                eventOdds[5] = 0.247;
                eventOdds[6] = 0.182;
                eventOdds[7] = 0.10;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 14:
                // Seby Zavala
                eventOdds[0] = 0.128;
                eventOdds[1] = 0.047;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.021;
                eventOdds[4] = 0.341;
                eventOdds[5] = 0.214;
                eventOdds[6] = 0.158;
                eventOdds[7] = 0.085;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            case 15:
                // Luis Urias
                eventOdds[0] = 0.119;
                eventOdds[1] = 0.037;
                eventOdds[2] = 0.004;
                eventOdds[3] = 0.032;
                eventOdds[4] = 0.221;
                eventOdds[5] = 0.251;
                eventOdds[6] = 0.185;
                eventOdds[7] = 0.142;
                eventOdds[8] = 0.098;
                canSteal = true;
                probTotal = 0;
                for (int i = 0; i < eventOdds.length; i++)
                {
                    probTotal += eventOdds[i];
                }
                break;
            default:
                break;
        }

    }

}
