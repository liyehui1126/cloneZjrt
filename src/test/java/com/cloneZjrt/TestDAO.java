package com.cloneZjrt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloneZjrt.controller.UserController;
import com.cloneZjrt.dao.UserDao;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.repository.UserRepository;
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

import java.util.Map;

/**
 * Created by Administrator on 2020-1-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@WebAppConfiguration
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class TestDAO {

    @Autowired
    private UserDao userDAO;

    private static RedisTemplate redisTemplate;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

//    protected final Logger log =LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        TestDAO.redisTemplate = redisTemplate;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static{
        RedisUtil.setCachePrefix("userid");
        if(!RedisUtil.openCache()){
            System.out.println("open error");
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void testOne() throws Exception {
//        try {
//            //获取UserController对象
//            Class userController = Class.forName("com.cloneZjrt.controller.UserController");
//
//            //这里形参为String.class,因为我这个方法返回的是String
//            Method userMethod = userController.getMethod("test2", String.class);
//
//            if(userMethod.isAnnotationPresent(XXSecurity.class)){
//                System.out.println("UserController类上配置了XXSecurity注解！");
//                //获取该元素上指定类型的注解
//                XXSecurity xxSecurity = userMethod.getAnnotation(XXSecurity.class);
//                System.out.println("RoleConstant: " + xxSecurity.value());
//                //在注解中，我设置的value属性是是一个数组，遍历一下全部打印
//                for(Long l : xxSecurity.value()){
//                    System.out.println(l);
//                }
//            }else{
//                System.out.println("UserController类上没有配置XXSecurity注解！");
//            }
//        } catch (LogicException e) {
//            e.printStackTrace();
//        }

//        LOG.info("info================");
//        List<String> list = new ArrayList<>();
//        try{
//            System.out.println(list.get(1));
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            LOG.error(e.getMessage(), e);
//        }
//        for(UserEntity u : userDAO.queryAll()){
//            System.out.println(u.getName());
//        }

        UserEntity userEntity = userDAO.getUserById(1l);
        System.out.println(JSON.parseObject(JSON.toJSONString(userEntity), new TypeReference<Map<String, Object>>() {}));
//
//        System.out.println(userMap);

//        UserEntity u1 = new UserEntity("user1");
//        int i = userDAO.insertUser(u1);
//        System.out.println(i + " == " + u1.getUserId());

//        int i = userDAO.update(new UserEntity(3l,"333"));
//        System.out.println(i);
//        UserEntity u2 = new UserEntity("user2");
//        List<UserEntity> userEntities = new ArrayList<UserEntity>(Arrays.asList(u1));
//        int i = userDAO.insertUsers(userEntities);
//        System.out.println(i);

//        List<Long> userIds = new ArrayList<Long>();
//        List<UserEntity> userEntities = userDAO.queryByIds(userIds,1l,5l);
//        for(UserEntity u:userEntities){
//            Map<String, Object> userMap = JSON.parseObject(JSON.toJSONString(u), new TypeReference<Map<String, Object>>() {});
//            System.out.println(userMap);
//        }

//        List<UserEntity> userEntities = userDAO.getUserByNameTest("1");
//        for(UserEntity u : userEntities){
//            System.out.println(JSON.parseObject(JSON.toJSONString(u), new TypeReference<Map<String, Object>>(){}));
//        }

//        System.out.println(userDAO.queryAll().size());
//        LOG.error("===");
//        System.out.println();
//        new BusinessException("文件已存在",415l).printStackTrace();
//        String s = JwtUtil.sign(1l,10000);
//        System.out.println(s);
//        Long ss = JwtUtil.unsign("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODMxMjczMzQxMjUsInBheWxvYWQiOiJudWxsIn0.y_prZvk8NG1uk-vnML28lwyCtiDObdnm3h5fOfX3RUA",Long.class);
//        System.out.println(ss);
//        List<Long> roles = userService.getRoleIdByUserId(1l);
//        System.out.println(roles.size());
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMp(){

        //根据ID查
        UserEntity userEntity = userRepository.selectById(1l);



        System.out.println(JSON.parseObject(JSON.toJSONString(userEntity), new TypeReference<Map<String, Object>>(){}));
    }

    @Test
    public void testTwo(){
        try{
//            RedisUtil.setString("test", "测试");
            System.out.println(RedisUtil.getString("test"));
//            redisTemplate.opsForValue().set("test", "测试");
//            System.out.println("value："+redisTemplate.opsForValue().get("test"));
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
    private UserController userController;

    @Test
    public void testController(){
        userController.getUser(1l);
    }
}
