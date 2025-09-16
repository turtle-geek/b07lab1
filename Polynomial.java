import java.lang.Math;

public class Polynomial{
    double [] coeff;
    public Polynomial(){
        coeff = new double[1];
	coeff[0] = 0;   
    }

    public Polynomial(double[] coeff){
	this.coeff = new double[coeff.length];
	for(int i = 0; i < coeff.length; i++){
            this.coeff[i] = coeff[i];
	}
    }

    public Polynomial add(Polynomial p1){
	if(p1 == null){
            return new Polynomial(this.coeff);
	}
	int maxLength = Math.max(this.coeff.length, p1.coeff.length);
	double[] sum = new double[maxLength];
	for(int i = 0; i < maxLength; i++){
            double thisCoeff = 0;
            if (i < this.coeff.length) {
                thisCoeff = this.coeff[i];
            }
            double p1Coeff = 0;
            if (i < p1.coeff.length) {
                p1Coeff = p1.coeff[i];
            }
            sum[i] = thisCoeff + p1Coeff;
	}
	return new Polynomial(sum);
    }

    public double evaluate(double x){
	double total = 0;
	for(int i = 0; i < coeff.length; i++){
	    total += this.coeff[i] * Math.pow(x, i);
	}
	return total;
    }

    public boolean hasRoot(double root){
	return this.evaluate(root) == 0;
    }
}