package commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import subsystems.DiffDriveTrain;

/**
 *
 */
public class DiffTimedGyroDrive extends Command 
{
	private DiffDriveTrain driveTrain;

	private Gyro gyro;
	private double kP;
	private double speed;

    public DiffTimedGyroDrive(DiffDriveTrain driveTrain, Gyro gyro, double speed, double kP, double time) 
    {
        super("TimedGyroDrive" , time);
    	this.gyro = gyro;
    	this.driveTrain = driveTrain;
    	this.kP = kP;
    	this.speed = speed;
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
				this.driveTrain.ArcadeDrive(-this.speed, angle, 1);
			}
			else
			{
				this.driveTrain.ArcadeDrive(this.speed, angle, 1);
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
			this.driveTrain.ArcadeDrive(0, 0, 1);
    }

    protected void interrupted() 
    {
    }
}
