package com.comp3350.recip_e.logic;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageManager {
    private Context context;

    public ImageManager(Context context) {
        this.context = context;
    }

    /**
     * Save an image into internal storage
     *
     * @param uri Image to save
     * @return String representing the file path
     */
    public String saveImage(Uri uri) {
        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE);
        File path = new File(directory, System.currentTimeMillis() + "_image.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path.toString();
    }

    public Bitmap getImage(String path) {
        return null;
    }
}
