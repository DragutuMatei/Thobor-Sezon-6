package org.firstinspires.ftc.teamcode.Amin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp()
public class test extends LinearOpMode {
    DcMotor lf, rf,lr,rr;
    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lr = hardwareMap.dcMotor.get("lr");
        rr = hardwareMap.dcMotor.get("rr");

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.dpad_up){
                lf.setPower(1);
            } else if(gamepad1.dpad_down){
                rf.setPower(1);
            } else if(gamepad1.dpad_right){
                lr.setPower(1);
            } else if(gamepad1.dpad_left){
                rr.setPower(1);
            } else{
                lf.setPower(0);
                rf.setPower(0);
                lr.setPower(0);
                rr.setPower(0);
            }
        }

    }
}
