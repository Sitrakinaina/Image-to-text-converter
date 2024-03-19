package com.example.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.google.mlkit.vision.face.FaceDetectorOptions;


import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView clear ,getImage,copy ,facerecogn;
    EditText rcgtext;
    Uri imageUri;
    TextRecognizer textRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        clear = findViewById(R.id.clear);
        copy = findViewById(R.id.copy);
        getImage= findViewById(R.id.getImage);
        rcgtext =findViewById(R.id.rcgtext);
        facerecogn =findViewById(R.id.facerecogn);
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=rcgtext.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(MainActivity.this,"there is no text to copy",Toast.LENGTH_SHORT).show();

                }else {
                    ClipboardManager clipboardManager = (ClipboardManager)  getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Data",rcgtext.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(MainActivity.this , "Text copy to clipboard" , Toast.LENGTH_SHORT).show();
                }
                }

        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = rcgtext.getText().toString();
                if(text.isEmpty()) {
                    Toast.makeText(MainActivity.this , "there is no text to clear" ,Toast.LENGTH_SHORT).show();

                }else{
                    rcgtext.setText("");

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(data!=null){
                imageUri =data.getData();
                Toast.makeText(MainActivity.this , "image selected", Toast.LENGTH_SHORT).show();
                    recognizeFace();
                    recognizeText();
            }
        }else {
            Toast.makeText(MainActivity.this , "image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void recognizeFace() {
        if(imageUri!=null){
            try {
                InputImage inputImage =InputImage.fromFilePath(MainActivity.this,imageUri);
                FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                        .build();
                FaceDetector detector = FaceDetection.getClient(options);

                detector.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                Bitmap bitmap;
                                try {
                                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return;
                                }
                                Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                                Canvas canvas = new Canvas(mutableBitmap);
                                Paint paint = new Paint();
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.STROKE);
                                paint.setStrokeWidth(5f);

                                for (Face face : faces) {
                                    float left = face.getBoundingBox().left;
                                    float top = face.getBoundingBox().top;
                                    float right = face.getBoundingBox().right;
                                    float bottom = face.getBoundingBox().bottom;
                                    canvas.drawRect(left, top, right, bottom, paint);
                                }
                                if(!faces.isEmpty()){
                                    Face face = faces.get(0); // On suppose qu'il y a un seul visage dans l'image
                                    Rect boundingBox = face.getBoundingBox();
                                    float left = boundingBox.left-20;
                                    float top = boundingBox.top-20;
                                    float right = boundingBox.right+20;
                                    float bottom = boundingBox.bottom+20;
                                    // Calculer les dimensions du visage
                                    int faceWidth = (int) (right - left);
                                    int faceHeight = (int) (bottom - top);


                                    // Assurer que les coordonnées ne dépassent pas les dimensions de l'image
                                    if (left < 0) left = 0;
                                    if (top < 0) top = 0;
                                    if (right > bitmap.getWidth()) right = bitmap.getWidth();
                                    if (bottom > bitmap.getHeight()) bottom = bitmap.getHeight();
//                                    Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, (int) left, (int) top, (int) bottom, (int) right);
                                    Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, (int) left, (int) top, faceWidth, faceHeight);
                                    facerecogn.setImageBitmap(croppedBitmap);
                                }
//                                face.setImageBitmap(mutableBitmap);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    private void recognizeText() {
        if(imageUri!=null){
            try {
                InputImage inputImage =InputImage.fromFilePath(MainActivity.this,imageUri);

                Task<Text> result =textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text texts) {
                                    String recognizeText =texts.getText();
                                    rcgtext.setText(recognizeText);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}