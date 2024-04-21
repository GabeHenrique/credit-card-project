package br.com.f1rst.cartaoapi.infrastructure.exception.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageResolver {

  private final MessageSource messageSource;

  private final List<String> availableLanguages = Arrays.asList("pt_BR", "en_US", "es_ES");

  public String translate(String code) {
    return translate(code, Collections.emptyList().toArray());
  }

  public String translate(String code, Object... args) {
    return messageSource.getMessage(code,
        args,
        code,
        LocaleContextHolder.getLocale());
  }

  public String translate(MessageSourceResolvable resolvable) {
    return messageSource.getMessage(resolvable, getLocale());
  }

  public String getLanguage() {
    String language = LocaleContextHolder.getLocale().toString();
    if (!availableLanguages.contains(language)) {
      language = "pt_BR";
    }
    return language;
  }

  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }
}