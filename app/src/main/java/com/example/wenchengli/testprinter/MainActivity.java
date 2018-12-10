package com.example.wenchengli.testprinter;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        String model = Build.MODEL;
        if (model.equals("N3") || model.equals("N5"))
            NexgoPrinter.getNewInstance(this);
        button.setOnClickListener(onButtonClicked);
    }

    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            PrinterHandler printerHandler = new PrinterHandler(editText.getText().toString());
            printerHandler.doInBackground(null);
        }
    };

}
