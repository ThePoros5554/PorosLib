package commands;

import edu.wpi.first.wpilibj.command.Command;
import systems.RobotManager;
import systems.subsystems.DoubleSolenoidSys;

/**
 *
 */
public class OpenDoubleSolenoid extends Command {

	DoubleSolenoidSys subsystem;
	
    public OpenDoubleSolenoid(String key)
    {
    	this.subsystem = ((DoubleSolenoidSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
    }

    protected void initialize()
    {
    	this.subsystem.foward();
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
