/**
 * 
 */
package com.samsoft.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sambhav.jain
 *
 */
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
