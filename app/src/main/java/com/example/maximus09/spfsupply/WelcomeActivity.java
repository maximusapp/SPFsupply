package com.example.maximus09.spfsupply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    KKViewPager mPager;

    Button signIn;
    Button signUp;
    Button contactWelcome;

    String n[] = {"https://www.frameweb.com/img/loading.jpg", "https://www.frameweb.com/img/loading.jpg", "https://www.frameweb.com/img/loading.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (KKViewPager) findViewById(R.id.kk_pager);
       // mPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(),WelcomeActivity.this));

        TestFragmentAdapter testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager(), this, n );

        mPager.setAdapter(testFragmentAdapter);

        signIn = (Button)findViewById(R.id.button_sign_in);
        signUp = (Button)findViewById(R.id.button_sign_up);
        contactWelcome = (Button)findViewById(R.id.button_contact);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sigInIntent = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(sigInIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        contactWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentContact = new Intent(WelcomeActivity.this, ContactUsActivity.class);
                startActivity(intentContact);
            }
        });



    }

}