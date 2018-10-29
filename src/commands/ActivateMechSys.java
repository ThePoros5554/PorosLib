package commands;

import systems.RobotManager;
import systems.subsystems.MechSys;
import triggers.JoyAxis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActivateMechSys extends Command 
{

	private MechSys subsystem;
	
	private String speedKey;
	private double speed;
	private JoyAxis speedAxis;
	
	private double zeroValue = 0;

    public ActivateMechSys(String key, String speedKey) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
    	this.speedKey = speedKey;
        requires(this.subsystem);
    }
    
    public ActivateMechSys(String key, String speedKey, int zeroValue) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
    	this.speedKey = speedKey;
        requires(this.subsystem);

    }
    
    public ActivateMechSys(String key, double speed) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
        this.speed = speed;
    }
    
    public ActivateMechSys(String key, double speed, double zeroValue) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
        this.speed = speed;
        this.zeroValue = zeroValue;
    }
    
    public ActivateMechSys(String key, JoyAxis speedAxis) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
        this.speedAxis = speedAxis;
    }
    
    public ActivateMechSys(String key, JoyAxis speedAxis, double zeroValue) 
    {
    	this.subsystem = ((MechSys)RobotManager.GetSubsystem(key));
        requires(this.subsystem);
        this.speedAxis = speedAxis;
        this.zeroValue = zeroValue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(this.speedKey != null)
    	{
    		this.subsystem.Activate(RobotManager.GetSpeed(this.speedKey));
    	}
    	else if(this.speedAxis != null)
    	{
    		this.subsystem.Activate(speedAxis.GetAxisValue());
    	}
    	else
    	{
    		this.subsystem.Activate(this.speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	this.subsystem.Activate(this.zeroValue, this.zeroValue);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	this.end();
    }
}
