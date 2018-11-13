package systems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class PIDProcessor extends PIDController
{
	private PIDOutput outputDevice;
	
	private boolean isOutputing = false;
	  
	
	public PIDProcessor(double Kp, double Ki, double Kd, PIDSource source) 
	{
		this(Kp, Ki, Kd, source, null, false); 
	}
	
	public PIDProcessor(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, boolean isOutputing) 
	{
		super(Kp, Ki, Kd, 0, source, null, kDefaultPeriod);
		  
		this.m_pidOutput = this::PIDWrite;
		this.outputDevice = output;
		this.isOutputing = isOutputing;
	}
		
	public void ResetFeedbackDevice()
	{
		if(this.m_pidInput.getClass() == Encoder.class)
		{
			((Encoder)this.m_pidInput).reset();
		}
		else if(this.m_pidInput.getClass() == ADXRS450_Gyro.class)
		{
			((ADXRS450_Gyro)this.m_pidInput).reset();
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


}
