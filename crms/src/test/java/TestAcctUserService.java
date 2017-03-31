import com.alibaba.fastjson.JSON;
import com.vifu.crms.model.AcctUser;
import com.vifu.crms.service.UserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 测试用户业务方法
 *
 * @author TangBo
 * @create 2017-02-28 17:09
 **/
public class TestAcctUserService extends TestBase {

    private static final Logger LOGGER_USER = Logger.getLogger(TestAcctUserService.class);
    @Autowired
    private UserService userService;

    @Test
    public void save() {
        AcctUser acctUser = new AcctUser();
        acctUser.setNickName("吴桑");
        acctUser.setRegisterTime(new Date());
        acctUser.setTelephone("13856245235");
        String id = userService.save(acctUser);
        LOGGER_USER.info(JSON.toJSONString(id));
    }
}
