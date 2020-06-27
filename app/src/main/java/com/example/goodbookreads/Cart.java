package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private Button next;
    private TextView tot;

    private String overtotal;
    private RecyclerView recyclerView;
    List<BookData> myBookList;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String totalamount= "";


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
        startActivity(new Intent(Cart.this,MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalamount = getIntent().getStringExtra("Total Price");

        setContentView(R.layout.activity_cart);
        tot = findViewById(R.id.total);
        next = findViewById(R.id.next);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        firebaseAuth = FirebaseAuth.getInstance();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Cart.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Cart Items...");
        myBookList = new ArrayList<>();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tot.setText("Total Price = $" + String.valueOf(overtotal));

                Intent i = new Intent(Cart.this,ConfirmOrder.class);
                i.putExtra("Total Price" , String.valueOf(overtotal));
                startActivity(i);
            }
        });

        final CartAdapter myAdapter = new CartAdapter(Cart.this,myBookList);
        recyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cart");

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


}
