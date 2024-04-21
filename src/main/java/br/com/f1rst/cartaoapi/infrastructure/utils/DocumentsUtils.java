package br.com.f1rst.cartaoapi.infrastructure.utils;

import jakarta.validation.constraints.NotNull;

import java.util.InputMismatchException;

public class DocumentsUtils {

  public DocumentsUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Realiza a validação de um cpf ou cnpj
   *
   * @param document String - o document pode ser passado no formato 999.999.999-99, 99999999999, 99.999.999/9999-99 ou 99999999999999
   */
  public static void isValidCpfCnpj(@NotNull String document) {
    if (!(isCpf(document) || isCnpj(document))) {
      throw new IllegalArgumentException("invalid.cpf.cnpj");
    }
  }

  /**
   * Realiza a validação de um cpf
   *
   * @param cpf String - o CPF pode ser passado no formato 999.999.999-99 ou 99999999999
   * @return boolean - true se for um cpf válido, false caso contrário
   */
  public static boolean isCpf(String cpf) {
    cpf = cpf.replace(".", "");
    cpf = cpf.replace("-", "");

    try {
      Long.parseLong(cpf);
    } catch (NumberFormatException e) {
      return false;
    }

    int d1;
    int d2;
    int digito1;
    int digito2;
    int resto;
    int digitoCPF;
    String nDigResult;

    d1 = d2 = 0;

    for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
      digitoCPF = Integer.parseInt(cpf.substring(nCount - 1, nCount));
      d1 = d1 + (11 - nCount) * digitoCPF;
      d2 = d2 + (12 - nCount) * digitoCPF;
    }
    resto = (d1 % 11);

    if (resto < 2)
      digito1 = 0;
    else
      digito1 = 11 - resto;

    d2 += 2 * digito1;

    resto = (d2 % 11);

    if (resto < 2)
      digito2 = 0;
    else
      digito2 = 11 - resto;

    String nDigVerific = cpf.substring(cpf.length() - 2);
    nDigResult = String.valueOf(digito1) + digito2;

    return nDigVerific.equals(nDigResult);
  }

  /**
   * Realiza a validação de um cnpj
   *
   * @param cnpj String - o CNPJ pode ser passado no formato 99.999.999/9999-99 ou 99999999999999
   * @return boolean - true se for um cnpj válido, false caso contrário
   */
  public static boolean isCnpj(String cnpj) {
    cnpj = cnpj.replace(".", "");
    cnpj = cnpj.replace("-", "");
    cnpj = cnpj.replace("/", "");

    try {
      Long.parseLong(cnpj);
    } catch (NumberFormatException e) {
      return false;
    }

    if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
        || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
        || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
        || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
        || cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14))
      return (false);
    char dig13;
    char dig14;
    int sm;
    int i;
    int r;
    int num;
    int peso;

    try {
      sm = 0;
      peso = 2;
      for (i = 11; i >= 0; i--) {
        num = (cnpj.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
          peso = 2;
      }

      r = sm % 11;
      if ((r == 0) || (r == 1))
        dig13 = '0';
      else
        dig13 = (char) ((11 - r) + 48);

      sm = 0;
      peso = 2;
      for (i = 12; i >= 0; i--) {
        num = (cnpj.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso + 1;
        if (peso == 10)
          peso = 2;
      }
      r = sm % 11;
      if ((r == 0) || (r == 1))
        dig14 = '0';
      else
        dig14 = (char) ((11 - r) + 48);

      return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
    } catch (InputMismatchException erro) {
      return (false);
    }

  }
}
