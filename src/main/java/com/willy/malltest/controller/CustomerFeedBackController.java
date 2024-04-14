package com.willy.malltest.controller;


import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.service.CustomerFeedback;
import com.willy.malltest.service.MailServiceImpl;
import com.willy.malltest.service.UserService;
import com.willy.malltest.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CustomerFeedBackController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerFeedback customerFeedback;
    @Autowired
    private MailServiceImpl mailService;


    @GetMapping("/feedbacks")
    public List<CustomerFeedbackDTO> getAllFeedbacks() {
        return customerFeedback.getAllFeedbacksDTO();
    }

    @GetMapping("/feedbacks/customerFeedbacks")
    public List<CustomerFeedbackDTO> getshowFeedbacks(@RequestParam("userId") Long userId) {
        return customerFeedback.getShowFeedbacksDTO(userId);
    }

    @GetMapping("/feedbacks/showCustomerFeedbacks")
    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacks(@RequestParam("userId") Long userId) {
        return customerFeedback.getShowAllFeedbacksDTO(userId);
    }

    @PostMapping("/create/customerFeedbacks")
    public String createCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO) {
        com.willy.malltest.model.CustomerFeedback customerFeedback1 = customerFeedback.addFeedbacksDTO(customerFeedbackDTO);
        Integer feedbackId = customerFeedback1.getFeedbackID();
        Long userId = customerFeedbackDTO.getUserID();
        String email = userService.findEmailById(userId);
        mailService.sendFeedbackEmailCreate(feedbackId, email);

        return "success create CustomerFeedbacks! FeedbackId: " + feedbackId;

    }

    @PutMapping("/update/customerFeedbacks")
    public com.willy.malltest.model.CustomerFeedback updateCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO) {
        return customerFeedback.updateFeedbacksDTO(customerFeedbackDTO);
    }

    @DeleteMapping("/delete/customerFeedbacks")
    public void deleteCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO) {
        customerFeedback.deleteCustomerFeedback(customerFeedbackDTO);
    }

    @PostMapping("/sendFeedbackEmail")
    public String sendFeedbackEmail(@RequestParam Integer feedbackId,@RequestBody String message) {
        try {
            mailService.sendFeedbackEmailReturn(feedbackId, message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "success send email! FeedbackId: " + feedbackId;
    }

    @PutMapping("sendFeedbackEmailAndUpdate")
    public String sendFeedbackEmailAndUpdate(@RequestParam Integer feedbackId,@RequestBody String message) {
        try {
            mailService.sendFeedbackEmailReturn(feedbackId, message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "success send email! FeedbackId: " + feedbackId;
    }

}
