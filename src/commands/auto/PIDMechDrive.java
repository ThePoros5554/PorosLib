package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import subsystems.MechDriveTrain;
import subsystems.MechDriveTrain.MechDrivingDirection;
import systems.PIDProcessor;

public class PIDMechDrive extends Command
{
	private MechDriveTrain dt;
	
	private PIDProcessor powerProc;
	private PIDProcessor fixProc;
	private PIDProcessor angleProc;
	
	private MechDrivingDirection direction;
		
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor powerProc, double powerSp, PIDProcessor fixProc, double fixSp, PIDProcessor angleProc, double angleSp)
	{
		this.LoadCmd(dt, direction, powerProc,powerSp, fixProc, fixSp, angleProc, angleSp);
	}
	
	public PIDMechDrive(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor powerProc, double powerSp, PIDProcessor fixProc, double fixSp, PIDProcessor angleProc, double angleSp, double timeout)
	{
		super(timeout);
		this.LoadCmd(dt, direction, powerProc , powerSp, fixProc, fixSp, angleProc, angleSp);
	}
	
	private void LoadCmd(MechDriveTrain dt, MechDrivingDirection direction, PIDProcessor powerProc, double powerSp, PIDProcessor fixProc, double fixSp, PIDProcessor angleProc, double angleSp)
	{
		requires(dt);

		this.dt = dt;		
		
    	if(powerProc != null)
    	{
    		this.powerProc = powerProc;
    		this.powerProc.SetForRun(powerSp);
    	}
    	
    	if(fixProc != null)
    	{
    		this.fixProc = fixProc;
    		this.fixProc.SetForRun(fixSp);
    	}
    	
    	if(angleProc != null)
    	{
    		this.angleProc = angleProc;
    		this.angleProc.SetForRun(angleSp);
    	}
    	
    	this.direction = direction;

	}
	
	@Override
    protected void initialize() 
    {
    	if(powerProc != null)
    	{
    		powerProc.ResetFeedbackDevice();
    		powerProc.enable();
    	}
    	
    	if(fixProc != null)
    	{
    		fixProc.ResetFeedbackDevice();
    		fixProc.enable();
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
    	double powerOutput = 0;
    	double fixOutput = 0;
    	double angleOutput = 0;
    	
    	if(this.powerProc != null)
    	{
    		powerOutput = this.powerProc.GetOutputValue();
    	}
    	
    	if(this.fixProc != null)
    	{
    		fixOutput = this.fixProc.GetOutputValue();
    	}
    	
    	if(this.angleProc != null)
    	{
    		angleOutput = this.angleProc.GetOutputValue();
    	}
    	
    	if(this.direction == MechDrivingDirection.Forward)
    	{
    		this.dt.MechanumDrive(fixOutput, powerOutput, angleOutput, 0, 1);
    	}
    	else
    	{
    		this.dt.MechanumDrive(powerOutput, fixOutput, angleOutput, 0, 1);
    	}
    }

	@Override
	protected boolean isFinished() 
	{
		return this.powerProc.onTarget() || this.isTimedOut();
	}
	
	@Override
	protected void end() 
	{
    	if(powerProc != null)
    	{
    		powerProc.reset();
    	}
    	
    	if(fixProc != null)
    	{
    		fixProc.reset();
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
