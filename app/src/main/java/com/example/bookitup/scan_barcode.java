//package com.example.bookitup;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//import com.karumi.dexter.listener.single.PermissionListener;
//import com.otaliastudios.cameraview.CameraView;
//import com.otaliastudios.cameraview.frame.Frame;
//import com.otaliastudios.cameraview.frame.FrameProcessor;
//
//import java.util.List;
//
//
//public class scan_barcode extends AppCompatActivity {
//
//    CameraView camera_view;
//    boolean isDetected = false;
//    Button btn_start_again;
//
//    FirebaseVisionBarcodeDetector detector;
//    FirebaseVisionBarcodeDetectorOptions options;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//
//        Dexter.withActivity (this)
//                .withPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                        setupCamera();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//
//                    }
//
//
//                }).check();
//
//    }
//
//
//
//
//
//    private void setupCamera() {
//        btn_start_again = (Button) findViewById(R.id.btn_again);
//        btn_start_again.setEnabled((isDetected));
//        btn_start_again.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isDetected = !isDetected;
//            }
//        });
//
//        camera_view = (CameraView) findViewById(R.id.cameraView);
//        camera_view.setLifecycleOwner(this);
//        camera_view.addFrameProcessor(new FrameProcessor() {
//            @Override
//            public void process(@NonNull Frame frame) {
//                processImage(getVisionImageFromFrame(frame));
//            }
//        });
//
//        options = new FirebaseVisionBarcodeDetectorOptions.Builder()
//                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_EAN_13)
//                .build();
//        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
//    }
//
//    private void processImage(FirebaseVisionImage image) {
//
//        if(!isDetected)
//        {
//            detector.detectInImage(image)
//                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
//                        @Override
//                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//                            processResult(firebaseVisionBarcodes);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(scan_barcode.this, "This is error", Toast.LENGTH_SHORT);
//                        }
//                    });
//        }
//    }
//
//    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//
//        if(firebaseVisionBarcodes.size() > 0)
//        {
//            isDetected = true;
//            btn_start_again.setEnabled(isDetected);
//            for(FirebaseVisionBarcode item: firebaseVisionBarcodes)
//            {
//                int value_type = item.getValueType();
//                switch (value_type)
//                {
//                    case FirebaseVisionBarcode.TYPE_TEXT:
//                    {
//                        createDialog(item.getRawValue());
//                    }
//                }
//            }
//        }
//
//    }
//
//    private void createDialog(String text) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(text)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {
//
//        byte[] data = frame.getData();
//        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
//                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//                .setHeight(frame.getSize().getHeight())
//                .setWidth(frame.getSize().getWidth())
//                .setRotation(frame.getRotationToUser())
//                .build();
//        return FirebaseVisionImage.fromByteArray(data, metadata);
//    }
//
//
//
//
//
//
//}
