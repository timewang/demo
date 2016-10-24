import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * Created by wangzhongfu on 2016/7/27.
 */
public class QueryTest {

    /**
     * 这种分词模式主要针对没有扩展词库的情况
     * 在有分词的情况下这样处理输入会造成混淆
     *
     * 并且，搜索王红，排名靠前的是王红红
     *
     * 可以针对具体场景优化改查询，比如手机号码识别，动态调整识别出来的字段权重
     *
     * 输入词越多，其整体权重应该更低
     *
     * 英文以空格进行分词
     */
    @Test
    public void queryWordTest(){

        String q = "中华人民共和国";

        char[] chars = q.toCharArray();

        StringBuilder stringBuilder = new StringBuilder(q + "^" + chars.length/10.00);

        System.out.println(chars);

        for(int i = 0; i < chars.length ; i++){
            stringBuilder.append(" " + chars[i] + "^" + Math.round((((chars.length - i)/10.00)/10.00)*1000)/1000.000);
        }

        System.out.println(stringBuilder.toString());

    }


}
