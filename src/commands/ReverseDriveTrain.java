package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.DriveTrain;

/**
 *
 */
public class ReverseDriveTrain extends Command {

	DriveTrain driveTrain;
	
    public ReverseDriveTrain() 
    {
    	this.driveTrain = RobotManager.GetDriveTrain();
    	requires(this.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	if(this.driveTrain.IsReversed() == true)
    	{
    		this.driveTrain.SetIsReversed(false);
    	}
    	else
    	{
    		this.driveTrain.SetIsReversed(true);
    	}
    }



    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return true;
    }

}
