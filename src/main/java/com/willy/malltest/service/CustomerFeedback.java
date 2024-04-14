package com.willy.malltest.service;

import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;

import java.util.List;

public interface CustomerFeedback {

    public List<CustomerFeedbackDTO> getAllFeedbacksDTO();

    public List<CustomerFeedbackDTO> getShowFeedbacksDTO(Long userId);

    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacksDTO(Long userId);

    public com.willy.malltest.model.CustomerFeedback addFeedbacksDTO(CustomerFeedbackDTO customerFeedbackDTO);

    public com.willy.malltest.model.CustomerFeedback updateFeedbacksDTO(CustomerFeedbackDTO customerFeedbackDTO);

    public void deleteCustomerFeedback(CustomerFeedbackDTO customerFeedbackDTO);


}
