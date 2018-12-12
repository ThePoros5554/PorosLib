package commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import subsystems.MechDriveTrain;
import subsystems.MechDriveTrain.MechDrivingDirection;
import util.ControllerOutput;

/**
 *
 */
public class MechDistanceGyroDrive extends Command {

	private MechDriveTrain driveTrain;
	
	private Encoder outputEnc;
	private PIDController controller;
	private ControllerOutput encPIDOutput;
	
	private Encoder fixEnc;
	private double fixKp;

	
	private Gyro gyro;
	private boolean gyroToZero = false;
	private boolean customSetPoint = false;
	private double gyroSetPoint;
	private double gyroKP;
	
	private boolean isTimed = false;
	
	private MechDrivingDirection drivingDirection;

	
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, boolean gyroToZero, double gyroKP, MechDrivingDirection drivingDirection) 
    {

    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, null, 0, gyroKP, drivingDirection);
    	
    	this.gyroToZero = gyroToZero;

    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, boolean gyroToZero, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, null, 0, gyroKP, drivingDirection);
    	
    	this.gyroToZero = gyroToZero;

    	
    	this.isTimed = true;
    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection) 
    {

    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, null, 0, gyroKP, drivingDirection);
    	
    	this.gyroToZero = false;
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, null, 0, gyroKP, drivingDirection);
    	
    	this.gyroToZero = false;
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    	
    	this.isTimed = true;
    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, Encoder fixEnc, double fixKp, boolean gyroToZero, double gyroKP, MechDrivingDirection drivingDirection) 
    {
    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, fixEnc, fixKp, gyroKP, drivingDirection);
    	
    	this.gyroToZero = gyroToZero;

    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, Encoder fixEnc, double fixKp, boolean gyroToZero, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, fixEnc, fixKp, gyroKP, drivingDirection);
    	
    	this.gyroToZero = gyroToZero;

    	
    	this.isTimed = true;
    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, Encoder fixEnc, double fixKp, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection) 
    {

    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, fixEnc, fixKp, gyroKP, drivingDirection);
    	
    	this.gyroToZero = false;
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    }
    
    public MechDistanceGyroDrive(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, Encoder fixEnc, double fixKp, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(driveTrain, gyro, kP, kI, kD, percentTolerance, setPoint, outputEnc, null, 0, gyroKP, drivingDirection);
    	
    	this.gyroToZero = false;
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    	
    	this.isTimed = true;
    }
    
    private void SetControllers(MechDriveTrain driveTrain, Gyro gyro, double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder outputEnc, Encoder fixEnc, double fixKp, double gyroKP, MechDrivingDirection drivingDirection)
    {
    	this.driveTrain = driveTrain;
    	requires(this.driveTrain);
    	
    	this.outputEnc = outputEnc;
    	this.outputEnc.setPIDSourceType(PIDSourceType.kDisplacement);
    	this.encPIDOutput = new ControllerOutput(false);
    	this.controller = new PIDController(kP, kI, kD, outputEnc, this.encPIDOutput);
    	
    	this.fixEnc = fixEnc;
    	this.fixKp = fixKp;
    	
    	if(setPoint > 0)
    	{
    		this.controller.setInputRange(0, setPoint);
    	}
    	else
    	{
    		this.controller.setInputRange(setPoint, 0);
    	}
    	
    	this.controller.setPercentTolerance(percentTolerance);
    	this.controller.setSetpoint(setPoint);
    	
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
    	
    	this.outputEnc.reset();
    	
    	if(this.fixEnc != null)
    	{
    		this.fixEnc.reset();
    	}
    	
    	this.controller.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        double angle;
        double output;
                
        if(this.driveTrain.IsReversed() == true)
        {
        	angle = (this.gyroSetPoint - gyro.getAngle()) * (-this.gyroKP);
        	output = this.encPIDOutput.GetOutputValue();
        }
        else
        {
        	angle = (this.gyroSetPoint - gyro.getAngle()) * (this.gyroKP);
        	output = -this.encPIDOutput.GetOutputValue();
        }
        
        double fixError = 0;
        
        if(this.fixEnc != null)
        {
            if(this.driveTrain.IsReversed() == true)
            {
            	fixError = (this.fixEnc.getDistance()) * (-this.fixKp);
            }
            else
            {
            	fixError = (this.fixEnc.getDistance()) * (this.fixKp);
            }
        }
        
        if (drivingDirection == MechDrivingDirection.Forward)
        {	
    		this.driveTrain.MechanumDrive(fixError, output , angle,0, 1);	
        }
        if (drivingDirection == MechDrivingDirection.Sideways)
        {
    		this.driveTrain.MechanumDrive(-output, fixError, angle,0, 1);
        }
        
    }

    protected boolean isFinished() 
    {
    	if(isTimed)
    	{
        	return (this.controller.onTarget() || this.isTimedOut());
    	}
    	else
    	{
    		return this.controller.onTarget();
    	}
    }

    protected void end() 
    {
    	this.controller.reset();
    	
		this.driveTrain.MechanumDrive(0, 0, 0, 0, 1);
    	this.driveTrain.StopSystem();
    	    	
    	System.out.println("End Auto Drive");

    }

    protected void interrupted()
    {
    	this.end();
    }
}
