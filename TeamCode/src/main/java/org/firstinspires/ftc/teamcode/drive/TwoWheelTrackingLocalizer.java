package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.PARALLEL_X_CT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.PARALLEL_Y_CT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.PERPENDICULAR_X_CT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.PERPENDICULAR_Y_CT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.X_MULTIPLIER_CT;
import static org.firstinspires.ftc.teamcode.Amin.NuSeMaiUmbla.Y_MULTIPLIER_CT;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    ^
 *    |
 *    | ( x direction)
 *    |
 *    v
 *    <----( y direction )---->

 *        (forward)
 *    /--------------\
 *    |     ____     |
 *    |     ----     |    <- Perpendicular Wheel
 *    |           || |
 *    |           || |    <- Parallel Wheel
 *    |              |
 *    |              |
 *    \--------------/
 *
 */

@Config()
public class TwoWheelTrackingLocalizer extends TwoTrackingWheelLocalizer {
    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_RADIUS = 1.96; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double PARALLEL_X = 3.267717; // X is the up and down direction
    public static double PARALLEL_Y = -0.51; // Y is the strafe direction

    public static double PERPENDICULAR_X = 13.580315;
    public static double PERPENDICULAR_Y = 0;

//    public static double PARALLEL_X = 3.346457; // X is the up and down direction
//    public static double PARALLEL_Y = 1.377953; // Y is the strafe direction
//
//    public static double PERPENDICULAR_X = 7.637795;
//    public static double PERPENDICULAR_Y = 0;

    public static double X_MULTIPLIER = 1;
    public static double Y_MULTIPLIER = 1;

//    public static double X_MULTIPLIER = X_MULTIPLIER_CT;
//    public static double Y_MULTIPLIER = Y_MULTIPLIER_CT;

    // Parallel/Perpendicular to the forward axis
    // Parallel wheel is parallel to the forward axis
    // Perpendicular is perpendicular to the forward axis
    private Encoder parallelEncoder, perpendicularEncoder;

    private SampleMecanumDrive drive;

    private double conversion = 2.54;

    public TwoWheelTrackingLocalizer(HardwareMap hardwareMap, SampleMecanumDrive drive) {
        super(Arrays.asList(
                new Pose2d(PARALLEL_X, PARALLEL_Y, 0),
                new Pose2d(PERPENDICULAR_X, PERPENDICULAR_Y, Math.toRadians(90))
        ));

//        PARALLEL_X /= conversion; // X is the up and down direction
//        PARALLEL_Y /= conversion; // Y is the strafe direction
//
//        PERPENDICULAR_X /= conversion;
//        PERPENDICULAR_Y /= conversion;

        this.drive = drive;

        parallelEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "par"));
        perpendicularEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "per"));

        perpendicularEncoder.setDirection(Encoder.Direction.REVERSE);

        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @Override
    public double getHeading() {
        return drive.getRawExternalHeading();
    }

    @Override
    public Double getHeadingVelocity() {
        return drive.getExternalHeadingVelocity();
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(parallelEncoder.getCurrentPosition()) * X_MULTIPLIER,
                encoderTicksToInches(perpendicularEncoder.getCurrentPosition()) * Y_MULTIPLIER
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        // TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
                encoderTicksToInches(parallelEncoder.getCorrectedVelocity()) * X_MULTIPLIER,
                encoderTicksToInches(perpendicularEncoder.getCorrectedVelocity()) * Y_MULTIPLIER
        );
    }
}