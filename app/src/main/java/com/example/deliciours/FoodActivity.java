package com.example.deliciours;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciours.adapter.FoodAdapter;
import com.example.deliciours.model.Food;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class FoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://deliciours-8adc2-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        this.recyclerView = findViewById(R.id.foodsListView);
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(
                this.mDatabase.getReference("food"),
                Food.class
        ).build();

        this.foodAdapter = new FoodAdapter(options);
        this.recyclerView.setAdapter(this.foodAdapter);

        FloatingActionButton openAddfoodBtn = findViewById(R.id.openAddfoodBtn);
        openAddfoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodActivity.this, AddFoodActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.foodAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.foodAdapter.stopListening();
    }
}