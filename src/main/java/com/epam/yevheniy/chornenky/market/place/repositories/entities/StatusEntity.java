package com.epam.yevheniy.chornenky.market.place.repositories.entities;

public class StatusEntity {
    private final String status;
    private final String statusView;

    public StatusEntity(String status, String statusView) {
        this.status = status;
        this.statusView = statusView;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusView() {
        return statusView;
    }
}
