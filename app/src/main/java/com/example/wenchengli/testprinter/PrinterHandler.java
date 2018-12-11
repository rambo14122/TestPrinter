package com.example.wenchengli.testprinter;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;


public class PrinterHandler extends AsyncTask {

    private String string;

    public PrinterHandler(String string) {
        this.string = string;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Log.i("Test String", string);
        String model = Build.MODEL;
        if (model.equals("N3") || model.equals("N5"))
            testPrint(string);
        return null;
    }

    public synchronized void testPrint(String string) {
        if (getPrinter().getLock())
            return;
        getPrinter().setLock(true);
        getPrinter();
        getPrinter().println("Test Printing", PrinterAPI.Alignment.CENTER, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().println("**********************", PrinterAPI.Alignment.CENTER, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);

        getPrinter().printBarcode(string);
//        getPrinter().println("Test End", PrinterAPI.Alignment.LEFT, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().println(string, PrinterAPI.Alignment.CENTER, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().println("**********************", PrinterAPI.Alignment.CENTER, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
        getPrinter().println("**********************", PrinterAPI.Alignment.CENTER, PrinterAPI.FontSize.NORMAL, PrinterAPI.Decoration.NORMAL, PrinterAPI.LineSpace.MEDIUM);
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