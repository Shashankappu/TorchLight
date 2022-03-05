package com.example.torch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    boolean isTorchOn = false;
    Button btnTorch;
    CameraManager cameraManager;
    String CameraId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isTorchAvailable = getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        btnTorch  = findViewById(R.id.btn_Torch);

        if(!isTorchAvailable)
        {
            btnTorch.setEnabled(false);
            btnTorch.setText("Torch is Not Available");
        }

        try{
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            CameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        btnTorch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTorchOn){
                    isTorchOn = false;
                    btnTorch.setText("Turn On Torch");
                }else{
                    isTorchOn = true;
                    btnTorch.setText("Turn Off Torch");
                }
                switchTorch(isTorchOn);
            }
        });

    }
    public void switchTorch(boolean isTorchOn){
        try {
            cameraManager.setTorchMode(CameraId,isTorchOn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}