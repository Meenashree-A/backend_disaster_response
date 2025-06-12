package com.meenashree.disasterresponse.model;

public class Need {
    private String id;
    private String location;  // Location coordinates (lat, lng)
    private String phone;     // ✅ New field for phone number
    private String type;
    private String description;
    private String fileUrl;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {        // ✅ New getter
        return phone;
    }

    public void setPhone(String phone) {  // ✅ New setter
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
