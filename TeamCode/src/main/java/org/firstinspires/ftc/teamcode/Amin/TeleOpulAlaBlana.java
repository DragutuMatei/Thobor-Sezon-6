/*
 *
 * ©Thobor 2021-2022
 *
 *           _
 *       .__(.)< (MEOW)
 *        \___)
 * ~~~~~~~~~~~~~~~~~~
 *
 *
 */

package org.firstinspires.ftc.teamcode.Amin;

import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.FULL_POWER;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_ABS;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_BRAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_RATA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_ARUNCA_CUVA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_IA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_LUAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_NORMAL_CUVA;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp()
public class TeleOpulAlaBlana extends LinearOpMode {

    private double v1, v2, v3, v4;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.setPoseEstimate(LocalizareCaSaStiuUndeE.currentPose);


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive() && !isStopRequested()) {
            // joysticks
            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = (gamepad1.right_stick_x);
            v1 = r * Math.cos(robotAngle) + rightX;
            v2 = r * Math.sin(robotAngle) - rightX;
            v3 = r * Math.sin(robotAngle) + rightX;
            v4 = r * Math.cos(robotAngle) - rightX;

            robot.bagaViteza(v1, v2, v3, v4);

            // miscari din dpad uri
            while (gamepad1.dpad_down) {
                robot.bagaViteza(-FULL_POWER, -FULL_POWER, -FULL_POWER, -FULL_POWER);
            }
            while (gamepad1.dpad_right) {
                robot.bagaViteza(FULL_POWER, -FULL_POWER, -FULL_POWER, FULL_POWER);
            }
            while (gamepad1.dpad_up) {
                robot.bagaViteza(FULL_POWER, FULL_POWER, FULL_POWER, FULL_POWER);
            }
            while (gamepad1.dpad_left) {
                robot.bagaViteza(-FULL_POWER, FULL_POWER, FULL_POWER, -FULL_POWER);
            }

            // rotiri fine din triggere
            while (gamepad1.right_trigger != 0) {
                robot.bagaViteza(0.3, -0.3, 0.3, -0.3);
            }
            while (gamepad1.left_trigger != 0) {
                robot.bagaViteza(-0.3, 0.3, -0.3, 0.3);
            }

            // schimbare putere
            if (gamepad2.a) {
                NuSeMaiUmbla.FULL_POWER = 1;
            } else if (gamepad2.b) {
                NuSeMaiUmbla.FULL_POWER = 0.7;
            }

            // absorbtia
            if (gamepad1.a) {
                robot.setAbsortiePower(POWER_ABS);
            } else {
                robot.setAbsortiePower(0);
            }

            // cuva
            if (gamepad1.x) {
                robot.setCuvaPosition(POZITIE_NORMAL_CUVA);
            } else if (gamepad1.y) {
                robot.setCuvaPosition(POZITIE_ARUNCA_CUVA);
            }

            // brat
            if (gamepad1.left_bumper) {
                robot.setPowerBrat(POWER_BRAT);
            } else if (gamepad1.right_bumper) {
                robot.setPowerBrat(-POWER_BRAT);
            } else {
                robot.setPowerBrat(0);
            }

            // rata
            if (gamepad2.left_trigger != 0) {
                robot.setRataPower(POWER_RATA);
            } else if (gamepad2.right_trigger != 0) {
                robot.setRataPower(-POWER_RATA);
            } else {
                robot.setRataPower(0);
            }

            // marker
            if (gamepad2.x) {
                robot.setMarkerPosition(POZITIE_MARKER_IA);
            } else if (gamepad2.y) {
                robot.setMarkerPosition(POZITIE_MARKER_LUAT);
            }
            robot.update();
        }
    }


}


