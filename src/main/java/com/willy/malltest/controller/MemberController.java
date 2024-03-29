package com.willy.malltest.controller;

import com.willy.malltest.dto.MemberReDataDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {

@Autowired
private MemberService MemberService;

    @GetMapping("/member/Allmemberredata")
        public List<MemberReDataDTO> getShowAllFeedbacks(){
        return MemberService.getAllMemberReDTOs();
    }

    @GetMapping("/member/showmemberredata")
    public MemberReDataDTO getShowFeedbacks(@RequestParam("userId") Long userId){
        return MemberService.getShowMemberReDTOs(userId);
    }
}