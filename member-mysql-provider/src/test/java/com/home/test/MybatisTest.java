package com.home.test;

import com.home.entity.po.MemberPO;
import com.home.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/24 16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class MybatisTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;


    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void testMapper() throws SQLException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String source ="123123";
        String encode = bCryptPasswordEncoder.encode(source);
        MemberPO memberPO =  new MemberPO(null,"jack",encode,"杰克","jack@qq.com",1,1,"杰克·马","123123",2);
        memberPOMapper.insert(memberPO);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }
}
