//package com.example.myapplication;
//
//import java.io.IOException;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class PostData {
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//
//    final OkHttpClient client = new OkHttpClient();
//
//    String post(String url, String json) throws IOException {
////        RequestBody body = RequestBody.create(json, JSON);
////        RequestBody requestBody =new MultipartBody.Builder()
////                .setType(MultipartBody.FORM)
////                .addFormDataPart("imageFile", imageFile.getName(), RequestBody.create(imageFile, mediaType))
////                .addFormDataPart("machineKey", machineKey)
////                .addFormDataPart("authToken", authToken)
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }
//
//    String dataJson(String nom, String prenom ,String date_de_naissance,String lieu_de_naissance,String signe,String numero,String lieu,String profession,String pere,String mere ,String fait ,String le) {
////        return "{'nom':'"+nom+"',"
////                + "'prenom':'"+prenom+"',"
////                + "'date_de_naissance':'"+date_de_naissance+"',"
////                + "'lieu_de_naissance':'"+lieu_de_naissance+"',"
////                + "'signe':'"+signe+"',"
////                + "'numero':'"+numero+"',"
////                + "'lieu':'"+lieu+"',"
////                + "'profession':'"+profession+"',"
////                + "'pere':'"+pere+"',"
////                + "'mere':'"+mere+"',"
////                + "'fait':'"+fait+"',"
////                + "'le':'"+le+"'}";
//        return "{"nom":"sitraka"}";
//    }
//}
