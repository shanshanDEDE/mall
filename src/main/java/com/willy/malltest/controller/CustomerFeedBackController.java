package com.willy.malltest.controller;


import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.model.CustomerFeedback;
import com.willy.malltest.model.Track;
import com.willy.malltest.service.CusotmerFeedback;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CustomerFeedBackController {

    @Autowired
    private CusotmerFeedback cusotmerFeedback;

    @GetMapping("/feedbacks")
    public List<CustomerFeedbackDTO> getAllFeedbacks(){
        return cusotmerFeedback.getAllFeedbacksDTO();
    }

    @GetMapping("/feedbacks/customerdeedbacks")
    public List<CustomerFeedbackDTO> getshowFeedbacks(@RequestParam ("userId") Long userId){
        return cusotmerFeedback.getShowFeedbacksDTO(userId);
    }

    @GetMapping("/feedbacks/showcustomerdeedbacks")
    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacks(@RequestParam ("userId") Long userId){
        return cusotmerFeedback.getShowAllFeedbacksDTO(userId);
    }

    @PostMapping("/create/customerdeedback")
    public CustomerFeedback createCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return cusotmerFeedback.addFeedbacksDTO(customerFeedbackDTO);
    }

    @PutMapping("/update/customerdeedback")
    public CustomerFeedback updateCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return cusotmerFeedback.updateFeedbacksDTO(customerFeedbackDTO);
    }

    @DeleteMapping("/delete/customerdeedback")
    public void deleteCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        cusotmerFeedback.deleteCusotmerFeedback(customerFeedbackDTO);
    }

}
