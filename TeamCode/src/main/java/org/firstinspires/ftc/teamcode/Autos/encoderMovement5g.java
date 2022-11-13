package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Robot.Shared;

/**
 * @author Havish
 * @author Kieran
 */

@Autonomous(name = "Encoder Movement 5g")
@Disabled
public class encoderMovement5g extends Shared {
    /////////////////
    //DECELERATIONS//
    /////////////////
    final int CENTER = 400;
    final int LEFT = 0;//350
    final int RIGHT = 1500;

    final int CENTER_RED = 400;
    final int LEFT_RED = 1100;
    final int RIGHT_RED = 0;

    final int END_DIST_RED = 2100;

    final int END_DIST = 2100;

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
    public void scoreGlyphIntakeBlue2(RelicRecoveryVuMark vuMark) {
        if (vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, -1, 0, 0, 0.55, 1, 85, CENTER); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 1, 0, 0, 0.55, 1, 85, LEFT); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, -1, 0, 0, 0.55, 1, 85, RIGHT); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
            move(outIn.NONE, 0, 1, 0, 0.7, 1, 85,200);
        } else {
            move(outIn.NONE, -1, 0, 0, 0.55, 1, 85, CENTER); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        }
        glyphDeposit.setPosition(0);
        eTime.reset();
        while (eTime.milliseconds() < 350) ;
        glyphDeposit.setPosition(1);

        ///////////////////////////////////////////////////////////////////////////////////////////


        if (vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, -1, 0, 0, 0.7, 1, 85, END_DIST - CENTER);
        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, -1, 0, 0, 0.7, 1, 85, END_DIST - LEFT);
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, -1, 0, 0, 0.7, 1, 85, END_DIST - RIGHT);
        } else {
            move(outIn.NONE, -1, 0, 0, 0.7, 1, 85, END_DIST - CENTER);
        }


        turn(outIn.NONE, 0, 0, 1, 0.8, 335);
        move(outIn.NONE, 1, 0, 0, 0.9, 1, 335, 2000);
    }

    public void scoreGlyphIntakeRed2(RelicRecoveryVuMark vuMark) {
        if (vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 1, 0, 0, 0.55, 1, 0, CENTER_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 1, 0, 0, 0.55, 1, 0, LEFT_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 1, 0, 0, 0.55, 1, 0, RIGHT_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        } else {
            move(outIn.NONE, 1, 0, 0, 0.55, 1, 0, CENTER_RED); //y, x, c, driveReduc, maxPower, desiredAngle, encoder*/
        }
        glyphDeposit.setPosition(0);
        eTime.reset();
        while (eTime.milliseconds() < 350) ;
        glyphDeposit.setPosition(1);

        ///////////////////////////////////////////////////////////////////////////////////////////

        if (vuMark == RelicRecoveryVuMark.CENTER) {
            move(outIn.NONE, 1, 0, 0, 0.7, 1, 0, END_DIST_RED - CENTER_RED);
        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            move(outIn.NONE, 1, 0, 0, 0.7, 1, 0, END_DIST_RED - LEFT_RED);
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            move(outIn.NONE, 1, 0, 0, 0.7, 1, 0, END_DIST_RED - RIGHT_RED);
        } else {
            move(outIn.NONE, 1, 0, 0, 0.7, 1, 0, END_DIST_RED - CENTER);
        }

        turn(outIn.NONE, 0, 0, 1, 0.8, 20);
        move(outIn.NONE, -1, 0, 0, 0.9, 1, 20, 2400);//2100

        turn(outIn.NONE, 0, 0, 1, 0.8, 215);
    }

    public void grabGlyphs(Color c) {
        if (c == Color.BLUE) {
            ////////////////////
            float desired = 335;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.5, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 750);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(0.8);

            ///////////////////////////////////////////////////////////////////////////////////////

            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 1500);

        } else if (c == Color.RED) {
            ////////////////////
            float desired = 215;
            ////////////////////

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            move(outIn.INTAKE, 1, 0, 0, 0.5, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);

            // Back up to let glyphs rise
            noEncoderMove(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 700);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(0.8);

            ///////////////////////////////////////////////////////////////////////////////////////

            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 1500);

        }
    }

    public void grabGlyphs2(Color c) {
        if (c == Color.BLUE) {

            ////////////////////
            float desired = 335;
            ////////////////////

            turn(outIn.NONE, 0, 0, 1, 0.8, desired);


            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.5, 1, desired, 2200);//3000

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);

            // Back up to let glyphs rise
            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 750);

            asstIntakeLeft.setPosition(asstLeftRetract);
            asstIntakeRight.setPosition(asstRightRetract);
            stopMotors();
            delay(0.8);

            ///////////////////////////////////////////////////////////////////////////////////////

            asstIntakeLeft.setPosition(asstLeftDeploy);
            asstIntakeRight.setPosition(asstRightDeploy);

            move(outIn.INTAKE, 1, 0, 0, 0.4, 1, desired, 2000);
            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);
            stopMotors();

            move(outIn.INTAKE, -1, 0, 0, 0.6, 1, desired, 1500);

        } else if (c == Color.RED) {
            ////////////////////
            float desired = 215;
            ////////////////////
            turn(outIn.NONE, 0, 0, 1, 0.8, desired);

            asstIntakeLeft.setPosition(asstLeft45);
            asstIntakeRight.setPosition(asstRight45);

            // Half at 45 degree angles
            move(outIn.INTAKE, 1, 0, 0, 0.6, 1, desired, 3100);//3400

            asstIntakeLeft.setPosition(asstLeftLocked);
            asstIntakeRight.setPosition(asstRightLocked);

            move(outIn.INTAKE, -1, 0, 0, 0.8, 1, desired, 3400);


            //move(outIn.INTAKE, -0.6, -0.3, 0, 0.8, 1, desired, 1800);
            //asstIntakeLeft.setPosition(intakeLeftOpen);
            //asstIntakeRight.setPosition(intakeRightOpen);
        }
    }

    // MultiBlue and MultiRed are for coming back from the glyph pit with a second glyph.
    public void scoreMulti1(RelicRecoveryVuMark vuMark, Color c) {
        if (c == Color.BLUE) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 134);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 134, 3500);

                asist(asst.OPEN);

                glyphIntakeRight.setPower(-0.2);
                delay(0.05);
                glyphIntakeLeft.setPower(0.2);

                stopMotors();
                delay(0.5);
                move(outIn.NONE, 1, 0, 0, 0.7, 1, 134, 1050, 500);//1000
                stopMotors();
                delay(0.1);
                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 134, 4550);

                asstIntakeLeft.setPosition(asstLeftDeploy);
                asstIntakeRight.setPosition(asstRightDeploy);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 175);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 175, 2700);//1500
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);


                move(outIn.NONE, 1, 0, 0, 0.7, 1, 175, 1000, 1500);
                stopMotors();

                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 175, 500);//3600

                asstIntakeLeft.setPosition(asstLeftDeploy);//0.460
                asstIntakeRight.setPosition(asstRightDeploy);//0.72

            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 140);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 140, 3250);

                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 140, 1300, 700);//1000
                stopMotors();
                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 140, 4550);

                asstIntakeLeft.setPosition(asstLeftDeploy);
                asstIntakeRight.setPosition(asstRightDeploy);
            }
        } else if (c == Color.RED) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 18);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 18, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.6, 1, 18, 2600, 1500);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.6, 1, 18, 5400);

                asstIntakeLeft.setPosition(asstLeftDeploy);//0.460
                asstIntakeRight.setPosition(asstRightDeploy);//0.72
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 10);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 10, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 10, 2200, 1500); // 2600

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 10, 5400);

                asstIntakeLeft.setPosition(asstLeftDeploy);//0.460
                asstIntakeRight.setPosition(asstRightDeploy);//0.72
            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 27);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 27, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 27, 2600, 3000);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 27, 5400);

                asstIntakeLeft.setPosition(asstLeftDeploy);//0.460
                asstIntakeRight.setPosition(asstRightDeploy);//0.72
            }
        }
    }

    public void scoreMulti2(RelicRecoveryVuMark vuMark, Color c) {
        if (c == Color.BLUE) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                float desired = 141;

                turn(outIn.INTAKE, 0, 0, 1, 0.6, desired);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, desired, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, desired, 2000, 800);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, desired, 500);

                asstIntakeLeft.setPosition(asstLeftRetract);//0.460
                asstIntakeRight.setPosition(asstLeftRetract);//0.72

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                float desired = 160;

                turn(outIn.INTAKE, 0, 0, 1, 0.6, desired);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, desired, 2800);
                asist(asst.OPEN);


                move(outIn.NONE, 1, 0, 0, 0.9, 1, desired, 1000, 800);

                eTime.reset();
                while (eTime.time() < 3) {
                    glyphLift.setPower(1);
                }
                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, desired, 500);

                asstIntakeLeft.setPosition(asstLeftRetract);//0.460
                asstIntakeRight.setPosition(asstLeftRetract);//0.72

            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.NONE, 0, 0, 1, 0.8, 150);

                move(outIn.NONE, 1, 0, 0, 0.7, 1, 150, 2500);//2700
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);


                move(outIn.NONE, 1, 0, 0, 0.7, 1, 150, 1500, 800);//1500
                stopMotors();

                delay(0.1);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 150, 600);//3600
                asstIntakeLeft.setPosition(asstLeftRetract);//0.460
                asstIntakeRight.setPosition(asstLeftRetract);//0.72

            }
        } else if (c == Color.RED) {
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 9);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 9, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 9, 2100, 1500);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 9, 9000);

                asstIntakeLeft.setPosition(asstLeftRetract);//0.460
                asstIntakeRight.setPosition(asstLeftRetract);//0.72

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {

            } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                turn(outIn.INTAKE, 0, 0, 1, 0.6, 16);
                move(outIn.NONE, 1, 0, 0, 0.9, 1, 16, 2800);
                asist(asst.OPEN);

                glyphIntakeLeft.setPower(0.2);
                glyphIntakeRight.setPower(-0.2);
                stopMotors();
                delay(0.5);

                move(outIn.NONE, 1, 0, 0, 0.9, 1, 16, 2600, 2500);

                //move(outIn.NONE, 1, 0, 0, 0.7, 1, 149, 1600, 1500);

                move(outIn.OUTAKE, -1, 0, 0, 0.7, 1, 16, 1000);

                asstIntakeLeft.setPosition(asstLeftRetract);//0.460
                asstIntakeRight.setPosition(asstLeftRetract);//0.72
            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
    }
}
