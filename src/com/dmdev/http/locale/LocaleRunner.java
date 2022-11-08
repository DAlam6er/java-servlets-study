package com.dmdev.http.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleRunner
{
    public static void main(String[] args)
    {
        Locale locale = new Locale("ru", "RU");
        System.out.println(Locale.US);
        System.out.println(Locale.getDefault());

        var translations = ResourceBundle.getBundle("translations", locale);
        // может быть выброшено исключение java.util.MissingResourceException
        System.out.println(translations.getString("page.login.password"));
        System.out.println(translations.getBaseBundleName());
    }
}
