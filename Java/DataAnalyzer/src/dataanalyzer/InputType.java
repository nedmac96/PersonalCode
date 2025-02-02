/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataanalyzer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Camden
 */
public class InputType
{

    private boolean isNumber;
    private ArrayList<String> posValues;
    private double[] values;
    private double[] valueImpacts;
    private double linearConst;
    private double sqConst;
    private double avgInput;
    private double impact;

    public InputType(String[] values, boolean isNumber)
    {
        this.isNumber = isNumber;

        if (isNumber)
        {
            this.values = new double[values.length];
            double total = 0;
            for (int i = 0; i < values.length; i++)
            {
                this.values[i] = Double.parseDouble(values[i]);
                total += this.values[i];
            }
            avgInput = total / this.values.length;
            sqConst = 0;
            linearConst = 1;
            impact = 1;
        } else
        {
            posValues = new ArrayList<>();
            boolean isNewValue;
            for (String value : values)
            {
                isNewValue = true;
                for (int j = 0; j < posValues.size(); j++)
                {
                    if (posValues.get(j).equals(value))
                    {
                        isNewValue = false;
                    }
                }
                if (isNewValue)
                {
                    posValues.add(value);
                }
            }
            this.values = new double[values.length];
            for (int i = 0; i < values.length; i++)
            {
                this.values[i] = posValues.indexOf(values[i]);
            }
        }
    }

    public double getExpectedValue(int index)
    {
        double expectedValue;
        if (isNumber)
        {
            expectedValue = linearConst * (values[index] - avgInput) + sqConst * (values[index] - avgInput) * (values[index] - avgInput);
        } else
        {
            expectedValue = valueImpacts[(int) values[index]];
        }
        return expectedValue * impact;
    }

    public void setValueImpacts(double[] valueImpacts)
    {
        this.valueImpacts = valueImpacts;
    }

    public void setImpact(double impact)
    {
        this.impact = impact;
    }

    public double getImpact()
    {
        return impact;
    }

    public void setLinearConst(double linearConst)
    {
        this.linearConst = linearConst;
    }

    public void setSqConst(double sqConst)
    {
        this.sqConst = sqConst;
    }

    public boolean isNumber()
    {
        return isNumber;
    }

    public ArrayList<String> getPosValues()
    {
        return posValues;
    }

    public double[] getValueImpacts()
    {
        return valueImpacts;
    }

    public double getLinearConst()
    {
        return linearConst;
    }

    public double getSqConst()
    {
        return sqConst;
    }

    public double[] getValues()
    {
        return values;
    }

}
