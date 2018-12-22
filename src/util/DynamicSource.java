package util;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DynamicSource implements PIDSource
{
	private double value;
	
	public DynamicSource(double value)
	{
		this.value = value; // value of source
	}
	
	public void SetValue(double value)
	{
		this.value = value;
	}
	
	@Override
	public double pidGet()
	{
		return value;
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
	}
}
