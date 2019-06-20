package me.leo.cepj4.core;

import me.leo.cepj4.Util;
import me.leo.cepj4.exceptions.InvalidCepLengthException;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

    @Test(expected = InvalidCepLengthException.class)
    public void tratarInputFail() {
        Util.tratarInput("1111");
    }


    @Test
    public void tratarInputComTracoSuccess() {
        String s = Util.tratarInput("00000-000");
        Assert.assertEquals("00000000", s);
    }


    @Test
    public void tratarInputSemTracoSuccess() {
        String s = Util.tratarInput("15043330");
        Assert.assertEquals("15043330", s);
    }
}