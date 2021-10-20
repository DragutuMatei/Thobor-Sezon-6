package org.firstinspires.ftc.teamcode.Amin;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous()
public class PrimaIncercare extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ZoneChooser zona = new ZoneChooser(hardwareMap, telemetry);
        waitForStart();

        Target target = zona.getTarget();
        zona.stop();
        switch (target) {
            case A:
                telemetry.addLine("A");
                telemetry.update();
                break;
            case B:
                telemetry.addLine("B");
                telemetry.update();
                break;
            case C:
                telemetry.addLine("C");
                telemetry.update();
                break;

        }
    }
}
