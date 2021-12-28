package org.firstinspires.ftc.teamcode.Amin.incercareDetectie3Patrate;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Detectie extends OpenCvPipeline {
    Mat mat = new Mat();

    public UNDE_E unde_e;

    static final Rect LEFT_ROI = new Rect(
            new Point(60, 35),
            new Point(120, 75)
    );

    static final Rect CENTER_ROI = new Rect(
            new Point(140, 35),
            new Point(200, 75)
    );

    static final Rect RIGHT_ROI = new Rect(
            new Point(220, 35),
            new Point(280, 75)
    );


    static double PERCENT_COLOR_THRESHOLD = 0.1;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        Scalar lowHSV = new Scalar(45, 100, 10);
        Scalar highHSV = new Scalar(75, 255, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat center = mat.submat(CENTER_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double centerValue = Core.sumElems(center).val[0] / CENTER_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

        left.release();
        center.release();
        right.release();

        boolean stoneLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean stoneCenter = centerValue > PERCENT_COLOR_THRESHOLD;
        boolean stoneRight = rightValue > PERCENT_COLOR_THRESHOLD;

        if (stoneCenter) {
            unde_e = UNDE_E.CENTER;
        } else if (stoneLeft) {
            unde_e = UNDE_E.LEFT;
        } else if (stoneRight) {
            unde_e = UNDE_E.RIGHT;
        }

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar nup = new Scalar(255, 0, 0);
        Scalar verde = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, unde_e == unde_e.LEFT ? verde : nup);
        Imgproc.rectangle(mat, CENTER_ROI, unde_e == unde_e.CENTER ? verde : nup);
        Imgproc.rectangle(mat, RIGHT_ROI, unde_e == unde_e.RIGHT ? verde : nup);

        return mat;
    }

    public UNDE_E getUnde_e() {
        return unde_e;
    }
}