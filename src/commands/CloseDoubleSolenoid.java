package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.DoubleSolenoidSys;

/**
 *
 */
public class CloseDoubleSolenoid extends Command {

	DoubleSolenoidSys subsystem;
	

    public CloseDoubleSolenoid(String key)
    {
    	this.subsystem = ((DoubleSolenoidSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
    }

 
    protected void initialize()
    {
    	this.subsystem.reverse();
    }

   
    protected void execute()
    {
    	
    }

  
    protected boolean isFinished() 
    {
        return false;
    }

    
    protected void end()
    {
    	this.subsystem.off();
    }

    
    protected void interrupted() 
    {
    	
    }
}
