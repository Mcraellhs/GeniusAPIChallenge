package com.example.Genius.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "song_data")
public class SongData {

    @Id
    private Long id;

    @Column(name = "song_title")
    private String songTitle;

    @Column(name = "primary_artist_names")
    private String primaryArtistNames;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "spotify_url")
    private String spotifyUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    @JsonBackReference
    private Song song;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getPrimaryArtistNames() {
        return primaryArtistNames;
    }

    public void setPrimaryArtistNames(String primaryArtistNames) {
        this.primaryArtistNames = primaryArtistNames;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
