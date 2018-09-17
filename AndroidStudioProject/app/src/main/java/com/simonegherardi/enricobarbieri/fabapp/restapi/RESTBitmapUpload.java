package com.simonegherardi.enricobarbieri.fabapp.restapi;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class RESTBitmapUpload implements Runnable, IRESTable {
    Bitmap photo;
    RESTResponse restResponse;
    IRESTable callback;
    public RESTBitmapUpload()
    {

    }
    public RESTResponse Init(Bitmap photo, IRESTable callback)
    {
        this.photo = photo;
        this.callback = callback;
        restResponse = new RESTResponse();
        return restResponse;
    }
    @Override
    public void run() {
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, Base64.DEFAULT, baos);
        byte[] b = baos.toByteArray();

        String result = Base64.encodeToString(b, Base64.DEFAULT);
        //String result = new String(encoded);*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        WebServer.Main().GenericRequest(HttpMethod.POST, Table.upload, new JSON(encodedImage), "image/jpeg", this);
    }

    @Override
    public void Success(RESTResponse response) {
        restResponse.SetRequestType(response.GetRequestType());
        restResponse.SetResponse(response.GetResponse());
        callback.Success(restResponse);
    }

    @Override
    public void Error(RESTResponse response) {
        Log.d("tag", "errore");
    }
}
