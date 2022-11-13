package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Mecanum Drive")
public class MecanumDrive extends OpMode {

    //Declares all of our motors
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor glyphLift;
    DcMotor relicSlide;

    Servo asstIntakeLeft;
    Servo asstIntakeRight;

    //Declares all of our servos
    DcMotor glyphIntakeLeft;
    DcMotor glyphIntakeRight;
    Servo relicArm;
    Servo relicGrabber;
    Servo jewelArm;
    Servo jewelWacker;
    Servo glyphDeposit;

    //Declares the touch sensor
    DigitalChannel glyphTouch;

    double glyphPower;
    double relicPower;

    float outtakeSpeedLeft;
    float outtakeSpeedRight;

    ColorSensor glyphColorTop;
    ColorSensor glyphColorBottom;
    DistanceSensor glyphTop;
    DistanceSensor glyphBottom;

    ElapsedTime eTime = new ElapsedTime();

    // double topThresh = 9;
    // double botThresh = 11;

    @Override
    public void init() {

        //Maps all of our motors, servos, and sensors
        frontLeft = hardwareMap.dcMotor.get("FL");
        frontRight = hardwareMap.dcMotor.get("FR");
        backLeft = hardwareMap.dcMotor.get("BL");
        backRight = hardwareMap.dcMotor.get("BR");

        glyphLift = hardwareMap.dcMotor.get("GL");

        glyphIntakeLeft = hardwareMap.dcMotor.get("GIL");
        glyphIntakeRight = hardwareMap.dcMotor.get("GIR");

        relicSlide = hardwareMap.dcMotor.get("RS");
        relicArm = hardwareMap.servo.get("RA");
        relicGrabber = hardwareMap.servo.get("RG");

        glyphTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");

        jewelArm = hardwareMap.servo.get("JA");
        jewelWacker = hardwareMap.servo.get("JW");

        asstIntakeLeft = hardwareMap.servo.get("AL");
        asstIntakeRight = hardwareMap.servo.get("AR");

        glyphColorTop = hardwareMap.get(ColorSensor.class, "GST");
        glyphColorBottom = hardwareMap.get(ColorSensor.class, "GB");

        glyphTop = hardwareMap.get(DistanceSensor.class, "GST");
        glyphBottom = hardwareMap.get(DistanceSensor.class, "GB");

        //Sets the mode of the glyph lift
        glyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        glyphLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Sets the mode of the relic slide
        relicSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Sets the mode of the touch sensor
        glyphTouch.setMode(DigitalChannel.Mode.INPUT);

        glyphDeposit = hardwareMap.servo.get("GD");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void start() {
        jewelArm.setPosition(0);
        jewelWacker.setPosition(0.25);

        relicArm.setPosition(1); //close claw and fully closes arm
        relicGrabber.setPosition(0.725);

        asstIntakeLeft.setPosition(0.9);
        asstIntakeRight.setPosition(0.3);

    }

    @Override
    public void loop() {
        jewelArm.setPosition(0);
        jewelWacker.setPosition(0.25);
 
        /////////////////////////////////////////////////////////////////////
        //=========================GAMEPAD 1 DRIVE=========================//
        /////////////////////////////////////////////////////////////////////
        if (gamepad1.right_bumper){
            //Set the modes of all the motors
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //Calls the drive function at a 50% slow power
            moveBy(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.3);
        } else {
            //Set the mode of all the motors
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //Calls the drive function at 100% power
            moveBy(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1);
        }

        if(gamepad1.a) {
            //Set the mode of all the motors
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //Calls the drive function at 100% power
            moveBy(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.5);
        }

        ////////////////////////////////////////////////////////////////
        //=========================GLYPH LIFT=========================//
        ////////////////////////////////////////////////////////////////
        glyphPower = gamepad2.right_stick_y;
        glyphLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if(glyphPower > 0 && glyphTouch.getState() == false){
            glyphPower = 0;
        } else {
            glyphLift.setPower(-glyphPower);
        }

        //////////////////////////////////////////////////////////////////
        //=========================RELIC SLIDES=========================//
        //////////////////////////////////////////////////////////////////
        relicPower = gamepad2.left_stick_y;

        relicSlide.setPower(-relicPower);

        /////////////////////////////////////////////////////////////////////////////
        //=========================RELIC GRABBER CONTROLLS=========================//
        /////////////////////////////////////////////////////////////////////////////
        if(gamepad2.a) {
            relicArm.setPosition(0.04); //Arm down
            //relicGrabber.setPosition(0.2);
        } else if(gamepad2.x) {
            relicArm.setPosition(0.4); //Arm down and claw close
            //relicGrabber.setPosition(0.725);
        } else if(gamepad2.y) {
            relicArm.setPosition(1); //close claw and fully closes arm
            //relicGrabber.setPosition(0.725);
        }else if(gamepad2.b){
            relicArm.setPosition(0.8);
        }

        if(gamepad2.right_trigger == 1) {
            relicGrabber.setPosition(0.35);
        } else {
            relicGrabber.setPosition(0.725);
        }

        ////////////////////////////////////////////////////////////////////
        //=========================GLPYPH INTAKES=========================//
        ////////////////////////////////////////////////////////////////////

        outtakeSpeedLeft = -gamepad1.left_trigger;
        outtakeSpeedRight = gamepad1.right_trigger;

        if(Math.abs(gamepad1.left_trigger) < 0.1) {
            glyphIntakeLeft.setPower(0.0);
        } else {
            glyphIntakeLeft.setPower(outtakeSpeedLeft);
        }

        if(Math.abs(gamepad1.right_trigger) < 0.1) {
            glyphIntakeRight.setPower(0.0);
        } else {
            glyphIntakeRight.setPower(outtakeSpeedRight);
        }

        if(gamepad1.left_bumper){
            glyphIntakeLeft.setPower(1.0);
            glyphIntakeRight.setPower(-1.0);
        }

       //////////////////////////////////////
        if(gamepad2.left_bumper) {
            asstIntakeLeft.setPosition(0.25);//45
            asstIntakeRight.setPosition(0.75);//70
        } else if (gamepad2.left_trigger == 1){
            asstIntakeLeft.setPosition(0.0);
            asstIntakeRight.setPosition(0.9);
        } else {
            asstIntakeLeft.setPosition(0.7);
            asstIntakeRight.setPosition(0.35);
        }

        if(gamepad1.dpad_up) {
            glyphDeposit.setPosition(0);
        } else {
            glyphDeposit.setPosition(1);
        }
    }

    public void moveBy(double y, double x, double c, double DRIVE_REDUC) {
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
}
