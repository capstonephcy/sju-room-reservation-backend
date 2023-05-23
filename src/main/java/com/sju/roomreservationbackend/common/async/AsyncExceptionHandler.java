package com.sju.roomreservationbackend.common.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, java.lang.reflect.Method method, Object... params) {
            System.out.println("--- THREAD ERROR ---");
            System.out.println("Exception Message: " + ex.getMessage());
            System.out.println("Exception Method: " + method.getName());
            for(Object param : params) {
                System.out.println("Exception Parameter Values: " + param);
            }
        }
}
