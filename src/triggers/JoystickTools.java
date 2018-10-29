package triggers;

public class JoystickTools 
{
	public static double MapAxisMinimum(double value, double newMinValue)
	{
		newMinValue = Math.abs(newMinValue);
		
		int factor;
		if(value > 0)
		{
			factor = 1;
		}
		else
		{
			factor = -1;
		}
		
		value = Math.abs(value);
		
		if (value <= newMinValue && value >= 0)
		{
			return 0;
		}
		else
		{

				
			double a = value - newMinValue;
			double b = a / (1 - newMinValue);
			
			return (b * factor);
			
		}
	}
	
	public static double MapAxisMax(double value, double newMaxValue)
	{
		double ratio = value/1;
		
		return newMaxValue * ratio;
	}
	
	public static double MapRange(double oldMax, double oldMin, double newMin, double newMax, double value)
	{
		double oldRange = (oldMax - oldMin);
		
		double newValue;
				
		if (oldRange == 0)
		{
			newValue = newMin;
		}
		else
		{
			double newRange = (newMax - newMin);  
			newValue = (((value - oldMin) * newRange) / oldRange) + newMin;
		}					
		
		if(newMax < 0)
		{
			newValue *= -1;
		}
		
		return newValue;
	}
}

