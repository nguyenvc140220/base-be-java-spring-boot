package com.metechvn.emloyee;

import com.metechvn.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeEntity>> getAllEmployee(){
        List<EmployeeEntity> list = service.getAllEmployees();
        return new ResponseEntity<List<EmployeeEntity>>(list,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable("id") Long id)
                                                    throws RecordNotFoundException {
        EmployeeEntity emp = service.getEmployeebyId(id);
        return new ResponseEntity<EmployeeEntity>(emp, new HttpHeaders(),HttpStatus.OK);
    }

    /**
     *
     * @param emp
     * @return
     * @throws RecordNotFoundException
     */
    @PostMapping("/create-update")
    public ResponseEntity<EmployeeEntity> createOrUpdateEmployee(@RequestBody EmployeeEntity emp)
                                                throws RecordNotFoundException{
        EmployeeEntity creOrup = service.createOrUpdateEmployee(emp);
        return new ResponseEntity<EmployeeEntity>(creOrup, new HttpHeaders(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException{
        service.deleteEmployeeById(id);
        return HttpStatus.FORBIDDEN;
    }
}
