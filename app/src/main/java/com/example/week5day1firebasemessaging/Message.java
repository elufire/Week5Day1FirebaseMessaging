package com.example.week5day1firebasemessaging;

public class Message {
    String email;
    String message;
    String time;
    String key;


    public Message() {
    }

    public Message(String email, String message, String time) {
        this.email = email;
        this.message = message;
        this.time = time;
    }

    public Message(String email, String message, String time, String key) {
        this.email = email;
        this.message = message;
        this.time = time;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
