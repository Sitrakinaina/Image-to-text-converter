package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class cardDetector extends AppCompatActivity {
    OkHttpClient client;
    ImageView imagedetect1 ;
    ImageView imagedetect2 ;
    Uri imageUri;
    Uri imageUri1;
    Uri imageUri2;
    Boolean image1selected;
    Boolean image2selected;
    Button submit;
    TextView json;
    EditText nom,prenom,date_de_naissance,lieu_de_naissance,signe,numero,lieu,profession,pere,mere,fait,le;
   private List<Bitmap> bitmapArray = new ArrayList<>();
   private List<EditText> editTextList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_detector2);
        imagedetect1 =findViewById(R.id.imagedetect1);
        imagedetect2=findViewById(R.id.imagedetect2);
        submit  = findViewById(R.id.submit);

        EditText nom =(EditText) findViewById(R.id.nom);
        EditText prenom =(EditText)findViewById(R.id.prenom);
        EditText date_de_naissance=(EditText)findViewById(R.id.date_de_naissance);
        EditText lieu_de_naissance =(EditText)findViewById(R.id.lieu_de_naissance);
        EditText signe =(EditText) findViewById(R.id.signe_particulier);
        EditText numero =(EditText) findViewById(R.id.numero);
        EditText lieu =(EditText) findViewById(R.id.lieu);
        EditText profession = (EditText) findViewById(R.id.profession);
        EditText pere = (EditText) findViewById(R.id.pere);
        EditText mere =(EditText) findViewById(R.id.mere);
        EditText fait =(EditText) findViewById(R.id.fait);
        EditText le =(EditText) findViewById(R.id.le);
        TextView json =findViewById(R.id.json);
        client=new OkHttpClient();

        image1selected=false;
        image2selected=false;
        imagedetect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image1selected=true;
                image2selected=false;
                ImagePicker.with(cardDetector.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        imagedetect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image2selected=true;
                image1selected=false;
                ImagePicker.with(cardDetector.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    post(
                            nom.getText().toString(),
                            prenom.getText().toString(),
                            date_de_naissance.getText().toString(),
                            lieu_de_naissance.getText().toString(),
                            signe.getText().toString(),
                            numero.getText().toString(),
                            lieu.getText().toString(),
                            profession.getText().toString(),
                            pere.getText().toString(),
                            mere.getText().toString(),
                            fait.getText().toString(),
                            le.getText().toString(),
                            imageUri1.getPath(),
                            imageUri2.getPath()
                            );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
public void post(
        String nom,
        String prenom,
        String date_de_naissnace,
        String lieu_de_naissance,
        String signe,
        String numero,
        String lieu,
        String profession ,
        String pere ,
        String mere,
        String fait,
        String le,
        String image1,
        String image2
        ) throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    // Your network activity
                    // code comes here
//                    PostData postData =new PostData();
//                    String jsonData =postData.dataJson(nom,prenom,date_de_naissnace,lieu_de_naissance,signe,numero,lieu,profession,pere,mere,fait,le);
//                    System.out.println(jsonData);
//                    String response =postData.post("http://192.168.43.237:5000/create",jsonData);
//                    System.out.println(response);
                    File file1=new File(image1);
                    File file2=new File(image2);
                    client=new OkHttpClient();
                    System.out.println("imageUri"+image1);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("nom", nom)
                            .addFormDataPart("prenom",prenom)
                            .addFormDataPart("date_de_naissance",date_de_naissnace)
                            .addFormDataPart("lieu_de_naissance",lieu_de_naissance)
                            .addFormDataPart("signe",signe)
                            .addFormDataPart("numero",numero)
                            .addFormDataPart("lieu",lieu)
                            .addFormDataPart("profession",profession)
                            .addFormDataPart("pere",pere)
                            .addFormDataPart("mere",mere)
                            .addFormDataPart("fait",fait)
                            .addFormDataPart("le",le)
                            .addFormDataPart("file1",file1.getPath() ,RequestBody.create(file1, MediaType.parse("image/*")))
                            .addFormDataPart("file2",file2.getPath(), RequestBody.create(file2, MediaType.parse("image/*")))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.168.43.237:3000/create")
                            .post(requestBody)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        // Do something with the response.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

//    Request request=new Request.Builder().url("http://10.10.11.29:5000").build();
//    client.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onFailure(@NonNull Call call, @NonNull IOException e) {
//            System.out.println("MISY OLANA "+e.getMessage());
//            e.printStackTrace();
//        }
//
//        @Override
//        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//            try {
//                String jsonData = response.body().string();
//                System.out.println("ty le data"+jsonData);
////                    textView.setText(jsonData);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            if (data != null) {

                //
                imageUri =data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    bitmapArray.add(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(imageUri!=null){
                    if(image1selected) {
                        Toast.makeText(cardDetector.this, "image  selected", Toast.LENGTH_SHORT).show();
                        System.out.println(imageUri);
                        imageUri1=imageUri;
                        imagedetect1.setImageURI(imageUri);

//                        imageToPdf(data);
                    }else {
                        Toast.makeText(cardDetector.this, "image  selected", Toast.LENGTH_SHORT).show();
                        System.out.println(imageUri);
                        imageUri2=imageUri;
                        imagedetect2.setImageURI(imageUri);
//                        imageToPdf(data);
                    }
//                    if(bitmapArray.size() == 2){
//                        Context context=getApplicationContext();
//                        ConvertToPdf.imageToPdf(bitmapArray,context);
//                    }
                }
            } else {
                Toast.makeText(cardDetector.this, "image not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}