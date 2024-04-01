package com.willy.malltest.controller;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.dto.TrackShowDTO;
import com.willy.malltest.model.Track;
import com.willy.malltest.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/track")
    public List<TrackDTO> getAllTrack(){
        return trackService.getAllTrackDTOs();
    }

    @GetMapping("/getshow/track")
    public List<TrackShowDTO> getShowTrackDTOs(@RequestParam Long userId){
        return trackService.getShowTrackDTOs(userId);
    }

    @PostMapping("/create/track")
    public Track creatTrack(@RequestBody TrackDTO trackDTO){
        Long userId = trackDTO.getUserID();
        String specID = trackDTO.getSpecID();

        return trackService.addTrack(specID,userId);
    }

    @DeleteMapping("/delete/track")
    public void deleteTrack(@RequestBody TrackDTO trackDTO){
        Long userId = trackDTO.getUserID();
        String specID = trackDTO.getSpecID();
        trackService.deleteTrack(specID,userId);
    }
}
