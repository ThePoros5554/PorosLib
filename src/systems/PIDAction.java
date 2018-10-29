package systems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import systems.subsystems.PidActionSubsys;

public class PIDAction 
{
	private PIDSource feedbackDevice;
	private PidActionSubsys subsystem;
	
	private PIDController controller;
	
	public PIDAction(double kP, double kI, double kD, PIDSource source, PidActionSubsys subsystem)
	{
		this.feedbackDevice = source;
		this.subsystem = subsystem;
		
		controller = new PIDController(kP, kI, kD, source, this.subsystem.GetPidOutput());
	}
	
	public PIDAction(double kP, double kI, double kD, double kF, PIDSource source, PidActionSubsys subsystem)
	{
		this.feedbackDevice = source;
		this.subsystem = subsystem;

		
		controller = new PIDController(kP, kI, kD, kF, source, this.subsystem.GetPidOutput());
	}
	
	public void ResetFeedbackDevice()
	{
		if(this.feedbackDevice.getClass() == Encoder.class)
		{
			((Encoder)this.feedbackDevice).reset();
		}
		else if(this.feedbackDevice.getClass() == ADXRS450_Gyro.class)
		{
			((ADXRS450_Gyro)this.feedbackDevice).reset();
		}
	}
	
	public Subsystem GetSubsystem()
	{
		return this.subsystem.GetSubsystem();
	}
	
	public void StopSubsystem()
	{
		subsystem.StopSystem();
	}

	public void SetP(double kP)
	{
		this.controller.setP(kP);
	}
	
	public void SetI(double kI)
	{
		this.controller.setP(kI);
	}
	
	public void SetD(double kD)
	{
		this.controller.setP(kD);
	}
	
	public void SetF(double kF)
	{
		this.controller.setP(kF);
	}
	
	public void SetInputRange(double min, double max)
	{
		this.controller.setInputRange(min, max);
	}
	
	public void SetPercentTolerance(double percent)
	{
		this.controller.setPercentTolerance(percent);
	}
	
	public void SetSetPoint(double setPoint)
	{
		this.controller.setSetpoint(setPoint); 
	}
	
	public double GetP()
	{
		return this.controller.getP();
	}
	
	public double GetI()
	{
		return this.controller.getI();
	}
	
	public double GetD()
	{
		return this.controller.getD();
	}
	
	public double GetF()
	{
		return this.controller.getF();
	}
	
	
	public double GetSetPoint()
	{
		return this.controller.getSetpoint();
	}
	
	public void Enable()
	{
		this.controller.enable();
	}
	
	public void Disable()
	{
		this.controller.disable();
	}
	
	public boolean OnTarget()
	{
		return this.controller.onTarget();
	}
	
	public double GetOutput()
	{
		return this.controller.get();
	}

	public void reset()
	{
		this.controller.reset();
	}
}
