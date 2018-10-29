package systems.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public interface PidActionSubsys 
{
	public void StopSystem();
	
	public PIDOutput GetPidOutput();
	
	public Subsystem GetSubsystem();
}
