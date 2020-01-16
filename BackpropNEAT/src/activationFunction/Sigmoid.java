/**Sigmoid.java
 * 
//TODO
 *
 */
package activationFunction;

/**Sigmoid.java
 * 
//TODO
 *
 */
public class Sigmoid implements ActivationFunction{

	@Override
	public double activate(double value) {
		return 1/(1+Math.pow(Math.E, -value));
	}

	@Override
	public double applyDerivative(double value) {
		double x = activate(value); 
		return x * (1 - x);
	}
	

}
