package systems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class PIDProcessor extends PIDController 
{	
	public PIDProcessor(double Kp, double Ki, double Kd, PIDSource source) 
	{
		this(Kp, Ki, Kd, source, null);
		  
	}
	
	public PIDProcessor(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output) 
	{
		super(Kp, Ki, Kd, 0, source, output, kDefaultPeriod);
		  
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

	public void SetOutput(PIDOutput output)
	{
		this.m_pidOutput = output;
	}

}
