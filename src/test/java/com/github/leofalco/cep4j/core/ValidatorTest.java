package com.github.leofalco.cep4j.core;

import com.github.leofalco.cep4j.Validator;
import com.github.leofalco.cep4j.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    @Test()
    public void tratarInputFail() {
        try {
            Validator.tratarInput("1111");
            Assert.fail();
        } catch (ServiceException e) {

            assertThat(e.getServiceName()).isEqualTo("Validator");
            assertThat(e.getCode()).isEqualTo("invalid_input");
            assertThat(e.getMessage()).isEqualTo("Cep inválido");
            assertThat(e.getDescription()).isEqualTo("Cep deve conter 8 caracteres.");
        }
    }

    @Test
    public void tratarInputNull() {
        try {
            Validator.tratarInput(null);
            Assert.fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo("cep is marked non-null but is null");
        }
    }

    @Test
    public void tratarInputComTraco() {
        String s = Validator.tratarInput("00000-000");
        Assert.assertEquals("00000000", s);
    }


    @Test
    public void tratarInputSemTraco() {
        String s = Validator.tratarInput("15043330");
        Assert.assertEquals("15043330", s);
    }

    @Test
    public void tratarInput() {
        String s = Validator.tratarInput("15-04-!..33-3..0");
        Assert.assertEquals("15043330", s);
    }

}