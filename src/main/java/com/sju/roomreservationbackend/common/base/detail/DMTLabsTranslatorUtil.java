package com.sju.roomreservationbackend.common.base.detail;

import com.sju.roomreservationbackend.common.base.SupportedLocale;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DMTLabsTranslatorUtil {
    public static String translateText(String text, Locale from, Locale to) throws Exception {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("from", from.toString());
        reqBody.addProperty("to", to.toString());
        reqBody.addProperty("text", text);

        TranslateText[] response = WebClient.create("https://dmtcloud.kr/translate-text").post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reqBody.toString()))
                .retrieve()
                .bodyToMono(TranslateText[].class)
                .block();

        if (response == null || response.length != 1 || response[0] == null || response[0].getTranslations() == null) {
            throw new Exception("DMTLabs translator API failure");
        }
        String translation = response[0].getTranslations();
        return translation.replace("\n", "").trim();
    }

    public static List<Locale> convertTargetLangStringsToLocales(List<String> languages) throws Exception {
        List<Locale> targetLanguages = new ArrayList<>();
        for (String langStr : languages) {
            targetLanguages.add(SupportedLocale.getLanguageSupported(langStr));
        }
        return targetLanguages;
    }
}
