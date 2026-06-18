package com.url.shortener.repository;

import com.url.shortener.entity.UrlEntity;
import com.url.shortener.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository  extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByShortCode(String shortCode);
    List<UrlEntity> findAllByUserOrderByCreatedAtDesc(UserEntity user);
}
