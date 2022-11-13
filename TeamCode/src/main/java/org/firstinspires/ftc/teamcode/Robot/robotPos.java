package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by MP3 on 5/23/2018.
 */

public class robotPos {

    public  BNO055IMU imu;
    public  Orientation angles;

    public DcMotor xPos, yPos;

    double previousAngle;
    double previousX;

    public double x() {
        double deltaX = xPos.getCurrentPosition() - previousX;

        double deltaAngle = c() - previousAngle;

        double turnRad = (360 * deltaX / ( deltaAngle * 2 * Math.PI ));

        //turnRad = (turnRad == Double.POSITIVE_INFINITY || turnRad == Double.NEGATIVE_INFINITY) ? turnRad : 0;

        previousAngle = c();
        previousX = xPos.getCurrentPosition();

        return turnRad;
    }
    public double y() {
        return yPos.getCurrentPosition(); //TODO: May have to change if they are too offset
    }
    public float c() {
        float adjustedAngle;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        if(angles.firstAngle > 0 ) {
            adjustedAngle = 360 - angles.firstAngle;
        } else if(angles.firstAngle < 0) {
            adjustedAngle = Math.abs(angles.firstAngle);
        } else {
            adjustedAngle = 0;
        }

        return adjustedAngle;
    }

    public robotPos(DcMotor xPos, DcMotor yPos, BNO055IMU imu) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.imu = imu;

        previousAngle = c();
        previousX = xPos.getCurrentPosition();
    }
}
