package com.vmware.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmware.employee.models.Employee;

@Repository
public interface IEmployeeDao extends JpaRepository<Employee, Long> {

}
