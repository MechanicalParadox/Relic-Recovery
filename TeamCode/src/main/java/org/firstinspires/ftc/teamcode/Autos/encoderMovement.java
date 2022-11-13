package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Robot.robotPos;
import org.firstinspires.ftc.teamcode.Robot.Shared;

/**
 * @author Havish
 * @author Kieran
 */

@Autonomous(name = "Encoder Movement")
@Disabled
public class encoderMovement extends Shared {
    final int CENTER = 1100;
    final int LEFT = 350;
    final int RIGHT = 2400;

    final int CENTER_RED = 1200;
    final int LEFT_RED = 2400;
    final int RIGHT_RED = 350;

    final int END_DIST_RED = 2100;

    final int END_DIST = 2100;

    final int NUM_LOOPS = 20;

    final double MIN_DRIVE_POWER = 0.2;

    public DcMotor xPos;
    public DcMotor yPos;

    robotPos position;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //=========================================FUNCTIONS=========================================//
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // scoreGlyphIntakeBlue2 and scoreGlyphIntakeRed2 are for scoring the glyph in the keyed position
    public void scoreGlyphIntakeBlue2 (RelicRecoveryVuMark vuMark) {
        if(vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 0, -1, 0, 0.55, 1, 180, CENTER); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if(vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 0, -1, 0, 0.55, 1, 180, LEFT); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if(vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 0, -1, 0, 0.55, 1, 180, RIGHT); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else {
            move(outIn.NONE, 0, -1, 0, 0.55, 1, 180, CENTER); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        }
        move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 180, 800); //y, x, c, driveReduc, maxPower, desiredAngle, encoder

        move(outIn.OUTAKE, -1, 0, 0, 0.5, 1, 180, 900); //y, x, c, driveReduc, maxPower, desiredAngle, encoder

        ///////////////////////////////////////////////////////////////////////////////////////////

        if(vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 0, -1, 0, 0.7, 1, 180, END_DIST - CENTER);
        } else if(vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 0, -1, 0, 0.7, 1, 180, END_DIST - LEFT);
        } else if(vuMark == RelicRecoveryVuMark.RIGHT) {
        } else {
            move(outIn.NONE, 0, -1, 0, 0.7, 1, 180, END_DIST - CENTER);
        }


        turn(outIn.NONE, 0, 0, 1, 0.8, 155);
        move(outIn.NONE, -1, 0, 0, 0.9, 1, 155, 2000);

        //asstIntakeLeft.setPosition(0.50);//0.50
        //asstIntakeRight.setPosition(0.68);//0.68

        turn(outIn.NONE, 0, 0, 1, 0.8, 325);
    }

    public void scoreGlyphIntakeRed2 (RelicRecoveryVuMark vuMark) {
        if(vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 0, 1, 0, 0.55, 1, 0, CENTER_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if(vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 0, 1, 0, 0.55, 1, 0, LEFT_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if(vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 0, 1, 0, 0.55, 1, 0, RIGHT_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else {
            move(outIn.NONE, 0, 1, 0, 0.55, 1, 0, CENTER_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        }
        move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 0, 800); //y, x, c, driveReduc, maxPower, desiredAngle, encoder
        stopMotors();
        delay(0.1);
        move(outIn.OUTAKE, -1, 0, 0, 0.5, 1, 0, 500); //y, x, c, driveReduc, maxPower, desiredAngle, encoder

        ///////////////////////////////////////////////////////////////////////////////////////////

        if(vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - CENTER_RED);
        } else if(vuMark == RelicRecoveryVuMark.LEFT) {
        } else if(vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - RIGHT_RED);
        } else {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - CENTER);
        }

        turn(outIn.NONE, 0, 0, 1, 0.8, 25);
        move(outIn.NONE, -1, 0, 0, 0.9, 1, 25, 2000);

        //asstIntakeLeft.setPosition(0.50);//0.50
        //asstIntakeRight.setPosition(0.68);//0.68

        turn(outIn.NONE, 0, 0, 1, 0.8, 215);
    }

    public void grabGlyphs(Color c) {
        if(c == Color.BLUE) {
            asstIntakeLeft.setPosition(0.6);//0
            asstIntakeRight.setPosition(0.6);//0.9


            moveClose(outIn.INTAKE, 1, 0, 0, 0.2, 1, 325, 1500);//1900

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 325, 250);//300

            asstIntakeLeft.setPosition(0.9);
            asstIntakeRight.setPosition(0.3);

            stopMotors();
            delay(0.3);

            moveClose(outIn.INTAKE, 1, 0, 0, 0.2, 1, 325, 850);//1250

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 325, 1200);//500

        }else if(c == Color.RED){
            asstIntakeLeft.setPosition(0.7);//0.50
            asstIntakeRight.setPosition(0.48);//0.68

            move(outIn.INTAKE, 1, 0, 0, 0.7, 1, 215, 1500);//1900

            asstIntakeLeft.setPosition(0.50);//0.50
            asstIntakeRight.setPosition(0.68);//0.68
            //asstIntakeLeft.setPosition(0.0);//0
            //asstIntakeRight.setPosition(0.9);//0.9

            turnNoGyro(outIn.INTAKE, 0, 0, 1, 0.6, 220);
            turnNoGyro(outIn.INTAKE, 0, 0, -1, 0.6, 215);

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 215, 250);//300


            asstIntakeLeft.setPosition(0.9);
            asstIntakeRight.setPosition(0.3);

            stopMotors();
            delay(0.2);

            //asstIntakeLeft.setPosition(0.50);//0.50
            //asstIntakeRight.setPosition(0.68);//0.68
            asstIntakeLeft.setPosition(0.0);//0
            asstIntakeRight.setPosition(0.9);//0.9

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, 215, 850);//1250

            //asstIntakeLeft.setPosition(0.0);//0
            //asstIntakeRight.setPosition(0.9);//0.9

            stopMotors();
            delay(0.2);

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 215, 1200);//500
        }
    }

    public void grabGlyphs2(Color c) {
        if(c == Color.BLUE) {
            move(outIn.INTAKE, 1, 0, 0, 0.9, 1, 340, 2100);//3300
            asstIntakeLeft.setPosition(0.50);
            //asstIntakeLeft.setPosition(0.7);//0.50
            asstIntakeRight.setPosition(0.48);//0.68


            move(outIn.INTAKE, 1, 0, 0, 0.7, 1, 340, 1500);//1900

            asstIntakeLeft.setPosition(0.50);//0.50
            asstIntakeRight.setPosition(0.68);//0.68
            //asstIntakeLeft.setPosition(0.0);//0
            //asstIntakeRight.setPosition(0.9);//0.9

            turnNoGyro(outIn.INTAKE, 0, 0, 1, 0.6, 345);
            turnNoGyro(outIn.INTAKE, 0, 0, -1, 0.6, 340);

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 340, 250);//300


            asstIntakeLeft.setPosition(0.9);
            asstIntakeRight.setPosition(0.3);

            stopMotors();
            delay(0.2);

            //asstIntakeLeft.setPosition(0.50);//0.50
            //asstIntakeRight.setPosition(0.68);//0.68
            asstIntakeLeft.setPosition(0.0);//0
            asstIntakeRight.setPosition(0.9);//0.9

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, 340, 850);//1250

            //asstIntakeLeft.setPosition(0.0);//0
            //asstIntakeRight.setPosition(0.9);//0.9

            stopMotors();
            delay(0.2);

            //move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 340, 1200);//500

            //turn(outIn.NONE, 0, 0, 1, 0.75, 340);
        } else if(c == Color.RED){
            move(outIn.INTAKE, 1, 0, 0, 0.9, 1, 200, 3300);//3700 //0.4
            asstIntakeLeft.setPosition(0.50);
            asstIntakeRight.setPosition(0.48);//0.68

            move(outIn.INTAKE, 1, 0, 0, 0.7, 1, 200, 1500);//1900

            asstIntakeLeft.setPosition(0.50);//0.50
            asstIntakeRight.setPosition(0.68);//0.68

            turnNoGyro(outIn.INTAKE, 0, 0, 1, 0.6, 205);
            turnNoGyro(outIn.INTAKE, 0, 0, -1, 0.6, 200);

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 200, 250);//300

            asstIntakeLeft.setPosition(0.9);
            asstIntakeRight.setPosition(0.3);

            stopMotors();
            delay(0.2);

            asstIntakeLeft.setPosition(0.0);//0
            asstIntakeRight.setPosition(0.9);//0.9

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, 200, 850);//1250

            stopMotors();
            delay(0.2);

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, 200, 1200);//500
        }
    }

    // MultiBlue and MultiRed are for coming back from the glyph pit with a second glyph.
    public void scoreMulti1(RelicRecoveryVuMark vuMark, Color c) {
        if(c == Color.BLUE) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 149);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 149, 2800);

                asist(asst.OPEN);
                move(outIn.OUTAKE, 1, 0, 0, 0.9, 1, 149, 1000);
                delay(0.1);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 149, 2000);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 340);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 162);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 162, 2700);//1500

                asist(asst.OPEN);

                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 162, 1700, 1500);
                stopMotors();

                delay(0.1);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 162, 1200);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 340);
            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 155);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 155, 3250);

                asist(asst.OPEN);
                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 155, 1400, 1500);
                stopMotors();
                delay(0.1);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 155, 3800);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 340);
            }
        } else if(c == Color.RED){
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 20);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 20, 3100);

                asist(asst.OPEN);
                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 20, 1600, 1500);
                stopMotors();
                delay(0.1);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 20, 4000);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 200);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 14);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 14, 2700);//1500

                asist(asst.OPEN);

                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 14, 1700, 1500);
                stopMotors();

                delay(0.1);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 14, 1200);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 200);
            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 31);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 31, 3600);//3700

                asist(asst.OPEN);

                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 31, 1800, 1500);
                stopMotors();

                delay(0.1);

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 31, 4000);//3600

                asstIntakeLeft.setPosition(0.50);//0.460
                asstIntakeRight.setPosition(0.68);//0.72

                turn(outIn.NONE, 0, 0, 1, 0.8, 200);
            }
        }
    }

    public void scoreMulti2(RelicRecoveryVuMark vuMark, Color c) {
        if (c == Color.BLUE){
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                liftMove(outIn.NONE, -1, 0, 0, 0.9, 1, 340, 4500, 1.0, 1500);//4600, 345

                turn(outIn.NONE, 0, 0, 1, 0.8, 168);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 168, 1700);//1500

                glyphIntakeLeft.setPower(0.5);
                glyphIntakeRight.setPower(-0.5);
                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 168, 500);

                asstIntakeLeft.setPosition(0.9);
                asstIntakeRight.setPosition(0.3);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 165);

                liftMove(outIn.NONE, 1, 0, 0, 0.42, 1, 165, 4750, 1.0, 3400);//1500

                //asist(asst.OPEN);

                glyphIntakeLeft.setPower(1.0);
                glyphIntakeRight.setPower(-0.8);
                delay(0.05);

                asist(asst.OPEN);

                //move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 161, 800);
            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 345);

                move(outIn.NONE, -1, 0, 0, 0.9, 1, 345, 5000);//4600

                turn(outIn.NONE, 0, 0, 1, 0.8, 158);

                asist(asst.OPEN);

                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 158, 400);//1500

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 158, 500);
            }
        } else if(c == Color.RED){
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 195);

                move(outIn.NONE, -1, 0, 0, 0.9, 1, 195, 4600);//4600

                turn(outIn.NONE, 0, 0, 1, 0.8, 25);

                asist(asst.OPEN);

                move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 25, 600);//1500

                move(outIn.NONE, -1, 0, 0, 0.7, 1, 25, 500);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 14);

                liftMove(outIn.NONE, 1, 0, 0, 0.42, 1, 14, 4750, 1.0, 3400);//1500

                glyphIntakeLeft.setPower(1.0);
                glyphIntakeRight.setPower(-0.8);
                delay(0.05);

                asist(asst.OPEN);
            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                liftMove(outIn.NONE, -1, 0, 0, 0.9, 1, 200, 5000, 1.0, 1500);//4600

                turn(outIn.NONE, 0, 0, 1, 0.8, 9);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 9, 1700);//1500

                glyphIntakeLeft.setPower(0.5);
                glyphIntakeRight.setPower(-0.5);
                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 9, 500);
            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
    }
}
