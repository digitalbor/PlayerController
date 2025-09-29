package com.pavel.qa.models;

public class DeletePlayerRequestModel {

        private int playerId;
        private String editor;

        // Getters and setters

        public int getPlayerId() {
            return playerId;
        }
        public void setPlayerId(int playerId) {
            this.playerId = playerId;
        }

        public String getEditor() {
        return editor;
        }

        public void setEditor(String editor) {
        this.editor = editor;
        }

}
