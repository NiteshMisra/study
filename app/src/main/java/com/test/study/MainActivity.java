package com.test.study;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private int itemId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Checkout.preload(this);
        addFragment(new StudyFragment());

    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void startPayment(int itemIdd, String desc, Integer amount) {

        this.itemId = itemIdd;
        Checkout checkout = new Checkout();
        Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "ExamPur");
            options.put("description", "Buying a PDF : " + desc);
            options.put("currency", "INR");
            options.put("amount", amount);
            checkout.open(activity, options);

        } catch (Exception e) {
            //Log.e(TAG, "Error in starting Razorpay Checkout", e)
        }

    }


    @Override
    public void onPaymentSuccess(String paymentId) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Client-Service","ExampurApp");
        headers.put("Auth-Key","exampurapi");
        headers.put("User-ID","1");
        headers.put("Authorization","67nPxwLC/yyGc");

        RetrofitClient.getInstance().getApi().postPurchase(headers,1,itemId,paymentId,2).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(@NotNull Call<PaymentResponse> call, @NotNull Response<PaymentResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Transaction Successful",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Transaction Successful but updating to server failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PaymentResponse> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this,"Transaction Successful but updating to server failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}
