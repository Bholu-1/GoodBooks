package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Try extends AppCompatActivity {
    RecyclerView recyclerView;
    List<BookData> myBookList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout1:{
            LogOut();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void LogOut(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Try.this,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        recyclerView = findViewById(R.id.recycler);
        firebaseAuth = FirebaseAuth.getInstance();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Try.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items...");

        myBookList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(Try.this,myBookList);
        recyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myBookList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    BookData bookData =itemSnapshot.getValue(BookData.class);

                    myBookList.add(bookData);

                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
            }
        });

    }

    public void onBackPressed()
    {
        Intent intent =new Intent(Try.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    public void gotocart(View view) {

        Intent i = new Intent(Try.this, Cart.class);
        startActivity(i);

        finish();
    }
}
