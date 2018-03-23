package com.ahfdkun.repository;

import com.ahfdkun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameLike(String name);

    User readByName(String name);

    List<User> getByCreatedateLessThan(Date start);

    @Query("select t from User t where t.name=?1 and t.createdate=?2")
    User findByNameAndCreatedate(String name, Date createdate);
}
