package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.DifferentialDriver;
import systems.RobotManager;

/**
 *
 */
public class DiffDriveTrain extends DriveTrain implements PidActionSubsys
{
	private DifferentialDriver driver;
	private DiffDriveTypes drivingType;
	private PIDOutput pidOutput;
	private boolean isSquared;
	
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    }
    
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController, boolean isReversed)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    	this.SetIsReversed(isReversed);; 
    }
    
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController, DiffDriveTypes drivingType)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    	this.drivingType = drivingType;
    }
    
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController, DiffDriveTypes drivingType, boolean isReversed)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    	this.SetIsReversed(isReversed);; 
    	this.drivingType = drivingType;
    }
    

    public void SetDriveType(DiffDriveTypes drivingType)
    {
    	this.drivingType = drivingType;
    }
   
    
    public void ArcadeDrive(double speed, double rotate)
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
    		driver.arcadeDrive(-speed,-rotate, this.isSquared);

    	}
    	else
    	{
    		driver.arcadeDrive(speed, rotate, this.isSquared);
    	}
    }
    
    public void TankDrive(double leftSpeed ,double rightSpeed)
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
        	driver.tankDrive(-leftSpeed , -rightSpeed, this.isSquared);

    	}
    	else
    	{
        	driver.tankDrive(leftSpeed , rightSpeed, this.isSquared);
    	}
    }
    
    
    @Override
    public void initDefaultCommand() 
    {
    	if(this.drivingType == DiffDriveTypes.ArcadeDrive)
    	{
    		setDefaultCommand(new commands.ArcadeDrive());
    	}
    	else if(this.drivingType == DiffDriveTypes.TankDrive)
    	{
    		setDefaultCommand(new commands.TankDrive());
    	}
    	else
    	{
    	}
    }
    
    public void SetPIDOutput(DiffPidAction actionType)
    {
    	if(actionType == DiffPidAction.PidTurnInPlace)
    	{
    		this.pidOutput = this::PidTurnInPlace;
    	}
    	else if(actionType == DiffPidAction.PidDrive)
    	{
    		this.pidOutput = this::PidDrive;
    	}
    }
    
    public void SetIsSquared (boolean isSquared)
    {
    	this.isSquared = isSquared;
    }
    
    public boolean GetIsSquared ()
    {
    	return this.isSquared;
    }
    
    private void PidTurnInPlace(double output)
    {
    	driver.PidTurnInPlace(output);
    }
    
    private void PidDrive(double output)
    {
    	driver.PidDrive(output);
    }
    
    
	@Override
	public void StopSystem()
	{
		this.driver.stopMotor();
	}
    
	@Override
	public PIDOutput GetPidOutput()
	{
		return this.pidOutput;
	}
	
    public enum DiffDriveTypes
    {
    	ArcadeDrive,
    	TankDrive;
    }

    public enum DiffPidAction
    {
    	PidTurnInPlace,
    	PidDrive;
    }

	@Override
	public Subsystem GetSubsystem() 
	{
		return this;
	}
	

}



