package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.MechaumDriver;

/**
 *
 */
public class MechDriveTrain extends DriveTrain implements PidActionSubsys {

	private MechaumDriver driver;

	private PIDOutput pidOutput;
	
	private double minTwistValue = 0;
	
    public MechDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor)
    {
    	this.driver = new MechaumDriver(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }
    
    public MechDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, boolean isReversed)
    {
    	this.driver = new MechaumDriver(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    	this.SetIsReversed(isReversed);
    }

    public void MechanumDrive(double sidewaysSpeed, double forwardSpeed, double zRotation, double gyroAngle, double maxOutput)
    {
    	this.driver.setMaxOutput(maxOutput);
    	
    	if(this.IsReversed())
    	{
    		driver.driveCartesian(-sidewaysSpeed, -forwardSpeed, -zRotation, gyroAngle);

    	}
    	else
    	{
    		driver.driveCartesian(-sidewaysSpeed, forwardSpeed, -zRotation, gyroAngle);
    	}
    }

	@Override
	public void StopSystem() 
	{
		driver.stopMotor();
	}

	private void PidDrive(double output)
	{
		driver.PidDrive(output);
	}
	
	private void PidTwist(double output)
	{
		driver.PidTwist(output);

	}
	
	private void PidTurnInPlace(double output)
	{
		if(this.IsReversed())
		{
			driver.PidTurnInPlace(output);
		}
		else
		{
			driver.PidTurnInPlace(-output);
		}
	}
	
    public void SetPIDOutput(MechPidAction actionType)
    {
    	if(actionType == MechPidAction.PidTurnInPlace)
    	{
    		this.pidOutput = this::PidTurnInPlace;
    	}
    	else if(actionType == MechPidAction.PidTwist)
    	{
    		this.pidOutput = this::PidTwist;
    	}
    	else if(actionType == MechPidAction.PidDrive)
    	{
    		this.pidOutput = this::PidDrive;
    	}
    }
    
    public void SetMinTwistValue(double minTwistValue)
    {
    	this.minTwistValue = minTwistValue;
    }
    
    public double GetMinTwistValue()
    {
    	return this.minTwistValue;
    }
	
	@Override
	public PIDOutput GetPidOutput() 
	{
    	return this.pidOutput;
	}

	@Override
	public Subsystem GetSubsystem() 
	{
		return this;
	}
	
    public enum MechDrivingDirection
    {
    	Forward,
    	Sideways
    }

    public enum MechPidAction
    {
    	PidDrive,
    	PidTwist,
    	PidTurnInPlace;
    }
}

