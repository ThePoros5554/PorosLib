package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.MechSys;

/**
 *
 */
public class TimedMechSys extends Command {

	private MechSys system;
	private double speed;
	
	private double zeroValue = 0;
	
    public TimedMechSys(String key, double speed, double timeout) 
    {
        super(timeout);
        
        this.system = ((MechSys)RobotManager.GetSubsystem(key));
        this.speed = speed;
        
        requires(this.system);
    }
    
    public TimedMechSys(String key, double speed, double zeroValue, double timeout) 
    {
        super(timeout);
        
        this.system = ((MechSys)RobotManager.GetSubsystem(key));
        this.speed = speed;
        
        requires(this.system);
        
        this.zeroValue = zeroValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	this.system.Activate(this.speed, zeroValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	this.system.Activate(0, zeroValue);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
