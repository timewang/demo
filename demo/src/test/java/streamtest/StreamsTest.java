/**
 *
 */
package streamtest;

import com.webhybird.module.label.entity.Label;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

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

    private List<Label> labels1;

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


        this.labels1 = new ArrayList<>();
        Label labe4 = new Label();
        labe4.setValue(new Date().getTime() + "**********Three");
        labe4.setCreateDate(new Date());
        labe4.setPageView(2);
        labels1.add(label);

        Label label5 = new Label();
        label5.setValue(new Date().getTime() + "*********Two");
        label5.setCreateDate(DateUtils.parseDate("2015-12-31 10:10:00","yyyy-MM-dd hh:mm:ss"));
        label5.setPageView(6);
        labels1.add(label1);

        Label label6 = new Label();
        label6.setValue(new Date().getTime() + "*********One");
        label6.setCreateDate(new Date());
        label6.setPageView(7);
        labels1.add(label2);

        Label label7 = new Label();
        label7.setValue(new Date().getTime() + "*********Four");
        label7.setCreateDate(new Date());
        label7.setPageView(8);
        labels1.add(label3);
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
        // 将两个集合合并
        List<String> values1 = Stream.of(labels,labels1).flatMap(labels2 -> labels2.stream()).map(Label::getValue).collect(Collectors.toList());
        System.out.println(values1.size());
        Label label = labels.stream().min(Comparator.comparing(Label::getPageView)).get();
        Label label2 = labels.stream().max(Comparator.comparing(Label::getPageView)).get();
        System.out.println(label.getPageView());
        System.out.println(label2.getPageView());
        String allValues = values1.stream().reduce("",((s, label1) -> s + label1));
        System.out.println(allValues);

        String result = labels.stream().map(Label::getValue).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(result);

        //分组
       Map<Date,List<Label>> dateListMap = labels.stream().collect(groupingBy(Label::getCreateDate));

    }

}
