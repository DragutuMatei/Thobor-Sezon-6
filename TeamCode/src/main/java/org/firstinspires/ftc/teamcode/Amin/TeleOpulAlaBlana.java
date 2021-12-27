package org.firstinspires.ftc.teamcode.Amin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp()
public class TeleOpulAlaBlana extends LinearOpMode {
    private DcMotor lf, lr, rf, rr;
    private double leftPower, rightPower, drive, turn;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.dcMotor.get("lf");
        lr = hardwareMap.dcMotor.get("lr");
        rf = hardwareMap.dcMotor.get("rf");
        rr = hardwareMap.dcMotor.get("rr");

        lf.setDirection(DcMotor.Direction.REVERSE);
        lr.setDirection(DcMotor.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {

            drive = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            lf.setPower(leftPower);
            lr.setPower(leftPower);
            rf.setPower(rightPower);
            rr.setPower(rightPower);


            if (gamepad1.dpad_up) {
                bagaViteza(1, 1, 1, 1);
            } else if (gamepad1.dpad_down) {
                bagaViteza(-1, -1, -1, -1);
            } else if (gamepad1.dpad_left) {
                bagaViteza(-1, 1, 1, -1);
            } else if (gamepad1.dpad_right) {
                bagaViteza(1, -1, -1, 1);
            } else {
                bagaViteza(0, 0, 0, 0);
            }
        }
    }

    private void bagaViteza(double lfp, double rfp, double lrp, double rrp) {
        lf.setPower(lfp);
        rf.setPower(rfp);
        lr.setPower(lrp);
        rr.setPower(rrp);
    }
}
