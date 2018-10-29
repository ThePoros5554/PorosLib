package commands;

import java.util.List;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.MechSys;

/**
 *
 */
public class SetAllLimits extends Command 
{
	
	private boolean setLimits;
	
    public SetAllLimits(boolean setLimits) 
    {
    	this.setLimits = setLimits;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	List<MechSys> systems = RobotManager.GetMechSystems();
    	
    	for(MechSys sys : systems)
    	{
    		sys.SetIsLimited(this.setLimits);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
