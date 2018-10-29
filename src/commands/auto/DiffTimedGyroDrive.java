package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import systems.RobotManager;
import systems.subsystems.DiffDriveTrain;

/**
 *
 */
public class DiffTimedGyroDrive extends Command 
{
	private DiffDriveTrain driveTrain;

	private Gyro gyro;
	private double kP;
	private double speed;

    public DiffTimedGyroDrive(String speedKey, double kP, double time) 
    {
        super("TimedGyroDrive" , time);
    	this.gyro = RobotManager.GetGyro();
    	this.driveTrain = (DiffDriveTrain)RobotManager.GetDriveTrain();
    	this.kP = kP;
    	this.speed = RobotManager.GetSpeed(speedKey);
        requires(this.driveTrain);
    }

    @Override
    protected void initialize() 
    {
    	gyro.reset();
    }

    @Override
    protected void execute() 
    {
        double angle = gyro.getAngle() * (-this.kP);
			if(this.driveTrain.IsReversed() == true)
			{
				this.driveTrain.ArcadeDrive(-this.speed, angle);
			}
			else
			{
				this.driveTrain.ArcadeDrive(this.speed, angle);
			}
    }

    @Override
    protected boolean isFinished() 
    {
        if(isTimedOut())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    @Override
    protected void end() 
    {
			((DiffDriveTrain) this.driveTrain).ArcadeDrive(0, 0);
    }

    protected void interrupted() 
    {
    }
}
