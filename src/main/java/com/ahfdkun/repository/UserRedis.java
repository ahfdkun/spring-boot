package com.ahfdkun.repository;

import com.ahfdkun.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, User user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<User> users) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(users), time, TimeUnit.MINUTES);
    }

    public User get(String key) {
        Gson gson = new Gson();
        String userJson = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        return gson.fromJson(userJson, User.class);
    }

    public List<User> getList(String key) {
        Gson gson = new Gson();
        String listJson = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(listJson)) {
            return gson.fromJson(listJson, new TypeToken<List<User>>() {}.getType());
        }
        return null;
    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}
