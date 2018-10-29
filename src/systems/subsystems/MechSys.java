package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import sensors.LimitSensor;
import sensors.SysPosition;

/**
 *
 */
public class MechSys extends Subsystem implements PidActionSubsys {

	private SpeedController motor;
	private SpeedController motor2;
	
	private LimitSensor limitSwitch;
	private boolean isLimited = false;

	private boolean isInverted;
	private boolean isLookedReversed = false;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public MechSys(int port)
	{
		this.motor = new Talon(port);
	}
	
	public MechSys(int port1, int port2)
	{
		this.motor = new Talon(port1);
		this.motor2 = new Talon(port2);
		
		this.isInverted = false;
	}
	
	public MechSys(int port1, int port2, boolean inverted)
	{
		this.motor = new Talon(port1);
		this.motor2 = new Talon(port2);
		
		this.isInverted = inverted;
	}
	
	
	public void Activate(double speed)
	{				
		this.Activate(speed, 0);
	}
	
	public void Activate(double speed, double zeroValue)
	{		
		double procSpeed;
		
		if(this.isLookedReversed) // reverses the speed so the limit if's will work properly
		{
			procSpeed = -speed;
		}
		else
		{
			procSpeed = speed;
		}
				
		if(this.limitSwitch != null && this.isLimited)
		{
			if(limitSwitch.GetPosition() != SysPosition.Blocked)
			{
				if(procSpeed < 0)
				{
					if(limitSwitch.GetPosition() == SysPosition.Bottom)
					{
						speed = 0;
					}
				}
				else if(procSpeed > 0)
				{
					if(limitSwitch.GetPosition() == SysPosition.Top)
					{
						speed = 0;
					}
				}
				else
				{
					speed = 0;
				}
			}
			else
			{
				speed = 0;
			}
		}
				
		if(speed == 0)
		{
			speed = zeroValue;
		}
		
		this.motor.set(speed);

		if(motor2 != null)
		{
			if(this.isInverted)
			{
				this.motor2.set(-speed);
			}
			else
			{
				this.motor2.set(speed);
			}
		}
	}
	@Override
	public void StopSystem() 
	{
		this.motor.set(0);
		
		if(motor2 != null)
		{
			this.motor2.set(0);
		}
	}
	
	public void SetIsLookedReversed(boolean isReversed)
	{
		this.isLookedReversed = isReversed;
	}
	
	public boolean GetIsLookedReversed()
	{
		return this.isLookedReversed;
	}

	@Override
	public PIDOutput GetPidOutput() 
	{
		return this::PidWrite;
	}
	
	public SysPosition GetPosition ()
	{
		return limitSwitch.GetPosition();
	}
	
	public void SetLimitSwitch(LimitSensor limitSwitch, boolean isLimited)
	{
		this.limitSwitch = limitSwitch;
		this.isLimited = isLimited;
	}
	
	public void SetLimitSwitch(LimitSensor limitSwitch)
	{
		this.limitSwitch = limitSwitch;
		this.isLimited = true;
	}
	
	private void PidWrite(double output)
	{
		this.motor.pidWrite(output);
		
		if(motor2 != null)
		{
			if(isInverted)
			{
				this.motor2.pidWrite(-output);
			}
			else
			{
				this.motor2.set(output);
			}
		}

	}
	
	public boolean GetIsLimited()
	{
		return this.isLimited;
	}
	
	public void SetIsLimited(boolean isLimited)
	{
		this.isLimited = isLimited;
	}
	
	public void initDefaultCommand() 
	{
    }

	@Override
	public Subsystem GetSubsystem() 
	{
		return this;
	}
}

