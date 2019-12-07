package com.hong.py;



import com.hong.py.pojo.UmsAdmin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


/**
 *  hamcrest一切都是Matcher (*￣︶￣)
 */
public class JunitTest {

    private static List<String> hamcrestTestList;

    private static String[] hamcrestTestArray;

    private static UmsAdmin umsAdmin;

    private static UmsAdmin umsAdminMock;

    @BeforeAll
    public static void setUp() {

        hamcrestTestList = new ArrayList<>();
        hamcrestTestList.add("first element");
        hamcrestTestList.add("second element");
        hamcrestTestList.add("third element");

        hamcrestTestArray = new String[3];
        hamcrestTestArray[0] = "first element";
        hamcrestTestArray[1] = "second element";
        hamcrestTestArray[2] = "third element";

        umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("hongpy");
        umsAdmin.setNickName("hpy");
        umsAdmin.setPassword("1234");
        umsAdmin.setStatus(1);
    }

    @Test
    public void Testhamcrest() {

        String world = "123";

        //判断是否相等  junit
        assertEquals("123",world);

        // 传入Matcher
        assertThat(world, instanceOf(String.class));

        //检查表达式是否为真
        assertTrue(hamcrestTestList.contains("first element")
                || hamcrestTestList.contains("second element")
                || hamcrestTestList.contains("third element"));

        //equalTo 检查两个对象是否相等；
        //anyOf 检查是否包含匹配器中的一个，相当于(||)；
        //hasItem测试集合是否有一个或者多个元素
        assertThat(hamcrestTestList, hasItem(anyOf(equalTo("first element"), equalTo("second element"),
                equalTo("third element"))));


        assertThat(hamcrestTestArray, hasItemInArray("first element"));


        //比较大小
        double clo=0.004688;
        closeTo(0.005,clo);

        assertThat(clo, greaterThanOrEqualTo(0.0045));

        assertThat(clo, lessThan(0.005));

        assertThat(clo, lessThanOrEqualTo (0.004688));


        //字符文本
        String worldCase = "123ABCedf";

        assertThat(worldCase, equalToIgnoringCase("123ABCEDF"));

        String worldWhiteSpace = "123ABCedf";

        //assertThat(worldWhiteSpace, equalToCompressingWhiteSpace("1 2 3A B Ce d f "));

        assertThat(worldCase, containsString ("123A"));

        assertThat(worldCase, containsStringIgnoringCase ("abc"));

        assertThat(worldCase, org.hamcrest.Matchers.startsWith ("123"));

        assertThat(worldCase, org.hamcrest.Matchers.endsWith ("edf"));


        //对象
        assertThat(umsAdmin,hasProperty("nickName"));

        assertThat(umsAdmin.getNickName().equals("hpy"), is(true));

        assertThat(umsAdmin, instanceOf(UmsAdmin.class));

        assertThat(umsAdmin, notNullValue());

        assertThat(umsAdmin, nullValue());

    }

    @Test
    public void TestMock() {

        umsAdminMock = mock(UmsAdmin.class);

        umsAdminMock.setUsername("hongpy");
        umsAdminMock.setNickName("hpy");
        umsAdminMock.setPassword("1234");
        umsAdminMock.setStatus(1);
        umsAdminMock.setStatus(1);
        umsAdminMock.setEmail("hpymn123@163.com");

        //验证有没有执行setEmail("hpymn123")
        //verify(umsAdminMock).setEmail("hpymn123");

        //是否setStatus执行了2遍
        verify(umsAdminMock,times(2)).setStatus(1);

        List singleMock = mock(List.class);
        singleMock.add("first element");
        singleMock.add("second element");
        singleMock.add("third element");


        verify(singleMock).add("third element");

        //verification using never(). never() is an alias to times(0)
        //verify(singleMock, never()).add("never happened");
        //verify(singleMock, atLeastOnce()).add("first element");
        //verify(singleMock, atLeast(2)).add("first element");
        //verify(singleMock, atMost(5)).add("first element");

        verify(singleMock, atLeastOnce()).add(any());

        //添加预言
        when(singleMock.get(0)).thenReturn("offset 0");
        when(singleMock.get(1)).thenReturn("offset 1");

        //使用参数匹配 如果一个参数使用了则全部需要使用
        when(singleMock.get(anyInt())).thenReturn("element anyInt");

        System.out.println(singleMock.get(0));
        System.out.println(singleMock.get(1));

        //抛出异常
        when(singleMock.get(2)).thenThrow(mock(RuntimeException.class));

        System.out.println(singleMock.get(2));
    }
}
