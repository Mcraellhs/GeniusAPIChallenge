package com.example.Genius.api.repository;

import com.example.Genius.api.entity.SongData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongDataRepository  extends JpaRepository<SongData, Long> {
}
