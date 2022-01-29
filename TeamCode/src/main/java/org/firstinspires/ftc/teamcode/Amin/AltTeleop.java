package org.firstinspires.ftc.teamcode.Amin;

import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.FULL_POWER;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_ABS;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_BRAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POWER_RATA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_ARUNCA_CUVA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_IA;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_MARKER_LUAT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.POZITIE_NORMAL_CUVA;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class AltTeleop extends LinearOpMode {
    private DcMotor lf, rf, lr, rr;

    private Servo marker;
    private Servo cuva;
    private CRServo absortie;
    private DcMotor brat;
    private CRServo rata;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        lr = hardwareMap.get(DcMotor.class, "lr");
        rr = hardwareMap.get(DcMotor.class, "rr");
        rf = hardwareMap.get(DcMotor.class, "rf");

        marker = hardwareMap.get(Servo.class, "marker");
        brat = hardwareMap.get(DcMotor.class, "brat");
        absortie = hardwareMap.get(CRServo.class, "absortie");
        cuva = hardwareMap.get(Servo.class, "cuva");

        rata = hardwareMap.get(CRServo.class, "rata");

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if (isStopRequested()) return;
        double v1, v2, v3, v4;

        while (opModeIsActive() && !isStopRequested()) {
            // miscari joy-stick
            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = (gamepad1.right_stick_x);
            v1 = r * Math.cos(robotAngle) + rightX;
            v2 = r * Math.sin(robotAngle) - rightX;
            v3 = r * Math.sin(robotAngle) + rightX;
            v4 = r * Math.cos(robotAngle) - rightX;

            this.bagaViteza(v1, v2, v3, v4);

            // miscari din dpad uri
            if (gamepad1.dpad_up) {
                this.bagaViteza(FULL_POWER, FULL_POWER, FULL_POWER, FULL_POWER);
            } else if (gamepad1.dpad_down) {
                this.bagaViteza(-FULL_POWER, -FULL_POWER, -FULL_POWER, -FULL_POWER);
            } else if (gamepad1.dpad_left) {
                this.bagaViteza(-FULL_POWER, FULL_POWER, FULL_POWER, -FULL_POWER);
            } else if (gamepad1.dpad_right) {
                this.bagaViteza(FULL_POWER, -FULL_POWER, -FULL_POWER, FULL_POWER);
            } else {
                this.bagaViteza(0, 0, 0, 0);
            }

            // rotiri fine din triggere
            if (gamepad1.right_trigger != 0) {
                this.bagaViteza(0.3, -0.3, 0.3, -0.3);
            } else if (gamepad1.left_trigger != 0) {
                this.bagaViteza(-0.3, 0.3, -0.3, 0.3);
            } else {
                this.bagaViteza(0, 0, 0, 0);
            }

            // schimbare putere
            if (gamepad2.a) {
                NuSeMaiUmbla.FULL_POWER = 1;
            } else if (gamepad2.b) {
                NuSeMaiUmbla.FULL_POWER = 0.7;
            }

            // absorbtia
            if (gamepad1.a) {
                this.setAbsortiePower(POWER_ABS);
            } else {
                this.setAbsortiePower(0);
            }

            // cuva
            if (gamepad1.x) {
                this.setCuvaPosition(POZITIE_NORMAL_CUVA);
            } else if (gamepad1.y) {
                this.setCuvaPosition(POZITIE_ARUNCA_CUVA);
            }

            // brat
            if (gamepad1.left_bumper) {
                this.setPowerBrat(POWER_BRAT);
            } else if (gamepad1.right_bumper) {
                this.setPowerBrat(-POWER_BRAT);
            } else {
                this.setPowerBrat(0);
            }

            // rata
            if (gamepad2.left_trigger != 0) {
                this.setRataPower(POWER_RATA);
            } else if (gamepad2.right_trigger != 0) {
                this.setRataPower(-POWER_RATA);
            } else {
                this.setRataPower(0);
            }

            // marker
            if (gamepad2.x) {
                this.setMarkerPosition(POZITIE_MARKER_IA);
            } else if (gamepad2.y) {
                this.setMarkerPosition(POZITIE_MARKER_LUAT);
            }

        }


    }

    public void bagaViteza(double lfp, double rfp, double lrp, double rrp) {
        lf.setPower(lfp);
        rf.setPower(rfp);
        lr.setPower(lrp);
        rr.setPower(rrp);
    }

    public void setAbsortiePower(double power) {
        absortie.setPower(power);
    }

    public void setCuvaPosition(double position) {
        cuva.setPosition(position);
    }

    public void setPowerBrat(double power) {
        brat.setPower(power);
    }

    public void setRataPower(double power) {
        rata.setPower(power);
    }

    public void setMarkerPosition(double position) {
        marker.setPosition(position);
    }


}
