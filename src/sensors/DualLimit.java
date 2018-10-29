package sensors;

public class DualLimit implements LimitSensor
{
	private LimitSensor max;
	private LimitSensor min;
	
	public DualLimit (LimitSensor max, LimitSensor min)
	{
		this.max = max;
		this.min = min;
	}
	
	@Override
	public SysPosition GetPosition ()
	{
		if(max.GetPosition() != SysPosition.Free && min.GetPosition() != SysPosition.Free)
		{
			return SysPosition.Blocked;
		}
		else if(max.GetPosition() != SysPosition.Free)
		{
			return SysPosition.Top;
		}
		else if(min.GetPosition() != SysPosition.Free)
		{
			return SysPosition.Bottom;
		}
		else
		{
			return SysPosition.Free;
		}
	}
}
