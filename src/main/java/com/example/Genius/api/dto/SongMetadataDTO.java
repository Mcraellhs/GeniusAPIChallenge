package com.example.Genius.api.dto;

public class SongMetadataDTO {
    private Long id;
    private String songTitle;
    private String primaryArtistNames;
    private String releaseDate;

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
}
