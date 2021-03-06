package com.unity.mynativeapp.fragment.recycle.tflite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unity.mynativeapp.fragment.recycle.bagActivity;
import com.unity.mynativeapp.fragment.recycle.canActivity;
import com.unity.mynativeapp.fragment.recycle.clothActivity;
import com.unity.mynativeapp.fragment.recycle.glassActivity;
import com.unity.mynativeapp.fragment.recycle.tflite.ClassifierWithModel;
import com.unity.mynativeapp.fragment.recycle.tflite.ClassifierWithSupport;

import java.io.IOException;
import java.util.Locale;
import com.unity.mynativeapp.R;
import com.unity.mynativeapp.fragment.recycle.umbrellaActivity;

public class galleryActivity extends AppCompatActivity {
    public static final String TAG = "[IC]GalleryActivity";
    public static final int GALLERY_IMAGE_REQUEST_CODE = 1;
    private Labelclass label = new Labelclass();
    public int value;
    private ClassifierWithModel cls;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button selectBtn = findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(v -> getImageFromGallery());

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        cls = new ClassifierWithModel(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void getImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            if (data == null) {
                return;
            }

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {
                if(Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src =
                            ImageDecoder.createSource(getContentResolver(), selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(src);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }
            String resultStr ="test";
            String Str = "";
            // ????????? ??????
            if(bitmap != null) {
                Pair<String, Float> output = cls.classify(bitmap);
                resultStr = output.first;
                imageView.setImageBitmap(bitmap);
                textView.setText(resultStr);
                value = label.Labelclass_function(resultStr);
            }

        }
    }
    public void DialogClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("?????? ??????").setMessage("???????????? ?????? ?????? ????????? ????????? ?????????");

        if(value == 1 || value == 2 ){
            builder.setTitle("???????????????.").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), clothActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 3) {
            builder.setTitle("???????????????.").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), glassActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 4) {
            builder.setTitle("???????????? ????????? ????????? ???????????????").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), glassActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 5) {
            builder.setTitle("???????????????.").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), canActivity.class);
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 6) {
            builder.setTitle("???????????????.").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), bagActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 7) {
            builder.setTitle("???????????????.").setMessage("???????????? ???????????? ????????????????");
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), umbrellaActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else if (value == 8) {
            builder.setTitle("??????????????????.").setMessage("????????? ????????? ???????????? ?????????.");
        }
        else if(value == 9){
            builder.setTitle("?????? ??????").setMessage("??????????????? ???????????? ????????? ???????????????.");
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }
}