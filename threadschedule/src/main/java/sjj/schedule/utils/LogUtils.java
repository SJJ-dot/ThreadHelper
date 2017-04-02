package sjj.schedule.utils;

import android.util.Log;

import sjj.schedule.BuildConfig;

/**
 * Created by sjj on 2016-10-11.
 */

public class LogUtils {
    private static boolean enable = BuildConfig.DEBUG;
    private static final int DEBUG = 0;
    private static final int INFO = 1;
    private static final int WARN = 2;
    private static final int ERROR = 3;
    private static final int ALL = -1;
    private static int lev = ALL;

    public static void d(Object object) {
        log(DEBUG, getCallM(), object + "");
    }

    public static void d(int i, Object o) {
        log(DEBUG, getCallM(i), o + "");
    }

    public static void e(Object object) {
        log(ERROR, getCallM(), object + "");
    }
    public static void e(int sq,Object object) {
        log(ERROR, getCallM(sq), object + "");
    }
    public static void e(Object object, Throwable throwable) {
        log(ERROR, getCallM(), object + "", throwable);
    }

    public static void e(int sq, Object object, Throwable throwable) {
        log(ERROR, getCallM(sq), object + "", throwable);
    }

    public static void i(Object object) {
        log(INFO, getCallM(), object + "");
    }

    public static void i(Object object, Throwable throwable) {
        log(INFO, getCallM(), object + "", throwable);
    }

    public static void i(int sq, Object object) {
        log(INFO, getCallM(sq), object + "");

    }

    public static void w(int sq, Object object, Throwable throwable) {
        log(WARN, getCallM(sq), object + "", throwable);
    }

    public static void w(Object object, Throwable throwable) {
        log(WARN, getCallM(), object + "", throwable);
    }

    public static void w(int sq, Object object) {
        log(WARN, getCallM(sq), object + "");
    }

    public static void w(Object object) {
        log(WARN, getCallM(), object + "");
    }

    private static void log(int lev, String tag, String message) {
        if (!enable && lev < LogUtils.lev) return;
        switch (lev) {
            case DEBUG:
                Log.d(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case WARN:
                Log.w(tag, message);
                break;
            case ERROR:
                Log.e(tag, message);
                break;
        }
    }

    private static void log(int lev, String tag, String message, Throwable throwable) {
        if (!enable && lev < LogUtils.lev) return;
        if (throwable == null) {
            log(lev, tag, message);
        } else {
            switch (lev) {
                case DEBUG:
                    Log.d(tag, message, throwable);
                    break;
                case INFO:
                    Log.i(tag, message, throwable);
                    break;
                case WARN:
                    Log.w(tag, message, throwable);
                    break;
                case ERROR:
                    Log.e(tag, message, throwable);
                    break;
            }
        }
    }

    private static String getCallM() {
        return getCallM(1);
    }

    private static String getCallM(int sq) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[4 + sq];
        StringBuilder buf = new StringBuilder();

        buf.append("(tag)").append(element.getMethodName());

        if (element.isNativeMethod()) {
            buf.append("(Native Method)");
        } else {
            String fName = element.getFileName();

            if (fName == null) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = element.getLineNumber();

                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }
                buf.append(')');
            }
        }

        return buf.toString();
    }


}