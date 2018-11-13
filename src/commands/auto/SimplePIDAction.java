package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDProcessor;
import systems.subsystems.PidActionSubsys;

/**
 *
 */
public class SimplePIDAction extends Command {

	private PidActionSubsys subsystem;
	private PIDProcessor controller;
	
    public SimplePIDAction(PidActionSubsys subsystem, PIDProcessor controller) 
    {
        this.controller = controller;
        
        requires(subsystem.GetSubsystem());
        
        this.subsystem = subsystem;
    }

    @Override
    protected void initialize() 
    {
    	this.controller.ResetFeedbackDevice();
    	
    	this.controller.enable();
    }
    
    @Override
    protected void execute()
    {
    }

    @Override
    protected boolean isFinished()
    {
        return this.controller.onTarget();
    }

    @Override
    protected void end() 
    {
    	this.controller.reset();
    	this.subsystem.StopSystem();
    	
    	System.out.println("PID Action Ends");
    }

    @Override
    protected void interrupted() 
    {
    	end();
    }
}
