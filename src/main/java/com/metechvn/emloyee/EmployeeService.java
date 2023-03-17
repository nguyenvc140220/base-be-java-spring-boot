package com.metechvn.emloyee;

import com.metechvn.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    @Cacheable(value = "getAllEmployeeCache")
    public List<EmployeeEntity> getAllEmployees(){
        List<EmployeeEntity> employeeList = repository.findAll();
        if(employeeList.size()>0){
            return employeeList;
        }
        else {
            return new ArrayList<EmployeeEntity>();
        }
    }

    @Cacheable(value = "getEmployeeCache")
    public EmployeeEntity getEmployeebyId(Long id) throws RecordNotFoundException {
        Optional<EmployeeEntity> emp = repository.findById(id);
        if(emp.isPresent()){
            return emp.get();
        }else{
            throw new RecordNotFoundException("can not find employee has is:"+id);
        }
    }

    /**
     *
     * @param emp
     * @return
     * @throws RecordNotFoundException
     */
    public EmployeeEntity createOrUpdateEmployee(EmployeeEntity emp) throws RecordNotFoundException{
        Optional<EmployeeEntity> employee = repository.findById(emp.getId());

        if(employee.isPresent()){
            EmployeeEntity newEmp = employee.get();
            newEmp.setFirstName(emp.getFirstName());
            newEmp.setLastName(emp.getLastName());
            newEmp.setEmail(emp.getEmail());
            newEmp =repository.save(newEmp);
            return newEmp;
        }else{
            emp = repository.save(emp);
            return emp;
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException{
        Optional<EmployeeEntity> empDel = repository.findById(id);

        if(empDel.isPresent()){
            repository.deleteById(id);
        }else{
            throw new RecordNotFoundException("Can not find employee has id" +id);
        }
    }
}
