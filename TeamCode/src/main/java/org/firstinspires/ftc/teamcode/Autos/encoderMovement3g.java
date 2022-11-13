package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Robot.Shared;

/**
 * @author Havish
 * @author Kieran
 */

@Autonomous(name = "Encoder Movement 3g")
@Disabled
public class encoderMovement3g extends Shared {
    /////////////////
    //DECELERATIONS//
    /////////////////
    public final int CENTER = 1150;
    public final int LEFT = 350;//250
    public final int RIGHT = 2400;

    public final int CENTER_RED = 1250;
    public final int LEFT_RED = 2400;
    public final int RIGHT_RED = 350;//2200

    public final int END_DIST_RED = 2100;

    public final int END_DIST = 2100;

    public final int ENC_STUCK_THRESH = 10;

    public static final double asstLeftLocked = 0;
    public static final double asstRightLocked = 1;

    public static final double asstLeftDeploy = 0.25;
    public static final double asstRightDeploy = 0.75;

    public static final double asstLeftRetract = 0.7;
    public static final double asstRightRetract = 0.35;

    public static final double asstLeft45 = 0.47;
    public static final double asstRight45 = 0.55;

    public DcMotor xPos;
    public DcMotor yPos;

    final int NUM_LOOPS = 100;
    final double MIN_DRIVE_POWER = 0.2;

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
        move(outIn.OUTAKE, 1, 0, 0, 0.7, 1, 180, 700, 700); //y, x, c, driveReduc, maxPower, desiredAngle, encoder

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
        glyphIntakeLeft.setPower(0.4);
        glyphIntakeRight.setPower(-0.4);
        stopMotors();
        delay(0.3);

        noEncoderMove(outIn.NONE, 1, 0, 0, 0.7, 1, 0, 1000);

        noEncoderMove(outIn.OUTAKE, -1, 0, 0, 0.5, 1, 0,
                600);//700
        ///////////////////////////////////////////////////////////////////////////////////////////

        if(vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - CENTER_RED);
        } else if(vuMark == RelicRecoveryVuMark.LEFT) {
        } else if(vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - RIGHT_RED);
        } else {
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 0, END_DIST_RED - CENTER);
        }

        turn(outIn.NONE, 0, 0, 1, 0.8, 20);
        move(outIn.NONE, -1, 0, 0, 0.9, 1, 20, 2400);//2100

        //asstIntakeLeft.setPosition(0.50);//0.50
        //asstIntakeRight.setPosition(0.68);//0.68

        turn(outIn.NONE, 0, 0, 1, 0.8, 215);
    }

    public void grabGlyphsShort(Color c) {
        if(c == Color.BLUE) {
            ////////////////////
            float desired = 325;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.5, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            //stopMotors();
            //delay(0.2);

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 750);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(0.8);


            ///////////////////////////////////////////////////////////////////////////////////////


            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            // Half at 45 degree angles
            //move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 1800);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            //delay(0.2);
            //glyphIntakeLeft.setPower(-1.0);
            //glyphIntakeRight.setPower(1.0);
            //delay(1);

            //turnNoGyro(outIn.NONE, 0,0, 1, );

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6                                                                                           , 1, desired, 1500);


            //move(outIn.INTAKE, -0.6, -0.3, 0, 0.8, 1, desired, 1800);
            //asstIntakeLeft.setPosition(intakeLeftOpen);
            //asstIntakeRight.setPosition(intakeRightOpen);

        } else if(c == Color.RED){
            ////////////////////
            float desired = 215;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.5, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            //stopMotors();
            //delay(0.2);

            // Back up to let glyphs rise
            noEncoderMove(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 700);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(0.8);


            ///////////////////////////////////////////////////////////////////////////////////////


            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            // Half at 45 degree angles
            //move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 1800);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            //delay(0.2);
            //glyphIntakeLeft.setPower(-1.0);
            //glyphIntakeRight.setPower(1.0);
            //delay(1);

            //turnNoGyro(outIn.NONE, 0,0, 1, );

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6                                                                                           , 1, desired, 1500);


            //move(outIn.INTAKE, -0.6, -0.3, 0, 0.8, 1, desired, 1800);
            //asstIntakeLeft.setPosition(intakeLeftOpen);
            //asstIntakeRight.setPosition(intakeRightOpen);
        }
    }

    public void grabGlyphs(Color c) {
        if(c == Color.BLUE) {
            ////////////////////
            float desired = 325;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            delay(0.2);

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 750);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(2.0);//1.5


            ///////////////////////////////////////////////////////////////////////////////////////


            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            // Half at 45 degree angles
            //move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 1800);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            delay(0.2);
            glyphIntakeLeft.setPower(-1.0);
            glyphIntakeRight.setPower(1.0);
            delay(1);

            //turnNoGyro(outIn.NONE, 0,0, 1, );

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.3, 1, desired, 1500);


            //move(outIn.INTAKE, -0.6, -0.3, 0, 0.8, 1, desired, 1800);
            //asstIntakeLeft.setPosition(intakeLeftOpen);
            //asstIntakeRight.setPosition(intakeRightOpen);

        } else if(c == Color.RED){
            ////////////////////
            float desired = 215;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            delay(0.2);

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 750);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(2.0);


            ///////////////////////////////////////////////////////////////////////////////////////


            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            // Half at 45 degree angles
            //move(outIn.INTAKE, 1, 0, 0, 0.2, 1, desired, 1800);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();
            delay(0.2);
            glyphIntakeLeft.setPower(-1.0);
            glyphIntakeRight.setPower(1.0);
            delay(1);

            //turnNoGyro(outIn.NONE, 0,0, 1, );

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.3, 1, desired, 1500);


            //move(outIn.INTAKE, -0.6, -0.3, 0, 0.8, 1, desired, 1800);
            //asstIntakeLeft.setPosition(intakeLeftOpen);
            //asstIntakeRight.setPosition(intakeRightOpen);
        }
    }

    // MultiBlue and MultiRed are for coming back from the glyph pit with a second glyph.
    public void scoreMulti1(RelicRecoveryVuMark vuMark, Color c) {
        if(c == Color.BLUE) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 149);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 149, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 149, 2600, 3000);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 149, 1000);

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 162);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 162, 2700);//1500
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);


                move(outIn.NONE, 1, 0, 0, 0.7, 1, 162, 1700, 1500);
                stopMotors();

                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 162, 500);//3600

            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 155);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 155, 3250);

                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 155, 1300, 700);//1000
                stopMotors();
                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 155, 1000);

            }
        }else if(c == Color.RED){
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 16);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 16, 2800);
                stopMotors();
                delay(0.5);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 16, 2600, 1500);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                noEncoderMove(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 16, 500);

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 11);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 11, 2800);
                stopMotors();
                delay(0.5);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 11, 2400, 1800);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                noEncoderMove(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 11, 500);//1000

            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 25);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 25, 2800);
                stopMotors();
                delay(0.5);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 25, 2600, 1500);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                noEncoderMove(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 25, 500);

            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
    }
}
