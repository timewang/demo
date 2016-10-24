import com.webhybird.config.ApplicationConfig;
import com.webhybird.module.label.entity.Label;
import com.webhybird.module.label.repositories.LabelRepository;
import com.webhybird.module.label.service.LabelService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={ApplicationConfig.class})
public class LabelTest {

    @Autowired
    private LabelService labelServiceImpl;

    @Autowired
    private LabelRepository labelRepository;

    @Test
    public void findTest(){
        List<Label> labels = this.labelRepository.findAllByC();
        System.out.println(labels.size());
    }

    @Test
    public void saveTest(){
        System.out.println(System.getenv("CONFIG_PATH"));
        System.out.println(System.getenv("JAVA_HOME"));
        Map<String,String> map =  System.getenv();
        System.out.println(map.size());
        for(Map.Entry<String, String>  entry: map.entrySet()){
            System.out.println(" 环境变量key："+entry.getKey() + "        --> 环境变量value：" + entry.getValue());
            //Assert.assertTrue(false);
            assert (true);
        }
        Label label = new Label();
        label.setValue("阿萨德发大水");
    }
    /*Map<String,String> env = System.getenv();
		Set<String> set = env.keySet();
		Iterator i = set.iterator();
		while(i.hasNext()){
			String evnName = i.next().toString();
			System.out.print(evnName+":");
			System.out.println(System.getenv(evnName));
		}*/

}
