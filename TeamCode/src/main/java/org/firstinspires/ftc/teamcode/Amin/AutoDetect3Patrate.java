package org.firstinspires.ftc.teamcode.Amin;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Amin.incercareDetectie3Patrate.Detectie;
import org.firstinspires.ftc.teamcode.Amin.incercareDetectie3Patrate.UNDE_E;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous()
public class AutoDetect3Patrate extends LinearOpMode {
    OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        int cameraViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraViewId);
//        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

//        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraViewId);

        Detectie detectie = new Detectie();

        camera.setPipeline(detectie);

//        camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        while (opModeIsActive()) {
            switch (detectie.getUnde_e()) {
                case LEFT:
                    telemetry.addLine(String.valueOf(UNDE_E.LEFT));
                    telemetry.update();
                    break;
                case CENTER:
                    telemetry.addLine(String.valueOf(UNDE_E.CENTER));
                    telemetry.update();
                    break;
                case RIGHT:
                    telemetry.addLine(String.valueOf(UNDE_E.RIGHT));
                    telemetry.update();
                    break;
            }
        }
        camera.stopStreaming();
    }
}
