import com.webhybird.config.ApplicationConfig;
import com.webhybird.framework.util.RandomUtils;
import com.webhybird.module.tree.entity.ZtreeEntity;
import com.webhybird.module.tree.service.ZtreeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.ArrayList;
/**
 * Created by wangzhongfu on 2015/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={ApplicationConfig.class})
public class ZtreeTest {


    @Autowired
    private ZtreeService ztreeServiceImpl;

    private List<ZtreeEntity> ztreeEntityList = new ArrayList<>();
    @Before
    public void initData(){
        String root1Id = RandomUtils.uuid2();
        ZtreeEntity root1 = new ZtreeEntity();
        root1.setId(root1Id);
        root1.setPid("0");
        root1.setName("第一个跟节点");
        root1.setIsParent(true);
        root1.setType("org");
        this.ztreeEntityList.add(root1);

        String leaf1Id = RandomUtils.uuid2();
        ZtreeEntity leaf1 = new ZtreeEntity();
        leaf1.setId(leaf1Id);
        leaf1.setPid(root1Id);
        leaf1.setName("第一个子节点的第一个节点");
        leaf1.setIsParent(false);
        leaf1.setType("org");
        ztreeEntityList.add(leaf1);

        String leaf2Id = RandomUtils.uuid2();
        ZtreeEntity leaf2 = new ZtreeEntity();
        leaf2.setId(leaf2Id);
        leaf2.setPid(root1Id);
        leaf2.setName("第一个节点的第二个子节点");
        leaf2.setIsParent(false);
        leaf2.setType("org");
        ztreeEntityList.add(leaf2);

        String leaf3Id = RandomUtils.uuid2();
        ZtreeEntity leaf3 = new ZtreeEntity();
        leaf3.setId(leaf3Id);
        leaf3.setPid(root1Id);
        leaf3.setName("第一个节点的第三个字节点");
        leaf3.setIsParent(true);
        leaf3.setType("org");
        ztreeEntityList.add(leaf3);

        String leaf4Id = RandomUtils.uuid2();
        ZtreeEntity leaf4 = new ZtreeEntity();
        leaf4.setId(leaf4Id);
        leaf4.setPid(leaf3Id);
        leaf4.setName("第三个子节点的第一个子节点");
        leaf4.setIsParent(false);
        leaf4.setType("org");
        ztreeEntityList.add(leaf4);

        String root2Id = RandomUtils.uuid2();
        ZtreeEntity root2 = new ZtreeEntity();
        root2.setId(root2Id);
        root2.setPid("0");
        root2.setName("第二个节点");
        root2.setIsParent(true);
        root2.setType("org");
        ztreeEntityList.add(root2);

        String leaf5Id = RandomUtils.uuid2();
        ZtreeEntity leaf5 = new ZtreeEntity();
        leaf5.setId(leaf5Id);
        leaf5.setPid(root2Id);
        leaf5.setName("第二个字节点的第一个子节点");
        leaf5.setIsParent(false);
        leaf5.setType("org");
        ztreeEntityList.add(leaf5);

        String leaf6Id = RandomUtils.uuid2();
        ZtreeEntity leaf6 = new ZtreeEntity();
        leaf6.setId(leaf6Id);
        leaf6.setPid(root2Id);
        leaf6.setName("第二个节点的第二个子节点");
        leaf6.setIsParent(false);
        leaf6.setType("org");
        ztreeEntityList.add(leaf6);
    }
    @org.junit.Test
    public void testSave(){
        for(ZtreeEntity ztreeEntity : this.ztreeEntityList){
            this.ztreeServiceImpl.saveZtree(ztreeEntity,false);
        }
    }
    @org.junit.Test
    public void testFindOrg(){
        List<ZtreeEntity> ztreeEntities = this.ztreeServiceImpl.findRootTree("org");
        System.out.println(ztreeEntities.size());
    }
}
