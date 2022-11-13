package org.firstinspires.ftc.teamcode.TestPrograms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "Encoder", group = "Test")

public class encoder extends LinearOpMode {
    enum Color {
        RED,
        BLUE,
        UNKNOWN
    }

    DcMotor encoderX;
    DcMotor encoderY;

    ColorSensor leftColor;

    Color L_color;

    public Orientation angles;
    public BNO055IMU imu;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        encoderY = hardwareMap.dcMotor.get("RS");
        encoderX = hardwareMap.dcMotor.get("GIR");

        leftColor = hardwareMap.get(ColorSensor.class, "sensor_color");

        encoderX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoderX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        // wait for the start button to be pressed.
        waitForStart();

        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            telemetry.addData("ENCODER X: ", encoderX.getCurrentPosition());
            telemetry.addData("ENCODER Y: ", encoderY.getCurrentPosition());

            L_color = Color.UNKNOWN;
            //Left
            if(leftColor.blue() < leftColor.red()) {
                L_color = Color.RED;
            } else if(leftColor.blue() > leftColor.red()) {
                L_color = Color.BLUE;
            } else {
                L_color = Color.UNKNOWN;
            }
            telemetry.addData("COLOR: ", L_color);
            telemetry.addData("ANGLE: ", angles.firstAngle);

            telemetry.update();
        }
    }
}