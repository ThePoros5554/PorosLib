package systems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class CurrentSafety 
{
	private SafeSubsystem subsystem;
	
	private PowerDistributionPanel PDP;
	private int[] motorPorts;
	
	private double maxAmp;
	
	private double dangerTime;	
	private Timer dangerTimer = new Timer();
	
	private double sleepTime;
	private Timer sleepTimer = new Timer();
	
	private boolean isTimerEnabled = false;
	
	public CurrentSafety(SafeSubsystem subsystem, PowerDistributionPanel PDP, int[] motorPorts, double maxAmp, double dangerTime, double sleepTimer)
	{
		this.subsystem = subsystem;
		this.PDP = PDP;
		this.motorPorts = motorPorts;
		this.maxAmp = maxAmp;
		this.dangerTime = dangerTime;
		this.sleepTime = sleepTimer;
	}

	public void calculate() 
	{
		if (!subsystem.IsDisabled()) 
		{
			for (int i = 0; i < motorPorts.length; i++) 
			{
				if (this.PDP.getCurrent(this.motorPorts[i]) >= this.maxAmp) 
				{
					if (!this.isTimerEnabled) 
					{
						this.dangerTimer.reset();
						this.dangerTimer.start();
						this.isTimerEnabled = true;
					}
					
					if (this.dangerTimer.hasPeriodPassed(this.dangerTime)) 
					{ 					
						subsystem.Disable();
						this.dangerTimer.stop();
						this.isTimerEnabled = false;
						
						this.sleepTimer.reset();
						this.sleepTimer.start();
					}
				} 
				else 
				{
					dangerTimer.stop();
					dangerTimer.reset();
					isTimerEnabled = false;
					
				}
			}
		}
		else if (this.WakeUp()) 
		{
			subsystem.Enable();
		}
		
	}
	
	private boolean WakeUp()
	{
		if (this.sleepTimer.get() >= this.sleepTime) 
		{
			this.sleepTimer.stop();
			this.sleepTimer.reset();
			return true;
		}
		return false;
	}
		
	
}
