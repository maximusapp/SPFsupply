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
import android.widget.EditText;
import android.widget.TextView;

public class AdditionalInformationActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public EditText editText_phone;
    public EditText editText_business_address;
    public EditText editText_delivery_address;

    Button buttonProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_information);
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

        editText_phone = (EditText)findViewById(R.id.phon_edit_text_signup);
        editText_business_address = (EditText)findViewById(R.id.business_address_edit_signup);
        editText_delivery_address = (EditText)findViewById(R.id.edit_delivery_adress);

        buttonProceed = (Button)findViewById(R.id.button_proceed_additional);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = editText_phone.toString();
                String address = editText_business_address.toString();
                String delivery = editText_delivery_address.toString();

                if (phone.isEmpty()) {
                    editText_phone.setError("Enter phone Number");
                } else
                    if (address.isEmpty()) {
                    editText_business_address.setError("Enter business address");
                    } else
                        if (delivery.isEmpty()) {
                    editText_delivery_address.setError("Enter delivery address");
                        } if (!phone.isEmpty() && !address.isEmpty() && !delivery.isEmpty()) {


                    Intent intentProceed = new Intent(AdditionalInformationActivity.this, PaymentMethodActivity.class);
                    // Pass data from 2-d act to 3-d act
                    intentProceed.putExtra("phone", phone);
                    intentProceed.putExtra("address", address);
                    intentProceed.putExtra("delivery_address", delivery);

                    // Get data from first activity
                    String company_name = getIntent().getStringExtra("companyName");
                    String email = getIntent().getStringExtra("email");
                    String owner = getIntent().getStringExtra("owner");
                    String pass = getIntent().getStringExtra("password");
                    String conf_pass = getIntent().getStringExtra("confirmPassword");

                    // Pass data from 1-t act to 3-d act
                    intentProceed.putExtra("com_name", company_name);
                    intentProceed.putExtra("mailta", email);
                    intentProceed.putExtra("owners", owner);
                    intentProceed.putExtra("pass", pass);
                    intentProceed.putExtra("conf", conf_pass);

                    startActivity(intentProceed);
                }

            }

        });

    }
}
