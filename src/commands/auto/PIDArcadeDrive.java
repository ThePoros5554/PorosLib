package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import subsystems.DiffDriveTrain;
import systems.PIDProcessor;

/**
 *
 */
public class PIDArcadeDrive extends Command {

	private DiffDriveTrain drivetrain;
	
	private PIDProcessor speedProc;
	private PIDProcessor turnProc;
	
    public PIDArcadeDrive(DiffDriveTrain drivetrain, PIDProcessor speedProc, PIDProcessor turnProc)
    {
        requires(drivetrain);
        
        this.drivetrain = drivetrain;
        this.speedProc = speedProc;
        this.turnProc = turnProc;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	if (speedProc != null)
    	{
    		speedProc.enable();
    	}
    	
    	if (turnProc != null)
    	{
    		turnProc.enable();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	double speedOutput = 0;
    	double turnOutput = 0;
    	
    	if (this.speedProc != null)
    	{
    		speedOutput = this.speedProc.GetOutputValue();
    	}
    	
    	if (this.turnProc != null)
    	{
    		turnOutput = this.turnProc.GetOutputValue();
    	}
    	
		this.drivetrain.ArcadeDrive(speedOutput, turnOutput, 1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
		boolean onTarget = false;
		
		if (this.speedProc != null)
		{
			onTarget = this.speedProc.onTarget();
		}
		
		return onTarget || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	if (speedProc != null)
    	{
    		speedProc.reset();
    	}
    	
    	if (turnProc != null)
    	{
    		turnProc.reset();
    	}
    	
    	this.drivetrain.ArcadeDrive(0, 0, 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
