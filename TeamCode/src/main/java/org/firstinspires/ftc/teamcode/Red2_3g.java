package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Autos.encoderMovement3g;

/**
 * Created by MP3 on 12/27/2017.
 */

@Autonomous (name = "Red2_3g", group = "Red")
@Disabled
public class Red2_3g extends encoderMovement3g {

    private RelicRecoveryVuMark vuMark;
    VuforiaLocalizer vuforia;

    public boolean isRunning = true;
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        asstIntakeLeft.setPosition(1);
        asstIntakeRight.setPosition(0);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AVIcNyn/////AAAAGSm1pLJ6+k15gr7wgnuFsYo/UcmZph3Fd/3akBDJ1OKmrDpayLZQghpyA6dABg2JeTgPVRx/1zxOX0gCKwtmayXN5PoXldBWONwdDyj4WiYfQYRrcsHQ68n4Tc926sKaD3sWW/FrWLVvoKL83tON9j2tSab+zvW81TauzBg91B6SjYhv6sAQA5T55vCwEFOkWzDR/ervnOxDslH/vXCq/xM0lZcitAmXbo2I+Ql0bZ7Q5jvz/Ztmb9vX9BdfMwjsMTm4nRmGxvol7UTNV4MHO/GFIVvConNjednPO7zTPuVwdcmHPJ/+GeFGv3SJTkq4b01G40HfTlqojBv4vzv+yjB/tYcZOhGbO2ot5z4f0W6m";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while(!isStarted()) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("VuMark: ", "Left");
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addData("VuMark", "Right");
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("VuMark: ", "Center");
            } else {
                telemetry.addData("VuMark: ", vuMark);
            }
            telemetry.update();
        }

        waitForStart();
        runtime.reset();

        //logger.start();

        asstIntakeLeft.setPosition(asstLeftRetract);
        asstIntakeRight.setPosition(asstRightRetract);

        //////////////////////////////////////////////////////////////
        //====================START OF AUTO CODE====================//
        //////////////////////////////////////////////////////////////
        while (isRunning && opModeIsActive()) {
            hitJewelColor(Color.RED);

            resetDriveEncoders();

            noEncoderMove(outIn.NONE, 1, 0, 0, 0.4, 1, 0, 3000);

            scoreGlyphIntakeRed2(vuMark);

            grabGlyphsShort(Color.RED);

            scoreMulti1(vuMark, Color.RED);

            isRunning = false;
        }
        //logger.closeLogFile();
    }
}
