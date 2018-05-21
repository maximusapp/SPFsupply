package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostAddCard;
import com.example.maximus09.spfsupply.data.model.ResponseAddedCard;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddPaymentMethodUserActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String ADD_CARD_URL = "http://api.spfsupply.com/public/api/buyers/payment/card/add";

    EditText etFirstName;
    EditText etLastName;
    EditText etCardNumber;
    EditText etExpMonth;
    EditText etExpYear;
    EditText etSecureCode;

    Button btAdd;

    String firstName;
    String lastName;
    String cardNumber;
    String expMonth;
    String expYear;
    String secureCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etFirstName = (EditText)findViewById(R.id.first_name_add_card);
        etLastName = (EditText)findViewById(R.id.last_name_add_card);
        etCardNumber = (EditText)findViewById(R.id.card_namber_add_card);
        etExpMonth = (EditText)findViewById(R.id.expiration_month_add_card);
        etExpYear = (EditText)findViewById(R.id.expiration_year_add_card);
        etSecureCode = (EditText)findViewById(R.id.security_code_add_card);

        btAdd = (Button)findViewById(R.id.add_payment_mathod);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                cardNumber = etCardNumber.getText().toString();
                expMonth = etExpMonth.getText().toString();
                expYear = etExpYear.getText().toString();
                secureCode = etSecureCode.getText().toString();

                AddPaymentMethod addPaymentMethod = new AddPaymentMethod();
                addPaymentMethod.execute();

            }
        });


    }


    @SuppressLint("StaticFieldLeak")
    private class AddPaymentMethod extends AsyncTask<String, String, ResponseAddedCard> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAddedCard doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAddCard postAddCard = new PostAddCard(preference.getToken(), firstName, lastName,
                    cardNumber, expMonth, expYear, secureCode);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAddCard));

                Request request = new Request.Builder()
                        .url(ADD_CARD_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ADDED_CARD?", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAddedCard responseAddedCard = gsonFromServer.fromJson(responseBody, ResponseAddedCard.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAddedCard;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseAddedCard responseAddedCard) {
            super.onPostExecute(responseAddedCard);

            if (isFinishing()) {
                return;
            }

            String message = responseAddedCard.getMessage();

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        }
    }
}
