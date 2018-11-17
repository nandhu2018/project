package hypercard.gigaappz.com.hypercart.payment;

/**
 * Created by DELL on 17-Nov-18.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import hypercard.gigaappz.com.hypercart.AddProduct;
import hypercard.gigaappz.com.hypercart.R;
import hypercard.gigaappz.com.hypercart.model_class.Product;
import hypercard.gigaappz.com.hypercart.model_class.SalesDetails;

public class PaymentActivity extends Activity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    TextView price,date;
    private DatabaseReference mFirebaseDatabase,mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("salesshop").child(getIntent().getStringExtra("shop"));
        mFirebaseDatabase1 = mFirebaseInstance.getReference("salesuser").child(getIntent().getStringExtra("mobile"));
        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);
        price = (TextView) findViewById(R.id.price);
        date = (TextView) findViewById(R.id.date);
        price.setText("₹"+getIntent().getStringExtra("price"));
        date.setText(getIntent().getStringExtra("date"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "HyperCart");
            options.put("description", "Purchase products");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(getIntent().getStringExtra("price"))*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@hypercart.com");
            preFill.put("contact", getIntent().getStringExtra("mobile"));

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
           // Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            shopsale(razorpayPaymentID);
            usersale(razorpayPaymentID);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
    private void shopsale(String bill) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth


        SalesDetails product = new SalesDetails(bill, getIntent().getStringExtra("price"),getIntent().getStringExtra("date"),getIntent().getStringExtra("mobile"));

        mFirebaseDatabase.child(bill).setValue(product);

        Toasty.success(PaymentActivity.this, "Payment Success", Toast.LENGTH_SHORT, true).show();
        //startActivity(new Intent(AddProduct.this,Mainpage.class));
        finish();

        //addUserChangeListener();
    }
    private void usersale(String bill) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth


        SalesDetails product = new SalesDetails(bill, getIntent().getStringExtra("price"),getIntent().getStringExtra("date"),getIntent().getStringExtra("mobile"));

        mFirebaseDatabase1.child(bill).setValue(product);

        Toasty.success(PaymentActivity.this, "Payment Success", Toast.LENGTH_SHORT, true).show();
        //startActivity(new Intent(AddProduct.this,Mainpage.class));
        finish();

        //addUserChangeListener();
    }
}
