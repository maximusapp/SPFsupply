package com.example.maximus09.spfsupply;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentMethodActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    TextView textSignInNow;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(this.getColor(R.color.colorStatusBar));
            }
        }

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        // Get data from 1-t and 2-d Activities
        //========== it is work ===========
        Intent intentExtras = getIntent();

        // From 1-t act
        String com_name = intentExtras.getStringExtra("com_name");
        String mailta = intentExtras.getStringExtra("mailta");
        String owners = intentExtras.getStringExtra("owners");
        String pass = intentExtras.getStringExtra("pass");
        String conf_pass = intentExtras.getStringExtra("conf");

        // From 2-d act
        String image_link = intentExtras.getStringExtra("image_link");
        String phone = intentExtras.getStringExtra("phone");
        String business_adr = intentExtras.getStringExtra("address");
        String delivery_address = intentExtras.getStringExtra("delivery_address");


        textSignInNow = (TextView)findViewById(R.id.text_sign_in_payment_method);
        textSignInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(PaymentMethodActivity.this, WelcomeActivity.class);
                startActivity(intentSignIn);
            }
        });

        signUpButton = (Button)findViewById(R.id.button_sign_up_payment_method);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(PaymentMethodActivity.this, HomeActivity.class);
                startActivity(intentSignUp);
            }
        });


    }
}
