/**
 *
 */
package streamtest;

import com.webhybird.module.label.entity.Label;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ***********************************************************
 *
 * @类名 ：StreamsTest.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/10/24
 * ***********************************************************
 */
public class StreamsTest {


    private List<Label> labels;

    @Before
    public void before() throws ParseException {
        this.labels = new ArrayList<>();
        Label label = new Label();
        label.setValue(new Date().getTime() + "**********Three");
        label.setCreateDate(new Date());
        label.setPageView(3);
        labels.add(label);

        Label label1 = new Label();
        label1.setValue(new Date().getTime() + "*********Two");
        label1.setCreateDate(DateUtils.parseDate("2015-12-31 10:10:00","yyyy-MM-dd hh:mm:ss"));
        label1.setPageView(4);
        labels.add(label1);

        Label label2 = new Label();
        label2.setValue(new Date().getTime() + "*********One");
        label2.setCreateDate(new Date());
        label2.setPageView(5);
        labels.add(label2);

        Label label3 = new Label();
        label3.setValue(new Date().getTime() + "*********Four");
        label3.setCreateDate(new Date());
        label3.setPageView(1);
        labels.add(label3);
    }

    @Test
    public void test(){
        labels.forEach(label -> {System.out.println(label.getCreateDate());});

        long count = labels.stream().filter(label -> label.getPageView() < 5).count();

        System.out.println(count);

        long count2 = 0L;

        for (Label l : labels) {
            if (l.getPageView() < 5)
                count2 ++;
        }

        System.out.println(count2);

        List<String> values = labels.stream().map(Label::getValue).collect(Collectors.toList());

        values.forEach(value -> System.out.println(value));

    }

}
