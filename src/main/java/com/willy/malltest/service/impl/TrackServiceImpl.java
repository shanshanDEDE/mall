package com.willy.malltest.service.impl;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.dto.TrackShowDTO;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.Track;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.ProductSpecRepository;
import com.willy.malltest.repository.TrackRepository;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTrackDTOs() {
        List<Track> tracks = trackRepository.findAll();
        List<TrackDTO> tracksdto = new ArrayList<>(); // 初始化空的 TrackDTO 列表
        for (Track track : tracks) { // 使用 for-each 迴圈遍歷 List 中的每個 Track 對象
            TrackDTO dto = new TrackDTO();
            dto.setTrackID(track.getTrackID());
            dto.setUserID(track.getUser().getUserId());
            dto.setSpecID(track.getProductSpec().getSpecId());
            tracksdto.add(dto); // 將轉換後的 TrackDTO 加入到列表中
        }
        return tracksdto;
    }

    //test
    @Override
    public List<TrackShowDTO> getShowTrackDTOs(Long userId) {
        List<Track> tracks = usersRepository.findById(userId).get().getTrack();

        List<TrackShowDTO> trackShowDTOs = new ArrayList<>(); // 初始化空的 TrackShowDTO 列表

        for (Track track : tracks) { // 遍历每个 Track 对象
            TrackShowDTO dto = new TrackShowDTO();
            dto.setTrackID(track.getTrackID());
            dto.setUserID(track.getUser().getUserId());
            dto.setSpecID(track.getProductSpec().getSpecId());
            dto.setProductId(track.getProductSpec().getProduct().getProductId()); // 設置 productId

            // 获取与 ProductSpec 关联的 ProductPhoto 对象列表
            List<ProductPhoto> productPhotos = track.getProductSpec().getProductPhotos();
            if (!productPhotos.isEmpty()) {
                // 取第一个 ProductPhoto 对象的 photoFile 属性，假设一对多关系中只有一个 ProductPhoto
                ProductPhoto productPhoto = productPhotos.get(0);

                String photoBase64 = Base64.getEncoder().encodeToString(productPhoto.getPhotoFile());
                dto.setPhotoFile(photoBase64); // 设置为 Base64 编码后的字符串
//                dto.setPhotoFile(productPhoto.getPhotoFile());
            }
            dto.setProductName(track.getProductSpec().getProduct().getProductName());
            dto.setProductPrice(track.getProductSpec().getProduct().getPrice());
            dto.setProductDescription(track.getProductSpec().getProduct().getProductDescription());
            List<String> specIds = new ArrayList<>();
            specIds.add(track.getProductSpec().getSpecId());
            dto.setSpecIds(specIds);

            trackShowDTOs.add(dto); // 将转换后的 TrackShowDTO 加入到列表中
        }

        return trackShowDTOs;
    }
//    @Override
//    public boolean getCheckTrackDTO(Long userId,String specId) {
//        Track tracks = trackRepository.findTrackByspecIdAnduserId(specId,userId);
//
//        if(tracks!=null){
//            return true;
//        }else{
//            return false;
//        }
//    }


    @Override
    @Transactional
    public boolean getCheckTrackDTO(Long userId, List<String> specId) {
       for (String s : specId) {
           Track tracks = trackRepository.findTrackByspecIdAnduserId(s,userId);
           if(tracks!=null){
               return true;
           }
       }
        return false;
    }

    @Override
    @Transactional
    public boolean addTrack(String specID, Long userId) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        ProductSpec productSpec = productSpecRepository.findById(specID).orElse(null);
        if (productSpec == null) {
            throw new IllegalArgumentException("ProductSpec with ID " + specID + " not found");
        }

        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack != null) {
            throw new IllegalStateException("Track already exists");
        }

        Track newTrack = new Track();
        newTrack.setUser(user);
        newTrack.setProductSpec(productSpec);
        trackRepository.save(newTrack);
        return true;
    }

    @Override
    @Transactional
    public void deleteTrack(String specID, Long userId) {
        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack == null) {
            throw new IllegalStateException("Track does not exist");
        }

        trackRepository.delete(existingTrack);
    }
    //deDeleteTrack


    public String getProductIdBySpecId(String specId) {
        return productSpecRepository.findProductIdBySpecId(specId)
                .orElseThrow(() -> new IllegalArgumentException("No product found for specId: " + specId));
    }
}

