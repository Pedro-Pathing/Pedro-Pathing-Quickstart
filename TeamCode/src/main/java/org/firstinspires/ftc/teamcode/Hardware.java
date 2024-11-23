package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.GoBildaPinpointDriver;

/*
This is what position to connect the motors to on the control hub
Names refer to variable names

rf - 0
lf - 1
lb - 2
rb - 3
 */

//close value right = .154, open = .298, close left = .684, open  = .538
public class Hardware {
    public DcMotorEx rf;
    public DcMotorEx rb;
    public DcMotorEx lf;
    public DcMotorEx lb;
    public DcMotorEx armVertical;
    public DcMotorEx armExtension;
    public GoBildaPinpointDriver odo;

    public Servo leftServo;
    public Servo rightServo;

    public static double maxSpeed = 0.6;
    private static Hardware instance = null;
    public static Hardware getInstance() {
        if(instance == null){
            instance = new Hardware();
        }
        return instance;
    }
    public void init(HardwareMap hwMap){
        rf = hwMap.get(DcMotorEx.class, "rf");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setPower(0);
        //1

        rb = hwMap.get(DcMotorEx.class, "rr");
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setPower(0);
        //3

        lf = hwMap.get(DcMotorEx.class, "lf");
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setPower(0);
        //0

        lb = hwMap.get(DcMotorEx.class, "lr");
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setPower(0);

        armVertical = hwMap.get(DcMotorEx.class, "armV");
        armVertical.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armVertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armVertical.setPower(0);

        armExtension = hwMap.get(DcMotorEx.class, "armE");
        armExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armExtension.setPower(0);
        leftServo = hwMap.get(Servo.class, "leftServo");
        rightServo = hwMap.get(Servo.class, "rightServo");

        odo = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");

    }


    public void setPower(double fr, double br, double fl, double bl){
        rf.setPower(Range.clip(fr, -maxSpeed, maxSpeed));
        rb.setPower(Range.clip(br, -maxSpeed, maxSpeed));
        lf.setPower(Range.clip(fl, -maxSpeed, maxSpeed));
        lb.setPower(Range.clip(bl, -maxSpeed, maxSpeed));
    }
    public void setSpeed(double speed){
        maxSpeed = speed;
    }
    public void moveArmForward(double distance){
        armVertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armVertical.setTargetPosition((int) (distance * 1120));
        while(armVertical.isBusy()){
            telemetry.addData("Arm Vertical Encoder", armVertical.getCurrentPosition());
            telemetry.update();
        }
        armVertical.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armVertical.setPower(0);
    }
}