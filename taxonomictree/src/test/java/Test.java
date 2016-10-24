import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/11.
 */
public class Test {



    @org.junit.Test
    public void enumToStringTest(){
        System.out.println(MessageTypeEnum.MAIL);
        System.out.println(MessageTypeEnum.MAIL.toString());
        System.out.println(MessageTypeEnum.MAIL.name());
    }
    @org.junit.Test
    public void test2(){
        Date date = new Date();
        Date date1 = date;
        Date date2 = new Date();
        System.out.println(date == date1);
        System.out.println(date.equals(date1));
        System.out.println(date == date2);
        System.out.println(date.equals(date2));
    }
    @org.junit.Test
    public void test3(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String[] b = list.toArray(new String[list.size()]);
        for(String s:b){
            System.out.println(s);
        }
    }

}
