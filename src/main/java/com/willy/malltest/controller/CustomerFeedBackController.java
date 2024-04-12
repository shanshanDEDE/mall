package com.willy.malltest.controller;


import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.service.CustomerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CustomerFeedBackController {

    @Autowired
    private CustomerFeedback customerFeedback;

    @GetMapping("/feedbacks")
    public List<CustomerFeedbackDTO> getAllFeedbacks(){
        return customerFeedback.getAllFeedbacksDTO();
    }

    @GetMapping("/feedbacks/customerFeedbacks")
    public List<CustomerFeedbackDTO> getshowFeedbacks(@RequestParam ("userId") Long userId){
        return customerFeedback.getShowFeedbacksDTO(userId);
    }

    @GetMapping("/feedbacks/showCustomerFeedbacks")
    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacks(@RequestParam ("userId") Long userId){
        return customerFeedback.getShowAllFeedbacksDTO(userId);
    }

    @PostMapping("/create/customerFeedbacks")
    public com.willy.malltest.model.CustomerFeedback createCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.addFeedbacksDTO(customerFeedbackDTO);
    }

    @PutMapping("/update/customerFeedbacks")
    public com.willy.malltest.model.CustomerFeedback updateCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.updateFeedbacksDTO(customerFeedbackDTO);
    }

    @PutMapping("/update/customerFeedbacksStatus")
    public com.willy.malltest.model.CustomerFeedback test(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.test(customerFeedbackDTO);
    }


    @DeleteMapping("/delete/customerFeedbacks")
    public void deleteCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        customerFeedback.deleteCustomerFeedback(customerFeedbackDTO);
    }

}
