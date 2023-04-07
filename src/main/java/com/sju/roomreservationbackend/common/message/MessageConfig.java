package com.dmtlabs.aidocentserver.global.message;

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
        SessionLocaleResolver localResolver=new SessionLocaleResolver();
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
                "messages/places/validation",
                "messages/exhibitions/validation",
                "messages/courses/validation",
                "messages/tours/validation"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getResponseMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/users/response",
                "messages/places/response",
                "messages/exhibitions/response",
                "messages/courses/response",
                "messages/tours/response"
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
    public static MessageSource getCourseMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/courses/validation",
                "messages/courses/response",
                "messages/courses/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getPlaceMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/places/validation",
                "messages/places/response",
                "messages/places/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getTourMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/tours/validation",
                "messages/tours/response",
                "messages/tours/error"
        );
        return msgSrc;
    }

    @Bean
    public static MessageSource getExhibitionMsgSrc() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setBasenames(
                "messages/global/validation",
                "messages/exhibitions/validation",
                "messages/exhibitions/response",
                "messages/exhibitions/error"
        );
        return msgSrc;
    }
}
