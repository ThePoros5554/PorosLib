package triggers;

import edu.wpi.first.wpilibj.Joystick;

public class SmartJoystick extends Joystick
{

	private boolean[] disabledAxis;
	
	public SmartJoystick(int port) 
	{
		super(port);
		
		this.disabledAxis = new boolean[this.getAxisCount()];
		
		for (int i = 0; i < this.disabledAxis.length; i++)
		{
			this.disabledAxis[i] = false;
		}
	}
	
	public void DisableAxis(int axis, boolean isDisabled)
	{
		if(axis < this.disabledAxis.length)
		{
			this.disabledAxis[axis] = isDisabled;
		}
	}
	
	public boolean GetIsDisabled(int axis)
	{
		if (axis < this.disabledAxis.length)
		{

			return this.disabledAxis[axis];

		}
		else
		{
			return false;
		}
	}
	
	@Override
	public double getRawAxis(int axis)
	{
		if (this.disabledAxis[axis] == false)
		{
			return super.getRawAxis(axis);
		}
		else
		{
			return 0;
		}
	}
	
	

}
