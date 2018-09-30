package apps.gliger.isafe;

/***Activity for capture Profile picture and License Card image and upload to firebase db**/
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 120;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private ImageView previewProfile,previewnic;
    int i = 0;
    private Uri uri;
    private StorageReference mStorageRef;
    private MaterialDialog dialog;
    private String profileURL = "";
    private String nicURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        previewProfile=findViewById(R.id.img_profile_upload);
        previewnic=findViewById(R.id.img_license_upload);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        previewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                showAlert("Profile Image");
            }

        });
        previewnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=1;
                showAlert("Image Of NIC");
            }

        });
    }

    private void chooseImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMAGE_REQUEST);

    }

    public void showAlert(String string)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(string);
        builder.setMessage("Capture a profile picture or Choose from gallery?");
        builder.setPositiveButton("Capture Image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dispatchTakePictureIntent();

            }
        });
        builder.setNegativeButton("Choose from Galery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chooseImage();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        if(i == 0){
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewProfile.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //ImageURL=saveToInternalStorage(imageBitmap);
                previewProfile.setImageBitmap(imageBitmap);


            }


        }
        if(i==1) {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewnic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //ImageURL=saveToInternalStorage(imageBitmap);
                previewnic.setImageBitmap(imageBitmap);


            }
        }
    }

    private void imageUpload(ImageView imageView, String token, final String type){
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.child(token).child(type).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(type=="Profile")
                    profileURL = taskSnapshot.getDownloadUrl().toString();
                else
                    nicURL = taskSnapshot.getDownloadUrl().toString();
                dialog.dismiss();

                if(profileURL!=null && nicURL!=null)
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    private void setProgressDialog(String message) {
        dialog = new MaterialDialog.Builder(ImageUploadActivity.this)
                .content(message)
                .cancelable(false)
                .progress(true, 0)
                .show();
    }
}
