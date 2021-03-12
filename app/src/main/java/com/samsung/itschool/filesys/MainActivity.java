package com.samsung.itschool.filesys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button viewButton, sizeButton;
    TextView filesText;
    EditText sizeField;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewButton  =   findViewById(R.id.viewButton);
        filesText   =   findViewById(R.id.filesText);
        sizeButton =    findViewById(R.id.sizeButton);
        sizeField =     findViewById(R.id.sizeField);

        sizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = Integer.parseInt(sizeField.getText().toString());
                filesText.setTextSize(size);
                sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Text size", size);
                editor.putInt("Text color", Color.rgb(56, 200, 185));
                editor.commit();
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recursiveViewFiles(Environment.getRootDirectory().getAbsolutePath(),
                        "| ");
            }
        });
    }

    void recursiveViewFiles(String path, String indent){
        File[] files = new File(path).listFiles();
        File oneFile;
        if (files == null){
            oneFile = new File(path);
            filesText.append(indent + oneFile.getName() + "\n");
        }else {
            for (File file: files){
                filesText.append(indent + file.getName() + "\n");
                recursiveViewFiles(file.getAbsolutePath(), "--" + indent);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreferences == null)
            sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        int size = sharedPreferences.getInt("Text size", 16);
        filesText.setTextSize(size);
        int color = sharedPreferences.getInt("Text color", Color.RED);
        filesText.setTextColor(color);
    }
}
