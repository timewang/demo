/**
 *
 */
package lambdatest;

import com.webhybird.module.label.entity.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * ***********************************************************
 *
 * @类名 ：LambdaTest.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/10/24
 * ***********************************************************
 */
public class LambdaTest {


    private List<Label> labels;

    @Before void before(){
        this.labels = new ArrayList<>();
        Label label = new Label();
        label.setValue(new Date().getTime() + "**********Three");
        label.setCreateDate(new Date());
        labels.add(label);

        Label label1 = new Label();
        label1.setValue(new Date().getTime() + "*********Two");
        label1.setCreateDate(new Date());
        labels.add(label1);

        Label label2 = new Label();
        label2.setValue(new Date().getTime() + "*********One");
        label2.setCreateDate(new Date());
        labels.add(label2);
    }

    @Test
    public void testSort(){
        labels.forEach(lable -> System.out.println(lable.getValue()));
        Collections.sort(labels, new Comparator<Label>() {
            @Override public int compare(Label o1, Label o2) {
                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        });
        Collections.sort(labels,(t1,t2) -> t1.getCreateDate().compareTo(t2.getCreateDate()));
        labels.forEach(Label::getId);
    }


}
