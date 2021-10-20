package org.firstinspires.ftc.teamcode.Amin;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class ZoneChooser extends OpenCvPipeline {
    private OpenCvCamera cam;
    private Mat mat = new Mat();
    private Rect upperROI = new Rect(new Point(240, 120), new Point(304, 145));
    private Rect lowerROI = new Rect(new Point(240, 145), new Point(304, 170));
    private Mat lowerMat, upperMat;
    private Target target;
    private Telemetry telemetry;

    public ZoneChooser(HardwareMap hwMap, Telemetry t) {
        this.telemetry = t;
        int camMonViewId = hwMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId",
                "id",
                hwMap.appContext.getPackageName()
        );
        cam = OpenCvCameraFactory.getInstance().createWebcam(
                hwMap.get(WebcamName.class, "Webcam 1"),
                camMonViewId
        );

        cam.setPipeline(this);
        cam.openCameraDeviceAsync(
                cameraOpenListener()
//                () -> cam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT)
        );
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2BGR);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2HSV);

        Scalar lowerBound = new Scalar(15.02 / 2, 100, 100);
        Scalar upperBound = new Scalar(45.0 / 2, 255, 255);
        Core.inRange(mat, lowerBound, upperBound, mat);

        upperMat = mat.submat(upperROI);
        lowerMat = mat.submat(lowerROI);

        double upperValue = Math.round(Core.mean(upperMat).val[2] / 255);
        double lowerValue = Math.round(Core.mean(lowerMat).val[2] / 255);

        upperMat.release();
        lowerMat.release();
        mat.release();

        final double THRESHOLD = 10;

        if (upperValue > THRESHOLD) {
            target = Target.C;
        } else if (lowerValue > THRESHOLD) {
            target = Target.B;
        } else {
            target = Target.A;
        }

        return null;
    }

    public Target getTarget() {
        return target;
    }

    @NonNull
    public OpenCvCamera.AsyncCameraOpenListener cameraOpenListener() {
        cam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
        return null;
    }

    public void stop() {
        cam.closeCameraDeviceAsync(
                () -> {
                }
        );
    }
}
