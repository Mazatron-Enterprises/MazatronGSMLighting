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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class RelayThreeClass extends AppCompatDialogFragment {

    private String finalNumber;
    private RelayThreeClassListener listener;
    private Button mButtonOff;
    private Button mButtonOn;
    private EditText mRelayThreeName;
    private String mRelayThreeNameStr;
    private String relayThreeName;


    public interface RelayThreeClassListener {
        void applyTExts(String var1);
    }


    public void onAttach(Context var1) {
        super.onAttach(var1);

        try {
            this.listener = (RelayThreeClass.RelayThreeClassListener) var1;
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
        finalNumber = var2.getString("GSMnumber", null);
        relayThreeName = var2.getString("RelayThree", null);
        View var3 = getActivity().getLayoutInflater().inflate(R.layout.layout_relaythree, null);
        mRelayThreeName = var3.findViewById(R.id.relayOneName);
        String var5 = relayThreeName;
        if (var5 != null) {
            mRelayThreeName.setText(var5);
        }

        var4.setView(var3)
                .setTitle(R.string.switch_three)
                .setIcon(android.R.drawable.btn_radio)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface var1, int var2) {

                        mRelayThreeNameStr = mRelayThreeName.getText().toString();
                        if (!mRelayThreeNameStr.equals("")) {
                            listener.applyTExts(mRelayThreeNameStr);
                        }

                    }
                });

        mButtonOn = var3.findViewById(R.id.relayoneON);
        mButtonOn.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var2 = new Intent("android.intent.action.SENDTO");
                if (var2.resolveActivity(getContext().getPackageManager()) != null) {
                    StringBuilder var3 = new StringBuilder();
                    var3.append("smsto:");
                    var3.append(finalNumber);
                    var2.setData(Uri.parse(var3.toString()));
                    var2.putExtra("sms_body", "RELAY3ON");
                    startActivity(var2);
                    dismiss();
                    Toast.makeText(getContext(), R.string.pumpon, Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    Toast.makeText(getContext(), R.string.app_notfound, Toast.LENGTH_SHORT).show();
                }

            }
        });

        mButtonOff = var3.findViewById(R.id.relayoneOFF);
        mButtonOff.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View var1) {
                Intent var3 = new Intent("android.intent.action.SENDTO");
                if (var3.resolveActivity(getContext().getPackageManager()) != null) {
                    StringBuilder var2 = new StringBuilder();
                    var2.append("smsto:");
                    var2.append(finalNumber);
                    var3.setData(Uri.parse(var2.toString()));
                    var3.putExtra("sms_body", "RELAY3OFF");
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

}
