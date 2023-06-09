package com.sju.roomreservationbackend.common.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localResolver = new SessionLocaleResolver();
        localResolver.setDefaultLocale(Locale.US);
        return localResolver;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(getValidationMsgSrc());
        return bean;
    }

    @Bean
    public static MessageSource getValidationMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/users/validation",
                "messages/rooms/validation",
                "messages/reservations/validation",
                "messages/metrics/validation",
                "messages/notification/validation"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getResponseMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/users/response",
                "messages/rooms/response",
                "messages/reservations/response",
                "messages/storage/response",
                "messages/metrics/response",
                "messages/notification/response"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getStorageMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/storage/validation",
                "messages/storage/response",
                "messages/storage/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getUserMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/users/validation",
                "messages/users/response",
                "messages/users/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getRoomMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/rooms/validation",
                "messages/rooms/response",
                "messages/rooms/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getReservationMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/reservations/validation",
                "messages/reservations/response",
                "messages/reservations/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getMetricMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/metrics/validation",
                "messages/metrics/response",
                "messages/metrics/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getNotificationMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/notification/validation",
                "messages/notification/response",
                "messages/notification/error"
        );
        return msgSrc;
    }
}
