package com.willy.malltest.service;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.dto.TrackShowDTO;
import com.willy.malltest.model.Track;

import java.util.List;

public interface TrackService {

    public List<TrackDTO> getAllTrackDTOs();

    public List<TrackShowDTO> getShowTrackDTOs(Long userId);

    public Track addTrack(String specID, Long userId);

    public void deleteTrack(String specID, Long userId);
}
