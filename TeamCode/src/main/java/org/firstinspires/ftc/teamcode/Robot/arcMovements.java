package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Robot.robotPos;

/**
 * @author Havish
 * @author Kieran
 */

@Autonomous(name = "Encoder Movement")
@Disabled
public class arcMovements extends LinearOpMode {
    /////////////////
    //DECELERATIONS//
    /////////////////
    public BNO055IMU imu;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor glyphLift;
    public DcMotor glyphIntakeLeft;
    public DcMotor glyphIntakeRight;
    public DcMotor relicSlide;

    public Servo jewelDeployment;
    public Servo jewelWhacker;
    public Servo asstIntakeLeft;
    public Servo asstIntakeRight;
    public Servo relicArm;
    public Servo relicGrabber;

    public ElapsedTime eTime;

    public Orientation angles;

    public ColorSensor leftColor;

    public DistanceSensor glyphTop;
    public DistanceSensor glyphBottom;

    public int new_enc_ct_x = 0;
    public int new_enc_ct_y = 0;

    public DcMotor xPos;
    public DcMotor yPos;

    robotPos position;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //=========================================FUNCTIONS=========================================//
    ///////////////////////////////////////////////////////////////////////////////////////////////

    //Tells the program to delay for a certain amount of seconds before moving or to allow time for
    //movements to complete
    public void delay(double seconds) {
        eTime.reset();
        while (eTime.time() < seconds && opModeIsActive());
    }

    //Stops the Motors and resets the drive encoders
    public void stopMotors() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

        resetDriveEncoders();
    }

    //resets the drive encoders
    public void resetDriveEncoders() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glyphLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        new_enc_ct_x = glyphIntakeRight.getCurrentPosition();
        new_enc_ct_y = relicSlide.getCurrentPosition();

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        relicSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphIntakeRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        glyphLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Gets the Encoder Average of the encoders
    public int getEncoderAverage() {
        return (int) Math.sqrt(Math.pow((glyphIntakeRight.getCurrentPosition()-new_enc_ct_x), 2) + Math.pow((relicSlide.getCurrentPosition()-new_enc_ct_y), 2));
    }

    public void moveBy(double y, double x, double c, double driveReduc) {
        //Joystick deadzones
        if(Math.abs(y) <= 0.049) { y = 0; }
        if(Math.abs(x) <= 0.049) { x = 0; }
        if(Math.abs(c) <= 0.049) { c = 0; }

        double FLval = y - x + c;
        double FRval = y + x - c;
        double BLval = y + x + c;
        double BRval = y - x - c;

        //Set power to the wheels
        frontLeft.setPower(Range.clip((FLval * driveReduc), -1, 1));
        frontRight.setPower(Range.clip(-(FRval * driveReduc), -1, 1));
        backLeft.setPower(Range.clip((BLval * driveReduc), -1, 1));
        backRight.setPower(Range.clip(-(BRval * driveReduc), -1, 1));
    }

    public void fieldOriented(double y, double x, double c, double driveReduc, double gyroheading) {
        double cosA = Math.cos(Math.toRadians(gyroheading));
        double sinA = Math.sin(Math.toRadians(gyroheading));
        double xOut = x * cosA - y * sinA;
        double yOut = x * sinA + y * cosA;
        moveBy(yOut, xOut, c, driveReduc);
    }

    public void move(double y, double x, double c, double angle, double driveReduc, int encoder) {
        double cVal;

        float adjustedAngle;
        while (opModeIsActive() && position.y() < encoder) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            if(angles.firstAngle > 0 ) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if(angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }

            if (adjustedAngle < angle) {
                cVal = c;
            } else {
                cVal = 0;
            }

            fieldOriented(y, x, cVal, driveReduc, angles.firstAngle);
        }
    }

    public void move(double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder) {

        eTime.reset();
        double cangle;
        float delta;
        float adjustedAngle;
        float deltaScaled;

        double errorX;
        double errorY;

        double adjustedX;
        double adjustedY;

        double scaleX = 0.0005;//0.0017
        double scaleY = 0.0005; //TODO: change number

        while (opModeIsActive() && getEncoderAverage() < encoder) {

            errorX = Range.clip((glyphIntakeRight.getCurrentPosition() - new_enc_ct_x) * scaleX * Math.abs(y), -1, 1);
            errorY = Range.clip((relicSlide.getCurrentPosition() - new_enc_ct_y) * scaleY * Math.abs(x), -1, 1); // CAUTION USE MIND

            adjustedX = (x * driveReduc) + errorX;
            adjustedY = (y * driveReduc) + errorY;

            //Gets the angles of the REV gyro
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            ////////////////////////////
            //Sets the angles to 0-360//
            ////////////////////////////

            if(angles.firstAngle > 0 ) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if(angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if(delta > 180) {
                delta -= 360;
            } else if(delta < -180) {
                delta += 360;
            }

            deltaScaled =  delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);
        }
        stopMotors();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        eTime = new ElapsedTime();

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

        leftColor = hardwareMap.get(ColorSensor.class, "sensor_color");
        glyphBottom = hardwareMap.get(DistanceSensor.class, "GB");
        glyphTop = hardwareMap.get(DistanceSensor.class, "GST");

        backRight = hardwareMap.dcMotor.get("FL");
        backLeft = hardwareMap.dcMotor.get("FR");
        frontRight = hardwareMap.dcMotor.get("BL");
        frontLeft = hardwareMap.dcMotor.get("BR");

        glyphLift = hardwareMap.dcMotor.get("GL");

        glyphIntakeLeft = hardwareMap.dcMotor.get("GIL");
        glyphIntakeRight = hardwareMap.dcMotor.get("GIR");
        glyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        jewelWhacker = hardwareMap.servo.get("JW");
        jewelDeployment = hardwareMap.servo.get("JA");

        relicSlide = hardwareMap.dcMotor.get("RS");
        relicArm = hardwareMap.servo.get("RA");
        relicGrabber = hardwareMap.servo.get("RG");

        asstIntakeLeft = hardwareMap.servo.get("AL");
        asstIntakeRight = hardwareMap.servo.get("AR");

        glyphIntakeLeft.setDirection(DcMotor.Direction.REVERSE);
        glyphIntakeRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        glyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        glyphLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        jewelWhacker.setPosition(0.2);

        xPos = glyphIntakeRight;
        yPos = relicSlide;

        position = new robotPos(xPos, yPos, imu);
    }
}
