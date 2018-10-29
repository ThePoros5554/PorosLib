package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.DiffDriveTrain;
import triggers.JoystickTools;

/**
 *
 */
public class ArcadeDrive extends Command
{

	private DiffDriveTrain driveTrain;
	
    public ArcadeDrive()
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
    	double speedValue = RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetSpeedAxis());
    	double rotateValue =  RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetRotateAxis());
    	
    	if (this.driveTrain.GetMinSpeedValue() > 0)
    	{
    		speedValue = JoystickTools.MapAxisMinimum(speedValue, this.driveTrain.GetMinSpeedValue());
    	}
    	if (this.driveTrain.GetMinRotateValue() > 0)
    	{
    		rotateValue = JoystickTools.MapAxisMinimum(rotateValue, this.driveTrain.GetMinRotateValue());
    	}
    	
		this.driveTrain.ArcadeDrive(speedValue, rotateValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	this.driveTrain.ArcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
