package util;

import jakarta.servlet.http.HttpServletRequest;

public class ParamUtil {

    public static String getString(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    public static int getInt(HttpServletRequest request, String name, int defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String name, boolean defaultValue) {
        String value = request.getParameter(name);
        if (value != null) {
            return value.equalsIgnoreCase("true") || value.equals("1");
        }
        return defaultValue;
    }
}