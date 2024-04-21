package br.com.f1rst.cartaoapi.utils;

import br.com.f1rst.cartaoapi.infrastructure.utils.DocumentsUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentsUtilsTest {

    @Test
    void deveRetornarTrueParaCPFValido() {
        assertTrue(DocumentsUtils.isCpf("123.456.789-09"));
    }

    @Test
    void deveRetornarFalseParaCPFInvalido() {
        assertFalse(DocumentsUtils.isCpf("123.456.789-10"));
    }

    @Test
    void deveRetornarTrueParaCNPJValido() {
        assertTrue(DocumentsUtils.isCnpj("50.289.245/0001-59"));
    }

    @Test
    void deveRetornarFalseParaCNPJInvalido() {
        assertFalse(DocumentsUtils.isCnpj("12.345.678/0001-10"));
    }

    @Test
    void naoDeveRetornarNadaSeForUmCPFOuCNPJValido() {
        assertDoesNotThrow(() -> DocumentsUtils.isValidCpfCnpj("123.456.789-09"));
        assertDoesNotThrow(() -> DocumentsUtils.isValidCpfCnpj("50.289.245/0001-59"));
    }

    @Test
    void deveLancarExcecaoSeForUmCPFOuCNPJInvalido() {
        assertThrows(IllegalArgumentException.class, () -> DocumentsUtils.isValidCpfCnpj("123.456.789-10"));
        assertThrows(IllegalArgumentException.class, () -> DocumentsUtils.isValidCpfCnpj("12.345.678/0001-10"));
    }
}