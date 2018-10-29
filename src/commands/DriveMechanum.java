package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.MechDriveTrain;
import triggers.JoystickTools;

/**
 *
 */
public class DriveMechanum extends Command{
	
	private MechDriveTrain driveTrain;
	
    public DriveMechanum()
    {   
    	this.driveTrain = (MechDriveTrain)RobotManager.GetDriveTrain();
    	requires(this.driveTrain);
    }

    
    protected void initialize()
    {
    	
    }

    
    protected void execute()
    {
    	double speedValue = RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetSpeedAxis());
    	double rotateValue =  RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetRotateAxis());
    	double twistValue =  RobotManager.GetDriveJoy().getRawAxis(RobotManager.GetTwistAxis());
    	
    	if(this.driveTrain.GetMinSpeedValue() > 0)
    	{
    		speedValue = JoystickTools.MapAxisMinimum(speedValue, this.driveTrain.GetMinSpeedValue());
    	}
    	if(this.driveTrain.GetMinRotateValue() > 0)
    	{
        	rotateValue = JoystickTools.MapAxisMinimum(rotateValue, this.driveTrain.GetMinRotateValue());
    	}
    	if(this.driveTrain.GetMinTwistValue() > 0)
    	{
        	twistValue = JoystickTools.MapAxisMinimum(twistValue, this.driveTrain.GetMinTwistValue());
    	}
    	
    	this.driveTrain.MechanumDrive(speedValue, rotateValue, twistValue, 0);
    }

    
    protected boolean isFinished()
    {
        return false;
    }

    
    protected void end()
    {
    	this.driveTrain.MechanumDrive(0, 0, 0, 0);
    }

    
    protected void interrupted()
    {
    	end();
    }
}
