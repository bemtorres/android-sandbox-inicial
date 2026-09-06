package com.example.sandbox.utils;

import android.util.Log;

/**
 * Wrapper de Logcat para demostrar niveles.
 * Uso: LoggerUtils.d(TAG, "mensaje");
 * Niveles: v (verbose), d (debug), i (info), w (warn), e (error)
 */
public final class LoggerUtils {
    private LoggerUtils() {}

    public static void v(String tag, String msg) { Log.v(tag, msg); }
    public static void d(String tag, String msg) { Log.d(tag, msg); }
    public static void i(String tag, String msg) { Log.i(tag, msg); }
    public static void w(String tag, String msg) { Log.w(tag, msg); }
    public static void e(String tag, String msg) { Log.e(tag, msg); }

    public static void demo(String tag) {
        Log.v(tag, "VERBOSE: traza detallada (solo debug)");
        Log.d(tag, "DEBUG: flujo interno del botón/validación");
        Log.i(tag, "INFO: evento esperado (login ok)");
        Log.w(tag, "WARN: dato sospechoso pero recuperable");
        Log.e(tag, "ERROR: validación fallida o excepción");
    }
}
