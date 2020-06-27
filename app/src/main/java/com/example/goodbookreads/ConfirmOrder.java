package com.example.goodbookreads;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class ConfirmOrder extends AppCompatActivity {

    private static final int PAY_PAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    EditText address,city,name;
    TextView tot;
    Button pay;
    String amount = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,ConfirmOrder.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAY_PAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try
                    {
                        String paymentdetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, com.example.goodbookreads.paymentdetails.class)
                                .putExtra("Payment Details",paymentdetails)
                                .putExtra("Payment Amount",amount));


                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this,"Cancle...",Toast.LENGTH_SHORT).show();
        }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this,"Invalid...",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Intent intent = new Intent(this,ConfirmOrder.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);


        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        name = findViewById(R.id.nam);
        pay = findViewById(R.id.pay);
        tot = findViewById(R.id.pricetot);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });

        }
        private void processPayment(){

            amount = tot.getText().toString();
            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"INR","Purchased Book",PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this,ConfirmOrder.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
            startActivityForResult(intent,PAY_PAL_REQUEST_CODE);

        }
}
