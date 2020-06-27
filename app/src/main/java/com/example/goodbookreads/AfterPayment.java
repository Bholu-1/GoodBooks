package com.example.goodbookreads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AfterPayment extends AppCompatActivity {

    TextView txtId,txtStatus,txtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_payment);

        txtAmount = findViewById(R.id.txtAmount);
        txtId = findViewById(R.id.txtId);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("status"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
