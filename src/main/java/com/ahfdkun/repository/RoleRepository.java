package com.ahfdkun.repository;

import com.ahfdkun.domain.Department;
import com.ahfdkun.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
