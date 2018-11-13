package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDProcessor;
import systems.subsystems.MechDriveTrain;
import systems.subsystems.MechDriveTrain.MechDrivingDirection;
import util.ControllerOutput;

public class PIDMechDrive extends Command
{
	private MechDriveTrain dt;
	
	private PIDProcessor fwdProc;
	private ControllerOutput fwdOut;
	private PIDProcessor sideProc;
	private ControllerOutput sideOut;
	private PIDProcessor angleProc;
	private ControllerOutput angleOut;
	
	private MechDrivingDirection direction;
	
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor fwdProc, double fwdSp, PIDProcessor sideProc, double sideSp, PIDProcessor angleProc, double angleSp)
	{
		this.LoadCmd(dt, direction, fwdProc,fwdSp, sideProc, sideSp, angleProc, angleSp);
	}
	
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor fwdProc, double fwdSp, PIDProcessor sideProc, double sideSp, PIDProcessor angleProc, double angleSp, double timeout)
	{
		super(timeout);
		this.LoadCmd(dt, direction, fwdProc,fwdSp, sideProc, sideSp, angleProc, angleSp);
	}
	
	private void LoadCmd(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor fwdProc, double fwdSp, PIDProcessor sideProc, double sideSp, PIDProcessor angleProc, double angleSp)
	{
		requires(dt);

		this.dt = dt;		
		this.direction = direction;
		
    	if(fwdProc != null)
    	{
    		this.fwdProc = fwdProc;
    		this.fwdOut = new ControllerOutput();
    		this.fwdProc.SetOutput(this.fwdOut);
    	}
    	
    	if(sideProc != null)
    	{
    		this.sideProc = sideProc;
    		this.sideOut = new ControllerOutput();
    		this.sideProc.SetOutput(this.sideOut);
    	}
    	
    	if(angleProc != null)
    	{
    		this.angleProc = angleProc;
    		this.angleOut = new ControllerOutput();
    		this.angleProc.SetOutput(this.angleOut);
    	}

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
    		fwdOutput = this.fwdOut.GetOutput();
    	}
    	
    	if(this.sideProc != null)
    	{
    		sideOutput = this.sideOut.GetOutput();
    	}
    	
    	if(this.angleProc != null)
    	{
    		angleOutput = this.angleOut.GetOutput();
    	}
    	
    	this.dt.MechanumDrive(sideOutput, fwdOutput, angleOutput, 0, 1);
    }

	@Override
	protected boolean isFinished() 
	{
		if(this.direction == MechDrivingDirection.Forward)
		{
			return this.fwdProc.onTarget() || this.isTimedOut();
		}
		else if(this.direction == MechDrivingDirection.Sideways)
		{
			return this.sideProc.onTarget() || this.isTimedOut();
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
