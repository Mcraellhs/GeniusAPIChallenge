package com.example.Genius.api.service;

import com.example.Genius.api.dto.SongMediaDTO;
import com.example.Genius.api.dto.SongMetadataDTO;
import com.example.Genius.api.entity.Song;
import com.example.Genius.api.entity.SongData;
import com.example.Genius.api.model.GeniusApiSearchResponse;
import com.example.Genius.api.model.GeniusApiSongMediaResponse;
import com.example.Genius.api.repository.SongDataRepository;
import com.example.Genius.api.repository.SongRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Autowired
    private GeniusService geniusService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongDataRepository songDataRepository;

    public List<Long> createSong(String query) {
        Optional<Song> existingSong = songRepository.findByQueryInput(query);

        if (existingSong.isPresent()) {
            throw new DataIntegrityViolationException(
                    "Data with query '" + query + "' already exists"
            );
        }

        try {
            GeniusApiSearchResponse songsResponse = geniusService.searchSongs(query);
            List<GeniusApiSearchResponse.Hit> hits = songsResponse.getResponse().getHits();

            Song song = buildSongWithQuery(query, hits);
            songRepository.save(song);

            return extractSongIds(hits);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating songs for query: " + query, e);
        }
    }

    public SongMetadataDTO getSongMetadataById(Long id) {
        try {
            SongData songData = fetchSongDataById(id);
            return buildSongMetadataDTO(songData);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    "Song not found"
            );
        }
    }

    public SongMediaDTO getSongMediaById(Long id) {
        try {
            SongData songData = fetchSongDataById(id);
            return buildSongMediaDTO(songData);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    "Media not found"
            );
        }
    }


    private Song buildSongWithQuery(String query, List<GeniusApiSearchResponse.Hit> hits) {
        Song song = new Song();
        song.setQueryInput(query);

        List<SongData> songDataList = hits.stream()
                .map(hit -> buildSongData(hit))
                .collect(Collectors.toList());

        song.setSongsData(songDataList);
        return song;
    }

    private SongData buildSongData(GeniusApiSearchResponse.Hit hit) {
        GeniusApiSearchResponse.Result result = hit.getResult();

        GeniusApiSongMediaResponse songDetailsResponse = geniusService.searchSongMedia(result.getId());
        Map<String, String> mediaUrls = extractMediaUrls(songDetailsResponse);

        SongData songData = new SongData();
        songData.setId(result.getId());
        songData.setSongTitle(result.getTitle());
        songData.setReleaseDate(result.getRelease_date_for_display());
        songData.setPrimaryArtistNames(result.getPrimary_artist().getName());
        songData.setSpotifyUrl(mediaUrls.getOrDefault("spotify", null));
        songData.setYoutubeUrl(mediaUrls.getOrDefault("youtube", null));

        return songData;
    }

    private Map<String, String> extractMediaUrls(GeniusApiSongMediaResponse response) {
        return response.getResponse().getSong().getMedia()
                .stream()
                .collect(Collectors.toMap(GeniusApiSongMediaResponse.Media::getProvider, GeniusApiSongMediaResponse.Media::getUrl));
    }

    private List<Long> extractSongIds(List<GeniusApiSearchResponse.Hit> hits) {
        return hits.stream()
                .map(hit -> hit.getResult().getId())
                .collect(Collectors.toList());
    }

    private SongData fetchSongDataById(Long id) {
        return songDataRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SongData not found with ID: " + id));
    }

    private SongMetadataDTO buildSongMetadataDTO(SongData songData) {
        SongMetadataDTO dto = new SongMetadataDTO();
        dto.setId(songData.getId());
        dto.setSongTitle(songData.getSongTitle());
        dto.setPrimaryArtistNames(songData.getPrimaryArtistNames());
        dto.setReleaseDate(songData.getReleaseDate());
        return dto;
    }

    private SongMediaDTO buildSongMediaDTO(SongData songData) {
        SongMediaDTO dto = new SongMediaDTO();
        dto.setSpotifyUrl(songData.getSpotifyUrl());
        dto.setYoutubeUrl(songData.getYoutubeUrl());
        return dto;
    }



}
