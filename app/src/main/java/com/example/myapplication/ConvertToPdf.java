package com.example.myapplication;

import static android.content.ContentValues.TAG;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.kerols.pdfconverter.CallBacks;
import com.kerols.pdfconverter.InSize;
import com.kerols.pdfconverter.PdfImageSetting;
import com.kerols.pdfconverter.PdfPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConvertToPdf {
    public static void imageToPdf(List<Bitmap> bitmapArray, Context context) {
        PdfPage pdfPage = new PdfPage(context);
        pdfPage.setPageSize(1100,1100);
        PdfImageSetting mPdfImageSetting = new PdfImageSetting();
        mPdfImageSetting.setImageSize(InSize.IMAGE_SIZE);
        mPdfImageSetting.setMargin(220,220,220,220);

        pdfPage.add(mPdfImageSetting);

        com.kerols.pdfconverter.ImageToPdf imageToPdf = new com.kerols.pdfconverter.ImageToPdf(pdfPage,context);
        imageToPdf.BitmapToPDF((ArrayList<Bitmap>) bitmapArray,
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        "imagetopdf.pdf"), new CallBacks() {
                    @Override
                    public void onFinish(String path) {
                        Toast.makeText(context,"onFinish",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(context,"onError",Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: ", throwable );
                    }

                    @Override
                    public void onProgress(int progress , int max) {
                        Log.e(TAG, "onProgress: " +  progress  + "  " +  max );

                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(context,"onCancel",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onStart() {
                        Toast.makeText(context,"onStart",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
