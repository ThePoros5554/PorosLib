package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDProcess;
import systems.subsystems.PidActionSubsys;

/**
 *
 */
public class SimplePIDAction extends Command {

	private PidActionSubsys subsystem;
	private PIDProcess controller;
	
    public SimplePIDAction(PidActionSubsys subsystem, PIDProcess controller) 
    {
        this.controller = controller;
        
        requires(subsystem.GetSubsystem());
        
        this.subsystem = subsystem;
    }

    @Override
    protected void initialize() 
    {
    	this.controller.ResetFeedbackDevice();
    	
    	this.controller.GetController().enable();
    }
    
    @Override
    protected void execute()
    {
    }

    @Override
    protected boolean isFinished()
    {
        return this.controller.GetController().onTarget();
    }

    @Override
    protected void end() 
    {
    	this.controller.GetController().reset();
    	this.subsystem.StopSystem();
    	
    	System.out.println("PID Action Ends");
    }

    @Override
    protected void interrupted() 
    {
    	end();
    }
}
