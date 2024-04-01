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

            // 获取与 ProductSpec 关联的 ProductPhoto 对象列表
            List<ProductPhoto> productPhotos = track.getProductSpec().getProductPhotos();
            if (!productPhotos.isEmpty()) {
                // 取第一个 ProductPhoto 对象的 photoFile 属性，假设一对多关系中只有一个 ProductPhoto
                ProductPhoto productPhoto = productPhotos.get(0);
                dto.setPhotoFile(productPhoto.getPhotoFile());
            }
            dto.setProductName(track.getProductSpec().getProduct().getProductName());
            dto.setProductPrice(track.getProductSpec().getProduct().getPrice());
            trackShowDTOs.add(dto); // 将转换后的 TrackShowDTO 加入到列表中
        }

        return trackShowDTOs;
    }

    @Transactional
    public Track addTrack(String specID, Long userId) {

        // 創建新的 Track 對象
        Track newTrack = new Track();

        // 根據 userId 查找相應的 User 實體並設置給新 Track 對象
        User user = usersRepository.findById(userId).orElse(null);
        if (user != null) {
            newTrack.setUser(user);
        } else {
            // 如果找不到對應的 User，您可能希望進行錯誤處理或者返回 null 或者拋出異常
            // 此處僅示例，您可以根據您的需求進行處理
            System.out.println("找不到對應的使用者");
            return null;
        }

        // 根據 specID 查找相應的 ProductSpec 實體並設置給新 Track 對象
        ProductSpec productSpec = productSpecRepository.findById(specID).get();
        if (productSpec != null) {
            newTrack.setProductSpec(productSpec);
        } else {
            // 如果找不到對應的 ProductSpec，您可能希望進行錯誤處理或者返回 null 或者拋出異常
            // 此處僅示例，您可以根據您的需求進行處理
            System.out.println("找不到對應的產品規格");
            return null;
        }

        // 檢查是否已經存在相同的 Track
        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack != null) {
            // 如果已經存在相同的 Track，您可以根據需要執行相應的處理，例如返回 null 或拋出異常
            System.out.println("相同的 Track 已存在");
            return null;
        }

        return trackRepository.save(newTrack); // 保存到資料庫中
    }

    @Override
    public void deleteTrack(String specID, Long userId) {
        // 創建新的 Track 對象
        Track newTrack = new Track();

        // 根據 userId 查找相應的 User 實體並設置給新 Track 對象
        User user = usersRepository.findById(userId).orElse(null);
        if (user != null) {
            newTrack.setUser(user);
        } else {
            // 如果找不到對應的 User，您可能希望進行錯誤處理或者返回 null 或者拋出異常
            // 此處僅示例，您可以根據您的需求進行處理
            System.out.println("找不到對應的使用者");
            return;
        }

        // 根據 specID 查找相應的 ProductSpec 實體並設置給新 Track 對象
        ProductSpec productSpec = productSpecRepository.findById(specID).get();
        if (productSpec != null) {
            newTrack.setProductSpec(productSpec);
        } else {
            // 如果找不到對應的 ProductSpec，您可能希望進行錯誤處理或者返回 null 或者拋出異常
            // 此處僅示例，您可以根據您的需求進行處理
            System.out.println("找不到對應的產品規格");
            return;
        }

        // 檢查是否已經存在相同的 Track
        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack == null) {
            // 如果已經存在相同的 Track，打印消息並終止方法執行
            System.out.println("沒有這筆 Track 已存在");
            return;
        }
            trackRepository.delete(existingTrack);
    }
}

