package org.firstinspires.ftc.teamcode.Robot.Acceleration;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by MP3 on 5/23/2018.
 */

public class Acceleration {

    public  BNO055IMU imu;
    public  Orientation angles;

    public DcMotor frontLeft, frontRight, backRight, backLeft, xPos, yPos;

    public int new_enc_ct_x = 0;
    public int new_enc_ct_y = 0;

    final double MIN_DRIVE_POWER = 0.2;
    final int NUM_LOOPS = 20;

    //==============================================================================================

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

    public void stopMotors() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

        resetDriveEncoders();
    }

    public void resetDriveEncoders() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        new_enc_ct_x = xPos.getCurrentPosition();
        new_enc_ct_y = yPos.getCurrentPosition();

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xPos.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yPos.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getEncoderAverage() {
        return (int) Math.sqrt(Math.pow((xPos.getCurrentPosition()-new_enc_ct_x), 2) + Math.pow((yPos.getCurrentPosition()-new_enc_ct_y), 2));
    }

    public void accelMove1(double y, double x, double c, double power, float desiredAngle, int encoder, double accelPercent, double decelPercent) {

        double cangle;
        float delta;
        float adjustedAngle;
        float deltaScaled;

        double errorX;
        double errorY;

        double adjustedX;
        double adjustedY;

        double decelBase = encoder - (encoder * decelPercent);

        double scaleX = 0.0005;//0.0017
        double scaleY = 0.0005; //TODO: change number

        //accelerate(y, x, c, 0, (int)(encoder * accelPercent), startPower, endPower);

        while (getEncoderAverage() < encoder) {

            errorX = Range.clip((xPos.getCurrentPosition() - new_enc_ct_x) * scaleX * Math.abs(y), -1, 1);
            errorY = Range.clip((yPos.getCurrentPosition() - new_enc_ct_y) * scaleY * Math.abs(x), -1, 1); // CAUTION USE MIND

            adjustedX = (x * power) + errorX;
            adjustedY = (y * power) + errorY;

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
            cangle = Range.clip(deltaScaled + c, -1, 1);

            moveBy(adjustedY * (1 - Range.clip(((getEncoderAverage() - decelBase) / (decelPercent * encoder)), MIN_DRIVE_POWER, 1)), adjustedX * (1 - Range.clip((getEncoderAverage()) / (encoder), MIN_DRIVE_POWER, 1)), cangle, 1);

        }
        stopMotors();
    }

    public void accelerate1 (double y, double x, double c, int beginEncoder, int encoder, double startPower, double endPower) {

        int encoderStep = encoder / NUM_LOOPS; //20

        int encoderStepCheck = beginEncoder + encoderStep; //20

        double startPowerClip = startPower;

        double endPowerClip = endPower;

        if (startPowerClip <= MIN_DRIVE_POWER) {
            startPowerClip = MIN_DRIVE_POWER;
        }

        if(endPowerClip <= MIN_DRIVE_POWER && endPowerClip != 0){
            endPowerClip = MIN_DRIVE_POWER;
        }

        double nextPower = startPowerClip;

        double powerIncrement = (endPowerClip - startPowerClip) / NUM_LOOPS; //0.008

        for (int i = 1; i < NUM_LOOPS; i += 1) {
            moveBy(y, x, c, nextPower);

            if(nextPower + powerIncrement < MIN_DRIVE_POWER) {
                nextPower = 0;
            } else {
                nextPower += powerIncrement;
            }

            while (getEncoderAverage() < encoderStepCheck) {}

            encoderStepCheck += encoderStep;
        }
    }

    public Acceleration(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, DcMotor xPos, DcMotor yPos, BNO055IMU imu) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.xPos = xPos;
        this.yPos = yPos;
        this.imu = imu;
    }
}
