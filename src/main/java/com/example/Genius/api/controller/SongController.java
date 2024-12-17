package com.example.Genius.api.controller;


import com.example.Genius.api.model.ErrorResponse;
import com.example.Genius.api.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codechallenge")
public class SongController {

    @Autowired
    private SongService songService;

    @Operation(summary = "Create songs based on a query")
    @PostMapping("/createSongs")
    public ResponseEntity<Object> createSongs(@RequestParam String q) {
        try {
            List<Long> songIds = songService.createSong(q);
            return ResponseEntity.ok(songIds);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Bad request: " + e.getMessage()));
        }
    }

    @Operation(summary = "Get song metadata by song ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Metadata fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Song not found")
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<Object> getSongMetadataById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(songService.getSongMetadataById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get song spotify and youtube url by song ID")
    @GetMapping("/song/{id}/media")
    public ResponseEntity<Object> getSongMedia(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(songService.getSongMediaById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
}
