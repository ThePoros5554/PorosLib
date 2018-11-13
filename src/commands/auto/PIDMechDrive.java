package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDProcess;
import systems.subsystems.MechDriveTrain;
import systems.subsystems.MechDriveTrain.MechDrivingDirection;

public class PIDMechDrive extends Command
{
	private MechDriveTrain dt;
	
	private PIDProcess fwdProc;
	private PIDProcess sideProc;
	private PIDProcess angleProc;
	
	private MechDrivingDirection direction;
	
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcess fwdProc, double fwdSp, PIDProcess sideProc, double sideSp, PIDProcess angleProc, double angleSp)
	{
		requires(dt);

		this.dt = dt;		
		this.direction = direction;
		
		this.fwdProc = fwdProc;
		this.fwdProc.SetIsOutputing(false);
		this.sideProc = sideProc;
		this.sideProc.SetIsOutputing(false);
		this.angleProc = angleProc;
		this.angleProc.SetIsOutputing(false);
	}
	
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcess fwdProc, double fwdSp, PIDProcess sideProc, double sideSp, PIDProcess angleProc, double angleSp, double timeout)
	{
		super(timeout);
		
		requires(dt);

		this.dt = dt;
		this.direction = direction;
		
		this.fwdProc = fwdProc;
		this.fwdProc.SetIsOutputing(false);
		this.sideProc = sideProc;
		this.sideProc.SetIsOutputing(false);
		this.angleProc = angleProc;
		this.angleProc.SetIsOutputing(false);
	}
	
	@Override
    protected void initialize() 
    {
    	if(fwdProc != null)
    	{
    		fwdProc.ResetFeedbackDevice();
    		fwdProc.GetController().enable();
    	}
    	
    	if(sideProc != null)
    	{
    		sideProc.ResetFeedbackDevice();
    		sideProc.GetController().enable();
    	}
    	
    	if(angleProc != null)
    	{
    		angleProc.ResetFeedbackDevice();
    		angleProc.GetController().enable();
    	}
    }
	
	@Override
    protected void execute() 
    {
    	double fwdOutput = 0;
    	double sideOutput = 0;
    	double angleOutput = 0;
    	
    	if(this.fwdProc != null)
    	{
    		fwdOutput = this.fwdProc.GetController().get();
    	}
    	
    	if(this.sideProc != null)
    	{
    		sideOutput = this.sideProc.GetController().get();
    	}
    	
    	if(this.angleProc != null)
    	{
    		angleOutput = this.angleProc.GetController().get();
    	}
    	
    	this.dt.MechanumDrive(sideOutput, fwdOutput, angleOutput, 0, 1);
    }

	@Override
	protected boolean isFinished() 
	{
		if(this.direction == MechDrivingDirection.Forward)
		{
			return this.fwdProc.GetController().onTarget() || this.isTimedOut();
		}
		else if(this.direction == MechDrivingDirection.Sideways)
		{
			return this.sideProc.GetController().onTarget() || this.isTimedOut();
		}
		else
		{
			return true;
		}
	}
	
	@Override
	protected void end() 
	{
    	if(fwdProc != null)
    	{
    		fwdProc.GetController().reset();
    	}
    	
    	if(sideProc != null)
    	{
    		sideProc.GetController().reset();
    	}
    	
    	if(angleProc != null)
    	{
    		angleProc.GetController().reset();
    	}
    	
		this.dt.MechanumDrive(0, 0, 0, 0, 1);
		
    	System.out.println("End Auto Drive");
	}
	
	@Override
	protected void interrupted() 
	{
		end();
	}
	
}
