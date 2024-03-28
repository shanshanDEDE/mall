package com.willy.malltest.service;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.model.Track;
import com.willy.malltest.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;


//    @Transactional(readOnly = true)
//    public List<Track> getAllTrack() {
//        return trackRepository.findAll();
//    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTrackDTOs() {
        List<Track> tracks = trackRepository.findAll();
        List<TrackDTO> tracksdto = new ArrayList<>(); // 初始化空的 TrackDTO 列表
        for (Track track : tracks) { // 使用 for-each 迴圈遍歷 List 中的每個 Track 對象
            TrackDTO dto = new TrackDTO();
            dto.setTrackID(track.getTrackID());
            dto.setUserID(track.getUser().getUserId());
            dto.setSpecID(track.getProductSpec().getSpecID());
            tracksdto.add(dto); // 將轉換後的 TrackDTO 加入到列表中
        }
        return tracksdto;
    }
}
