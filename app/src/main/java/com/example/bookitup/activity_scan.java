package com.example.bookitup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class activity_scan extends AppCompatActivity {

    CameraView camera_view;
    boolean isDetected = false;
    Button btn_start_again;
    private TextView edBook;
    private TextView edAuthor;
    private TextView edEdition;
    private TextView edIsbn;
    private TextView edCondition;
    private TextView edPrice;
    private TextView edDate;
    private TextView edDescription;
    private ImageView mImage;
    private TextView eSeller;

    private String key;
    public String book;
    private String author;
    private String edition;
    private String isbn;
    private String condition;
    private String price;
    private String date;
    private String description;
    private String seller;


    private FirebaseDatabase database;
    private RequestQueue mQueue;
    BookActivity bookActivity;
    FirebaseVisionBarcodeDetector detector;
    FirebaseVisionBarcodeDetectorOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent intent = getIntent();
         author =  intent.getStringExtra("Author:");

        Dexter.withContext (this)
                .withPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        setupCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }


                }).check();

    }




    //s Setup of Camera View
    private void setupCamera() {
        btn_start_again = (Button) findViewById(R.id.btn_again);
        btn_start_again.setEnabled((isDetected));
        btn_start_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDetected = !isDetected;
            }
        });

        camera_view = (CameraView) findViewById(R.id.cameraView);
        camera_view.setLifecycleOwner(this);
        camera_view.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                processImage(getVisionImageFromFrame(frame));
            }
        });

        // selecting only format EAN 13 which is specifically for isbn 13
        options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_EAN_13)
                .build();
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    }

    // Processes the images
    private void processImage(FirebaseVisionImage image) {

        if(!isDetected)
        {
            detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                            processResult(firebaseVisionBarcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_scan.this, "This is error", Toast.LENGTH_SHORT);
                        }
                    });
        }
    }

    // processes the result and start add book activity
    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {

        if(firebaseVisionBarcodes.size() > 0)
        {
            isDetected = true;
            btn_start_again.setEnabled(isDetected);
            for(FirebaseVisionBarcode item: firebaseVisionBarcodes)
            {
                int value_type = item.getValueType();
                switch (value_type)
                {
                    //type_isbn looks specifically for isbn
                    case FirebaseVisionBarcode.TYPE_ISBN:
                    {

                        //createDialog(item.getRawValue());
                        //invokes the AddBook class
//                        Intent intent = new Intent(activity_scan.this, AddBookActivity.class);
//                        startActivity(intent);
//                        invokeAdd(item.getRawValue());
                        populateData(item.getRawValue());
                        break;
                    }
                }
            }
        }

    }

    private void populateData(String rawValue) {
        isbn = rawValue;
        System.out.println(isbn);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject jsonObject1 = items.getJSONObject(i);
                                JSONObject volumeInfo = jsonObject1.getJSONObject("volumeInfo");

                                // other info and objects experiment
                                if(volumeInfo.has("title")){
                                    book = volumeInfo.getString("title");
                                    author = volumeInfo.getString("authors");
                                }

//                                if(volumeInfo.has("imageLinks")){
//                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
//                                    String imgLink = imageLinks.getString("smallThumbnail").substring(4);
//                                     Glide.with(getApplicationContext()).load("https"+imgLink).error(R.drawable.ic_nocover).into(mImage);
//
//                                    System.out.println(imgLink);
//
//
//                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //createDialog(author);

                        Intent ResultIntent = new Intent(activity_scan.this, AddBookActivity.class);
                        ResultIntent.putExtra("bookname:", book);
                        ResultIntent.putExtra("Book ISBN:", isbn);
                        ResultIntent.putExtra("author", author);

                        setResult(RESULT_OK, ResultIntent);
                        finish();
//                        startActivity(ResultIntent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        mQueue.add(jsonObjectRequest);
    }

    // will be used later
//    private void invokeAdd(String rawValue) {
//        Intent intent = new Intent(activity_scan.this, AddBookActivity.class);
//        startActivity(intent);
//    }
//    //was used to make a dialog box and show the isbn number
    private void createDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //gets the firebaseVisionImage data
    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {

        byte[] data = frame.getData();
        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setHeight(frame.getSize().getHeight())
                .setWidth(frame.getSize().getWidth())
                //.setRotation(frame.getRotationToUser())
                .build();
        return FirebaseVisionImage.fromByteArray(data, metadata);
    }






}
