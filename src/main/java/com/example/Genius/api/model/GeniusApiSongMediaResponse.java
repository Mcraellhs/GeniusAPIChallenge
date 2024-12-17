package com.example.Genius.api.model;

import java.util.List;

public class GeniusApiSongMediaResponse {
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
        private Song song;

        public Song getSong() {
            return this.song;
        }

        public void setHits(Song song) {
            this.song = song;
        }
    }

    public static class Song {
        private List<Media> media;

        public List<Media> getMedia() {
            return  this.media;
        }

        public void setMedia(List<Media> media) {
            this.media = media;
        }
    }

    public static class Media {

        private String provider;
        private String url;

        public String getProvider(){
            return this.provider;
        }

        public void setProvider(String provider){
            this.provider=provider;
        }

        public String getUrl(){
            return this.url;
        }

        public void setUrl(String url){
            this.url=url;
        }


    }


}
