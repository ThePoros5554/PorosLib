package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import systems.PIDAction;
import systems.RobotManager;

/**
 *
 */
public class RunPIDAction extends Command {

	private PIDAction action;
	
    public RunPIDAction(String actionKey) 
    {
        this.action = RobotManager.GetPIDAction(actionKey);
        
        requires(this.action.GetSubsystem());
    }

    @Override
    protected void initialize() 
    {
    	this.action.ResetFeedbackDevice();
    	
    	this.action.Enable();
    }
    
    @Override
    protected void execute()
    {
    }

    @Override
    protected boolean isFinished()
    {
        return this.action.OnTarget();
    }

    @Override
    protected void end() 
    {
    	this.action.reset();
    	this.action.StopSubsystem();
    	
    	System.out.println("PID Action Ends");
    }

    @Override
    protected void interrupted() 
    {
    	end();
    }
}
