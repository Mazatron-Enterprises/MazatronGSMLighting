package com.mazatron.mazatrongsmlighting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GsmActivity extends AppCompatActivity implements RelayOneClass.RelayOneClassListener, RelayTwoClass.RelayTwoClassListener, RelayThreeClass.RelayThreeClassListener {

    ImageView mMazatronView;
    public Button mRelayOne;
    public Button mRelayThree;
    public Button mRelayTwo;
    public String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsm);

        mMazatronView = findViewById(R.id.mazlogopump);
        mMazatronView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                Intent var2 = new Intent();
                var2.setAction("android.intent.action.VIEW");
                var2.addCategory("android.intent.category.BROWSABLE");
                var2.setData(Uri.parse("https://www.mazatron.com/index.php?route=information/contact"));
                GsmActivity.this.startActivity(var2);
            }
        });

        mRelayOne = findViewById(R.id.relay1);
        mRelayTwo = findViewById(R.id.relay2);
        mRelayThree = findViewById(R.id.relay3);
        SharedPreferences var2 = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0);
        String var3 = var2.getString("RelayOne", (String)null);
        String var4 = var2.getString("RelayTwo", (String)null);
        String var5 = var2.getString("RelayThree", (String)null);
        if (var3 != null && !var3.equals("") && !var3.equals("Relay 1")) {
            mRelayOne.setText(var3);
        }

        if (var4 != null && !var4.equals("") && !var4.equals("Relay 2")) {
            mRelayTwo.setText(var4);
        }

        if (var5 != null && !var5.equals("") && !var5.equals("Relay 3")) {
            mRelayThree.setText(var5);
        }

        phoneNumber = getIntent().getStringExtra("FinalNumber");
    }

    public void lightalert(View var1) {
        this.openLightDialog();
    }

    public void openLightDialog() {
        (new AlertDialog.Builder(this))
                .setTitle(R.string.lightalert_dialog_heading)
                .setMessage(R.string.lightalert_dialog)
                .setPositiveButton(R.string.save_button, new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface var1, int var2) {
                        Intent var4 = new Intent("android.intent.action.SENDTO");
                        if (var4.resolveActivity(GsmActivity.this.getPackageManager()) != null) {
                            StringBuilder var3 = new StringBuilder();
                            var3.append("smsto:");
                            var3.append(phoneNumber);
                            var4.setData(Uri.parse(var3.toString()));
                            var4.putExtra("sms_body", "LIGHTCONFIG");
                            GsmActivity.this.startActivity(var4);
                            Toast.makeText(GsmActivity.this.getApplicationContext(), R.string.light_text, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GsmActivity.this.getApplicationContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton(R.string.cancel, (android.content.DialogInterface.OnClickListener) null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    public void relay1(View var1) {
        (new RelayOneClass()).show(this.getSupportFragmentManager(), "Relay One");
    }

    public void relay2(View var1) {
        (new RelayTwoClass()).show(this.getSupportFragmentManager(), "Relay Two");
    }

    public void relay3(View var1) {
        (new RelayThreeClass()).show(this.getSupportFragmentManager(), "Relay Three");
    }


    public void applyTExts(String var1) {
        if (!var1.equals("")) {
            SharedPreferences.Editor var2 = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0).edit();
            var2.putString("RelayThree", var1);
            var2.apply();
            mRelayThree.setText(var1);
        }

    }

    public void applyTexts(String var1) {
        if (!var1.equals("")) {
            SharedPreferences.Editor var2 = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0).edit();
            var2.putString("RelayTwo", var1);
            var2.apply();
            mRelayTwo.setText(var1);
        }

    }

    public void applytexts(String var1) {
        if (!var1.equals("")) {
            SharedPreferences.Editor var2 = getApplicationContext().getSharedPreferences("MyMazatronLightingNumber", 0).edit();
            var2.putString("RelayOne", var1);
            var2.apply();
            mRelayOne.setText(var1);
        }

    }

}
