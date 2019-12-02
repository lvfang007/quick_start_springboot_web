package com.lvfang.mybatisplus.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @描述
 * @创建人 lvfang
 * @创建时间 2019/10/21
 */
@Slf4j
@RestController
@RequestMapping("/lock")
public class IndexController {

    private static final String LOCK_NAME = "lock";

    private static final String COUNT_KEY = "locktest";

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    /**
     * actuator模块提供的服务
     *
     * http://localhost/actuator
     */

    /**
     * 无锁处理
     * @return
     */
    @RequestMapping("/test1")
    public String testlock1() {

        int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
        if(count > 0){
            int realCount = count - 1;
            redisTemplate.opsForValue().set("locktest",realCount+"");
            log.info("扣减成功，余量为：{}",realCount);
        }else {
            log.info("扣减失败，余量为不足");
        }
        return "END";
    }

    /**
     * 简单锁处理
     * @return
     */
    @RequestMapping("/test2")
    public String testlock2() {

        /**
         * 添加锁
         */
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(LOCK_NAME,"lock");
        if(!flag) return "error";

        int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
        if(count > 0){
            int realCount = count - 1;
            redisTemplate.opsForValue().set("locktest",realCount+"");
            log.info("扣减成功，余量为：{}",realCount);
        }else {
            log.info("扣减失败，余量为不足");
        }

        /**
         * 释放所
         */
        redisTemplate.delete(LOCK_NAME);

        return "END";
    }

    /**
     * 简单锁处理
     * @return
     */
    @RequestMapping("/test3")
    public String testlock3() {

        try {
            /**
             * 添加锁
             */
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(LOCK_NAME,"lock");
            redisTemplate.expire(LOCK_NAME,10, TimeUnit.SECONDS);
            if(!flag) return "error";

            int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
            if(count > 0){
                int realCount = count - 1;
                redisTemplate.opsForValue().set("locktest",realCount+"");
                log.info("扣减成功，余量为：{}",realCount);
            }else {
                log.info("扣减失败，余量为不足");
            }
        }finally {
            /**
             * 释放所
             */
            redisTemplate.delete(LOCK_NAME);
        }

        return "END";
    }

    /**
     * 简单锁处理
     * @return
     */
    @RequestMapping("/test4")
    public String testlock4() {

        try {
            /**
             * 添加锁
             */
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(LOCK_NAME,"lock",10,TimeUnit.SECONDS);
            if(!flag) return "error";

            int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
            if(count > 0){
                int realCount = count - 1;
                redisTemplate.opsForValue().set("locktest",realCount+"");
                log.info("扣减成功，余量为：{}",realCount);
            }else {
                log.info("扣减失败，余量为不足");
            }
        }finally {
            /**
             * 释放所
             */
            redisTemplate.delete(LOCK_NAME);
        }

        return "END";
    }

    /**
     * 简单锁处理
     * @return
     */
    @RequestMapping("/test5")
    public String testlock5() {

        String uuid = UUID.randomUUID().toString();

        try {
            /**
             * 添加锁
             */
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(LOCK_NAME,uuid,10,TimeUnit.SECONDS);
            if(!flag) return "error";

            int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
            if(count > 0){
                int realCount = count - 1;
                redisTemplate.opsForValue().set("locktest",realCount+"");
                log.info("扣减成功，余量为：{}",realCount);
            }else {
                log.info("扣减失败，余量为不足");
            }
        }finally {
            /**
             * 释放所
             */
            if(uuid.equals(redisTemplate.opsForValue().get(LOCK_NAME))){
                redisTemplate.delete(LOCK_NAME);
            }
        }

        return "END";
    }

    /**
     * 简单锁处理
     * @return
     */
    @RequestMapping("/test6")
    public String testlock6() {

        RLock rLock = redissonClient.getLock(LOCK_NAME);

        try {
            /**
             * 添加锁
             */
            rLock.tryLock(1,1,TimeUnit.SECONDS);

            int count = Integer.parseInt(redisTemplate.opsForValue().get(COUNT_KEY));
            if(count > 0){
                int realCount = count - 1;
                redisTemplate.opsForValue().set("locktest",realCount+"");
                log.info("扣减成功，余量为：{}",realCount);
            }else {
                log.info("扣减失败，余量为不足");
            }

        }catch (InterruptedException e){
            log.error("", e);
            rLock.unlock();
        }finally {
            /**
             * 释放所
             */
            rLock.unlock();
        }

        return "END";
    }
}
