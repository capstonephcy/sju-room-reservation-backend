package com.sju.roomreservationbackend.common.exception.objects;

/* Spring Validation 예외 객체 */
public class DTOValidityException extends Exception {
    public DTOValidityException(String msg) {
        super(msg);
    }
}
