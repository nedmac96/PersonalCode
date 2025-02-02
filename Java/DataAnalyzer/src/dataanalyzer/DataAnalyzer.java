/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dataanalyzer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Camden
 */
public class DataAnalyzer
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        DataAnalyzer analyzer = new DataAnalyzer();
        analyzer.analyze();
    }

    public void analyze()
    {
        String input
                = "SUN	1	0	0	1	1	0	0	77\n"
                + "MON	0	0	0	0	1	0	0	94\n"
                + "TUE	0	0	0	1	1	0	1	88\n"
                + "WED	0	0	0	0	1	0	0	85\n"
                + "THU	0	0	0	0	1	0	0	85\n"
                + "FRI	1	1	0	0	1	0	1	87\n"
                + "SAT	0	0	1	0	1	0	0	88\n"
                + "SUN	0	0	0	0	1	0	0	79\n"
                + "MON	0	0	0	1	1	0	0	82\n"
                + "TUE	0	1	0	0	1	0	0	79\n"
                + "WED	0	0	1	1	1	0	0	79\n"
                + "THU	1	0	0	0	1	0	0	83\n"
                + "FRI	0	0	0	0	1	0	0	87\n"
                + "SAT	1	0	0	1	1	0	0	87\n"
                + "SUN	1	0	0	1	1	0	0	91\n"
                + "MON	1	0	0	1	1	0	0	92\n"
                + "TUE	0	0	0	1	1	0	1	87\n"
                + "WED	0	0	0	0	1	0	0	82\n"
                + "THU	1	0	0	1	1	0	1	80\n"
                + "FRI	1	0	0	0	1	0	0	78\n"
                + "SAT	0	0	0	1	1	0	0	77\n"
                + "SUN	0	0	0	1	1	0	0	83\n"
                + "MON	0	0	0	1	1	0	0	86\n"
                + "TUE	0	0	0	0	1	0	0	83\n"
                + "WED	0	0	0	0	1	0	0	85\n"
                + "THU	0	1	0	0	1	0	0	82\n"
                + "FRI	0	0	1	1	0	1	0	81\n"
                + "SAT	1	0	0	1	0	0	0	94\n"
                + "SUN	0	0	0	0	1	1	0	83\n"
                + "MON	0	0	0	1	1	0	0	84\n"
                + "TUE	0	1	0	1	1	0	0	78\n"
                + "WED	0	0	1	1	1	0	0	86\n"
                + "THU	0	0	0	1	1	0	1	76\n"
                + "FRI	0	0	0	1	1	0	0	90\n"
                + "SAT	1	0	0	1	1	0	0	91\n"
                + "SUN	1	1	0	1	1	0	0	95\n"
                + "MON	0	0	1	1	1	0	0	82\n"
                + "TUE	0	0	0	1	1	0	1	79\n"
                + "WED	0	0	0	1	1	0	0	83\n"
                + "THU	0	0	0	1	1	0	0	95\n"
                + "FRI	0	0	0	1	0	1	0	79\n"
                + "SAT	1	0	0	1	0	0	0	94\n"
                + "SUN	0	1	0	1	1	1	0	89\n"
                + "MON	1	0	1	1	1	0	0	81\n"
                + "TUE	0	0	0	0	1	0	1	75\n"
                + "WED	0	0	0	1	1	0	0	78\n"
                + "THU	1	1	0	0	1	0	1	91\n"
                + "FRI	1	0	1	1	1	0	0	83 ";
        int numInputTypes = 9;
        int numInputValues = 48;
        String endData = "83";
        boolean[] inputIsNum =
        {
            false, false, false, false, false, false, false, false, true
        };

        /*String input
                = "25	15.5	19.5	17.4\n"
                + "5	2.5	12.5	16.3\n"
                + "38	28	20	33.3\n"
                + "7	4.5	12.5	14\n"
                + "40	22	28	37.4\n"
                + "30	21	19	35\n"
                + "43	31.5	21.5	43.7\n"
                + "22	12	20	27.6\n"
                + "37	21.5	25.5	38.3\n"
                + "7	5.5	11.5	13.6\n"
                + "2	3	9	12.7\n"
                + "5	3.5	11.5	22.9\n"
                + "18	18	10	29.9\n"
                + "7	11.5	5.5	13\n"
                + "17	10.5	16.5	16.3\n"
                + "16	18	8	28.3\n"
                + "25	15.5	19.5	23.4\n"
                + "19	15.5	13.5	22.7\n"
                + "41	20.5	30.5	29.9\n"
                + "36	27	19	36.1\n"
                + "45	26.5	28.5	36.4\n"
                + "38	19	29	32.1\n"
                + "18	19	9	29.4\n"
                + "17	18.5	8.5	16.9\n"
                + "44	25	29	36.3\n"
                + "7	11.5	5.5	26\n"
                + "12	13	9	12.3\n"
                + "19	16.5	12.5	34.3\n"
                + "8	11	7	15\n"
                + "16	15	11	30.6\n"
                + "32	17	25	34.3\n"
                + "17	10.5	16.5	17.3 ";
        int numInputTypes = 4;
        int numInputValues = 32;

        boolean[] inputIsNum =
        {
            true, true, true, true
        };*/
        InputType[] inputValues = processInput(input, numInputTypes, numInputValues, inputIsNum, endData);

        String output
                = "94\n"
                + "88\n"
                + "85\n"
                + "85\n"
                + "87\n"
                + "88\n"
                + "79\n"
                + "82\n"
                + "79\n"
                + "79\n"
                + "83\n"
                + "87\n"
                + "87\n"
                + "91\n"
                + "92\n"
                + "87\n"
                + "82\n"
                + "80\n"
                + "78\n"
                + "77\n"
                + "83\n"
                + "86\n"
                + "83\n"
                + "85\n"
                + "82\n"
                + "81\n"
                + "94\n"
                + "83\n"
                + "84\n"
                + "78\n"
                + "86\n"
                + "76\n"
                + "90\n"
                + "91\n"
                + "95\n"
                + "82\n"
                + "79\n"
                + "83\n"
                + "95\n"
                + "79\n"
                + "94\n"
                + "89\n"
                + "81\n"
                + "75\n"
                + "78\n"
                + "91\n"
                + "83\n"
                + "90 ";

        double[] outputValues = processOutput(output, numInputValues);
        double outputSum = 0;
        for (int i = 0; i < outputValues.length; i++)
        {
            outputSum += outputValues[i];
        }
        double outputAvg = outputSum / numInputValues;
        double rSq = 0;
        int orderNum = 0;
        InputType[] model = null;
        int runs = 1;
        for (int j = 0; j < runs; j++)
        {
            int[] set1 = getSet(numInputValues);
            outputSum = 0;
            for (int i = 0; i < set1.length; i++)
            {
                outputSum += outputValues[set1[i]];
            }
            double set1Avg = outputSum / set1.length;

            int[] test1 = getTest(set1, numInputValues);
            outputSum = 0;
            for (int i = 0; i < test1.length; i++)
            {
                outputSum += outputValues[test1[i]];
            }
            double test1Avg = outputSum / test1.length;

            int[] order1 =
            {
                0, 7, 5, 2, 6, 3, 4, 8, 1
            };
            int[] order2 =
            {
                1, 7, 5, 2, 6, 3, 4, 8, 0
            };
            int[] order3 =
            {
                8, 1, 6, 5, 2, 7, 3, 4, 0
            };
            int[] order4 =
            {
                1, 6, 5, 2, 7, 3, 4, 8, 0
            };
            int[] order5 =
            {
                4, 1, 8, 6, 5, 2, 7, 3, 0
            };
            InputType[] testModel = generateModel2(inputValues, set1, outputValues, set1Avg, order1);
            orderNum = 1;
            model = testModel;
            double modelRSq = testModel(model, set1, outputValues, set1Avg, set1Avg);
            //printModel(model);
            System.out.println(modelRSq);
            double testModelRSq;
            testModel = generateModel2(inputValues, set1, outputValues, set1Avg, order2);
            testModelRSq = testModel(testModel, set1, outputValues, set1Avg, set1Avg);
            //printModel(testModel);
            System.out.println(testModelRSq);
            if (testModelRSq > modelRSq)
            {

                model = testModel;
                modelRSq = testModelRSq;
                orderNum = 2;
            }

            testModel = generateModel2(inputValues, set1, outputValues, set1Avg, order3);
            testModelRSq = testModel(testModel, set1, outputValues, set1Avg, set1Avg);
            System.out.println(testModelRSq);
            if (testModelRSq > modelRSq)
            {
                model = testModel;
                modelRSq = testModelRSq;
                orderNum = 3;
            }

            testModel = generateModel2(inputValues, set1, outputValues, set1Avg, order4);
            testModelRSq = testModel(testModel, set1, outputValues, set1Avg, set1Avg);
            System.out.println(testModelRSq);
            if (testModelRSq > modelRSq)
            {
                model = testModel;
                modelRSq = testModelRSq;
                orderNum = 4;
            }

            testModel = generateModel2(inputValues, set1, outputValues, set1Avg, order5);
            testModelRSq = testModel(testModel, set1, outputValues, set1Avg, set1Avg);
            System.out.println(testModelRSq);
            if (testModelRSq > modelRSq)
            {
                model = testModel;
                modelRSq = testModelRSq;
                orderNum = 5;
            }

            rSq += testModel(model, test1, outputValues, set1Avg, test1Avg);
        }
        printModel(model);
        System.out.println("Predictive R Squared: " + (rSq / runs));
        System.out.println("Order Number: " + orderNum);
    }

    public InputType[] processInput(String input, int numInputTypes, int numInputValues, boolean[] inputIsNum, String endData)
    {
        ArrayList<String> inputValues = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < numInputTypes; i++)
        {
            for (int j = 0; j < numInputValues; j++)
            {
                String value = "";
                while ((input.charAt(index) == '\n' || input.charAt(index) == '	') && index + 1 < input.length())
                {
                    index++;
                }
                while ((input.charAt(index) != '\n' && input.charAt(index) != '	') && index + 1 < input.length())
                {
                    value = value + input.charAt(index);
                    index++;
                }
                inputValues.add(value);
            }
        }
        while (!inputValues.get(inputValues.size() - 1).equals(endData))
        {
            inputValues.remove(inputValues.size() - 1);
        }

        InputType[] inputs = new InputType[numInputTypes];
        String[] values;
        for (int i = 0; i < numInputTypes; i++)
        {
            values = new String[numInputValues];
            for (int j = 0; j < numInputValues; j++)
            {
                values[j] = inputValues.get(j * numInputTypes + i);
            }
            inputs[i] = new InputType(values, inputIsNum[i]);
        }
        return inputs;
    }

    public double[] processOutput(String output, int numOutputValues)
    {
        double[] outputValues = new double[numOutputValues];
        int index = 0;

        for (int i = 0; i < numOutputValues; i++)
        {
            String value = "";
            while ((output.charAt(index) == '\n' || output.charAt(index) == '	') && index + 1 < output.length())
            {
                index++;
            }
            while ((output.charAt(index) != '\n' && output.charAt(index) != '	') && index + 1 < output.length())
            {
                value = value + output.charAt(index);
                index++;
            }
            outputValues[i] = Double.parseDouble(value);
        }

        return outputValues;
    }

    public int[] getSet(int length)
    {
        int[] set = new int[length / 2];
        for (int i = 0; i < length / 2; i++)
        {
            boolean value1Found = false;
            int id;
            while (!value1Found)
            {
                id = (int) Math.floor(Math.random() * length);
                boolean value1Used = false;
                for (int j = 0; j < i; j++)
                {
                    if (set[j] == id)
                    {
                        value1Used = true;
                    }
                }
                if (!value1Used && !value1Found)
                {
                    set[i] = id;
                    value1Found = true;
                }
            }
        }
        return set;
    }

    public int[] getTest(int[] usedValues, int length)
    {
        int[] set;
        if (length % 2 == 1)
        {
            set = new int[1 + length / 2];
        } else
        {
            set = new int[length / 2];
        }
        int index = 0;

        for (int i = 0; i < length; i++)
        {
            boolean value1Used = false;

            for (int j = 0; j < usedValues.length; j++)
            {
                if (usedValues[j] == i)
                {
                    value1Used = true;
                }

            }
            if (!value1Used)
            {
                set[index] = i;
                index++;
            }

        }
        return set;
    }

    public double testModel(InputType[] inputValues, int[] testData, double[] outputValues, double outputAvg, double testDataAvg)
    {
        double predictedChange = 0;
        for (int i = 0; i < testData.length; i++)
        {
            double predictedValue = outputAvg;
            for (InputType inputValue : inputValues)
            {
                predictedValue += inputValue.getExpectedValue(testData[i]);
            }
            predictedChange += (predictedValue - outputValues[testData[i]]) * (predictedValue - outputValues[testData[i]]);
        }
        double actualChange = 0;
        for (int i = 0; i < testData.length; i++)
        {
            actualChange += (outputValues[testData[i]] - testDataAvg) * (outputValues[testData[i]] - testDataAvg);
        }
        return 1.0 - (predictedChange / actualChange);
    }

    public void printModel(InputType[] inputValues)
    {
        for (int i = 0; i < inputValues.length; i++)
        {
            System.out.println("Column: " + (i + 1));
            if (!inputValues[i].isNumber())
            {
                for (int j = 0; j < inputValues[i].getPosValues().size(); j++)
                {
                    System.out.println(inputValues[i].getPosValues().get(j) + ": " + inputValues[i].getValueImpacts()[j]);
                }
            } else
            {
                if (inputValues[i].getSqConst() == 0)
                {
                    System.out.println("Value * " + inputValues[i].getLinearConst());
                } else
                {
                    System.out.println("Value * " + inputValues[i].getLinearConst() + " + Value squared * " + inputValues[i].getSqConst());
                }
            }
            System.out.println("");
        }
    }

    public InputType[] generateModel1(InputType[] inputValues, int[] dataToUse, double[] outputValues, double outputAvg)
    {
        InputType[] model = inputValues;
        for (InputType model1 : model)
        {
            if (model1.isNumber())
            {
                double xSum = 0;
                double ySum = 0;
                double xxSum = 0;
                double xySum = 0;
                for (int j = 0; j < dataToUse.length; j++)
                {
                    xSum += model1.getValues()[dataToUse[j]];
                    ySum += outputValues[dataToUse[j]];
                    xxSum += model1.getValues()[dataToUse[j]] * model1.getValues()[dataToUse[j]];
                    xySum += model1.getValues()[dataToUse[j]] * outputValues[dataToUse[j]];
                }
                model1.setLinearConst((dataToUse.length * xySum - xSum * ySum) / (dataToUse.length * xxSum - xSum * xSum));
            } else
            {
                double[] valueImpacts = new double[model1.getPosValues().size()];
                for (int j = 0; j < valueImpacts.length; j++)
                {
                    double totalValue = 0;
                    int numValuesUsed = 0;
                    for (int k = 0; k < dataToUse.length; k++)
                    {
                        if (model1.getValues()[dataToUse[k]] == j)
                        {
                            totalValue += outputValues[dataToUse[k]];
                            numValuesUsed++;
                        }
                    }
                    if (numValuesUsed == 0)
                    {
                        valueImpacts[j] = 0;
                    } else
                    {
                        valueImpacts[j] = (totalValue / numValuesUsed) - outputAvg;
                    }
                }
                model1.setValueImpacts(valueImpacts);
            }
        }
        return model;
    }

    public InputType[] generateModel2(InputType[] inputValues, int[] dataToUse, double[] outputValues, double outputAvg, int[] order)
    {
        InputType[] model = inputValues;
        double[] outputValuesCopy = Arrays.copyOf(outputValues, outputValues.length);

        for (int i = 0; i < order.length; i++)
        {
            InputType model1 = model[order[i]];
            //model[order[i]].setImpact(1 - i / 4.0);
            if (model1.isNumber())
            {
                double xSum = 0;
                double ySum = 0;
                double xxSum = 0;
                double xySum = 0;
                for (int j = 0; j < dataToUse.length; j++)
                {
                    xSum += model1.getValues()[dataToUse[j]];
                    ySum += outputValuesCopy[dataToUse[j]];
                    xxSum += model1.getValues()[dataToUse[j]] * model1.getValues()[dataToUse[j]];
                    xySum += model1.getValues()[dataToUse[j]] * outputValuesCopy[dataToUse[j]];
                }
                model1.setLinearConst(((dataToUse.length * xySum) - (xSum * ySum)) / ((dataToUse.length * xxSum) - (xSum * xSum)));
            } else
            {
                double[] valueImpacts = new double[model1.getPosValues().size()];
                for (int j = 0; j < valueImpacts.length; j++)
                {
                    double totalValue = 0;
                    int numValuesUsed = 0;
                    for (int k = 0; k < dataToUse.length; k++)
                    {
                        if (model1.getValues()[dataToUse[k]] == j)
                        {
                            totalValue += outputValuesCopy[dataToUse[k]];
                            numValuesUsed++;
                        }
                    }
                    if (numValuesUsed == 0)
                    {
                        valueImpacts[j] = 0;
                    } else
                    {
                        valueImpacts[j] = (totalValue / numValuesUsed) - outputAvg;
                    }
                }
                model1.setValueImpacts(valueImpacts);
            }
            for (int j = 0; j < dataToUse.length; j++)
            {
                outputValuesCopy[dataToUse[j]] = outputValuesCopy[dataToUse[j]] - model1.getExpectedValue(dataToUse[j]);
            }
        }
        return model;
    }

}
