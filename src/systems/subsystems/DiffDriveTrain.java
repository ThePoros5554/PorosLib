package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.DifferentialDriver;

/**
 *
 */
public class DiffDriveTrain extends DriveTrain implements PidActionSubsys
{
	private DifferentialDriver driver;
	private PIDOutput pidOutput;
	private boolean isSquared;
	
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    }
    
    public DiffDriveTrain(SpeedController leftController, SpeedController rightController, boolean isReversed)
    {
    	driver = new DifferentialDriver(leftController , rightController);
    	this.SetIsReversed(isReversed);
    }   
    
    public void ArcadeDrive(double speed, double rotate, double maxOutput)
    {
    	this.driver.setMaxOutput(maxOutput);
    	
    	if(this.IsReversed())
    	{
    		driver.arcadeDrive(-speed,-rotate, this.isSquared);
    	}
    	else
    	{
    		driver.arcadeDrive(speed, rotate, this.isSquared);
    	}
    }
    
    public void TankDrive(double leftSpeed ,double rightSpeed, double maxOutput)
    {
    	this.driver.setMaxOutput(maxOutput);
    	
    	if(this.IsReversed())
    	{
        	driver.tankDrive(-leftSpeed , -rightSpeed, this.isSquared);
    	}
    	else
    	{
        	driver.tankDrive(leftSpeed , rightSpeed, this.isSquared);
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



