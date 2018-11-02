package util;

public class MathHelper 
{	
	public static double MapRange(double oldMin, double oldMax, double newMin , double newMax, double value)
	{
		double newSlope = (newMax - newMin) / (oldMax - oldMin);
		double newB = newMax - (newSlope*oldMax);

		double output = (newSlope * value) + newB;
		
		return output;
	}
}

