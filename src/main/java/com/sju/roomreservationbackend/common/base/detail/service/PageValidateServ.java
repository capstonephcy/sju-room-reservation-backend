package com.dmtlabs.aidocentserver.global.base.detail.service;

import com.dmtlabs.aidocentserver.global.exception.objects.DTOValidityException;
import com.dmtlabs.aidocentserver.global.message.MessageConfig;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PageValidateServ {
    private final MessageSource msgSrc = MessageConfig.getValidationMsgSrc();

    public void checkPageSize(int pageSize) throws DTOValidityException {
        if (pageSize > 1000) {
            throw new DTOValidityException(
                    msgSrc.getMessage("valid.page.size", new String[]{"1000"} , Locale.ENGLISH)
            );
        }
    }

    public void checkPageIdx(int reqPageIdx, int totalPages) throws DTOValidityException {
        if (reqPageIdx >= totalPages) {
            throw new DTOValidityException(
                    msgSrc.getMessage("valid.page.idx", new String[]{Integer.valueOf(totalPages).toString()}, Locale.ENGLISH)
            );
        }
    }
}
