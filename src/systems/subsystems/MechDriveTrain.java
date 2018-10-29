package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.MechaumDriver;
import systems.RobotManager;

/**
 *
 */
public class MechDriveTrain extends DriveTrain implements PidActionSubsys {

	private MechaumDriver driver;
	private MechDriveTypes drivingType;

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
    
    public MechDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, MechDriveTypes drivingType)
    {
    	this.driver = new MechaumDriver(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    	this.drivingType = drivingType;
    }
    
    public MechDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, MechDriveTypes drivingType, boolean isReversed)
    {
    	this.driver = new MechaumDriver(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    	this.SetIsReversed(isReversed);
    	this.drivingType = drivingType;
    }

	
	@Override
    public void initDefaultCommand() 
	{    
    	if(this.drivingType == MechDriveTypes.CartesianDrive)
    	{
    		setDefaultCommand(new commands.DriveMechanum());
    	}
    }
	
    public void MechanumDrive(double speed, double rotate, double twist, double gyroAngle)
    {
    	if(this.IsRanged())
    	{
    		this.driver.setMaxOutput(RobotManager.GetSliderValue());
    		
    	}
    	else
    	{
    		this.driver.setMaxOutput(this.GetMaxOutput());
    	}
    	
    	if(this.IsReversed())
    	{
    		driver.driveCartesian(-speed, -rotate, -twist, gyroAngle);

    	}
    	else
    	{
    		driver.driveCartesian(-speed, rotate, -twist, gyroAngle);
    	}
    }
    
    public void MecanumDrive(double speed, double rotate, double twist)
    {
    	this.MechanumDrive(speed, rotate, twist, 0);
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
	
    public enum MechDriveTypes
    {
    	CartesianDrive,
    	PolarDrive;
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

