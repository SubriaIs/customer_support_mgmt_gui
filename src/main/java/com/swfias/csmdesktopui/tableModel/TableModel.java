package com.swfias.csmdesktopui.tableModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TableModel {
    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleStringProperty createdDate;
    private final SimpleStringProperty status;
    private final SimpleStringProperty severity;
    private final SimpleStringProperty solvedBy;
    private final SimpleStringProperty resolutionDetails;
    private final SimpleStringProperty resolvedDate;


    public TableModel(String title, String description, String createdDate, String status, String severity, String assignedTo, String resolutionDetails, String resolvedDate) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.createdDate = new SimpleStringProperty(createdDate);
        this.status = new SimpleStringProperty(status);
        this.severity = new SimpleStringProperty(severity);
        this.solvedBy = new SimpleStringProperty(assignedTo);
        this.resolutionDetails = new SimpleStringProperty(resolutionDetails);
        this.resolvedDate = new SimpleStringProperty(resolvedDate);
    }


    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {

        return description.get();
    }

    public void setDescription(String description) {

        this.description.set(description);
    }

    public String getCreatedDate() {

        return createdDate.get();
    }

    public void setCreatedDate(String createdDate) {

        this.createdDate.set(createdDate);
    }

    public String getStatus() {

        return status.get();
    }

    public void setStatus(String status) {

        this.status.set(status);
    }

    public String getSeverity() {

        return severity.get();
    }

    public void setSeverity(String severity) {

        this.severity.set(severity);
    }

    public String getSolvedBy() {

        return solvedBy.get();
    }

    public void setSolvedBy(String solvedBy) {

        this.solvedBy.set(solvedBy);
    }

    public String getResolutionDetails() {

        return resolutionDetails.get();
    }

    public void setResolutionDetails(String resolutionDetails) {

        this.resolutionDetails.set(resolutionDetails);
    }

    public String getResolvedDate() {

        return resolvedDate.get();
    }

    public void setResolvedDate(String resolvedDate) {

        this.resolvedDate.set(resolvedDate);
    }


}

