package systems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;


public class PIDProcessor extends PIDController 
{	
	public enum ToleranceType
	{
		AbsoluteTolerance,
		PercentageTolerance
	}
	
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
	
	
	public void SetForRun(double setPoint)
	{
		double maxInput;
		double minInput;
		
		if(setPoint > 0)
		{
			maxInput = setPoint;
			minInput = 0;
		}
		else
		{
			maxInput = 0;
			minInput = setPoint;
		}
		
		this.SetForRun(setPoint, maxInput, minInput, 1 , ToleranceType.PercentageTolerance, false);
	}
	
	public void SetForRun(double setPoint, double absTolerance)
	{
		this.SetForRun(setPoint, 0, 0, absTolerance, ToleranceType.AbsoluteTolerance, false);
	}
	
	public void SetForRun(double setPoint, double maxInput, double minInput, double toleranceValue, ToleranceType toleranceType, boolean isContinous)
	{
		this.setSetpoint(setPoint);
		
		if(minInput != 0 && maxInput != 0)
		this.setInputRange(minInput, maxInput);
		
		if(toleranceType == ToleranceType.AbsoluteTolerance)
		{
			this.setAbsoluteTolerance(toleranceValue);
		}
		else
		{
			this.setPercentTolerance(toleranceValue);
		}
	}

}
