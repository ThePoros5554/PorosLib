package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDProcessor;
import systems.subsystems.MechDriveTrain;

public class PIDMechDrive extends Command
{
	private MechDriveTrain dt;
	
	private PIDProcessor fwdProc;
	private PIDProcessor sideProc;
	private PIDProcessor angleProc;
	
	public PIDMechDrive(MechDriveTrain dt, PIDProcessor fwdProc, double fwdSp, PIDProcessor sideProc, double sideSp, PIDProcessor angleProc, double angleSp)
	{
		this.dt = dt;
		
		requires(dt);
		
		this.fwdProc = fwdProc;
		this.fwdProc.SetIsOutputing(false);
		this.sideProc = sideProc;
		this.sideProc.SetIsOutputing(false);
		this.angleProc = angleProc;
		this.angleProc.SetIsOutputing(false);
	}
	
	public PIDMechDrive(MechDriveTrain dt, PIDProcessor fwdProc, double fwdSp, PIDProcessor sideProc, double sideSp, PIDProcessor angleProc, double angleSp, double timeout)
	{
		super(timeout);
		this.dt = dt;
		
		requires(dt);
		
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
    		fwdProc.enable();
    	}
    	
    	if(sideProc != null)
    	{
    		sideProc.ResetFeedbackDevice();
    		sideProc.enable();
    	}
    	
    	if(angleProc != null)
    	{
    		angleProc.ResetFeedbackDevice();
    		angleProc.enable();
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
    		fwdOutput = this.fwdProc.get();
    	}
    	
    	if(this.sideProc != null)
    	{
    		sideOutput = this.sideProc.get();
    	}
    	
    	if(this.angleProc != null)
    	{
    		angleOutput = this.angleProc.get();
    	}
    	
    	this.dt.MechanumDrive(sideOutput, fwdOutput, angleOutput, 0, 1);
    }

	@Override
	protected boolean isFinished() 
	{
    	return this.fwdProc.onTarget() || this.isTimedOut();
	}
	
	@Override
	protected void end() 
	{
    	if(fwdProc != null)
    	{
    		fwdProc.reset();
    	}
    	
    	if(sideProc != null)
    	{
    		sideProc.reset();
    	}
    	
    	if(angleProc != null)
    	{
    		angleProc.reset();
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
