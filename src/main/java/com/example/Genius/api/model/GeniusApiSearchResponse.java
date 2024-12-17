package com.example.Genius.api.model;

import java.util.List;

public class GeniusApiSearchResponse {
    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Meta {
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class Response {
        private List<Hit> hits;

        public List<Hit> getHits() {
            return hits;
        }

        public void setHits(List<Hit> hits) {
            this.hits = hits;
        }
    }

    public static class Hit {
        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    public static class Result {
        private Long id;
        private String title;
        private PrimaryArtist primary_artist;
        private String release_date_for_display;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public PrimaryArtist getPrimary_artist() {
            return primary_artist;
        }

        public void setPrimary_artist(PrimaryArtist primary_artist) {
            this.primary_artist = primary_artist;
        }

        public String getRelease_date_for_display() {
            return release_date_for_display;
        }

        public void setRelease_date_for_display(String release_date_for_display) {
            this.release_date_for_display = release_date_for_display;
        }
    }

    public static class PrimaryArtist {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
