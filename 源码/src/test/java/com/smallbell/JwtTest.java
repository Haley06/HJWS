package com.smallbell;

import com.smallbell.modules.app.dao.MonitoredDao;
import com.smallbell.modules.app.form.monitor.UserDrivenResult;
import com.smallbell.modules.app.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private MonitoredDao monitoredDao;

    @Test
    public void test() {
//        String token = jwtUtils.generateToken(1);
//
//        System.out.println(token);

        System.out.println(System.currentTimeMillis());
//        String[] msg = new String[4];
//        msg[0] = "1";
//        System.out.println(Arrays.stream(msg).toArray());
    }

}
