import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial{
    double [] coeff;
    int [] exp;

    public Polynomial(){
        coeff = new double[0];
        exp = new int[0];
    }

    public Polynomial(double[] coeff, int[] exp) {
        if (coeff == null || exp == null || coeff.length != exp.length) {
            this.coeff = new double[0];
            this.exp = new int[0];
            return;
        }

        int notZero = 0;
        for (int i = 0; i < coeff.length; i++) {
            if (coeff[i] != 0){
                notZero++;
            }
        }

        this.coeff = new double[notZero];
        this.exp = new int[notZero];
        int idx = 0;
        for (int i = 0; i < coeff.length; i++) {
            if (coeff[i] != 0) {
                this.coeff[idx] = coeff[i];
                this.exp[idx] = exp[i];
                idx++;
            }
        }
    }

    public Polynomial(double[] coefficientsArray) {
        int notZero = 0;
        for(double c : coefficientsArray){
            if(c != 0){
                notZero++;
            }
        }

        if (notZero == 0) {
            this.coeff = new double[0];
            this.exp = new int[0];
        } else {
            this.coeff = new double[notZero];
            this.exp = new int[notZero];
            int idx = 0;
            for (int i = 0; i < coefficientsArray.length; i++) {
                if (coefficientsArray[i] != 0) {
                    this.coeff[idx] = coefficientsArray[i];
                    this.exp[idx] = i;
                    idx++;
                }
            }
        }
    }

    public Polynomial(File file){
        String firstLine = null;
        try (Scanner myReader = new Scanner(file)) {
            if (myReader.hasNextLine()) {
                firstLine = myReader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        }

        if (firstLine == null) {
            this.coeff = new double[0];
            this.exp = new int[0];
            return;
        }
        String[] terms = firstLine.split("(?=-)|\\+");

        double[] coeff = new double[terms.length];
        int[] exp = new int[terms.length];
        int k = 0;

        for (String term : terms) {
            String[] current_term = term.trim().split("x");
            if (current_term.length == 1) {
                coeff[k] = Double.parseDouble(current_term[0].trim());
                exp[k] = 0;
            } else if (current_term.length == 2) {
                double coefficientValue;
                if (current_term[0].length() == 0) {
                    if (term.startsWith("-")) {
                        coefficientValue = -1;
                    } else {
                        coefficientValue = 1;
                    }
                } else {
                    coefficientValue = Double.parseDouble(current_term[0].trim());
                }
                coeff[k] = coefficientValue;

                int exponentValue;
                if (current_term[1].length() == 0) {
                    exponentValue = 1;
                } else {
                    exponentValue = Integer.parseInt(current_term[1].trim());
                }
                exp[k] = exponentValue;
            }
            k++;
        }

        // Filter out zero coefficients
        int notZero = 0;
        for (int i = 0; i < k; i++) {
            if (coeff[i] != 0) notZero++;
        }
        this.coeff = new double[notZero];
        this.exp = new int[notZero];
        int idx = 0;
        for (int i = 0; i < k; i++) {
            if (coeff[i] != 0) {
                this.coeff[idx] = coeff[i];
                this.exp[idx] = exp[i];
                idx++;
            }
        }

    }

    public Polynomial add(Polynomial p1) {
        if (p1 == null) {
            return new Polynomial(this.coeff, this.exp);
        }

        int i = 0, j = 0, k = 0;
        double[] tempCoeff = new double[this.coeff.length + p1.coeff.length];
        int[] tempExp = new int[this.coeff.length + p1.coeff.length];

        while (i < this.coeff.length || j < p1.coeff.length) {
            double sumCoeff = 0;
            int exp = 0;

            if (i < this.coeff.length && (j >= p1.coeff.length || this.exp[i] < p1.exp[j])) {
                sumCoeff = this.coeff[i];
                exp = this.exp[i];
                i++;
            } else if (j < p1.coeff.length && (i >= this.coeff.length || this.exp[i] > p1.exp[j])) {
                sumCoeff = p1.coeff[j];
                exp = p1.exp[j];
                j++;
            } else if (i < this.coeff.length && j < p1.coeff.length) {
                sumCoeff = this.coeff[i] + p1.coeff[j];
                exp = this.exp[i];
                i++;
                j++;
            }

            if (sumCoeff != 0) {
                tempCoeff[k] = sumCoeff;
                tempExp[k] = exp;
                k++;
            }
        }

        double[] newCoeff = new double[k];
        int[] newExp = new int[k];
        System.arraycopy(tempCoeff, 0, newCoeff, 0, k);
        System.arraycopy(tempExp, 0, newExp, 0, k);

        return new Polynomial(newCoeff, newExp);
    }

    public double evaluate(double x){
        if(this.coeff.length == 0) return 0;
        double total = 0;
        for(int i = 0; i < coeff.length; i++){
            total += this.coeff[i] * Math.pow(x, this.exp[i]);
        }
        return total;
    }

    public boolean hasRoot(double root){
	    return this.evaluate(root) == 0;
    }

    public Polynomial multiply(Polynomial p1) {
        if (p1 == null) {
            return new Polynomial();
        }
        int maxTerms = this.coeff.length * p1.coeff.length;
        double[] tempCoeff = new double[maxTerms];
        int[] tempExp = new int[maxTerms];
        int k = 0;

        for (int i = 0; i < this.coeff.length; i++) {
            for (int j = 0; j < p1.coeff.length; j++) {
                tempCoeff[k] = this.coeff[i] * p1.coeff[j];
                tempExp[k] = this.exp[i] + p1.exp[j];
                k++;
            }
        }

        int maxExp = 0;
        for (int i = 0; i < k; i++) {
            if (tempExp[i] > maxExp) maxExp = tempExp[i];
        }
        double[] coeffSum = new double[maxExp + 1];
        for (int i = 0; i < k; i++) {
            coeffSum[tempExp[i]] += tempCoeff[i];
        }

        int notZero = 0;
        for (double c : coeffSum) {
            if (c != 0) notZero++;
        }
        double[] finalCoeff = new double[notZero];
        int[] finalExp = new int[notZero];
        int idx = 0;
        for (int i = 0; i < coeffSum.length; i++) {
            if (coeffSum[i] != 0) {
                finalCoeff[idx] = coeffSum[i];
                finalExp[idx] = i;
                idx++;
            }
        }
        return new Polynomial(finalCoeff, finalExp);
    }

    public void saveToFile(String fileName) {
        StringBuilder poly = new StringBuilder();
        for (int i = 0; i < this.coeff.length; i++) {
            if (i == 0) {
                poly.append(this.coeff[i]);
            } else if (this.coeff[i] >= 0) {
                poly.append("+").append(this.coeff[i]);
            } else {
                poly.append(this.coeff[i]);
            }
            if (this.exp[i] > 0) {
                poly.append("x").append(this.exp[i]);
            }
        }
        try (PrintStream output = new PrintStream(fileName)) {
            output.println(poly.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}