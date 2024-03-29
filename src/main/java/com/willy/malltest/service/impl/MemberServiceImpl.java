package com.willy.malltest.service.impl;


import com.willy.malltest.dto.MemberReDataDTO;
import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.Track;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.MemberRepository;
import com.willy.malltest.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public List<MemberReDataDTO> getAllMemberReDTOs() {
        List<User> Users = memberRepository.findAll();
        List<MemberReDataDTO> memberReDataDTO = new ArrayList<>(); // 初始化空的 TrackDTO 列表
        for (User user : Users) { // 使用 for-each 迴圈遍歷 List 中的每個 Track 對象
            MemberReDataDTO dto = new MemberReDataDTO();

            dto.setUserID(user.getUserID());
            dto.setUserName(user.getUserName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setUserAddress(user.getUserAddress());
            dto.setDeliverAddress(user.getDeliverAddress());
            dto.setRegisterDate(user.getRegisterDate());

            memberReDataDTO.add(dto); // 將轉換後的 TrackDTO 加入到列表中
        }
        return memberReDataDTO;
    }

    @Transactional
    public MemberReDataDTO getShowMemberReDTOs(Long userId) {

        // 根據 userId 查找相應的 User 實體並設置給新 Track 對象
        User user = memberRepository.findById(userId).orElse(null);

        MemberReDataDTO dto = new MemberReDataDTO();
        dto.setUserID(user.getUserID());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUserAddress(user.getUserAddress());
        dto.setDeliverAddress(user.getDeliverAddress());
        dto.setRegisterDate(user.getRegisterDate());
        return dto;
    }
}