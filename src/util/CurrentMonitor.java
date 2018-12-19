package util;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class CurrentMonitor 
{
	private PowerDistributionPanel PDP;
	private int[] motorPorts;
	
	private double stallCurrent = 0;
	private boolean[] stalling;
	private boolean isStalling = false;
	
	public CurrentMonitor(PowerDistributionPanel PDP, int[] motorPorts)
	{
		this.PDP = PDP;
		this.motorPorts = motorPorts;
	}
	
	public CurrentValue[] GetCurrent()
	{
		CurrentValue[] values = new CurrentValue[motorPorts.length];
		
		for (int i = 0; i < motorPorts.length; i++) 
		{
			values[i] = new  CurrentValue(motorPorts[i], PDP.getCurrent(motorPorts[i]));
		}
		
		return values;
	}
	
	public void SetStallCurrent(double stallCurrent)
	{
		this.stallCurrent = stallCurrent;
		this.stalling = new boolean[this.motorPorts.length];
	}
	
	public double GetStallCurrent()
	{
		return this.stallCurrent;
	}
	
	private void UpdateStallingInfo()
	{
		if(this.stalling != null)
		{
			this.isStalling = false;

			for (int i = 0; i < motorPorts.length; i++) 
			{
				
				if (this.PDP.getCurrent(this.motorPorts[i]) >= this.stallCurrent) 
				{
					this.isStalling = true;
					this.stalling[i] = true;
				}
			}
			
		}
	}
	
	public boolean IsStalling()
	{
		this.UpdateStallingInfo();
		
		return this.isStalling;
	}
	
	public boolean[] Stalling()
	{
		this.UpdateStallingInfo();

		return this.stalling;
	}
	
}
