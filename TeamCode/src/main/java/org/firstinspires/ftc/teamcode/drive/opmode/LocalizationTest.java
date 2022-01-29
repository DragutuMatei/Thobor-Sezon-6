package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.FULL_POWER;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_ABS;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_BRAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_RATA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_ARUNCA_CUVA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_IA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_LUAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_NORMAL_CUVA;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This is a simple teleop routine for testing localization. Drive the drive around like a normal
 * teleop routine and make sure the drive's estimated pose matches the drive's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@TeleOp(group = "drive")
public class LocalizationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();
            
//            if(gamepad2.a){
//                NuSeMaiUmbla.FULL_POWER = 1;
//            } else if(gamepad2.b){
//                NuSeMaiUmbla.FULL_POWER = 0.3;
//            }

            //miscari din dpad uri
//            if (gamepad1.dpad_up) {
//                drive.bagaViteza(FULL_POWER, FULL_POWER, FULL_POWER, FULL_POWER);
//            } else if (gamepad1.dpad_down) {
//                drive.bagaViteza(-FULL_POWER, -FULL_POWER, -FULL_POWER, -FULL_POWER);
//            } else if (gamepad1.dpad_left) {
//                drive.bagaViteza(-FULL_POWER, FULL_POWER, FULL_POWER, -FULL_POWER);
//            } else if (gamepad1.dpad_right) {
//                drive.bagaViteza(FULL_POWER, -FULL_POWER, -FULL_POWER, FULL_POWER);
//            } else {
//                drive.bagaViteza(0, 0, 0, 0);
//            }

            //rotiri fine din triggere
//            if(gamepad1.right_trigger != 0){
//                drive.bagaViteza(FULL_POWER, -FULL_POWER, FULL_POWER, -FULL_POWER);
//            } else if(gamepad1.left_trigger != 0){
//                drive.bagaViteza(-FULL_POWER, FULL_POWER, -FULL_POWER, FULL_POWER);
//            } else{
//                drive.bagaViteza(0, 0, 0, 0);
//            }

            drive.update();
            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());

            //absortia
//            if (gamepad1.a) {
//                drive.setAbsortiePower(POWER_ABS);
//            } else {
//                drive.setAbsortiePower(0);
//            }
//
//            if (gamepad1.x) {
//                drive.setCuvaPosition(POZITIE_NORMAL_CUVA);
//            } else if (gamepad1.y) {
//                drive.setCuvaPosition(POZITIE_ARUNCA_CUVA);
//            }
//
//            //brat
//            if (gamepad1.left_bumper) {
//                drive.setPowerBrat(POWER_BRAT);
//            } else if (gamepad1.right_bumper) {
//                drive.setPowerBrat(-POWER_BRAT);
//            } else {
//                drive.setPowerBrat(0);
//            }
//
//            //rata
//            if (gamepad2.left_trigger != 0) {
//                drive.setRataPower(POWER_RATA);
//            } else if (gamepad2.right_trigger != 0) {
//                drive.setRataPower(-POWER_RATA);
//            } else {
//                drive.setRataPower(0);
//            }
//
//            if (gamepad2.x) {
//                drive.setMarkerPosition(POZITIE_MARKER_IA);
//            } else if (gamepad2.y) {
//                drive.setMarkerPosition(POZITIE_MARKER_LUAT);
//            }
        }
    }
}
