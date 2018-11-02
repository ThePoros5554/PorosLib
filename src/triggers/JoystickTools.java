package triggers;

public class JoystickTools 
{	
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

