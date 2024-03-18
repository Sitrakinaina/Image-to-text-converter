package com.example.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
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
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView clear ,getImage,copy ;
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
                recognizeText();
            }
        }else {
            Toast.makeText(MainActivity.this , "image not selected", Toast.LENGTH_SHORT).show();

        }

    }

    private void recognizeText() {
        if(imageUri!=null){
            try {
                InputImage inputImage =InputImage.fromFilePath(MainActivity.this,imageUri);

                Task<Text> result =textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                                    String recognizeText =text.getText();
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