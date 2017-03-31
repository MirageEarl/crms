import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类的基础类
 *
 * @author TangBo
 * @create 2017-02-28 16:43
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml","classpath:applicationContext-*.xml"})
public class TestBase {
}
