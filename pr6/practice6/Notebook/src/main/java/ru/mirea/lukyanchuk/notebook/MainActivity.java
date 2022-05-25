package ru.mirea.lukyanchuk.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private EditText fileName;
    private EditText textField;

    private SharedPreferences preferences;
    final String NAME_SAVED = "name_saved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = findViewById(R.id.fileName);
        textField = findViewById(R.id.textField1);

        preferences = getPreferences(MODE_PRIVATE);

        FileInputStream fin = null;
        try {
            String savedName = preferences.getString(NAME_SAVED, "Empty");
            fin = openFileInput(savedName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            fileName.setText(savedName);
            textField.setText(text);
        } catch (IOException ex) {
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
            }
        }

    }

    public void saveText(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME_SAVED, fileName.getText().toString());
        editor.apply();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName.getText().toString(), Context.MODE_PRIVATE);
            outputStream.write(textField.getText().toString().getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}