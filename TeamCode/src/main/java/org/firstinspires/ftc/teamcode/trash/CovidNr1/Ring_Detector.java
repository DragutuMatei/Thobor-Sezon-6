package org.firstinspires.ftc.teamcode.trash.CovidNr1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "Ring_Detector",group = "Alex")
public class Ring_Detector extends LinearOpMode{

    //Hardware
    OpenCvCamera phoneCam;

    static double ringCount = 0;

    //17.5 cm

    //Cel mare
    /*int rect1X = 80;
    int rect1Y = 130;
    Cel mic
    int rect2X = 80;
    int rect2Y = 200;*/

    //26.5 cm
   
    //Cel mare
    /*int rect1X = 80;
    int rect1Y = 130;
    Cel mic
    int rect2X = 80;
    int rect2Y = 180;*/

    //54.5 cm

    //Cel mare 
    /*int rect1X = 100;
    int rect1Y = 140;
    Cel mic
    int rect2X = 100;
    int rect2Y = 160;*/

    int rect1X = 120;
    int rect1Y = 130;

    int rect2X = 120;
    int rect2Y = 150;


    /*
    @Override
    public void init() {
        if(ringCount == 1){
        }else{
         }
        Moving section
    }*/

    @Override
    public void runOpMode(){
        //Hardware Maps

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK,cameraMonitorViewId);

        final int cameraMonitorViewIds = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewIds);


        //Detector

        phoneCam.setPipeline(new RingDetectingPipeline());

        //Camera Loop

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened(){
                phoneCam.startStreaming(320,240,OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }

        });

    }

    //Visual Stuff
    class RingDetectingPipeline extends OpenCvPipeline{

        Mat YCbCr = new Mat();
        Mat outPut = new Mat();
        Mat upperCrop = new Mat();
        Mat lowerCrop = new Mat();


    //Normal
        @Override
        public Mat processFrame(Mat input){
            //Image Conversion
            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);

            input.copyTo(outPut);


            //17.5cm aprox. 18cm
            /*Rect rect1 = new Rect(rect1X, rect1Y, 100, 70);
            Rect rect2 = new Rect(rect2X, rect2Y, 100,20);*/

            //26cm aprox 26.5cm
            /*Rect rect1 = new Rect(rect1X, rect1Y, 70, 50);
            Rect rect2 = new Rect(rect2X, rect2Y, 70,20);*/

            //54.5cm aprox 55cm
            Rect rect1 = new Rect(rect1X, rect1Y, 3, 20);
            Rect rect2 = new Rect(rect2X, rect2Y, 3,3);

            Scalar rectangleColor = new Scalar(0,0,255);

//            Screen rectangles
            Imgproc.rectangle(outPut, rect1,rectangleColor, 1);
            Imgproc.rectangle(outPut, rect2,rectangleColor, 1);

            //Crop the image

            //First ring and three rings above box
            lowerCrop = YCbCr.submat(rect1);
            upperCrop = YCbCr.submat(rect2);

            //Taking the orange color out, placing on mat
            Core.extractChannel(lowerCrop, lowerCrop, 2);
            Core.extractChannel(upperCrop, upperCrop, 2);

            //Take the raw average data, put it on a Scalar variable
            Scalar lowerAverageOrange = Core.mean(lowerCrop);
            Scalar upperAverageOrange = Core.mean(upperCrop);

            //Taking the first value of average and putting it in a variable
            double finalLowerAverage = lowerAverageOrange.val[0];
            double finalUpperAverage = upperAverageOrange.val[0];

            //Comparing Values
            if(finalLowerAverage > 18  && finalLowerAverage < 120 && finalUpperAverage < 120){
                ringCount = 4.0;
                telemetry.addData("Cate plm sunt","4 plm");

            }else if(finalLowerAverage > 10 && finalUpperAverage < 120 && finalLowerAverage > 10 && finalUpperAverage < 120){
                ringCount = 1.0;
                telemetry.addData("Cate plm sunt","1 plm");

            }else{
                ringCount = 0.0;
                telemetry.addData("Cate plm sunt","0 plm");
            }

            telemetry.update();
            //Viewport Preview
            return outPut;
        }
    }
}
