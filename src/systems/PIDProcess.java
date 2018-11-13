package systems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class PIDProcess 
{
	private PIDController controller;
	
	private PIDSource source;
	private PIDOutput outputDevice;
	
	private boolean isOutputing = false;
	  
	
	public PIDProcess(double Kp, double Ki, double Kd, PIDSource source) 
	{
		this(Kp, Ki, Kd, source, null, false); 
	}
	
	public PIDProcess(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, boolean isOutputing) 
	{				
		this.controller = new PIDController(Kp, Ki, Kd, 0, source, this::PIDWrite);

		this.source = source;
		this.outputDevice = output;
		this.isOutputing = isOutputing;
	}
		
	public void ResetFeedbackDevice()
	{
		if(this.source.getClass() == Encoder.class)
		{
			((Encoder)this.source).reset();
		}
		else if(this.source.getClass() == ADXRS450_Gyro.class)
		{
			((ADXRS450_Gyro)this.source).reset();
		}
	}
	
	public void PIDWrite(double output)
	{		
		if(this.isOutputing)
		{
			this.outputDevice.pidWrite(output);
		}
	}
	
	public void SetOutputProperties(PIDOutput output, boolean isOutputing)
	{
		this.outputDevice = output;
		this.isOutputing = isOutputing;
	}
	
	public void SetOutputDevice(PIDOutput output)
	{
		this.outputDevice = output;
	}
	
	public boolean GetIsOutputing()
	{
		return this.isOutputing;
	}
	
	public void SetIsOutputing(boolean isOutputing)
	{
		this.isOutputing = isOutputing;
	}
	
	public PIDController GetController()
	{
		return this.controller;
	}


}
