package com.url.shortener.repository;

import com.url.shortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository  extends JpaRepository<UrlEntity, Long> {
}
