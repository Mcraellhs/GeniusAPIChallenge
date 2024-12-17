package com.example.Genius.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "query_input", unique = true, nullable = false)
    private String queryInput;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SongData> songsData = new ArrayList<>();

    public Song() {}

    public Song(String queryInput) {
        this.queryInput = queryInput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryInput() {
        return queryInput;
    }

    public void setQueryInput(String queryInput) {
        this.queryInput = queryInput;
    }

    public List<SongData> getSongsData() {
        return songsData;
    }

    public void setSongsData(List<SongData> songsData) {
        this.songsData = songsData;
        for (SongData songData : songsData) {
            songData.setSong(this);
        }
    }
}
