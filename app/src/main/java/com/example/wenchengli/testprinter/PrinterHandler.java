package com.example.wenchengli.testprinter;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;


public class PrinterHandler extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        String string = objects[0].toString();
        Log.i("Test String", string);
        String model = Build.MODEL;
        if (model.equals("N3") || model.equals("N5"))
            testPrint(string);
        return null;
    }

    public synchronized void testPrint(String string) {
        getPrinter();
        getPrinter().println("Start Printing", PrinterAPI.Alignment.LEFT, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().printBarcode(string);
//        getPrinter().println("Test End", PrinterAPI.Alignment.LEFT, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().feedAndCutPaper();
    }

    private PrinterAPI _printer;

    public PrinterAPI getPrinter() {
        String model = Build.MODEL;
        if (model.equals("N3") || model.equals("N5")) {
            _printer = NexgoPrinter.getInstance();
        }
        return _printer;
    }
}