package com.example.wenchengli.testprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.BarcodeFormatEnum;
import com.nexgo.oaf.apiv3.device.printer.DotMatrixFontEnum;
import com.nexgo.oaf.apiv3.device.printer.FontEntity;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

import org.json.JSONArray;

public class NexgoPrinter implements PrinterAPI {


    private boolean lock = false;
    private DeviceEngine deviceEngine;
    private Printer printer;
    private final int FONT_SIZE_SMALL = 20;
    private final int FONT_SIZE_EXTRA_SMALL = 16;
    private final int FONT_SIZE_NORMAL = 24;
    private final int FONT_SIZE_BIG = 24;
    private FontEntity fontSmall = new FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_8X16);
    private FontEntity fontNormal = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24);
    private FontEntity fontBold = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_BOLD_16X24);
    private FontEntity fontBig = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24, false, true);
    private Context context;

    private static final String TAG = "com.example.wenchengli.testprinter.NexgoPrinter";
    public static final int REQUEST_CODE = 1;


    private static NexgoPrinter instance = null;
    private static StringBuilder stringBuilder;

    public static NexgoPrinter getInstance() {
        return instance;
    }

    public static NexgoPrinter getNewInstance(Context context) {
        instance = new NexgoPrinter(context);
        return instance;
    }

    private NexgoPrinter(Context context) {
        this.stringBuilder = new StringBuilder();
        lock = false;
        setContext(context);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public boolean getLock() {
        return lock;
    }

    @Override
    public boolean connect(String ipAddress, int port) {
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void print(String text, Alignment alignment, FontSize fontSize, Decoration bold, LineSpace lineSpace) {

    }

    @Override
    public void print(String text) {

    }

    public int convertFontSize(FontSize fontSize) {
        switch (fontSize) {
            case EXTRA_LARGE:
            case LARGE:
//                return FONT_SIZE_BIG;
            case MEDIUM:
//                return FONT_SIZE_NORMAL;
            case NORMAL:
                return FONT_SIZE_SMALL;
            default:
                return FONT_SIZE_SMALL;
        }
    }

    public int convertAlignment(Alignment alignment) {
        switch (alignment) {
            case LEFT:
                return 0;
            case CENTER:
                return 1;
            case RIGHT:
                return 2;
            default:
                return 0;
        }
    }

    public boolean convertBold(Decoration bold) {
        switch (bold) {
            case BOLD:
                return true;
            case NORMAL:
                return false;
            default:
                return false;
        }
    }

    @Override
    public void println(String text, Alignment alignment, FontSize fontSize, Decoration bold, LineSpace lineSpace) {
        stringBuilder.append("\nPRINT,"
                + text + ","
                + convertFontSize(fontSize) + ","
                + convertAlignment(alignment) + ","
                + convertBold(bold));
    }

    @Override
    public void println(String text) {
        if (text.length() > 35) {
            String[] contents = text.split(":");
            if (contents.length == 2) {
                stringBuilder.append("\nPRINT," + contents[0] + "," + FONT_SIZE_SMALL + ",0,false");
                stringBuilder.append("\nPRINT," + contents[1] + "," + FONT_SIZE_SMALL + ",0,false");
            } else {
                String[] temp = text.split(" ");
                if (temp.length >= 2) {
                    String line1 = "";
                    String line2 = "";
                    for (int i = 0; i < temp.length; i++) {
                        if (line1.length() < 35)
                            line1 += temp[i] + " ";
                        else
                            line2 += temp[i] + " ";
                    }
                    stringBuilder.append("\nPRINT," + line1 + "," + FONT_SIZE_EXTRA_SMALL + ",1,false");
                    stringBuilder.append("\nPRINT," + line2 + "," + FONT_SIZE_EXTRA_SMALL + ",1,false");
                } else
                    stringBuilder.append("\nPRINT," + text + "," + FONT_SIZE_SMALL + ",0,false");
            }
        } else {
            stringBuilder.append("\nPRINT," + text + "," + FONT_SIZE_SMALL + ",0,false");
        }
    }

    @Override
    public void printLF() {
        stringBuilder.append("\nPRINT, ," + FONT_SIZE_SMALL + ",0,false");
    }

    @Override
    public void printBarcode(String barcode) {
        stringBuilder.append("\nBARCODE," + barcode);
    }

    @Override
    public void feedAndCutPaper() {
        execute("print");
    }

    @Override
    public void printLineSeperator() {
        stringBuilder.append("\nPRINT,------------------------------------------------------,24,1,false");
    }


    public boolean execute(String action) {
        try {
            JSONArray args = new JSONArray();
            args.put(stringBuilder.toString());
            try {
                deviceEngine = APIProxy.getDeviceEngine();
                printer = deviceEngine.getPrinter();
                printer.setTypeface(Typeface.DEFAULT);
            } catch (Exception e) {

            }


            if (action.equals("echo")) {
                String phrase = args.getString(0);
                // Echo back the first argument
            } else if (action.equals("getDate")) {
                // An example of returning data back to the web layer

            } else if (action.equals("getSN")) {
                String sn = deviceEngine.getDeviceInfo().getSn();

            } else if (action.equals("print")) {
                printer.initPrinter();
                printer.setLetterSpacing(5);
                String text = args.getString(0);
                for (String lineStr : text.split("\n")) {
                    if (lineStr.equals(""))
                        continue;
                    String line[] = lineStr.split(",");
                    String func = line[0];
                    String str = line[1];

                    if (func.equals("BARCODE")) {
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                        700
//                        5200, 2200
                        Bitmap bitmap = barcodeEncoder.encodeBitmap(str, BarcodeFormat.CODE_128, 520, 220);
                        printer.appendImage(bitmap,AlignEnum.LEFT);
//                        printer.appendBarcode("0123456789", 50, 0, 2, BarcodeFormatEnum.CODE_128, AlignEnum.CENTER);
//                        int result = printer.appendQRcode(str, 120, AlignEnum.CENTER);
//                        int result =  printer.appendBarcode(str,40,1,1,BarcodeFormatEnum.CODE_128,AlignEnum.CENTER);
//                        Log.e(TAG, result + "");
                    } else {
                        int fontsize = Integer.parseInt(line[2]);
                        int align = Integer.parseInt(line[3]);
                        boolean isBoldFont = Boolean.parseBoolean(line[4]);


                        switch (align) {
                            case 0:
                                printer.appendPrnStr(str, fontsize, AlignEnum.LEFT, isBoldFont);
                                break;
                            case 1:
                                printer.appendPrnStr(str, fontsize, AlignEnum.CENTER, isBoldFont);
                                break;
                            case 2:
                                printer.appendPrnStr(str, fontsize, AlignEnum.RIGHT, isBoldFont);
                                break;
                        }
                    }
                }
//                printer.cutPaper();
                lock = true;
                printer.startPrint(true, new OnPrintListener() {
                    @Override
                    public void onPrintResult(final int retCode) {
                        instance = new NexgoPrinter(context);
                    }
                });
            }
            return true;
        } catch (Exception e) {
            Log.d("Test", e.toString());
            return false;
        }
    }

}