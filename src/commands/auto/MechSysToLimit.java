package commands.auto;

import systems.RobotManager;
import systems.subsystems.MechSys;
import edu.wpi.first.wpilibj.command.Command;
import sensors.SysPosition;

/**
 *
 */
public class MechSysToLimit extends Command 
{

	private MechSys subsystem;
	private double speed;
	private SysPosition targetPosition;
	
	private double zeroValue = 0;

    public MechSysToLimit(String key, double speed, SysPosition targetPosition) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
    	this.speed = speed;
    	this.targetPosition = targetPosition;
        requires(this.subsystem);
    }
    
    public MechSysToLimit(String key, double speed, double zeroValue, SysPosition targetPosition) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
    	this.speed = speed;
    	this.targetPosition = targetPosition;
        requires(this.subsystem);
        
        this.zeroValue = zeroValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	this.subsystem.Activate(this.speed, this.zeroValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        if(subsystem.GetPosition() != targetPosition)
        {
        	return false;
        }
        else
        {
        	return true;
        }
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	this.subsystem.Activate(this.zeroValue, this.zeroValue);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	this.end();
    }
}
