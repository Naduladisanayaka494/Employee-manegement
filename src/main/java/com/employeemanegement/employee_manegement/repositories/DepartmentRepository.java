package com.employeemanegement.employee_manegement.repositories;

import com.employeemanegement.employee_manegement.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
}
