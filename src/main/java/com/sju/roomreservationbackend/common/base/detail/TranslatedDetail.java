package com.dmtlabs.aidocentserver.global.base.detail;

import com.dmtlabs.aidocentserver.global.base.SupportedLocale;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
