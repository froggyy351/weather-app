package com.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.*;
import org.junit.jupiter.api.Test;;

/**
 * Unit test for simple App.
 * mavenのtestコマンドで実行。
 */
public class AppTest {

    //assertEquals(期待値, テストしたい関数)

    //正常系
    @Test
    public void testGetAdviceCold(){
        assertEquals("コートやダウンなど上着が必須です", App.getAdvice(1.0));
    }

    @Test
    public void testGetAdviceCool(){
        assertEquals("ジャケットがおすすめです", App.getAdvice(11.0));
    }

    @Test
    public void testGetAdviceWarm(){
        assertEquals("半袖でも大丈夫そうです", App.getAdvice(21.0));
    }
}
