package com.dmtlabs.aidocentserver.global.base;

import java.util.Locale;

public enum SupportedLocale {
    en("en"),
    ko("ko"),
    ja("ja"),
    cn("zh-cn"),
    de("de"),
    fr("fr"),
    es("es"),
    pt("pt"),
    it("it"),
    ru("ru"),
    vi("vi");

    private final String value;
    SupportedLocale(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public static Locale getLanguageSupported(String localeStr) throws Exception {
        for (SupportedLocale locale : SupportedLocale.values()) {
            if (locale.getValue().equalsIgnoreCase(localeStr)) {
                return new Locale(locale.getValue());
            }
        }
        throw new Exception("Locale not supported");
    }
}
