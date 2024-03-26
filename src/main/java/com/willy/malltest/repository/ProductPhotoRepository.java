package com.willy.malltest.repository;

import com.willy.malltest.model.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
}
