package com.simonegherardi.enricobarbieri.fabapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.fragments.ImageGalleryFragment;
import com.simonegherardi.enricobarbieri.fabapp.fragments.MenuFragment;
import com.simonegherardi.enricobarbieri.fabapp.fragments.ProfileFragment;
import com.simonegherardi.enricobarbieri.fabapp.resources.Image;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncObserver;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ResourceSynchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Synchronizer;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends FragmentAwareActivity implements IRESTable, ISyncObserver {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private FloatingActionButton button;
    public int userId;
    public boolean isUser;
    private boolean isUploading;
    private RESTResponse imageResponse;
    private ProgressBar progressBar;
    String mCurrentPhotoPath;
    ImageGalleryFragment galleryFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        Bundle b = getIntent().getExtras();
        //this.container = R.id.profile_menu;
        //this.AddFragment(new MenuFragment());
        int value = 0; // or other values
        if(b != null) {
            userId = value = b.getInt(getString(R.string.idKey));
        }
        if(value == this.userSharedPref.getInt(getString(R.string.idKey),0))
        {
            isUser = true;
        }
        this.container = R.id.profile_info;
        profileFragment = (ProfileFragment) this.GetProfileInfoFragment(value);
        this.SetFragment(profileFragment);
        this.container = R.id.profile_gallery;
        galleryFragment = (ImageGalleryFragment) this.GetProfileGalleryFragment(value);
        this.SetFragment(galleryFragment);
        // galleryFragment.getRecyclerView().setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        progressBar = this.findViewById(R.id.image_upload_progress);
        button = this.findViewById(R.id.addImageButton);
        if(isUser)
        {
            button.setVisibility(View.INVISIBLE);
        }
        else
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUploading = true;
                    SetButtonClickable(false);
                    DispatchTakePictureIntent();
                }
            });
        }
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

       getSharedPreferences(getString(R.string.imageUploadInfo), Context.MODE_PRIVATE).edit().putString("imagePath", this.mCurrentPhotoPath).commit();
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        this.mCurrentPhotoPath = getSharedPreferences(getString(R.string.imageUploadInfo), Context.MODE_PRIVATE).getString("imagePath", "");
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        this.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void DispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, ProfileActivity.REQUEST_TAKE_PHOTO);
            }
            //startActivityForResult(takePictureIntent, ProfileActivity.REQUEST_IMAGE_CAPTURE);
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mCurrentPhotoPath = getSharedPreferences(getString(R.string.imageUploadInfo), Context.MODE_PRIVATE).getString("imagePath", "");
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if(resultCode == RESULT_OK) {
                galleryAddPic();
                SetProgressBarVisibility(View.VISIBLE);
                imageResponse = WebServer.Main().BitmapUpload(CurrentFileToBitmap(), this);
            }
            else
            {
                SetButtonClickable(true);
            }
        }
    }
    private Bitmap CurrentFileToBitmap()
    {
        // Get the dimensions of the bitmap
        int targetW = 1280;
        int targetH = 720;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }

        /*File sd = Environment.getExternalStorageDirectory();
        File image = new File(mCurrentPhotoPath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);*/
        //bitmap = Bitmap.createScaledBitmap(bitmap,this.getVie.getWidth(),parent.getHeight(),true);
        return rotatedBitmap;
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void SetProfileImage()
    {
        this.profileFragment.SetProfileImage(this.galleryFragment.GetLastPhoto());
    }

    @Override
    public void OnGalleryReady()
    {
        this.SetProfileImage();
    }

    @Override
    public void Success(RESTResponse response) {
        if(response == imageResponse)
        {
            String url = response.GetResponse();
            url = url.substring(1, url.length()-1);
            Image image = new Image();
            image.SetUrl(url);
            image.SetPhotographed(userId);
            image.SetPhotographer(this.userSharedPref.getInt(getString(R.string.idKey),0));
            ResourceSynchronizer is = new ResourceSynchronizer(image, this);
            is.Upload();
        }
    }
    private void SetProgressBarVisibility(final int visibility)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                progressBar.setVisibility(visibility);
            }
        });
    }
    private void SetButtonClickable(final boolean status)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                button.setClickable(status);
            }
        });
    }

    @Override
    public void Error(RESTResponse response) {
        SetProgressBarVisibility(View.INVISIBLE);
        SetButtonClickable(true);
    }

    @Override
    public void UploadStart() {

    }

    @Override
    public void UpdateStart() {

    }

    @Override
    public void DownloadStart() {

    }

    @Override
    public void DeleteStart() {

    }

    @Override
    public void UploadComplete(boolean result) {
        SetButtonClickable(true);
        this.isUploading = false;
        SetProgressBarVisibility(View.INVISIBLE);
        galleryFragment.UpdateImageList();
    }

    @Override
    public void UpdateComplete(boolean result) {

    }

    @Override
    public void DownloadComplete(boolean result) {

    }

    @Override
    public void DeleteComplete(boolean result) {

    }

}
