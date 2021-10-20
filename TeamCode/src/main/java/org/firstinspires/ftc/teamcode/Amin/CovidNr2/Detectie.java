package org.firstinspires.ftc.teamcode.Amin.CovidNr2;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Detectie extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();

    public Detectie(Telemetry t) {
        this.telemetry = t;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar x = new Scalar(23, 34, 54);



        return null;
    }
}
