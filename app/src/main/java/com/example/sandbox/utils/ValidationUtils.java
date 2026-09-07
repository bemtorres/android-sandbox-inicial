package com.example.sandbox.utils;

import android.util.Patterns;
import java.util.regex.Pattern;

/**
 * Utilidad central de validación.
 * Se usa en Login, Registro y todos los labs del Sandbox.
 * Cada método explica su operación interna en JavaDoc.
 */
public final class ValidationUtils {

    private ValidationUtils() {}

    // Regex internos explicados:
    // - PHONE: ^\+?[0-9 ]{9,15}$  -> opcional +, 9-15 dígitos/espacios
    // - POSTAL: ^\d{5}$           -> exactamente 5 dígitos (España)
    // - NUMERIC_PASS: ^\d{4,6}$   -> solo dígitos, 4 a 6
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9 ]{9,15}$");
    private static final Pattern POSTAL_PATTERN = Pattern.compile("^\\d{5}$");
    private static final Pattern NUMERIC_PASS_PATTERN = Pattern.compile("^\\d{4,6}$");

    /** @return true si no es null ni vacío tras trim */
    public static boolean isRequired(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /** Usa Patterns.EMAIL_ADDRESS de Android (RFC simplificado) */
    public static boolean isEmail(String s) {
        return s != null && Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    /** Mínimo 6 caracteres para password alfanumérica */
    public static boolean isPassword(String s) {
        return s != null && s.length() >= 6;
    }

    /** PIN numérico: solo dígitos 4-6 */
    public static boolean isNumericPassword(String s) {
        return s != null && NUMERIC_PASS_PATTERN.matcher(s).matches();
    }

    public static boolean isPhone(String s) {
        if (s == null) { return false; }
        String noSpaces = s.replace(" ", "");
        // validamos longitud dígitos 9-15
        return PHONE_PATTERN.matcher(s).matches() && noSpaces.replace("+","").length() >= 9;
    }

    public static boolean isPostalCode(String s) {
        return s != null && POSTAL_PATTERN.matcher(s).matches();
    }

    /** Comprueba que una fecha no sea futura (para fecha de nacimiento) */
    public static boolean isNotFuture(long millis) {
        return millis <= System.currentTimeMillis();
    }
}
