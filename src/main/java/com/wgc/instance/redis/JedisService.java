package com.wgc.instance.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wgc.instance.dao.PUserMapper;
import com.wgc.instance.entity.PUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class JedisService {

    /*
     * Redis使用的场景： 高并发、频繁的读
     * */
    @Autowired
    PUserMapper mapper;
    /*不建议使用这种方法，搞得太复杂了*/
    public List<PUser> selectAll(String name) {
        Jedis jedis = new Jedis();
        Gson gson = new Gson();

        List<PUser> pUsers = new ArrayList<>();

        Set<String> user = jedis.zrange("user", 0, -1);
        //List<String> user = jedis.lrange("user", 0, -1);
        System.out.println(user);
        int num = 0;
        int count = 0;
        if (user != null) {
            for (String s : user) {
                num++;
                if (s.equals(name)) {
                    String str = jedis.get(name);
                    PUser pUser = gson.fromJson(str, PUser.class);
                    pUsers.add(pUser);
                    return pUsers;
                }
            }

        }
        if (num == user.size() || user == null) {
            List<PUser> list = mapper.selectAll();
            for (PUser pUser : list) {
                ++count;
                String key = "user:" + count;
                System.out.println(count);
                jedis.set(key, new Gson().toJson(pUser));
                jedis.zadd("user", count - 1, key);
            }
            return list;
        }
        return null;
    }

    /*以下的方法都可以使用*/
    public List<PUser> select() {
        try {
            Jedis jedis = new Jedis();
            String key = "newss";
            //jackson内置的对象有序列化和反序列化的方法
            ObjectMapper objectMapper = new ObjectMapper();
            //判断Redis是否有这个key
            if (jedis.exists(key)) {
                //Gson的字符串转为List<Object>
                //new Gson().fromJson(jedis.get(key), new TypeReference<List<PUser>>() {}.getType());
                //iackson字符串转为List<Object>
                return objectMapper.readValue(jedis.get(key), new TypeReference<List<PUser>>() {
                });
            }
            //Redis当中没有，直接查询数据库
            List<PUser> pUsers = mapper.selectAll();
            jedis.set("newss", objectMapper.writeValueAsString(PUser.class));
            return pUsers;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(PUser pUser) {
        int insert = mapper.insert(pUser);
        Jedis jedis = new Jedis();
        String key = "newss";
        //判断Redis是否存在这个key
        if (jedis.exists(key)) jedis.del(key);
        return insert;
    }

    /*ObjectOutStream是使用装饰模式*/
    /*Serializable是一个标志接口*/
    /*IO流缺点：
                在实体类中一定要是现实Serializable接口
                它存入的数据会有额外的功能，所以占的空间比jackson和gson的大倍
     */
    public void selectIO() throws IOException {
        /*输入流*/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(bos);
        Jedis jedis = new Jedis();
        //List<PUser> pUsers = mapper.selectAll();
        ous.writeObject(new PUser("张三"));
        ous.writeObject(new PUser("李四"));
        jedis.set("puser".getBytes(), bos.toByteArray());
        ous.close();
    }

    //使用注解的方式启动缓存
    @Cacheable("PU")
    public List<PUser> anncationSelectAll() {
        List<PUser> pUsers = mapper.selectAll();
        return pUsers;
    }

    //读入到文件中
    public void readFile() throws IOException {
        /*实际的环境中我们是不是抛异常的，要处理这个异常*/
        FileOutputStream fos = new FileOutputStream(new File("d:/Redis.class"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(new PUser("李四"));
        oos.writeObject(new PUser("王五"));
        oos.close();
    }
}
