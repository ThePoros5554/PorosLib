package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.DiffDriveTrain;
import triggers.JoystickTools;

/**
 *
 */
public class TankDrive extends Command
{

	private DiffDriveTrain driveTrain;
	
    public TankDrive()
    {
    	this.driveTrain = (DiffDriveTrain)RobotManager.GetDriveTrain();
        requires(this.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	double leftValue = RobotManager.GetDriveJoy2().getRawAxis(RobotManager.GetSpeedAxis());
    	double rightValue = RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetSpeedAxis());
    	
    	if (this.driveTrain.GetMinSpeedValue() > 0)
    	{
    		leftValue = JoystickTools.MapAxisMinimum(leftValue, this.driveTrain.GetMinSpeedValue());
    		rightValue = JoystickTools.MapAxisMinimum(leftValue, this.driveTrain.GetMinSpeedValue());
    	}

    	this.driveTrain.TankDrive(leftValue, rightValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	this.driveTrain.TankDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
