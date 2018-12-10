package com.example.wenchengli.testprinter;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final private EditText editText = (EditText) findViewById(R.id.editText);
    final private Button button = (Button) findViewById(R.id.button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NexgoPrinter.getNewInstance(this);
        PrinterHandler printerHandler = new PrinterHandler();
        printerHandler.execute(button.getText());
    }


}
