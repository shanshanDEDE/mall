package com.willy.malltest.controller;

import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.MemberReDataDTO;
import com.willy.malltest.dto.MemberRePasswordDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.model.CustomerFeedback;
import com.willy.malltest.model.User;
import com.willy.malltest.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {

@Autowired
private MemberService MemberService;

    @GetMapping("/member/Allmemberredata")
        public List<MemberReDataDTO> getShowAllUsers(){
        return MemberService.getAllMemberReDTOs();
    }

    @GetMapping("/member/showmemberredata")
    public MemberReDataDTO getShowUser(@RequestParam("userId") Long userId){
        return MemberService.getShowMemberReDTOs(userId);
    }

    @GetMapping("/member/showrepassworddata")
    public MemberRePasswordDTO getShowpassworddata(@RequestParam("userId") Long userId){
        return MemberService.getShowpassworddata(userId);
    }

    @PutMapping("/update/memberdataupdate")
    public User updatememberdata(@RequestBody MemberReDataDTO memberReDataDTO){
        return MemberService.updatememberdata(memberReDataDTO);
    }
}
