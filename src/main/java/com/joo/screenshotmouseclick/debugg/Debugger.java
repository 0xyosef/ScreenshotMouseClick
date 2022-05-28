package com.joo.screenshotmouseclick.debugg;

public class Debugger {
    private static boolean enable;

    static {
        enable = false;
    }

    public static void println(String massage) {
        Debugger.println(massage, false);
    }

    public static void println(String massage, boolean isErr) {
        if (enable) {
            if (isErr)
                System.err.println(massage);
            else
                System.out.println(massage);
        }
    }

    public static boolean isEnable() {
        return enable;
    }

    public static void setEnable(boolean enable) {
        Debugger.enable = enable;
    }
}
