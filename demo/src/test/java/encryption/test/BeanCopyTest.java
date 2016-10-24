package encryption.test;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * Created by wangzhongfu on 2016/1/6.
 */
public class BeanCopyTest {

    @Test
    public void copyTest(){
        CopyTestBean1 copyTestBean1 = new CopyTestBean1();
        copyTestBean1.setId("1");
        copyTestBean1.setName("哈哈哈哈");
        copyTestBean1.setCreatedBy("admin");

        CopyTestBean2 copyTestBean2 = new CopyTestBean2();
        copyTestBean2.setName("修改了的值");

        BeanUtils.copyProperties(copyTestBean2,copyTestBean1,"createdBy","id");

        System.out.println(copyTestBean1.getName());
        System.out.println(copyTestBean1.getId());
        System.out.println(copyTestBean1.getCreatedBy());
    }

}