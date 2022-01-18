package org.firstinspires.ftc.teamcode.Amin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp()
public class teleopSimplu extends LinearOpMode {
    DcMotor lf, rf, lr, rr;

    @Override
    public void runOpMode() throws InterruptedException {

        lf = hardwareMap.get(DcMotor.class, "lf");
        lr = hardwareMap.get(DcMotor.class, "lr");
        rr = hardwareMap.get(DcMotor.class, "rr");
        rf = hardwareMap.get(DcMotor.class, "rf");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_left) {
                lf.setPower(1);
            } else {
                lf.setPower(0);
            }
            if (gamepad1.dpad_right) {
                rf.setPower(1);
            } else {
                rf.setPower(0);
            }
            if (gamepad1.dpad_up) {
                lr.setPower(1);
            } else {
                lr.setPower(0);
            }
            if (gamepad1.dpad_down) {
                rr.setPower(1);
            } else {
                rr.setPower(0);
            }
        }

    }
}
