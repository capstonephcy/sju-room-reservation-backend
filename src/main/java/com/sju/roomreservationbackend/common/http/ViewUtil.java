package com.sju.roomreservationbackend.common.http;

import org.springframework.ui.Model;

public abstract class ViewUtil {
    public abstract String onDisplay() throws Exception;

    public String display(Model model) {
        try {
            return onDisplay();
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getLocalizedMessage());
            return "error";
        }
    }
}
