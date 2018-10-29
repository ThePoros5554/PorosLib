package systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import systems.subsystems.DiffDriveTrain;
import systems.subsystems.DiffDriveTrain.DiffDriveTypes;
import systems.subsystems.DriveTrain;
import systems.subsystems.MechDriveTrain;
import systems.subsystems.MechDriveTrain.MechDriveTypes;
import systems.subsystems.MechSys;
import triggers.SmartJoystick;

public class RobotManager 
{
	private static DriveTrain driveTrain;	
	private static HashMap<String, Subsystem> subsystems = new HashMap<String, Subsystem>();
	private static HashMap<String, Double> speeds = new HashMap<String, Double>();
	
	private static HashMap<String, PIDAction> pidAction = new HashMap<String, PIDAction>();
		
	private static SmartJoystick driveJoy;
	private static SmartJoystick driveJoy2;
	private static SmartJoystick systemsJoy;
	private static int speedAxis = 1;
	private static int rotateAxis = 2;
	private static int twistAxis = 1;
	
	private static int sliderAxis;
	private static int minSlider;
	private static int maxSlider;
	private static Gyro gyro;

	public static void SetDriveTrain(DriveTrain driveTrain)
	{
		RobotManager.driveTrain = driveTrain;
	}
	
	public static void SetDriveTrain(SpeedController left, SpeedController right)
	{
		RobotManager.driveTrain = new DiffDriveTrain(left, right);
	}
	
	public static void SetDriveTrain(SpeedController left, SpeedController right, boolean isReversed)
	{
		RobotManager.driveTrain = new DiffDriveTrain(left, right, isReversed);
	}
	
	public static void SetDriveTrain(SpeedController left, SpeedController right, DiffDriveTypes driveType)
	{
		RobotManager.driveTrain = new DiffDriveTrain(left, right, driveType);
	}
	
	public static void SetDriveTrain(SpeedController left, SpeedController right, DiffDriveTypes drivingType, boolean isReversed)
	{
		RobotManager.driveTrain = new DiffDriveTrain(left, right, drivingType, isReversed);
	}
	
	public static void SetDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor )
	{
		RobotManager.driveTrain = new MechDriveTrain(frontLeftMotor, rearLeftMotor, frontRightMotor,rearRightMotor);
	}
	
	public static void SetDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, boolean isReversed)
	{
		RobotManager.driveTrain = new MechDriveTrain(frontLeftMotor, rearLeftMotor, frontRightMotor,rearRightMotor, isReversed);
	}
	
	public static void SetDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, MechDriveTypes driveType)
	{
		RobotManager.driveTrain = new MechDriveTrain(frontLeftMotor, rearLeftMotor, frontRightMotor,rearRightMotor,driveType);
	}
	
	public static void SetDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor, MechDriveTypes drivingType, boolean isReversed)
	{
		RobotManager.driveTrain = new MechDriveTrain(frontLeftMotor, rearLeftMotor, frontRightMotor,rearRightMotor,drivingType, isReversed);
	}
	
	public static void SetDriveTrain(DiffDriveTrain driveTrain)
	{
		RobotManager.driveTrain = driveTrain;
	}
	
	public static void SetDriveTrain(MechDriveTrain driveTrain)
	{
		RobotManager.driveTrain = driveTrain;
	}
	
	public static DriveTrain GetDriveTrain()
	{
		return RobotManager.driveTrain;
	}
	
	public static Gyro GetGyro()
	{
		return RobotManager.gyro;
	}
	
	public static void SetGyro(Gyro gyro)
	{
		RobotManager.gyro = gyro;
	}
	
	public static void AddSubsystem(String key, Subsystem subsystem)
	{
		RobotManager.subsystems.put(key, subsystem);
	}
	
	public static void RemoveSubsystem(String key)
	{
		RobotManager.subsystems.remove(key);
	}
	
	public static Subsystem GetSubsystem(String key)
	{
		return RobotManager.subsystems.get(key);
	}
	
	public static void AddSpeed(String key, Double speed)
	{
		RobotManager.speeds.put(key, speed);
	}

	public static void RemoveSpeed(String key)
	{
		RobotManager.speeds.remove(key);
	}

	public static Double GetSpeed(String key)
	{
		return RobotManager.speeds.get(key);
	}

	public static void ChangeSpeed(String key, Double speed)
	{
		RobotManager.speeds.replace(key, speed);
	}
	
	public static void AddPIDAction(String key, PIDAction action)
	{
		RobotManager.pidAction.put(key, action);
	}

	public static void RemovePIDAction(String key)
	{
		RobotManager.pidAction.remove(key);
	}

	public static PIDAction GetPIDAction(String key)
	{
		return RobotManager.pidAction.get(key);
	}


	public static void SetDriveJoy(int port)
	{
		RobotManager.driveJoy = new SmartJoystick(port);
	}
	
	public static void SetDriveJoy2(int port)
	{
		RobotManager.driveJoy2 = new SmartJoystick(port);
	}
	
	public static void SetSystemsJoy(int port)
	{
		RobotManager.systemsJoy = new SmartJoystick(port);
	}
	
	public static SmartJoystick GetDriveJoy()
	{
		return RobotManager.driveJoy;
	}
	
	public static SmartJoystick GetDriveJoy2()
	{
		return RobotManager.driveJoy2;
	}
	
	public static SmartJoystick GetSystemsJoy()
	{
		return RobotManager.systemsJoy;
	}
	
	public static void SetSpeedAxis(int speedAxis)
	{
		RobotManager.speedAxis = speedAxis;
	}
	
	public static void SetRotateAxis(int rotateAxis)
	{
		RobotManager.rotateAxis = rotateAxis;
	}
	
	public static void SetTwistAxis(int twistAxis)
	{
		RobotManager.twistAxis = twistAxis;
	}
	
	public static int GetSpeedAxis()
	{
		return RobotManager.speedAxis;
	}
	
	public static int GetRotateAxis()
	{
		return RobotManager.rotateAxis;
	}
	
	public static int GetTwistAxis()
	{
		return RobotManager.twistAxis;
	}
	
	public static double GetSliderValue()
	{
		double sliderValue = RobotManager.driveJoy.getRawAxis(RobotManager.sliderAxis);
		sliderValue = (sliderValue + RobotManager.minSlider * -1) / (RobotManager.maxSlider + (RobotManager.minSlider * -1));
		
		return (sliderValue);
	}
	
	public static void setRanged(int sliderAxis, int minValue, int maxValue, boolean isRanged)
	{
		RobotManager.sliderAxis = sliderAxis;
		RobotManager.minSlider = minValue;
		RobotManager.maxSlider = maxValue;
		RobotManager.driveTrain.SetIsRanged(isRanged);

	}
	
	public static List<MechSys> GetMechSystems()
	{
		List<MechSys> systems = new ArrayList<MechSys>();
		
		for(String sysKey : RobotManager.subsystems.keySet())
		{
			if(RobotManager.subsystems.get(sysKey).getClass() == MechSys.class)
			{
				systems.add((MechSys) RobotManager.subsystems.get(sysKey));
			}
		}
		
		return systems;
	}
	

	
	
}
