package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Luke on 3/29/2017.
 */
public class DataLogThread2 extends Thread {

    private PrintStream logFile;
    private File file;
    private long delay;
    private ElapsedTime currentTime = new ElapsedTime();

    private int fLEnc;
    private double fLPow;
    private int bLEnc;
    private double bLPow;
    private int fREnc;
    private double fRPow;
    private int bREnc;
    private double bRPow;
    private float heading;
    private int x_Enc;
    private int y_Enc;
    private double topDist;
    private double bottomDist;

    BNO055IMU imu;

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor omniX;
    DcMotor omniY;

    ElapsedTime eTime;

    DistanceSensor glyphTop;
    DistanceSensor glyphBottom;

    private boolean isRunning;

    public DataLogThread2(String fileName , long logTime, DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight,
                          BNO055IMU imu, DistanceSensor glyphTop, DistanceSensor glyphBottom, DcMotor omniX, DcMotor omniY){

        file = new File(Environment.getExternalStorageDirectory(), createUniqueFileName(fileName, ".txt") + "" );

        try {
            logFile = new PrintStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        delay = logTime;

        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.backRight = backRight;
        this.imu = imu;
        this.glyphBottom = glyphBottom;
        this.glyphTop = glyphTop;
        this.omniX = omniX;
        this.omniY = omniY;
        isRunning = true;

        fLEnc = 0;
        fLPow = 0.0;
        bLEnc = 0;
        bLPow = 0.0;
        fREnc = 0;
        fRPow = 0.0;
        bREnc = 0;
        bRPow = 0.0;
        heading = 0;
        x_Enc = 0;
        y_Enc = 0;
        topDist = 0.0;
        bottomDist = 0.0;

    }

    public DataLogThread2(String fileName , long logTime, DcMotor frontLeft, DcMotor backLeft, DcMotor frontRight, DcMotor backRight,
                          DistanceSensor glyphTop, DistanceSensor glyphBottom, DcMotor omniX, DcMotor omniY){

        file = new File(Environment.getExternalStorageDirectory(), createUniqueFileName(fileName, ".txt") + "" );

        try {
            logFile = new PrintStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        delay = logTime;

        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.backRight = backRight;
        this.glyphBottom = glyphBottom;
        this.glyphTop = glyphTop;
        this.omniX = omniX;
        this.omniY = omniY;
        isRunning = true;

        fLEnc = 0;
        fLPow = 0.0;
        bLEnc = 0;
        bLPow = 0.0;
        fREnc = 0;
        fRPow = 0.0;
        bREnc = 0;
        bRPow = 0.0;
        heading = 0;
        x_Enc = 0;
        y_Enc = 0;
        topDist = 0.0;
        bottomDist = 0.0;

    }

    private String createUniqueFileName(String fileName, String extension){
        return fileName + DateFormat.getDateTimeInstance().format(new Date()) + extension;
    }

    public void run() {

        currentTime.reset();
        logFile.println("time, front left, fl power, back left, bl power, front right, fr power, back right, br power, heading, x encoder, " +
                "y encoder, top distance sensor, bottom distance sensor");
        logFile.println();
        while(isRunning){
            saveToTemp();
            logFile.println(currentTime.milliseconds() + ", " + fLEnc + ", " + fRPow + ", " + bLEnc + ", " + bLPow + ", " + fREnc + ", "
                    + fRPow + ", " + bREnc + ", " + bRPow + ", " + heading + ", " + x_Enc + ", " + y_Enc + ", " + topDist + ", " + bottomDist);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToTemp(){
        int fL = frontLeft.getCurrentPosition();
        double fLP = frontLeft.getPower();
        int bL = backLeft.getCurrentPosition();
        double bLP = backLeft.getPower();
        int fR = frontRight.getCurrentPosition();
        double fRP = frontRight.getPower();
        int bR = backRight.getCurrentPosition();
        double bRP = backRight.getPower();
        float heading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        int xEnc = omniX.getCurrentPosition();
        int yEnc = omniY.getCurrentPosition();
        double topDist = glyphTop.getDistance(DistanceUnit.CM);
        double bottomDist = glyphBottom.getDistance(DistanceUnit.CM);

        setfLEnc(fL);
        setfLPow(fLP);
        setbLEnc(bL);
        setbLPow(bLP);
        setfREnc(fR);
        setfRPow(fRP);
        setbREnc(bR);
        setbRPow(bRP);
        setHeading(heading);
        setXEnc(xEnc);
        setYEnc(yEnc);
        setTopDist(topDist);
        setBottomDist(bottomDist);
    }

    public void setfLEnc(int fLEnc) {this.fLEnc = fLEnc;}

    public void setfLPow(double fLPow) {this.fLPow = fLPow;}

    public void setbLEnc(int bLEnc) {this.bLEnc = bLEnc;}

    public void setbLPow(double bLPow) {this.bLPow = bLPow;}

    public void setfREnc(int fREnc) {this.fREnc = fREnc;}

    public void setfRPow(double fRPow) {this.fRPow = fRPow;}

    public void setbREnc(int bREnc) {this.bREnc = bREnc;}

    public void setbRPow(double bRPow) {this.bRPow = bRPow;}

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public void setXEnc(int x_Enc) {
        this.x_Enc = x_Enc;
    }

    public void setYEnc(int y_Enc) {
        this.y_Enc = y_Enc;
    }

    public void setTopDist(double topDist) {
        this.topDist = topDist;
    }

    public void setBottomDist(double bottomDist) {
        this.bottomDist = bottomDist;
    }

    public void setIsRunning(boolean isRunning){this.isRunning = isRunning;}

    public void pushMessageTitle(String msg){
        logFile.println();
        logFile.println(msg);
        logFile.println();
    }

    public void pushMessage(String msg){
        logFile.println(msg);
    }

    public void forceUpdate(){
        logFile.println("FORCED: " + currentTime.milliseconds() + ", " + fLEnc + ", " + fRPow + ", " + bLEnc + ", " + bLPow + ", " + fREnc + ", "
                + fRPow + ", " + bREnc + ", " + bRPow + ", " + heading + ", " + x_Enc + ", " + y_Enc + ", " + topDist + ", " + bottomDist);

    }

    public void closeLogFile(){
        logFile.close();
    }
}
