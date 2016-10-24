import com.webhybird.module.sso.common.Token;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/26.
 */
public class TestFinal {

    private static final Map<String,String> map = new HashMap<>();

    public static void put(){
        map.put("sdfdsf","sdfds");
    }

    public static void get(){
        System.out.println(map.get("sdfdsf"));
    }

    @Test
    public void test(){
        TestFinal.put();
        TestFinal.get();
    }

    @Test
    public void test2(){
        Token token = null;
        Assert.notNull(token,"token信息不能为空");
    }
    @Test
    public void test3(){
        String a = "http://www.domain.com/sadfasdf";
        String b = "http://www.domain.com";
        String c = "http://www.domain.com/";
       // System.out.println(a.substring(a.indexOf("//")+ 2));
        String d = a.substring(a.indexOf("//")+ 2);
        System.out.println(d.substring(0,d.indexOf("/")));
    }

}
