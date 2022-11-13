package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Field Centric")
public class fieldCentricDrive extends OpMode {

    //Declares all of our motors
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor encoderX;
    DcMotor encoderY;

    Servo jewelArm;
    Servo jewelWacker;

    public BNO055IMU imu;
    public Orientation angles;

    @Override
    public void init() {
        //Maps all of our motors, servos, and sensors
        frontLeft = hardwareMap.dcMotor.get("FL");
        frontRight = hardwareMap.dcMotor.get("FR");
        backLeft = hardwareMap.dcMotor.get("BL");
        backRight = hardwareMap.dcMotor.get("BR");

        jewelArm = hardwareMap.servo.get("JA");
        jewelWacker = hardwareMap.servo.get("JW");

        encoderY = hardwareMap.dcMotor.get("RS");
        encoderX = hardwareMap.dcMotor.get("GIR");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = false;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    @Override
    public void start() {
        jewelArm.setPosition(0);
        jewelWacker.setPosition(0.25);
    }

    @Override
    public void loop() {
        jewelArm.setPosition(0);
        jewelWacker.setPosition(0.25);

        angles = this.imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);

        fieldOriented(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1, angles.firstAngle);

        telemetry.addData("ENCODER X: ", encoderX.getCurrentPosition());
        telemetry.addData("ENCODER Y: ", encoderY.getCurrentPosition());

        telemetry.update();
    }

    public void moveBy(double y, double x, double c, double DRIVE_REDUC) {
        angles = this.imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);

        //Joystick deadzones
        if(Math.abs(y) <= 0.05) { y = 0; }
        if(Math.abs(x) <= 0.05) { x = 0; }
        if(Math.abs(c) <= 0.05) { c = 0; }

        double FLval = y - x + c;
        double FRval = y + x - c;
        double BLval = y + x + c;
        double BRval = y - x - c;

        double FLabs = Math.abs(FLval);
        double FRabs = Math.abs(FRval);
        double BLabs = Math.abs(BLval);
        double BRabs = Math.abs(BRval);

        double maxPower = 1;

        if(FLabs > maxPower) {
            maxPower = FRabs;
        }
        if(FRabs > maxPower) {
            maxPower = FRabs;
        }
        if(BLabs > maxPower) {
            maxPower = BLabs;
        }
        if(BRabs > maxPower) {
            maxPower = BRabs;
        }

        FLval /= maxPower;
        FRval /= maxPower;
        BLval /= maxPower;
        BRval /= maxPower;

        //Set power to the wheels
        frontLeft.setPower(Range.clip((FLval * DRIVE_REDUC), -1, 1));
        frontRight.setPower(Range.clip(-(FRval * DRIVE_REDUC), -1, 1));
        backLeft.setPower(Range.clip((BLval * DRIVE_REDUC), -1, 1));
        backRight.setPower(Range.clip(-(BRval * DRIVE_REDUC), -1, 1));
    }

    public void fieldOriented(double y, double x, double c, double driveReduc,double gyroheading) {
        double cosA = Math.cos(Math.toRadians(gyroheading));
        double sinA = Math.sin(Math.toRadians(gyroheading));
        double xOut = x * cosA - y * sinA;
        double yOut = x * sinA + y * cosA;
        moveBy(yOut, xOut, c, driveReduc);
    }
}
