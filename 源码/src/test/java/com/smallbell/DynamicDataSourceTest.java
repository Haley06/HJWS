package com.smallbell;

import com.smallbell.modules.app.dao.MessageDao;
import com.smallbell.service.DynamicDataSourceTestService;
import org.apache.logging.log4j.util.PropertySource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 多数据源测试
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DynamicDataSourceTestService dynamicDataSourceTestService;

    @Resource
    private MessageDao messageDao;
    @Test
    public void test(){
//        Long id = 1L;
//
//        dynamicDataSourceTestService.updateUser(id);
//        dynamicDataSourceTestService.updateUserBySlave1(id);
//        dynamicDataSourceTestService.updateUserBySlave2(id);
        //messageDao.s
    }

    @Test
    public  void qingwa() {

        int count = 0;
        for (int i = 1; i <= 2020; ++i) {
            String string = i + "";
            for (int j = 0; i < string.length(); ++j) {
                if (string.charAt(j) == '2')
                    count++;
            }
        }

        System.out.println(count);
    }

}
