package com.ahfdkun.repository;

import com.ahfdkun.domain.Department;
import com.ahfdkun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
