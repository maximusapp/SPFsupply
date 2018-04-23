package com.example.maximus09.spfsupply;

import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class NewManufacturerActivity extends AppCompatActivity {

    private TextView textCompanyName;
    private EditText textEnterCompany;
    private ImageView imageAddCompany;

    Switch aSwitch;

    TextView text_tax_amount;
    EditText edit_add_amount;
    View view;

    TextView text_shipping;
    EditText edit_shipping;
    View view1;

    TextView text_fee;
    EditText edit_add_amount_fee;

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manufacturer);
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


        aSwitch = (Switch)findViewById(R.id.switch_new_manufacturer);

        text_tax_amount = (TextView)findViewById(R.id.text_tax_amount);
        edit_add_amount = (EditText)findViewById(R.id.edit_add_amount);
        view = (View)findViewById(R.id.view6);

        text_shipping = (TextView)findViewById(R.id.text_shiping_cost);
        edit_shipping = (EditText)findViewById(R.id.edit_shiping_cost);
        view1 = (View)findViewById(R.id.view7);

        text_fee = (TextView)findViewById(R.id.text_fee);
        edit_add_amount_fee = (EditText)findViewById(R.id.edit_add_amount_fee);

        text_tax_amount.setVisibility(View.INVISIBLE);
        edit_add_amount.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);

        text_shipping.setVisibility(View.INVISIBLE);
        edit_shipping.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.INVISIBLE);

        text_fee.setVisibility(View.INVISIBLE);
        edit_add_amount_fee.setVisibility(View.INVISIBLE);


        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (aSwitch.isChecked()) {
                    text_tax_amount.setVisibility(View.VISIBLE);
                    edit_add_amount.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);

                    text_shipping.setVisibility(View.VISIBLE);
                    edit_shipping.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);

                    text_fee.setVisibility(View.VISIBLE);
                    edit_add_amount_fee.setVisibility(View.VISIBLE);
                } else {
                    text_tax_amount.setVisibility(View.INVISIBLE);
                    edit_add_amount.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.INVISIBLE);

                    text_shipping.setVisibility(View.INVISIBLE);
                    edit_shipping.setVisibility(View.INVISIBLE);
                    view1.setVisibility(View.INVISIBLE);

                    text_fee.setVisibility(View.INVISIBLE);
                    edit_add_amount_fee.setVisibility(View.INVISIBLE);
                }
            }
        });




        imageAddCompany = (ImageView)findViewById(R.id.icon_new_manuf);
        textCompanyName = (TextView)findViewById(R.id.text_company_name);
        textEnterCompany = (EditText)findViewById(R.id.text_enter_company);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_manufacturer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done_new_manufacturer) {
              Toast.makeText(this, "Pressed DONE button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
