package commands.auto;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTableInstance;
import systems.subsystems.DriveTrain;
import systems.subsystems.DiffDriveTrain;
import systems.subsystems.MechDriveTrain;


public class ImageAllignment extends Command {

	private double speed;
	private final double kP;
	
	private double error;
	
	private double targX;
	
	private double targArea;
	
	private DriveTrain driveTrain;
	private String driveTrainClass;
	
    public ImageAllignment(DriveTrain driveTrain, double speed, double kP, Point targetPoint, double targArea) 
    {
    	this.driveTrain = driveTrain;   
    	requires(this.driveTrain);
    	this.driveTrainClass = this.driveTrain.getClass().getTypeName();
    	
        this.speed = speed;
        this.kP = kP;
        
        this.targX = targetPoint.x;
        
        this.targArea = targArea;
        

    }
    
    public ImageAllignment(double speed, double kP, double targetX, double targArea) 
    {
        requires(this.driveTrain);
        
        this.speed = speed;
        this.kP = kP;
        
        this.targX = targetX;
        
        this.targArea = targArea;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

	protected void execute() 
    {
    	double objX = NetworkTableInstance.getDefault().getTable("VISION").getEntry("OBJX").getDouble(-100);
    	
    	if(objX != -100)
    	{
    		this.error = (NetworkTableInstance.getDefault().getTable("VISION").getEntry("OBJX").getDouble(this.targX) - this.targX) * 0.02;
    	
    		if(this.driveTrainClass == "systems.subsystems.DiffDriveTrain")
    		{
    			((DiffDriveTrain) this.driveTrain).ArcadeDrive(this.speed, error * this.kP, 1);
    		}
    		else
    		{
    			((MechDriveTrain) this.driveTrain).MechanumDrive(0, this.speed, error * this.kP, 0, 1);

    		}
    	}
    	else
    	{
    		System.out.println("Didn't recieve object x value from network table");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() 
    {
        if(NetworkTableInstance.getDefault().getTable("VISION").getEntry("AREA").getDouble(-100) >= this.targArea)
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() 
    {
		if(this.driveTrainClass == "systems.subsystems.DiffDriveTrain")
		{
			((DiffDriveTrain) this.driveTrain).StopSystem();
		}
		else
		{
			((MechDriveTrain) this.driveTrain).StopSystem();
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
