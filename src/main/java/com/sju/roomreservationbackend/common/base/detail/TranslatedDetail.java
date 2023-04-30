package com.sju.roomreservationbackend.common.base.detail;

import com.sju.roomreservationbackend.common.base.SupportedLocale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class TranslatedDetail {
    @NotBlank(message = "valid.detail.name.blank")
    @Size(max = 200, message = "valid.detail.name.size")
    @Column(nullable = false)
    protected String name;
    @NotNull(message = "valid.detail.language.null")
    @Column(nullable = false)
    @Basic(optional = false)
    protected Locale language;
    @NotBlank(message = "valid.detail.author.blank")
    @Size(max = 50, message = "valid.detail.author.size")
    @Column(nullable = false)
    protected String author;
    @NotBlank(message = "valid.detail.translator.blank")
    @Size(max = 50, message = "valid.detail.translator.size")
    @Column(nullable = false)
    protected String translator;

    public void setLanguageSupported(String localeStr) throws Exception {
        for (SupportedLocale locale : SupportedLocale.values()) {
            if (locale.getValue().equals(localeStr)) {
                this.language = new Locale(locale.getValue());
                return;
            }
        }
        throw new Exception("Locale not supported");
    }
}
