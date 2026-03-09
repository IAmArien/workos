package com.workos.corporate.domain.user.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false, length = 255)
    private String sessionId;

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "device_name", nullable = false, length = 999)
    private String deviceName;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", length = 999)
    private String userAgent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    public UserSessions() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        revoked = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }
}
