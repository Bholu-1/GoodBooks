package com.example.goodbookreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {

    ImageView image;
    TextView t,a,p,l,h,r,i,page,publisher;
    Button cart;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        cart = findViewById(R.id.add);
        image = findViewById(R.id.b_imaged);
        t = findViewById(R.id.booktitle);
        a = findViewById(R.id.bookauthor);
        p = findViewById(R.id.bookprice);
        l = findViewById(R.id.booklike);
        h = findViewById(R.id.bookhates);
        r = findViewById(R.id.bookrating);
        i = findViewById(R.id.bookisbn);
        page = findViewById(R.id.bookpages);
        publisher = findViewById(R.id.bookpublisher);

        Bundle mBundle = getIntent().getExtras();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ProductDetails.this,Pay2.class);
                startActivity(i);
                finish();

            }
        });

        if(mBundle != null){

            t.setText(mBundle.getString("Title"));
            a.setText(mBundle.getString("Author"));
            p.setText(mBundle.getString("Price"));
            l.setText(mBundle.getString("Likes"));
            h.setText(mBundle.getString("Hates"));
            r.setText(mBundle.getString("Rating"));
            i.setText(mBundle.getString("Isbn"));
            page.setText(mBundle.getString("Pages"));
            publisher.setText(mBundle.getString("Publisher"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(image);

        }

    }

    public void onBackPressed()
    {
        Intent intent =new Intent(ProductDetails.this,Try.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }



    private void addTocartList(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding to cart...");
        progressDialog.show();

        BookData bookData = new BookData(
            t.getText().toString(),
            a.getText().toString(),
            p.getText().toString(),
            l.getText().toString(),
            h.getText().toString(),
            r.getText().toString(),
            i.getText().toString(),
            page.getText().toString(),
            publisher.getText().toString(),
            url
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Cart")
                .child(myCurrentDateTime).setValue(bookData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ProductDetails.this,"Add To Cart",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductDetails.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }


}
