/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication28;

import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author Camden
 */
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.HashMap;

public class JavaApplication28
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Queue<String> queue = new LinkedList<String>();
        HashMap<String, Double> map = new HashMap<>();
        String name = "Bob";
        Double grade = 10.0;
        map.put(name, grade);
        System.out.println(map.get(name));

        System.out.println(map.replace(name, 20.0));
    }
}
