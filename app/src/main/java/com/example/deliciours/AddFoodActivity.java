package com.example.deliciours;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciours.model.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddFoodActivity extends AppCompatActivity {

    Button selectImageBtn;
    Button uploadImageBtn;
    ImageView imagePreview;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://deliciours-8adc2-default-rtdb.europe-west1.firebasedatabase.app/");


    Uri filePath;
    String foodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        this.selectImageBtn = findViewById(R.id.selectImageBtn);
        this.uploadImageBtn = findViewById(R.id.uploadImageBtn);
        this.imagePreview = findViewById(R.id.imagePreview);

        this.storage = FirebaseStorage.getInstance();
        this.storageReference = this.storage.getReference();

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        EditText foodTitleEdTxt = findViewById(R.id.foodTitleEdTxt);
        EditText foodScoreEdTxt = findViewById(R.id.foodScoreEdTxt);
        EditText foodYearEdTxt = findViewById(R.id.foodYearEdTxt);
        EditText foodingredientsEdTxt = findViewById(R.id.foodingredientsEdTxt);
        EditText foodReceptEdTxt = findViewById(R.id.foodReceptEdTxt);

        Button foodSaveBtn = findViewById(R.id.savefoodBtn);

        DatabaseReference foodsReference = mDatabase.getReference("food");

        foodSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = foodTitleEdTxt.getText().toString();
                Double score = Double.valueOf(foodScoreEdTxt.getText().toString());
                Long year = Long.valueOf(foodYearEdTxt.getText().toString());
                String ingredients = foodingredientsEdTxt.getText().toString();
                String recept = foodReceptEdTxt.getText().toString();
                Food m = new Food(title, ingredients, score, year, recept, foodImage);
                foodsReference.push().setValue(m);
                Intent i = new Intent(AddFoodActivity.this, FoodActivity.class);
                startActivity(i);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null) {
            this.filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectImage () {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(i, "Odaberite sliku jela"), 22
        );
    }

    private void uploadImage () {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Učitavam sliku");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"
                    + UUID.randomUUID().toString()
            );
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.hide();
                    Toast.makeText(
                            getApplicationContext(),
                            "Slika je učitana na server",
                            Toast.LENGTH_LONG).show();
                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            foodImage = task.getResult().toString();
                        }
                    });
                }
            });


        }
    }
}