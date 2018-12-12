package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import subsystems.MechDriveTrain;
import subsystems.MechDriveTrain.MechDrivingDirection;

/**
 *
 */
public class MechTimedGyroDrive extends Command {

	private MechDriveTrain driveTrain;
	
	private double speed;
	
	private Gyro gyro;
	private boolean gyroToZero = false;
	private boolean customSetPoint = false;
	private double gyroSetPoint;
	private double gyroKP;
		
	private MechDrivingDirection drivingDirection;
    
    public MechTimedGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double speed, boolean gyroToZero, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, speed, gyroKP, drivingDirection);
    	
    	this.gyroToZero = gyroToZero;

    }
    
    public MechTimedGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double speed, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, speed, gyroKP, drivingDirection);
    	
    	this.gyroToZero = false;
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    }
    
    private void SetControllers(MechDriveTrain driveTrain, Gyro gyro, double speed, double gyroKP, MechDrivingDirection drivingDirection)
    {
    	this.driveTrain = driveTrain;
    	requires(this.driveTrain);
    	
    	this.speed = speed;
    	
    	this.gyro = gyro;
    	this.gyroKP = gyroKP;
    	
    	this.drivingDirection = drivingDirection;
    }

    protected void initialize() 
    {
    	if(!this.customSetPoint)
    	{
    		if(this.gyroToZero)
    		{
    			this.gyroSetPoint = this.gyro.getAngle();
    		}
    		else
    		{
    			this.gyro.reset();
    			this.gyroSetPoint = 0;
    		}
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        double angle;
                
        if(this.driveTrain.IsReversed() == true)
        {
        	angle = (this.gyroSetPoint - gyro.getAngle()) * (-this.gyroKP);
        }
        else
        {
        	angle = (this.gyroSetPoint - gyro.getAngle()) * (this.gyroKP);
        }
        
        if (drivingDirection == MechDrivingDirection.Forward)
        {	
    		this.driveTrain.MechanumDrive(0, this.speed , angle, 0, 1);	
        }
        if (drivingDirection == MechDrivingDirection.Sideways)
        {
    		this.driveTrain.MechanumDrive(-this.speed, 0, angle, 0, 1);
        }
        
    }

    protected boolean isFinished() 
    {

        return this.isTimedOut();

    }

    protected void end() 
    {    	
    	this.driveTrain.StopSystem();
    	    	
    	System.out.println("End Auto Drive");

    }

    protected void interrupted()
    {
    	this.end();
    }
}
