package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
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

/**
 * @author Havish
 * @author Kieran
 */

public class Shared extends LinearOpMode {
    /////////////////
    //DECELERATIONS//
    /////////////////
    public enum Color {
        RED,
        BLUE,
        UNKNOWN
    }

    public enum outIn {
        INTAKE,
        OUTAKE,
        NONE
    }

    public enum asst {
        OPEN,
        CLOSE
    }

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
    public Servo glyphDeposit;

    public ElapsedTime eTime;

    public Orientation angles;

    public ColorSensor leftColor;
    public Color L_color;

    public DistanceSensor glyphTop;
    public DistanceSensor glyphBottom;

    public int new_enc_ct_x = 0;
    public int new_enc_ct_y = 0;

    public DcMotor xPos;
    public DcMotor yPos;

    final int NUM_LOOPS = 100;
    final double MIN_DRIVE_POWER = 0.2;


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //=========================================FUNCTIONS=========================================//
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public class Move {
        outIn outIn;
        double y, x, c, driveReduc, maxPower;
        float maPower, desiredAngle;
        int encoder;

        public void moveTest() {

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


            //delay(0.02);
            //logger.forceUpdate();
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

                if (angles.firstAngle > 0) {
                    adjustedAngle = 360 - angles.firstAngle;
                } else if (angles.firstAngle < 0) {
                    adjustedAngle = Math.abs(angles.firstAngle);
                } else {
                    adjustedAngle = 0;
                }
                /////////////////////////////////////////

                delta = desiredAngle - adjustedAngle;
                if (delta > 180) {
                    delta -= 360;
                } else if (delta < -180) {
                    delta += 360;
                }

                deltaScaled = delta / 80;
                deltaScaled = Range.clip(deltaScaled, -1, 1);
                cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

                moveBy(adjustedY, adjustedX, cangle, 1);

                if (outIn == outIn.OUTAKE) {
                    //glyphIntakeLeft.setPower(0.5);
                    glyphIntakeRight.setPower(-0.5);//?
                } else if (outIn == outIn.INTAKE) {
                    glyphIntakeLeft.setPower(-1.0);
                    glyphIntakeRight.setPower(1.0);
                } else {
                    glyphIntakeLeft.setPower(0);
                    glyphIntakeRight.setPower(0);
                }
                telemetry.addData("Y: ", yPos.getCurrentPosition());
                telemetry.addData("X: ", xPos.getCurrentPosition());
                telemetry.update();
            }
            stopMotors();
        }
        public void moveTest(long millis) {

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


            //delay(0.02);
            //logger.forceUpdate();
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

                if (angles.firstAngle > 0) {
                    adjustedAngle = 360 - angles.firstAngle;
                } else if (angles.firstAngle < 0) {
                    adjustedAngle = Math.abs(angles.firstAngle);
                } else {
                    adjustedAngle = 0;
                }
                /////////////////////////////////////////

                delta = desiredAngle - adjustedAngle;
                if (delta > 180) {
                    delta -= 360;
                } else if (delta < -180) {
                    delta += 360;
                }

                deltaScaled = delta / 80;
                deltaScaled = Range.clip(deltaScaled, -1, 1);
                cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

                moveBy(adjustedY, adjustedX, cangle, 1);

                if (outIn == outIn.OUTAKE) {
                    //glyphIntakeLeft.setPower(0.5);
                    glyphIntakeRight.setPower(-0.5);//?
                } else if (outIn == outIn.INTAKE) {
                    glyphIntakeLeft.setPower(-1.0);
                    glyphIntakeRight.setPower(1.0);
                } else {
                    glyphIntakeLeft.setPower(0);
                    glyphIntakeRight.setPower(0);
                }
                telemetry.addData("Y: ", yPos.getCurrentPosition());
                telemetry.addData("X: ", xPos.getCurrentPosition());
                telemetry.update();
            }
            stopMotors();
        }

    }

    //Tells the program to delay for a certain amount of seconds before moving or to allow time for movements to complete
    public void delay(double seconds) {
        eTime.reset();
        while (eTime.time() < seconds && opModeIsActive()) ;
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

        //delay(0.05);//0.05

        //logger.forceUpdate();

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
        return (int) Math.sqrt(Math.pow((glyphIntakeRight.getCurrentPosition() - new_enc_ct_x), 2) + Math.pow((relicSlide.getCurrentPosition() - new_enc_ct_y), 2));
    }

    public int getNotEncoderAverage() {
        return (int) (Math.abs(backLeft.getCurrentPosition()) + Math.abs(backRight.getCurrentPosition()) +
                Math.abs(frontRight.getCurrentPosition()) + Math.abs(frontLeft.getCurrentPosition()) / 4);
    }

    // This function lets us open the assisted intakes
    public void asist(asst Assist) {
        if (Assist == asst.CLOSE) {
            asstIntakeLeft.setPosition(0.46);//0
            asstIntakeRight.setPosition(0.72);//0.9
        } else if (Assist == asst.OPEN) {
            asstIntakeLeft.setPosition(0.9);
            asstIntakeRight.setPosition(0.3);
        }
    }

    public void moveBy(double y, double x, double c, double driveReduc) {
        //Joystick deadzones
        if (Math.abs(y) <= 0.049) {
            y = 0;
        }
        if (Math.abs(x) <= 0.049) {
            x = 0;
        }
        if (Math.abs(c) <= 0.049) {
            c = 0;
        }

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

    public void turn(outIn outIn, double y, double x, double c, double driveReduc, float desiredAngle) {
        // logger.pushMessageTitle("Turning " + desiredAngle + " degrees");
        eTime.reset();
        double cangle;
        float delta;
        float adjustedAngle;
        float deltaScaled;
        double minPower = 0.2;//0.2

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        ////////////////////////////
        //Sets the angles to 0-360//
        ////////////////////////////

        if (angles.firstAngle > 0) {
            adjustedAngle = 360 - angles.firstAngle;
        } else if (angles.firstAngle < 0) {
            adjustedAngle = Math.abs(angles.firstAngle);
        } else {
            adjustedAngle = 0;
        }

        delta = desiredAngle - adjustedAngle;
        if (delta > 180) {
            delta -= 360;
        } else if (delta < -180) {
            delta += 360;
        }

        //logger.forceUpdate();

        while (opModeIsActive() && (Math.abs(delta) > 1)) {
            //Gets the angles of the REV gyro
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            ////////////////////////////
            //Sets the angles to 0-360//
            ////////////////////////////

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            // deltaScaled = Range.clip(deltaScaled, -1, 1);

            cangle = Range.clip(deltaScaled, -1, 1);
            if (Math.abs(cangle) < minPower) {
                if (cangle < 0) {
                    cangle = -minPower;
                } else {
                    cangle = minPower;
                }
            }

            moveBy(y, x, cangle, driveReduc);

            if (outIn == outIn.OUTAKE) {
                glyphIntakeRight.setPower(-1.0);
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }
            telemetry.addData("ANGLE: ", angles.firstAngle);
        }
        stopMotors();
        glyphIntakeLeft.setPower(0);
        glyphIntakeRight.setPower(0);
    }

    public void turnNoGyro(outIn outIn, double y, double x, double c, double driveReduc, float desiredAngle) {
        //logger.pushMessageTitle("Turning " + desiredAngle + " degrees");
        eTime.reset();
        double cangle;
        float delta;
        float adjustedAngle;
        float deltaScaled;
        double minPower = 0.55;//0.2

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        ////////////////////////////
        //Sets the angles to 0-360//
        ////////////////////////////

        if (angles.firstAngle > 0) {
            adjustedAngle = 360 - angles.firstAngle;
        } else if (angles.firstAngle < 0) {
            adjustedAngle = Math.abs(angles.firstAngle);
        } else {
            adjustedAngle = 0;
        }

        delta = desiredAngle - adjustedAngle;
        if (delta > 180) {
            delta -= 360;
        } else if (delta < -180) {
            delta += 360;
        }

        //logger.forceUpdate();

        while (opModeIsActive() && (Math.abs(delta) > 1)) {
            //Gets the angles of the REV gyro
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            ////////////////////////////
            //Sets the angles to 0-360//
            ////////////////////////////

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            // deltaScaled = Range.clip(deltaScaled, -1, 1);

            cangle = Range.clip(deltaScaled, -1, 1);
            if (Math.abs(cangle) < minPower) {
                if (cangle < 0) {
                    cangle = -minPower;
                } else {
                    cangle = minPower;
                }
            }

            moveBy(y, x, cangle, driveReduc);

            if (outIn == outIn.OUTAKE) {
                glyphIntakeRight.setPower(-1.0);
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }
        }
        stopMotors();
        glyphIntakeLeft.setPower(0);
        glyphIntakeRight.setPower(0);
    }

    public void move(outIn outIn, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder) {
        //logger.pushMessageTitle("Driving " + encoder + " ticks");

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


        //delay(0.02);
        //logger.forceUpdate();
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

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);

            if (outIn == outIn.OUTAKE) {
                //glyphIntakeLeft.setPower(0.5);
                glyphIntakeRight.setPower(-0.5);//?
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }
            telemetry.addData("Y: ", yPos.getCurrentPosition());
            telemetry.addData("X: ", xPos.getCurrentPosition());
            telemetry.update();
        }
        stopMotors();
    }

    public void move(outIn outIn, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder, long millis) {
        // logger.pushMessageTitle("Driving " + encoder + " ticks");

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
        double scaleY = 0.0005; //TODO: change number0


        //delay(0.02);
        //logger.forceUpdate();
        while (opModeIsActive() && getEncoderAverage() < encoder && eTime.milliseconds() < millis) {

            errorX = Range.clip((glyphIntakeRight.getCurrentPosition() - new_enc_ct_x) * scaleX * Math.abs(y), -1, 1);
            errorY = Range.clip((relicSlide.getCurrentPosition() - new_enc_ct_y) * scaleY * Math.abs(x), -1, 1); // CAUTION USE MIND

            adjustedX = (x * driveReduc) + errorX;
            adjustedY = (y * driveReduc) + errorY;

            //Gets the angles of the REV gyro
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            ////////////////////////////
            //Sets the angles to 0-360//
            ////////////////////////////

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);

            if (outIn == outIn.OUTAKE) {
                //glyphIntakeLeft.setPower(0.5);
                glyphIntakeRight.setPower(-0.5);//?
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }
        }
        stopMotors();
    }

    public void accelerateForward(outIn outIn, double y, float desiredAngle, int encoder, double accelPercent, double decelPercent, double startPower, double endPower) {
        double nextPower = startPower;

        double encoderStepCheck = (encoder * accelPercent) / NUM_LOOPS;

        double startPowerClip = startPower;

        if (startPower <= MIN_DRIVE_POWER) {
            startPowerClip = MIN_DRIVE_POWER;
        }

        double powerIncrement = (endPower - startPowerClip) / NUM_LOOPS;

        for (int i = 1; i < NUM_LOOPS; i += 1) {

            nextPower += powerIncrement;
            //check encoder position calls

            while (yPos.getCurrentPosition() < encoderStepCheck) {
            }

            //increment powers by powerincrement

            moveBy(y, 0, 0, nextPower);
        }
        stopMotors();
    }

    public void moveClose(outIn outIn, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder) {
        // logger.pushMessageTitle("Driving " + encoder + " ticks");

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

        //delay(0.02);
        // logger.forceUpdate();
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

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);

            if (outIn == outIn.OUTAKE) {
                glyphIntakeLeft.setPower(0.5);
                glyphIntakeRight.setPower(-0.5);//?
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }

            if (getEncoderAverage() > encoder / 2) {
                asstIntakeLeft.setPosition(0.0);//0
                asstIntakeRight.setPosition(0.9);//0.9

                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            }
        }
        stopMotors();
    }

    public void liftMove(outIn outIn, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder, double liftPower, int liftEncoder) {
        // logger.pushMessageTitle("Driving " + encoder + " ticks");

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

        //delay(0.02);
        //logger.forceUpdate();
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

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);

            if (outIn == outIn.OUTAKE) {
                glyphIntakeLeft.setPower(0.5);
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }

            if (Math.abs(glyphLift.getCurrentPosition()) < liftEncoder) {
                glyphLift.setPower(liftPower);
            } else {
                glyphLift.setPower(0.0);
            }
        }
        glyphLift.setPower(0.0);
        stopMotors();
    }

    public void noEncoderMove(outIn outIn, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder) {
        //logger.pushMessageTitle("Moving (no encoders) " + encoder + " ticks");
        eTime.reset();
        double cangle;
        float delta;
        float adjustedAngle;
        float deltaScaled;

        while (opModeIsActive() && getNotEncoderAverage() < encoder) {
            //Gets the angles of the REV gyro
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            ////////////////////////////
            //Sets the angles to 0-360//
            ////////////////////////////

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(y, x, cangle, driveReduc);

            if (outIn == outIn.OUTAKE) {
                glyphIntakeLeft.setPower(1.0);
            } else if (outIn == outIn.INTAKE) {
                glyphIntakeLeft.setPower(-1.0);
                glyphIntakeRight.setPower(1.0);
            } else {
                glyphIntakeLeft.setPower(0);
                glyphIntakeRight.setPower(0);
            }
        }
        stopMotors();
    }

    public void getColor() {
        L_color = Color.UNKNOWN;
        //Left
        if (leftColor.blue() < leftColor.red()) {
            L_color = Color.RED;
        } else if (leftColor.blue() > leftColor.red()) {
            L_color = Color.BLUE;
        } else {
            L_color = Color.UNKNOWN;
        }
    }

    public void hitJewelColor(Color color) {
        jewelWhacker.setPosition(0.5);
        delay(0.2);
        jewelDeployment.setPosition(0.9);

        delay(0.5);

        glyphIntakeLeft.setPower(0);
        glyphIntakeRight.setPower(0);

        getColor();
        delay(0.1);

        if (color == color.BLUE) {
            if (L_color == Color.BLUE) {
                telemetry.addData("Color: ", "BLUE, RED");
                jewelWhacker.setPosition(1);
                delay(0.4);
            } else if (L_color == Color.RED) {
                telemetry.addData("Color: ", "RED, BLUE");
                jewelWhacker.setPosition(0);
                delay(0.4);
            } else {
                telemetry.addData("Color: ", "Unknown");
            }
        } else if (color == color.RED) {
            if (L_color == Color.BLUE) {
                telemetry.addData("Color: ", "BLUE, RED");
                jewelWhacker.setPosition(0);
                delay(0.4);
            } else if (L_color == Color.RED) {
                telemetry.addData("Color: ", "RED, BLUE");
                jewelWhacker.setPosition(1);
                delay(0.4);
            } else {
                telemetry.addData("Color: ", "Unknown");
            }
        }
        jewelDeployment.setPosition(0);
        telemetry.update();
    }


    public void hitJewelColorRunnable(Color color, Runnable block1) {
        jewelWhacker.setPosition(0.5);
        delay(0.2);
        jewelDeployment.setPosition(0.9);

        delay(0.5);

        glyphIntakeLeft.setPower(0);
        glyphIntakeRight.setPower(0);

        getColor();
        delay(0.1);

        block1.run();
        delay(2);

        if (color == color.BLUE) {
            if (L_color == Color.BLUE) {
                telemetry.addData("Color: ", "BLUE, RED");
                jewelWhacker.setPosition(1);
                delay(0.4);
            } else if (L_color == Color.RED) {
                telemetry.addData("Color: ", "RED, BLUE");
                jewelWhacker.setPosition(0);
                delay(0.4);
            } else {
                telemetry.addData("Color: ", "Unknown");
            }
        } else if (color == color.RED) {
            if (L_color == Color.BLUE) {
                telemetry.addData("Color: ", "BLUE, RED");
                jewelWhacker.setPosition(0);
                delay(0.4);
            } else if (L_color == Color.RED) {
                telemetry.addData("Color: ", "RED, BLUE");
                jewelWhacker.setPosition(1);
                delay(0.4);
            } else {
                telemetry.addData("Color: ", "Unknown");
            }
        }
        jewelDeployment.setPosition(0);
        telemetry.update();
    }

    public void moveRunnable(Runnable block1, double y, double x, float c, double driveReduc, double maxPower, float desiredAngle, int encoder) {
        //logger.pushMessageTitle("Driving " + encoder + " ticks");

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


        //delay(0.02);
        //logger.forceUpdate();
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

            if (angles.firstAngle > 0) {
                adjustedAngle = 360 - angles.firstAngle;
            } else if (angles.firstAngle < 0) {
                adjustedAngle = Math.abs(angles.firstAngle);
            } else {
                adjustedAngle = 0;
            }
            /////////////////////////////////////////

            delta = desiredAngle - adjustedAngle;
            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }

            deltaScaled = delta / 80;
            deltaScaled = Range.clip(deltaScaled, -1, 1);
            cangle = Range.clip(maxPower * deltaScaled + c, -1, 1);

            moveBy(adjustedY, adjustedX, cangle, 1);

            block1.run();
        }
        stopMotors();
    }


    //RUNABLES
    Runnable intake = new Runnable() {
        @Override
        public void run() {
            glyphIntakeLeft.setPower(-1.0);
            glyphIntakeRight.setPower(1.0);
        }
    };

    Runnable outake = new Runnable() {
        @Override
        public void run() {
            glyphIntakeLeft.setPower(1.0);
            glyphIntakeRight.setPower(-1.0);
        }
    };

    @Override
    public void runOpMode() throws InterruptedException {
        eTime = new ElapsedTime();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = false;
        parameters.loggingTag = "IMU";
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
        glyphDeposit = hardwareMap.servo.get("GD");

        jewelWhacker.setPosition(0.2);

        xPos = relicSlide;
        yPos = glyphIntakeRight;
        //logger = new DataLogThread2("logs", 200, frontLeft, backLeft, frontRight, backRight, imu, glyphTop, glyphBottom, glyphIntakeRight, relicSlide);
    }
}
