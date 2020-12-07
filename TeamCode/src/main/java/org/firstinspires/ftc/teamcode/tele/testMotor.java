package org.firstinspires.ftc.teamcode.tele;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.SetShootPower;
import org.firstinspires.ftc.teamcode.subsystems.BevelShooterSubsystem;

import java.time.Instant;

@Config
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Maximum Velocity Measurement")
public class testMotor extends CommandOpMode {
  private BevelShooterSubsystem shooter;
  private SetShootPower shootCommand;
  private double currentVelocity;
  private double maximumVelocity = 0.0;

  @Override
  public void initialize() {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    shooter = new BevelShooterSubsystem(new MotorEx(hardwareMap, "motor"), new MotorEx(hardwareMap, "inverted"));

    GamepadEx driverOp = new GamepadEx(gamepad1);
    GamepadButton flywheelShoot = new GamepadButton(driverOp, GamepadKeys.Button.A);
    ButtonReader reader = new ButtonReader(driverOp, GamepadKeys.Button.A);

    if(reader.isDown()){
      shootCommand = new SetShootPower(shooter, 1);
      currentVelocity = shooter.getCurrentVelocity();
    }

    if(currentVelocity > maximumVelocity){
      maximumVelocity = currentVelocity;
    }

    dashboardTelemetry.addData("current velocity", currentVelocity);
    dashboardTelemetry.addData("maximum velocity", maximumVelocity);

    schedule(shootCommand);
    register(shooter);
  }
}
