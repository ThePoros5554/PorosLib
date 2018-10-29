package commands.auto;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import systems.ControllerOutput;
import systems.RobotManager;
import systems.subsystems.DiffDriveTrain;
import systems.subsystems.MechDriveTrain.MechDrivingDirection;

/**
 *
 */
public class DiffDistanceGyroDrive extends Command {

	private DiffDriveTrain driveTrain;
	
	private Encoder enc;
	private PIDController controller;
	private ControllerOutput encPIDOutput;
	
	private Gyro gyro;
	private boolean gyroToZero = false;
	private boolean customSetPoint = false;
	private double gyroSetPoint;
	private double gyroKP;
	
	private boolean isTimed = false;
	
    public DiffDistanceGyroDrive(double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder enc, boolean gyroToZero, double gyroKP) 
    {

    	this.SetControllers(kP, kI, kD, percentTolerance, setPoint, enc, gyroToZero, gyroKP);
    }
    
    public DiffDistanceGyroDrive(double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder enc, boolean gyroToZero, double gyroKP, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(kP, kI, kD, percentTolerance, setPoint, enc ,gyroToZero, gyroKP);
    	
    	this.isTimed = true;
    }
    
    public DiffDistanceGyroDrive(double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder enc, double gyroSetPoint, double gyroKP) 
    {

    	this.SetControllers(kP, kI, kD, percentTolerance, setPoint, enc, gyroToZero, gyroKP);
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    }
    
    public DiffDistanceGyroDrive(double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder enc, double gyroSetPoint, double gyroKP, MechDrivingDirection drivingDirection, double timeout) 
    {
    	super(timeout);
    	this.SetControllers(kP, kI, kD, percentTolerance, setPoint, enc ,gyroToZero, gyroKP);
    	
    	this.customSetPoint = true;
    	this.gyroSetPoint = gyroSetPoint;
    	
    	this.isTimed = true;
    }
    
    private void SetControllers(double kP, double kI, double kD,  double percentTolerance, double setPoint, Encoder enc, boolean gyroToZero, double gyroKP)
    {
    	this.driveTrain = (DiffDriveTrain)RobotManager.GetDriveTrain();
    	requires(this.driveTrain);
    	
    	this.enc = enc;
    	this.enc.setPIDSourceType(PIDSourceType.kDisplacement);
    	this.encPIDOutput = new ControllerOutput();
    	this.controller = new PIDController(kP, kI, kD, enc, this.encPIDOutput);
    	
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
    	
    	this.gyro = RobotManager.GetGyro();
    	this.gyroToZero = gyroToZero;
    	this.gyroKP = gyroKP;
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
    	this.enc.reset();
    	
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
        	output = this.encPIDOutput.GetOutput();
        }
        else
        {
        	angle = (this.gyroSetPoint - gyro.getAngle()) * (this.gyroKP);
        	output = -this.encPIDOutput.GetOutput();
        }

    	this.driveTrain.ArcadeDrive(output , angle);	
        
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
    	
    	this.driveTrain.StopSystem();
    	
    	System.out.println(enc.getDistance());
    	
    	System.out.println("End Auto Drive");

    }

    protected void interrupted()
    {
    	this.end();
    }
}
