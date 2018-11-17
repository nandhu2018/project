package hypercard.gigaappz.com.hypercart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import es.dmoral.toasty.Toasty;
import hypercard.gigaappz.com.hypercart.shop.ShopActivity;

public class Login extends AppCompatActivity {
    Button login;
    TextView forgotpsw;
    TextInputLayout mobile, password;
    KProgressHUD hud;
    SharedPreferences sharedPreferences;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String message = "password";
    String encryptedMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.login_btn);
        forgotpsw = (TextView) findViewById(R.id.forgot_pass);
        mobile = (TextInputLayout) findViewById(R.id.login_mobile);
        password = (TextInputLayout) findViewById(R.id.login_password);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getEditText().getText().toString().equalsIgnoreCase("") || password.getEditText().getText().toString().equalsIgnoreCase("")) {
                    Toasty.error(Login.this, "Enter Mobile number and password", Toast.LENGTH_SHORT, true).show();
                } else if (mobile.getEditText().getText().length() < 10) {
                    mobile.getEditText().setError("Enter Valid Mobile Number");
                } else {
                    hud = KProgressHUD.create(Login.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setLabel("Please Wait")
                            .show();

                    try {
                        encryptedMsg = AESCrypt.encrypt(password.getEditText().getText().toString(), message);
                    } catch (GeneralSecurityException e) {
                        //handle error
                    }

                    mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (hud.isShowing()) {
                                    hud.dismiss();
                                }

                                User user = snapshot.getValue(User.class);
                                if (mobile.getEditText().getText().toString().equalsIgnoreCase(user.mobile)) {
                                    if (encryptedMsg.equalsIgnoreCase(user.password)) {
                                        Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                                        if (user.acctype.equalsIgnoreCase("user")) {
                                            Toasty.success(Login.this, "Login success", Toast.LENGTH_SHORT, true).show();
                                            SharedPreferences.Editor editor1 = getSharedPreferences("logged", MODE_PRIVATE).edit();
                                            editor1.putString("mobile", user.mobile);
                                            editor1.apply();
                                            startActivity(new Intent(Login.this, MainScreen1.class));
                                            finish();
                                        } else if (user.acctype.equalsIgnoreCase("shop")) {
                                            Toasty.success(Login.this, "Login success", Toast.LENGTH_SHORT, true).show();
                                            SharedPreferences.Editor editor1 = getSharedPreferences("logged", MODE_PRIVATE).edit();
                                            editor1.putString("mobile", user.mobile);
                                            editor1.apply();
                                            startActivity(new Intent(Login.this, ShopActivity.class));
                                            finish();
                                        } else if (user.acctype.equalsIgnoreCase("admin")){
                                            Toasty.success(Login.this, "Login success", Toast.LENGTH_SHORT, true).show();
                                            SharedPreferences.Editor editor1 = getSharedPreferences("logged", MODE_PRIVATE).edit();
                                            editor1.putString("mobile", user.mobile);
                                            editor1.apply();
                                            startActivity(new Intent(Login.this, MainScreen1.class));
                                            finish();
                                        }

                                    } else {
                                        if (hud.isShowing()) {
                                            hud.dismiss();
                                        }
                                        Toasty.error(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                                /*Query query=mFirebaseDatabase.equalTo("9995727778");

                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot data:dataSnapshot.getChildren()){

                                            User user = data.getValue(User.class);
                                            Toast.makeText(Login.this, ""+user.place, Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });*/
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }


//authenticate user
                    /*auth.signInWithEmailAndPassword(mobile.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    //progressBar.setVisibility(View.GONE);
                                    if (hud.isShowing()){
                                        hud.dismiss();
                                    }
                                    if (!task.isSuccessful()) {
                                        // there was an error

                                    } else {
                                        Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });*/


                // makeJsonRequest(mobile.getEditText().getText().toString(),password.getEditText().getText().toString());

            }
        });
        forgotpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, Mainpage.class));
        finish();
    }


}
