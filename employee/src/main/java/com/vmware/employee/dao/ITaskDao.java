package com.vmware.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmware.employee.models.TaskEntity;

/**
 * Repository to save Task info into database.
 */
@Repository
public interface ITaskDao extends JpaRepository<TaskEntity, Long>{

}
