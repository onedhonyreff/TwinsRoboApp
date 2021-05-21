package com.codepan.twinsrobo_apps.OtherClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class EncodeDecodeImage {

//    String stringImage;
//    Bitmap bitmapImage;

//    public EncodeDecodeImage(String stringImage, Bitmap bmpImage){
//        this.stringImage = stringImage;
//        this.bitmapImage = bmpImage;
//    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        /*
        jika quality -100- maka pertimbangkan ukuran String image akan membuat aplikasi force close
        saat transaksi data untuk mengambil data savedInstanceState
         */
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
    }

    public Bitmap getBitmapImage(String stringImage) {
        byte[] imageBytes = Base64.decode(stringImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        Bitmap hasilDecode = BitmapFactory.decodeByteArray(imageBytes, 0 , imageBytes.length);
//        return hasilDecode;
    }
}
