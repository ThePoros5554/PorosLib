package systems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class CurrentSafety 
{
	private SafeSubsystem subsystem;
	
	private PowerDistributionPanel PDP;
	private int[] motorPorts;
	private boolean[] stalling;
	
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
		this.stalling = new boolean[this.motorPorts.length];
		this.maxAmp = maxAmp;
		this.dangerTime = dangerTime;
		this.sleepTime = sleepTimer;
	}

	public void calculate() 
	{
		if (!subsystem.IsDisabled()) 
		{
			boolean isStalling = false;
			
			for (int i = 0; i < motorPorts.length; i++) 
			{
				if (this.PDP.getCurrent(this.motorPorts[i]) >= this.maxAmp) 
				{
					isStalling = true;
					this.stalling[i] = true;
				}
			}
			
			if (isStalling) 
			{
				if (!this.isTimerEnabled) 
				{
					this.dangerTimer.reset();
					this.dangerTimer.start();
					this.isTimerEnabled = true;
				}
					
				if (this.dangerTimer.hasPeriodPassed(this.dangerTime)) 
				{ 					
					this.SendStallingReport();
					this.subsystem.Disable();
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
		else if (this.WakeUp()) 
		{
			subsystem.Enable();
		}
		
	}
	
	private void SendStallingReport()
	{
		for (int i = 0; i < stalling.length; i++) 
		{
			if(stalling[i])
			{
				int stallingPort = this.motorPorts[i];
				DriverStation.reportError(String.format("It appears that the motor connected to port %s is stalling... "
						+ "\nPutting motor in sleep mode to avoid additional damage.", stallingPort), false);
						
			}
		}
	}
	
	public boolean[] GetIsStalling()
	{
		return this.stalling;
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
