package systems;

import edu.wpi.first.wpilibj.PIDOutput;

public class ControllerOutput implements PIDOutput
{
		
	private double output;
		
	@Override
	public void pidWrite(double output) 
	{
		this.output = output;
	}
		
	public double GetOutput()
	{
		return this.output;
	}
		
}

