package subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.DifferentialDriver;

/**
 *
 */
public class DiffDriveTrain extends DriveTrain implements PidActionSubsys
{
	private DifferentialDriver driver;
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
    
    public void SetIsSquared (boolean isSquared)
    {
    	this.isSquared = isSquared;
    }
    
    public boolean GetIsSquared ()
    {
    	return this.isSquared;
    }
    
    public void PidTurnInPlace(double output)
    {
    	driver.PidTurnInPlace(output);
    }
    
    public void PidDrive(double output)
    {
    	driver.PidDrive(output);
    }
    
    
	@Override
	public void StopSystem()
	{
		this.driver.stopMotor();
	}
    

	@Override
	public Subsystem GetSubsystem() 
	{
		return this;
	}
	

}


