/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rootsolver;

/**
 *
 * @author Camden
 */
public class RootSolver
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        RootSolver solver = new RootSolver();
        solver.solve();
    }

    public void solve()
    {
        int root = 2;
        double num = 2;
        int digitsToCalc = 5;

        double answer;
        switch (root)
        {
            case 1 ->
            {
                answer = num;
                System.out.println(answer);
            }
            case 2 ->
            {
                int devisor = 0;
                int newDigit;
                double numCopy = num;
                int remainder;
                int digits = 1;
                int numBeforeDec = (int) num;
                int NumLengthBeforeDec = String.valueOf(numBeforeDec).length();
                int nextTwoDigits;
                if (NumLengthBeforeDec % 2 == 0)
                {
                    nextTwoDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 2));
                    remainder = nextTwoDigits;
                    numCopy -= nextTwoDigits * Math.pow(10, NumLengthBeforeDec - 2);
                } else
                {
                    nextTwoDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 1));
                    remainder = nextTwoDigits;
                    numCopy -= nextTwoDigits * Math.pow(10, NumLengthBeforeDec - 1);
                }
                while (devisor * devisor <= remainder)
                {
                    devisor++;
                }
                devisor--;
                newDigit = devisor;
                answer = newDigit * Math.pow(10, (int) (NumLengthBeforeDec - 1) / 2);
                while (digits < digitsToCalc)
                {
                    remainder = (remainder - devisor * newDigit) * 100;
                    if (NumLengthBeforeDec % 2 == 0)
                    {
                        nextTwoDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 2 * (digits + 1)));
                        remainder += nextTwoDigits;
                        numCopy -= nextTwoDigits * Math.pow(10, NumLengthBeforeDec - 2 * (digits + 1));
                    } else
                    {
                        nextTwoDigits = (int) (numCopy / Math.pow(10, 1 + NumLengthBeforeDec - 2 * (digits + 1)));
                        remainder += nextTwoDigits;
                        numCopy -= nextTwoDigits * Math.pow(10, 1 + NumLengthBeforeDec - 2 * (digits + 1));
                    }
                    devisor = (devisor + newDigit) * 10;
                    newDigit = 0;
                    while (newDigit * (devisor + newDigit) <= remainder)
                    {
                        newDigit++;
                    }
                    newDigit--;
                    devisor += newDigit;
                    answer += newDigit * Math.pow(10, ((int) (NumLengthBeforeDec - 1) / 2) - digits);
                    digits++;

                }
                System.out.println(answer);
            }
            case 3 ->
            {
                if (digitsToCalc > 4)
                {
                    digitsToCalc = 4;
                }
                int devisor;
                int newDigit;
                double numCopy = num;
                int remainder;
                int digits = 1;
                int numBeforeDec = (int) num;
                int NumLengthBeforeDec = String.valueOf(numBeforeDec).length();
                int nextDigits;
                switch (NumLengthBeforeDec % 3)
                {
                    case 0 ->
                    {
                        nextDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 4));
                        numCopy -= nextDigits * Math.pow(10, NumLengthBeforeDec - 4);
                    }
                    case 1 ->
                    {
                        nextDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 3));
                        numCopy -= nextDigits * Math.pow(10, NumLengthBeforeDec - 3);
                    }
                    default ->
                    {
                        nextDigits = (int) (numCopy / Math.pow(10, NumLengthBeforeDec - 2));
                        numCopy -= nextDigits * Math.pow(10, NumLengthBeforeDec - 2);
                    }
                }
                remainder = nextDigits;
                newDigit = 0;
                while (newDigit * newDigit * newDigit <= remainder)
                {
                    newDigit++;
                }
                newDigit--;
                devisor = newDigit;
                answer = newDigit * Math.pow(10, (int) ((NumLengthBeforeDec - 2) / 3)); // check this line 2 might be 1
                remainder = (remainder - newDigit * newDigit * newDigit) * 1000;
                while (digits < digitsToCalc)
                {
                    switch (NumLengthBeforeDec % 3)
                    {
                        case 0 ->
                        {
                            nextDigits = (int) (numCopy / Math.pow(10, -1 + NumLengthBeforeDec - 3 * (digits + 1)));
                            remainder += nextDigits;
                            numCopy -= nextDigits * Math.pow(10, -1 + NumLengthBeforeDec - 3 * (digits + 1));
                        }
                        case 1 ->
                        {
                            nextDigits = (int) (numCopy / Math.pow(10, 0 + NumLengthBeforeDec - 3 * (digits + 1)));
                            remainder += nextDigits;
                            numCopy -= nextDigits * Math.pow(10, 0 + NumLengthBeforeDec - 3 * (digits + 1));
                        }
                        default ->
                        {
                            nextDigits = (int) (numCopy / Math.pow(10, 1 + NumLengthBeforeDec - 3 * (digits + 1)));
                            remainder += nextDigits;
                            numCopy -= nextDigits * Math.pow(10, 1 + NumLengthBeforeDec - 3 * (digits + 1));
                        }
                    }
                    newDigit = 0;

                    while (newDigit * (newDigit * newDigit + 30 * newDigit * devisor + 300 * devisor * devisor) <= remainder)
                    {
                        newDigit++;
                    }
                    newDigit--;

                    remainder = (remainder - (newDigit * (newDigit * newDigit + 30 * newDigit * devisor + 300 * devisor * devisor))) * 1000;
                    devisor = devisor * 10 + newDigit;
                    answer += newDigit * Math.pow(10, ((int) (NumLengthBeforeDec - 1) / 3) - digits);
                    digits++;

                }
                System.out.println(answer);
            }
            default ->
            {
            }
        }
    }
}
