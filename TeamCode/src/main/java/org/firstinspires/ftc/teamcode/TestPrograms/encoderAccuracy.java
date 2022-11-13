package org.firstinspires.ftc.teamcode.TestPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.Shared;

@Autonomous(name = "Encoder Accuracy Test", group = "Test")
@Disabled
public class encoderAccuracy extends Shared {
    enum Axis {
        X,
        Y,
    }

    DcMotor encoderX;
    DcMotor encoderY;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    final int TICKS_PER_ROTATION_ENCODER = 1440;
    final double TICKS_PER_ROTATION_MECANUM = 537.6;

    final int DIAMETER_OF_ENCODER_WHEEL = 3;
    final int DIAMETER_OF_MECANUM_WHEEL = 4;

    final int ticksInchesEncoder = TICKS_PER_ROTATION_ENCODER / DIAMETER_OF_ENCODER_WHEEL;
    final double ticksInchesMecanum = TICKS_PER_ROTATION_MECANUM / DIAMETER_OF_MECANUM_WHEEL;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        encoderY = hardwareMap.dcMotor.get("RS");
        encoderX = hardwareMap.dcMotor.get("GIR");


        encoderX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoderX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRight = hardwareMap.dcMotor.get("FL");
        backLeft = hardwareMap.dcMotor.get("FR");
        frontRight = hardwareMap.dcMotor.get("BL");
        frontLeft = hardwareMap.dcMotor.get("BR");


        // wait for the start button to be pressed.
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Y Encoder: ", getAccuracyEncoder(Axis.Y) + " Inches");
            telemetry.addData("Y Mecanum: ", getAccuracyMecanum(Axis.Y) + " Inches");
            telemetry.update();
        }
    }

    public int getAccuracyEncoder(Axis axis) {
        if (axis == Axis.Y) {
            return (encoderY.getCurrentPosition() / ticksInchesEncoder);
        } else {
            return (encoderX.getCurrentPosition() / ticksInchesEncoder);
        }
    }
    public int getAccuracyMecanum(Axis axis) {
        if (axis == Axis.Y) {
            return (getEncoderAverage() / ticksInchesEncoder);
        } else {
            return (getEncoderAverage() / ticksInchesEncoder);
        }
    }
}