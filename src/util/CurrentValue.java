package util;

public class CurrentValue
{
	private int port;
	private double current;
	
	public CurrentValue(int port, double current)
	{
		this.port = port;
		this.current = current;
	}
	
	public int GetPort()
	{
		return this.port;
	}
	
	public double GetCurrent()
	{
		return this.current;
	}
}
