package org.firstinspires.ftc.teamcode.TestPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.Shared;

/**
 * Created by MP3 on 12/27/2017.
 */

@Autonomous(name = "TEST", group = "Test")
public class testProgram extends Shared {

    public boolean isRunning = true;
    private ElapsedTime runtime = new ElapsedTime();
    private Move move = new Move();
    private double increment = 0.2;
    private int step = 0;

    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        // robotPos position = new robotPos(xPos, yPos, imu);
        waitForStart();
        runtime.reset();

        resetDriveEncoders();
        delay(0.2);
        while (opModeIsActive() && isRunning) {

            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    glyphIntakeLeft.setPower(-1.0);
                    glyphIntakeRight.setPower(1.0);
                }
            };
            moveRunnable(r1, 0.5, 0, 0, 1, 1, 0, 2000);

            move.moveTest();

            isRunning = false;
        }
    }
}
