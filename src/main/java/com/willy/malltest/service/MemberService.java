package com.willy.malltest.service;

import com.willy.malltest.dto.MemberReDataDTO;
import com.willy.malltest.dto.MemberRePasswordDTO;
import com.willy.malltest.model.User;

import java.util.List;

public interface MemberService {

    public List<MemberReDataDTO> getAllMemberReDTOs();

    public MemberReDataDTO getShowMemberReDTOs(Long userId);

    public User updatememberdata(MemberReDataDTO memberReDataDTO);
    public MemberRePasswordDTO getShowpassworddata(Long userId);

}
