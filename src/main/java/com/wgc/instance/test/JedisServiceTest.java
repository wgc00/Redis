package com.wgc.instance.test;

import com.wgc.instance.entity.PUser;
import com.wgc.instance.redis.JedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import spring.root.RootConfig;

import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class JedisServiceTest {

    @Autowired
    JedisService service;
    @Test
    public void selectAll() {
        /*此方法不建议使用，因为几句代码能搞定的事，自己把它搞复杂化了*/
        List<PUser> pUsers = service.selectAll("user:2");
        for (PUser pUser : pUsers) {
            System.out.println(pUser.getId());
            System.out.println(pUser.getName());
        }
    }

    @Test
    public void insert() {
       service.insert(new PUser("sdfs"));
    }

    @Test
    public void selectIO()  {
        /*
        * 工作原理：我们去读取数据时，就要判断Redis是否有缓存如果有，就从中获取出来，
        * 如果没有我们就要去查询数据库了，然后在添加到Redis当中。
        *
        * 插入数据：我们为了保证数据的正确性，一般在添加或者插入数据时，对缓存进行清空，
        * 然后在查询时，在添加到Reids当中。
        *
        * 如果我们在插入数据时要同时修改Redis当中数据，而不是直接清除；当数据是几十万或上亿时
        * 你要先取出来，然后在修改，原本简单的逻辑让我们自己搞得复杂了，
        * 违背了，当前添加Reids是的作用，这样子搞只会让程序变慢
        * */
        try {
            service.selectIO();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void selectIO1() throws Exception{
        /*输出流*/
        Jedis jedis = new Jedis();
        ByteArrayInputStream bais = new ByteArrayInputStream(jedis.get("puser".getBytes()));
        ObjectInputStream ois = new ObjectInputStream(bais);
        if (jedis.exists("puser")) {
            Object o = ois.readObject();
            System.out.println(o);
        }
        ois.close();
    }

    @Test
    public void testAnnotationSelect() {
        /*
        * 使用注解的原理和上面写的IO流的输入和输出都是一样的
        * */
        List<PUser> pUsers = service.anncationSelectAll();
        System.out.println(pUsers);
    }

    @Test
    public void testReadFile() throws IOException, ClassNotFoundException {
      //  service.readFile();

        //读取Redis文件中内容
        File file = new File("d:/Redis.class");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object o = ois.readObject();
        System.out.println(o);
    }
}