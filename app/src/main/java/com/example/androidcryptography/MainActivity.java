/*
This encodes or decodes a message using three different ways
Name: Brian Chou
Date: 4/1/22
 */
package com.example.androidcryptography;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup cipherType;
    RadioButton scytaleCipher;
    RadioButton caesarCipher;
    RadioButton vigenereCipher;

    EditText cipherInfo;
    EditText decryptedMessage;
    EditText encryptedMessage;

    Button help;
    Button encrypt;
    Button decrypt;

    int selectedCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates the buttons and text fields
        cipherType = (RadioGroup)this.findViewById(R.id.radioGroup);
        scytaleCipher = (RadioButton)this.findViewById(R.id.radioButton);
        caesarCipher = (RadioButton)this.findViewById(R.id.radioButton2);
        vigenereCipher = (RadioButton)this.findViewById(R.id.radioButton3);
        cipherInfo = (EditText)this.findViewById(R.id.editTextTextPersonName3);
        decryptedMessage = (EditText)this.findViewById(R.id.editTextTextPersonName1);
        encryptedMessage = (EditText)this.findViewById(R.id.editTextTextPersonName2);
        help = (Button)this.findViewById(R.id.button3);
        help.setOnClickListener(this);
        encrypt = (Button)this.findViewById(R.id.button);
        encrypt.setOnClickListener(this);
        decrypt = (Button)this.findViewById(R.id.button2);
        decrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //tells the user what to enter when the ? button is clicked
        if (v.equals(help)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setMessage("For a Scytale Cipher, please enter the number of rows.\nFor a Caesar Cipher, please enter the offset.\nFor a Vigenere Cipher, please enter the keyword.")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        //encrypts the message
        if (v.equals(encrypt)) {
            String input = decryptedMessage.getText().toString().toUpperCase();
            String message = "";
            for(int i = 0; i<input.length(); i++){
                if(Character.isAlphabetic(input.charAt(i))){
                    message += input.charAt(i);
                }
            }
            //encrypts the message using a scytale cipher
            if (scytaleCipher.isChecked() == true) {
                int rows = Integer.parseInt(cipherInfo.getText().toString());
                int remainder = message.length() % rows;
                int columns = message.length() / rows;
                if (remainder > 0) {
                    columns++;
                }
                char[][] array = new char[rows][columns];
                int letter = 0;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (j == columns - 1 && i >= rows - remainder) {
                            array[i][j] = '@';
                        }
                        else {
                            array[i][j] = message.charAt(letter);
                            letter++;
                        }
                    }
                }
                String output = "";
                for (int j = 0; j < columns; j++) {
                    for (int i = 0; i < rows; i++) {
                        output = output + array[i][j];
                    }
                }
                encryptedMessage.setText(output);
            }
            //encrypts using a caesar cipher
            else if (caesarCipher.isChecked() == true) {
                int offset = Integer.parseInt(cipherInfo.getText().toString()) % 26;
                String output = "";
                for (int i = 0; i < message.length(); i++) {
                    int character = message.charAt(i);
                    int shifted;
                    if (character + offset > 90) {
                        shifted = character + offset - 26;
                    }
                    else {
                        shifted = character + offset;
                    }
                    output += (char)(shifted);
                }
                encryptedMessage.setText(output);
            }
            //encrypts using a vigenere cipher
            else if (vigenereCipher.isChecked() == true) {
                String keyword = cipherInfo.getText().toString();
                int shift = message.length() / keyword.length();
                if (message.length() % keyword.length() > 0) {
                    shift++;
                }
                String keywords = "";
                for (int i = 0; i < shift; i++) {
                    keywords = keywords + keyword;
                }
                keywords = keywords.toLowerCase();
                String output = "";
                for (int i = 0; i < message.length(); i++) {
                    int character = message.charAt(i);
                    int offset = keywords.charAt(i) - 97;
                    int shifted;
                    if (character + offset > 90) {
                        shifted = character + offset - 26;
                    }
                    else {
                        shifted = character + offset;
                    }
                    output += (char)(shifted);
                }
                encryptedMessage.setText(output);
            }
        }
        //decrypts the message
        if (v.equals(decrypt)) {
            String input = encryptedMessage.getText().toString().toUpperCase();
            String message = "";
            for(int i = 0; i<input.length(); i++){
                if(Character.isAlphabetic(input.charAt(i))){
                    message += input.charAt(i);
                }
            }
            //decrypts using a scytale cipher
            if (scytaleCipher.isChecked() == true) {
                int rows = Integer.parseInt(cipherInfo.getText().toString());
                int columns = message.length() / rows;
                char[][] array = new char[rows][columns];
                int letter = 0;
                for (int j = 0; j < columns; j++) {
                    for (int i = 0; i < rows; i++) {
                        array[i][j] = message.charAt(letter);
                        letter++;
                    }
                }
                String output = "";
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (array[i][j] != '@') {
                            output = output + array[i][j];
                        }
                    }
                }
                decryptedMessage.setText(output);
            }
            //decrypts using a caesar cipher
            else if (caesarCipher.isChecked() == true) {
                int offset = Integer.parseInt(cipherInfo.getText().toString()) % 26;
                String output = "";
                for (int i = 0; i < message.length(); i++) {
                    int character = message.charAt(i);
                    int shifted;
                    if (character - offset < 65) {
                        shifted = character - offset + 26;
                    }
                    else {
                        shifted = character - offset;
                    }
                    output += (char)(shifted);
                }
                decryptedMessage.setText(output);
            }
            //decrypts using a vigenere cipher
            else if (vigenereCipher.isChecked() == true) {
                String keyword = cipherInfo.getText().toString();
                int shift = message.length() / keyword.length();
                if (message.length() % keyword.length() > 0) {
                    shift++;
                }
                String keywords = "";
                for (int i = 0; i < shift; i++) {
                    keywords = keywords + keyword;
                }
                keywords = keywords.toLowerCase();
                String output = "";
                for (int i = 0; i < message.length(); i++) {
                    int character = message.charAt(i);
                    int offset = keywords.charAt(i) - 97;
                    int shifted;
                    if (character - offset < 65) {
                        shifted = character - offset + 26;
                    }
                    else {
                        shifted = character - offset;
                    }
                    output += (char)(shifted);
                }
                decryptedMessage.setText(output);
            }
        }
    }
}