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

public class SignUpActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final int LENGTH_TO_ENABLE_PASSWORD = 8;

    Button buttonProceed;

    public EditText editText_company_name;
    public EditText editText_mail;
    public EditText editText_owner;
    public EditText editText_pass;
    public EditText editText_conf_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

        editText_company_name = (EditText)findViewById(R.id.edit_text_company_name_signup);
        editText_mail = (EditText)findViewById(R.id.edit_text_mail_sign_up);
        editText_owner = (EditText)findViewById(R.id.edit_text_owner_signup);
        editText_pass = (EditText)findViewById(R.id.edit_text_pass_signup);
        editText_conf_pass = (EditText)findViewById(R.id.edit_text_confirm_pass_signup);

        buttonProceed = (Button)findViewById(R.id.button_proceed);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String companyName = editText_company_name.getText().toString().trim();
                String email = editText_mail.getText().toString().trim();
                String owner = editText_owner.getText().toString().trim();
                String password = editText_pass.getText().toString().trim();
                String confirmPass = editText_conf_pass.getText().toString().trim();

                if (companyName.isEmpty()) {
                    editText_company_name.setError("Enter company name");
                } else
                    if (email.isEmpty()) {
                    editText_mail.setError("Enter email");
                    } else
                        if (owner.isEmpty()) {
                    editText_owner.setError("Enter owner");
                        } else
                            if (password.isEmpty()) {
                    editText_pass.setError("Enter password");
                            } else
                                if (password.length() < LENGTH_TO_ENABLE_PASSWORD) {
                    editText_pass.setError("Password must be longer than 8 symbols");
                                } else
                                    if (confirmPass.isEmpty()) {
                    editText_conf_pass.setError("Enter confirmation password");
                                    } else
                                        if (!password.equals(confirmPass)) {
                    editText_conf_pass.setError("Confirmation password must be the same as password");
                                        } else
                                            if (!companyName.isEmpty() && !email.isEmpty()
                                                    && !owner.isEmpty() && !password.isEmpty()
                                                    && !confirmPass.isEmpty()) {

                                                Intent intentProceed = new Intent(SignUpActivity.this, AdditionalInformationActivity.class);
                                                intentProceed.putExtra("companyName", companyName);
                                                intentProceed.putExtra("email", email);
                                                intentProceed.putExtra("owner", owner);
                                                intentProceed.putExtra("password", password);
                                                intentProceed.putExtra("confirmPassword", confirmPass);
                                                startActivity(intentProceed);
                                            }


            }
        });


    }
}
