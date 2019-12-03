package com.eis.dailycallregister.Activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CapNUpSelfie extends AppCompatActivity {


    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    public LinearLayout ll1, ll2;
    public RelativeLayout rl;
    public String cntcd="",doctorname="",keycontactper="", phonenumber="",chemistname="",doccntcd="",flag="";
    public boolean isimgcropped = false;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    final int CROP_PIC = 98;
    private Uri picUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    ViewDialog progressDialoge;
    private Uri fileUri; // file url to store image/video

    private ImageView btnCapturePicture, cardpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nup_selfie);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        cntcd = getIntent().getStringExtra("cntcd");
        chemistname = getIntent().getStringExtra("chemistname");
        doctorname = getIntent().getStringExtra("doctorname");
        keycontactper = getIntent().getStringExtra("keycontactper");
        phonenumber = getIntent().getStringExtra("phonenumber");
        doccntcd = getIntent().getStringExtra("doccntcd");
        flag = getIntent().getStringExtra("flag");
      //status = getIntent().getStringExtra("status");
        // Changing action bar background color
        // These two lines are not needed
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>Take Selfie</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        btnCapturePicture = findViewById(R.id.btnCapturePicture);
        cardpic = findViewById(R.id.cardpic);
        progressDialoge = new ViewDialog(CapNUpSelfie.this);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);


        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });


           // capture picture
                captureImage();

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }


    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            Log.d("fileUri",fileUri.toString());

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            intent.putExtra("return-data", true);
                            // start the image capture Intent
                            //startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            Log.d("requestCode1",""+requestCode);
            if (resultCode == RESULT_OK) {
                Log.d("requestCode2",""+requestCode);

                //picUri = data.getData();
                //Log.d("pic uri 1",picUri.toString());
                performCrop();

                //todo call crop image method
                // successfully captured the image
                // launching upload activity

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 98) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {
                isimgcropped = true;
                launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                isimgcropped = false;
                picUri = fileUri;
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
            /*else if (resultCode == RESULT_CANCELED) {
                picUri = fileUri;
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            }*/
        } else if (requestCode == 3) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                isimgcropped = true;
                launchUploadActivity(true);
            } else if (resultCode == RESULT_CANCELED) {
                picUri = Uri.parse(getRealPathFromURI(fileUri));
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                isimgcropped = false;
                //Log.d("gallery pic path 2222",fileUri.toString());
                launchUploadActivity(true);
                Toast.makeText(getApplicationContext(),
                        "Crop operation canceled !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == 99) {
            //picUri = data.getExtras();
            //Log.d("pic uri 2",picUri.toString());
            if (resultCode == RESULT_OK) {
                fileUri = data.getData();
                //Log.d("gallery pic path",fileUri.toString());
                performCrop2();
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            //cropIntent.setClassName("com.google.android.gallery3d", "com.android.gallery3d.app.CropImage");
            //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // indicate image type and Uri
            //cropIntent.setDataAndType(fileUri, "image/*");
            //Log.d("fileUri 1",fileUri.toString());
            cropIntent.setDataAndType(fileUri, "image/*");

            // set crop properties
            cropIntent.putExtra("crop", "true");

            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            //cropIntent.putExtra("scaleUpIfNeeded",true);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);

            File f = createNewFile();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                // Log.e("io", ex.getMessage());
            }

            picUri = Uri.fromFile(f);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
            /*} else {
                picUri = fileUri;
                launchUploadActivity(true);
                Toast toast = Toast
                        .makeText(this, "Device version below 5.0 doesn't support the crop action !", Toast.LENGTH_SHORT);
                toast.show();
            }*/

        } catch (ActivityNotFoundException anfe) {

            picUri = fileUri;
            launchUploadActivity(true);
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void performCrop2() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            //cropIntent.setClassName("com.google.android.gallery3d", "com.android.gallery3d.app.CropImage");
            //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // indicate image type and Uri
            //cropIntent.setDataAndType(fileUri, "image/*");
            //Log.d("fileUri 1",fileUri.toString());
            cropIntent.setDataAndType(fileUri, "image/*");

            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            cropIntent.putExtra("scaleUpIfNeeded", true);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);

            File f = createNewFile();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                // Log.e("io", ex.getMessage());
            }

            picUri = Uri.fromFile(f);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 3);
            /*} else {
                picUri = Uri.parse(getRealPathFromURI(fileUri));
                launchUploadActivity(true);
                Toast toast = Toast
                        .makeText(this, "Device version below 5.0 doesn't support the crop action !", Toast.LENGTH_SHORT);
                toast.show();
            }*/

        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

            picUri = fileUri;
            launchUploadActivity(true);
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private File createNewFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String prefix = "ABC_" + Global.netid + "_" + cntcd + "_" + timeStamp + ".jpg";

        File newDirectory = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                RetrofitClient.IMAGE_DIRECTORY_NAME);
        if (!newDirectory.exists()) {
            if (newDirectory.mkdir()) {
                //Log.d(CapNUpVstCard.this.getClass().getName(), newDirectory.getAbsolutePath()+" directory created");
            }
        }

        File file = new File(newDirectory, (prefix));
        if (file.exists()) {
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(CapNUpSelfie.this, "File creation Failed ! Try again.", Toast.LENGTH_LONG).show();
            }
        }

        return file;
    }

    private void launchUploadActivity(boolean isImage) {
        Intent i = new Intent(CapNUpSelfie.this, UploadSelFie.class);
        i.putExtra("filePath", picUri.getPath());
        i.putExtra("fileUri", fileUri.getPath());
        i.putExtra("isImage", isImage);
        i.putExtra("isimgcropped", isimgcropped);
        i.putExtra("cntcd", cntcd);
        i.putExtra("doccntcd", doccntcd);
        i.putExtra("chemistname", chemistname);
        i.putExtra("doctorname", doctorname);
        i.putExtra("keycontactper", keycontactper);
        i.putExtra("phonenumber", phonenumber);
        i.putExtra("flag", flag);
        startActivity(i);
        finish();
    }


    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type, cntcd));
    }

    /**
     * returning image / video
     */


    private static File getOutputMediaFile(int type, String cntcd) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                RetrofitClient.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                //Log.d(TAG, "Oops! Failed create "+ RetrofitClient.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "ABC_" + Global.netid + "_" + cntcd + "_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        CapNUpSelfie.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }


}
