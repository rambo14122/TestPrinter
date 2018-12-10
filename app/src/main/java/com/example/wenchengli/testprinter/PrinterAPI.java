package com.example.wenchengli.testprinter;

import android.content.Context;

/**
 * Created by Babie on 31/8/15.
 */
public interface PrinterAPI {

    public Context getContext();

    public void setContext(Context context);

    public void setLock(boolean lock);

    public boolean getLock();

    public boolean connect(String ipAddress, int port);

    public boolean close();

    public boolean isConnected();

    public void print(String text, Alignment alignment, FontSize fontSize, Decoration bold, LineSpace lineSpace);

    public void print(String text);

    public void println(String text, Alignment alignment, FontSize fontSize, Decoration bold, LineSpace lineSpace);

    public void println(String text);

    public void printLF();

    public void printBarcode(String barcode);

    public void feedAndCutPaper();

    public void printLineSeperator();

    public enum Alignment {

        LEFT((byte) 0), CENTER((byte) 1), RIGHT((byte) 2);

        private byte value;

        private Alignment(byte value) {
            this.value = value;
        }

        public byte value() {
            return value;
        }

    }

    public enum FontSize {

//        Z33((byte) 0x33),
//        Z23((byte) 0x23),
//        Z22((byte) 0x22),
        EXTRA_LARGE((byte) 0x21),
        LARGE((byte) 0x12),
//        Z11((byte) 0x11),
//        Z10((byte) 0x10),
        MEDIUM((byte) 0x01),
        NORMAL((byte) 0x00),
        SMALL((byte) 0x22);

        private byte value;

        private FontSize(byte value) {
            this.value = value;
        }

        public byte value() {
            return value;
        }
    }

    public enum LineSpace {

        BIG((byte) 0x30), MEDIUM((byte) 0x20), SMALL((byte) 0x10);

        private byte value;

        private LineSpace(byte value) {
            this.value = value;
        }

        public byte value() {
            return value;
        }
    }

    public enum Decoration {

        BOLD((byte) 0x01), NORMAL((byte) 0x00);

        private byte value;

        private Decoration(byte value) {
            this.value = value;
        }

        public byte value() {
            return value;
        }
    }

}
