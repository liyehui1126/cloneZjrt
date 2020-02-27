package com.cloneZjrt;

import com.cloneZjrt.controller.UserController;
import com.cloneZjrt.dao.UserDAO;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.service.UserService;
import com.cloneZjrt.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2020-1-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestDAO {

    @Autowired
    private UserDAO userDAO;

    private static RedisTemplate redisTemplate;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

//    protected final Logger log =LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        TestDAO.redisTemplate = redisTemplate;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    static RedisUtil redisUtil = new RedisUtil();

    static{
        RedisUtil.setCachePrefix("userid");
        if(!RedisUtil.openCache()){
            System.out.println("open error");
        }
    }

    @Test
    public void testOne() throws Exception {

//        LOG.info("info================");
//        List<String> list = new ArrayList<>();
//        try{
//            System.out.println(list.get(1));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            LOG.error(e.getMessage(), e);
//        }
//        System.out.println(userDAO.queryAll().size());
//        LOG.error("===");
    }

    @Test
    public void testTwo(){
        try{
            stringRedisTemplate.opsForValue().set("test", "测试");
            System.out.println("value："+stringRedisTemplate.opsForValue().get("test"));
//            redisTemplate.opsForValue().set("userid:1", "ha");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testThree(){
        UserEntity userEntity = new UserEntity(2l,"second");
        try{
            if(!RedisUtil.set(String.valueOf(userEntity.getUserId()),userEntity)){
                System.out.println("set error");
            }else {
                System.out.println("ok set");
            }
//            if(!RedisUtil.setString(String.valueOf(userid),name)){
//                System.out.println("set error");
//            }else{
//                System.out.println("ok set");
//            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    static{
        RedisUtil.setCachePrefix("userid");
        if(!RedisUtil.openCache()){
            System.out.println("open error");
        }
    }

    @Test
    public void testQuery(){
        Long userid = 2l;
        String name = new String();
        try{
            name = RedisUtil.getString(String.valueOf(userid));
            if(name != null){
                System.out.println("get name by redis:" + name);
            }else {
                name = userDAO.getUserById(userid).getUserName();
                System.out.println("get name by mysql:" + name);

                if(!RedisUtil.setString(String.valueOf(userid),name)){
                    System.out.println("set error");
                }else{
                    System.out.println("set OK");
                }
            }
//            UserEntity userEntity = RedisUtil.getObj(String.valueOf(userid),UserEntity.class);
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        System.out.println("Mission accomplished");
    }

    @Autowired
    private UserService userService;

    @Test
    public void testController(){
        userService.getUserByName("111");
    }
}
