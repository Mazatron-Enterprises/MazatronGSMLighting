package com.mazatron.mazatrongsmlighting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String finalNumber;
    public EditText gsmNumber;
    ImageView mMazatronView;
    public String numberGSM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finalNumber = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0).getString("GSMnumber", (String)null);
        if (finalNumber != null) {
            Intent var2 = new Intent(this, GsmActivity.class);
            var2.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            var2.putExtra("FinalNumber", finalNumber);
            this.finish();
            this.startActivity(var2);
        }

        gsmNumber = findViewById(R.id.gsmnumber);
        gsmNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View var1, boolean var2) {
                if (var2) {
                    gsmNumber.setHint("");
                } else {
                    gsmNumber.setHint("+91");
                }

            }
        });

        mMazatronView = findViewById(R.id.mazlogo);
        mMazatronView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                Intent var2 = new Intent();
                var2.setAction("android.intent.action.VIEW");
                var2.addCategory("android.intent.category.BROWSABLE");
                var2.setData(Uri.parse("https://www.mazatron.com/index.php?route=information/contact"));
                startActivity(var2);
            }
        });
    }

    public void save_number(View var1) {
        numberGSM = gsmNumber.getText().toString();
        if (numberGSM.length() == 10) {
            SharedPreferences.Editor var2 = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0).edit();
            var2.putString("GSMnumber", numberGSM);
            var2.apply();
            Intent var3 = new Intent(this, GsmActivity.class);
            var3.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            var3.putExtra("FinalNumber", numberGSM);
            finish();
            startActivity(var3);
        } else {
            Toast.makeText(getApplicationContext(), R.string.numbererror, Toast.LENGTH_SHORT).show();
        }

    }
}
