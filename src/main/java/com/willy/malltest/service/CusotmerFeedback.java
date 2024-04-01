package com.willy.malltest.service;

import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.model.CustomerFeedback;

import java.util.List;

public interface CusotmerFeedback {

    public List<CustomerFeedbackDTO> getAllFeedbacksDTO();

    public List<CustomerFeedbackDTO> getShowFeedbacksDTO(Long userId);

    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacksDTO(Long userId);

    public CustomerFeedback addFeedbacksDTO(CustomerFeedbackDTO customerFeedbackDTO);

    public CustomerFeedback updateFeedbacksDTO(CustomerFeedbackDTO customerFeedbackDTO);

    public void deleteCusotmerFeedback(CustomerFeedbackDTO customerFeedbackDTO);
}
