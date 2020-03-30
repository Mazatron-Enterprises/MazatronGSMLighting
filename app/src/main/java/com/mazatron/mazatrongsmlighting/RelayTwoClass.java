package com.mazatron.mazatrongsmlighting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class RelayTwoClass extends AppCompatDialogFragment {

    private String finalNumber;
    private RelayTwoClassListener listener;
    private Button mButtonOff;
    private Button mButtonOn;
    private EditText mRelayTwoName;
    private String mRelayTwoNameStr;
    private String relayTwoName;


    public void onAttach(Context var1) {
        super.onAttach(var1);

        try {
            listener = (RelayTwoClassListener) var1;
        } catch (ClassCastException var3) {
            StringBuilder var2 = new StringBuilder();
            var2.append(var1.toString());
            var2.append("Must Implement ExampleDialogListener");
            throw new ClassCastException(var2.toString());
        }
    }

    public Dialog onCreateDialog(Bundle var1) {
        AlertDialog.Builder var4 = new AlertDialog.Builder(this.getActivity());
        SharedPreferences var2 = getContext().getSharedPreferences("MyMazatronLightingNumber", 0);
        finalNumber = var2.getString("GSMnumber",  null);
        relayTwoName = var2.getString("RelayTwo", null);
        View var5 = this.getActivity().getLayoutInflater().inflate(R.layout.layout_relaytwo, null);
        mRelayTwoName = var5.findViewById(R.id.relayOneName);
        String var3 = this.relayTwoName;
        if (var3 != null) {
            this.mRelayTwoName.setText(var3);
        }

        var4.setView(var5)
                .setTitle(R.string.switch_two)
                .setIcon(android.R.drawable.btn_radio)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface var1, int var2) {
                        mRelayTwoNameStr = mRelayTwoName.getText().toString();
                        if (!mRelayTwoNameStr.equals("")) {
                            listener.applyTexts(RelayTwoClass.this.mRelayTwoNameStr);
                        }

                    }
                });

        mButtonOn = var5.findViewById(R.id.relayoneON);
        mButtonOn.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var3 = new Intent("android.intent.action.SENDTO");
                if (var3.resolveActivity(getContext().getPackageManager()) != null) {
                    StringBuilder var2 = new StringBuilder();
                    var2.append("smsto:");
                    var2.append(finalNumber);
                    var3.setData(Uri.parse(var2.toString()));
                    var3.putExtra("sms_body", "RELAY2ON");
                    startActivity(var3);
                    dismiss();
                    Toast.makeText(getContext(), R.string.pumpon, Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    Toast.makeText(getContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                }

            }
        });

        mButtonOff = var5.findViewById(R.id.relayoneOFF);
        mButtonOff.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var3 = new Intent("android.intent.action.SENDTO");
                if (var3.resolveActivity(getContext().getPackageManager()) != null) {
                    StringBuilder var2 = new StringBuilder();
                    var2.append("smsto:");
                    var2.append(finalNumber);
                    var3.setData(Uri.parse(var2.toString()));
                    var3.putExtra("sms_body", "RELAY2OFF");
                    startActivity(var3);
                    dismiss();
                    Toast.makeText(getContext(), R.string.pumpoff, Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    Toast.makeText(getContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return var4.create();
    }


    public interface RelayTwoClassListener {
        void applyTexts(String var1);
    }
}
