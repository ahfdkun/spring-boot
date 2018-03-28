package com.ahfdkun.service;

import com.ahfdkun.domain.Role;
import com.ahfdkun.repository.RoleRedis;
import com.ahfdkun.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleRedis roleRedis;

    @Cacheable(value = "mysql:findById:role", keyGenerator = "simpleKey")
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    @CachePut(value = "mysql:findById:role", keyGenerator = "objectId")
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @CachePut(value = "mysql:findById:role", keyGenerator = "objectId")
    public Role update(Role role) {
        return roleRepository.save(role);
    }


    @CacheEvict(value = "mysql:findById:role", keyGenerator = "simpleKey")
    public void delete(Long id) {
        roleRepository.delete(id);
    }


    public List<Role> findAll() {
        List<Role> roleList = roleRedis.getList("mysql:findAll:role");
        if (roleList == null) {
            roleList = roleRepository.findAll();
            if (roleList != null)
                roleRedis.add("mysql:findAll:role", 5L, roleList);
        }
        return roleList;
    }

}
