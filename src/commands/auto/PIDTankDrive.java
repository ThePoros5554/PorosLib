package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import subsystems.DiffDriveTrain;
import systems.PIDProcessor;

/**
 *
 */
public class PIDTankDrive extends Command {

	private DiffDriveTrain drivetrain;
	
	private PIDProcessor rightProc;
	private PIDProcessor leftProc;
	private PIDProcessor angleProc;
	
    public PIDTankDrive(DiffDriveTrain drivetrain, PIDProcessor leftProc, PIDProcessor rightProc, PIDProcessor angleProc)
    {
        requires(drivetrain);
        
        this.drivetrain = drivetrain;
        this.rightProc = rightProc;
        this.leftProc = leftProc;
        this.angleProc = angleProc;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	if (rightProc != null)
    	{
    		rightProc.enable();
    	}
    	
    	if (leftProc != null)
    	{
    		leftProc.enable();
    	}
    	
    	if (angleProc != null)
    	{
    		angleProc.enable();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	double rightOutput = 0;
    	double leftOutput = 0;
    	double angleOutput = 0;
    	
    	if (this.angleProc != null)
    	{
    		angleOutput = this.angleProc.GetOutputValue();
    	}
    	
    	if (this.rightProc != null)
    	{
    		rightOutput = this.rightProc.GetOutputValue() - angleOutput;
    	}
    	
    	if (this.leftProc != null)
    	{
    		leftOutput = this.leftProc.GetOutputValue() + angleOutput;
    	}
    	
		this.drivetrain.TankDrive(leftOutput, rightOutput, 1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
		boolean onTarget = false;
		
		if (this.rightProc != null)
		{
			onTarget = this.rightProc.onTarget();
		}
		
		if (this.leftProc != null)
		{
			onTarget = this.leftProc.onTarget();
		}
		
		if (this.angleProc != null)
		{
			onTarget = this.angleProc.onTarget();
		}
		
		return onTarget || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	if (rightProc != null)
    	{
    		rightProc.reset();
    	}
    	
    	if (leftProc != null)
    	{
    		leftProc.reset();
    	}
    	
    	if (angleProc != null)
    	{
    		angleProc.reset();
    	}
    	
    	this.drivetrain.TankDrive(0, 0, 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
