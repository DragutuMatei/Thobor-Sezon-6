package org.firstinspires.ftc.teamcode.idkmhan;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "aSkystone Detecotor", group = "Auto")
public class SkystoneAutoMode extends LinearOpMode {
    OpenCvCamera phoneCam;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        Dector detector = new Dector(telemetry);
        phoneCam.setPipeline(detector);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        waitForStart();
        while (opModeIsActive()) {
            switch (detector.getLocation()) {
                case LEFT:
                    // ...
                    telemetry.addLine(String.valueOf(Dector.Location.LEFT));
                    telemetry.update();
                    break;
                case RIGHT:

                    telemetry.addLine(String.valueOf(Dector.Location.RIGHT));
                    telemetry.update();
                    // ...
                    break;
                case NOT_FOUND:
                    telemetry.addLine(String.valueOf(Dector.Location.NOT_FOUND));
                    telemetry.update();
                    // ...
            }
        }
        phoneCam.stopStreaming();
    }
}
