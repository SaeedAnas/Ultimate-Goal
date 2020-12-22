package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.NewSetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.NewBevelShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.NewMotorSubsystem;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Motor Velocity PID")
public class NewShooterTest extends CommandOpMode {
    private NewBevelShooterSubsystem shooter;
    private NewSetShootPower shootCommand;
    private NewMotorSubsystem m_motor;
    private NewMotorSubsystem m_reverse_motor;
    static double kP = 30.00;
    static double kI = 0.00;
    static double kD = 7.00;
    static double kS = 0.10;
    static double kV = 0.0003;
    static double power = 0.5;

    @Override
    public void initialize() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();
        m_motor = new NewMotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "motor"), "velo");
        m_reverse_motor = new NewMotorSubsystem(hardwareMap, new MotorEx(hardwareMap, "inverted"),
                "velo");
        shooter = new NewBevelShooterSubsystem(m_motor, m_reverse_motor);

        m_motor.setVelo(kP, kI, kD);
        m_motor.setFF(kS, kV);
        m_reverse_motor.setVelo(kP, kI, kD);
        m_reverse_motor.setFF(kS, kV);

        shootCommand = new NewSetShootPower(shooter, power);
        dashboardTelemetry.addData("current velocity Motor:", m_motor::getCurrentVelocity);
        dashboardTelemetry.addData("current velocity Inverse: ", m_reverse_motor::getCurrentVelocity);
        dashboardTelemetry.update();

        schedule(shootCommand);
        register(shooter);
        register(m_motor);
        register(m_reverse_motor);
    }
}