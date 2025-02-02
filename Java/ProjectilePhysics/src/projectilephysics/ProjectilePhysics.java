/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectilephysics;

/**
 *
 * @author Camden
 */
public class ProjectilePhysics
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        double vx = 1000000000;
        double vy = 10;
        double x = 0;
        double y = 0;
        double g = 9.81;
        double c = 0.5;
        double A = 0.00454;
        double m = 0.145;
        double p = 1.29;
        double airConst = c * A * p / (m * 2);
        long percision = 1000000000000l;
        double vxHolder = vx;
        double t = 0;
        System.out.println("Air constant = " + airConst);
        System.out.println("v = " + Math.sqrt(vx * vx + vy * vy));
        System.out.println("angle = " + Math.atan(vy / vx) * 180 / 3.1415926535);
        x += vx / percision;
        y += vy / percision;
        vy -= (Math.sqrt(vxHolder * vxHolder + vy * vy) * vy * airConst) / percision;
        vy -= g / percision;
        t += 1 / percision;
        while (y >= 0)
        {
            x += vx / percision;
            y += vy / percision;
            vxHolder = vx;
            vx -= (Math.sqrt(vx * vx + vy * vy) * vx * airConst) / percision;
            vy -= (Math.sqrt(vxHolder * vxHolder + vy * vy) * vy * airConst) / percision;
            vy -= g / percision;
            t += 1 / percision;
        }
        System.out.println("range = " + x);
        System.out.println("time = " + t);

    }

}
