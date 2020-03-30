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

public class RelayOneClass extends AppCompatDialogFragment {

    private String finalNumber;
    private RelayOneClassListener listener;
    private Button mButtonOff;
    private Button mButtonOn;
    private EditText mRelayOneName;
    private String mRelayOneNameStr;
    private String relayOneName;


    public void onAttach(Context var1) {
        super.onAttach(var1);

        try {
            listener = (RelayOneClassListener) var1;
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
        relayOneName = var2.getString("RelayOne",  null);
        View var3 = this.getActivity().getLayoutInflater().inflate(R.layout.layout_relayone,  null);
        mRelayOneName = var3.findViewById(R.id.relayOneName);
        String var5 = relayOneName;
        if (var5 != null) {
            mRelayOneName.setText(var5);
        }

        var4.setView(var3)
                .setTitle(R.string.switch_one)
                .setIcon(android.R.drawable.btn_radio)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface var1, int var2) {

                        mRelayOneNameStr = mRelayOneName.getText().toString();
                        if (!mRelayOneNameStr.equals("")) {
                            listener.applytexts(mRelayOneNameStr);
                        }

                    }
                });

        mButtonOn = var3.findViewById(R.id.relayoneON);
        mButtonOn.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var2 = new Intent("android.intent.action.SENDTO");
                if (var2.resolveActivity(RelayOneClass.this.getContext().getPackageManager()) != null) {
                    StringBuilder var3 = new StringBuilder();
                    var3.append("smsto:");
                    var3.append(RelayOneClass.this.finalNumber);
                    var2.setData(Uri.parse(var3.toString()));
                    var2.putExtra("sms_body", "RELAY1ON");
                    startActivity(var2);
                    dismiss();
                    Toast.makeText(RelayOneClass.this.getContext(), R.string.pumpon, Toast.LENGTH_SHORT).show();
                } else {
                    RelayOneClass.this.dismiss();
                    Toast.makeText(RelayOneClass.this.getContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                }

            }
        });

        mButtonOff = var3.findViewById(R.id.relayoneOFF);
        mButtonOff.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var2 = new Intent("android.intent.action.SENDTO");
                if (var2.resolveActivity(RelayOneClass.this.getContext().getPackageManager()) != null) {
                    StringBuilder var3 = new StringBuilder();
                    var3.append("smsto:");
                    var3.append(RelayOneClass.this.finalNumber);
                    var2.setData(Uri.parse(var3.toString()));
                    var2.putExtra("sms_body", "RELAY1OFF");
                    startActivity(var2);
                    dismiss();
                    Toast.makeText(getContext(), R.string.pumpoff, Toast.LENGTH_SHORT).show();
                } else {
                    RelayOneClass.this.dismiss();
                    Toast.makeText(RelayOneClass.this.getContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return var4.create();
    }

    public interface RelayOneClassListener {
        void applytexts(String var1);
    }

}
