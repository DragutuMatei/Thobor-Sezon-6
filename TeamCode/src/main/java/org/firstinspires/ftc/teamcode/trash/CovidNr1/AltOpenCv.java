package org.firstinspires.ftc.teamcode.trash.CovidNr1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous()
@Disabled()
public class AltOpenCv extends LinearOpMode {
    OpenCvCamera camera;

    final int X_LEFT = 1,
            X_RIGHT = 2,
            Y_UP = 1,
            Y_MIDDLE = 2,
            Y_DOWN = 3;


    @Override
    public void runOpMode() throws InterruptedException {
        int camMonViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId",
                "id",
                hardwareMap.appContext.getPackageName()
        );
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                camMonViewId
        );

        RingPipeline visionPipeline = new RingPipeline();
        camera.setPipeline(visionPipeline);

        camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("ring1: ", visionPipeline.ring1);
            telemetry.addData("ring4: ", visionPipeline.ring4);
            telemetry.update();
        }

    }

    class RingPipeline extends OpenCvPipeline {
        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        Mat tholdMat = new Mat();

        Scalar GRAY = new Scalar(220, 220, 220);
        Scalar GREEN = new Scalar(0, 255, 0);

        int ring1, ring4;

        Point BigSquare1 = new Point(X_LEFT, Y_UP);
        Point BigSquare2 = new Point(X_RIGHT, Y_DOWN);

        Point SmallSquare1 = new Point(X_LEFT, Y_MIDDLE);
        Point SmallSquare2 = new Point(X_RIGHT, Y_DOWN);

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_BGR2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);

            Imgproc.threshold(Cb, tholdMat, 150, 255, Imgproc.THRESH_BINARY_INV);

            int bigSquarePointX = (int) ((BigSquare1.x + BigSquare2.x) / 2);
            int bigSquarePointY = (int) ((BigSquare1.y + SmallSquare1.y) / 2);

            int smallSquarePointX = (int) ((SmallSquare1.x + SmallSquare2.x) / 2);
            int smallSquarePointY = (int) ((SmallSquare1.y + SmallSquare2.y) / 2);

            double[] bigSquarePointValues = tholdMat.get(bigSquarePointY, bigSquarePointX);
            double[] smallSquarePointValues = tholdMat.get(smallSquarePointY, smallSquarePointX);

            ring4 = (int) bigSquarePointValues[0];
            ring1 = (int) smallSquarePointValues[0];

            Imgproc.rectangle(input, BigSquare1, BigSquare2, GRAY, 1);
            Imgproc.rectangle(input, SmallSquare1, SmallSquare2, GRAY, 1);
            Imgproc.circle(input, new Point(bigSquarePointX, bigSquarePointY), 2, GRAY, 1);
            Imgproc.circle(input, new Point(smallSquarePointX, smallSquarePointY), 2, GRAY, 1);

            if (ring1 == 0 && ring4 == 0) {
                Imgproc.rectangle(input, BigSquare1, BigSquare2, GREEN, 1);
                Imgproc.circle(input, new Point(bigSquarePointX, bigSquarePointY), 2, GREEN, 1);
            }
            if (ring1 == 0) {
                Imgproc.rectangle(input, SmallSquare1, SmallSquare2, GREEN, 1);
                Imgproc.circle(input, new Point(smallSquarePointX, smallSquarePointY), 2, GREEN, 1);
            }

            return input;
        }
    }
}
