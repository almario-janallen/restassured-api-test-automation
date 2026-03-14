package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceResponse {
    @JsonProperty("data")
    private ResourceData resourceData;
    private Support support;

    public ResourceResponse() {
        // Default Constructor
    }

    public ResourceResponse(ResourceData resourceData, Support support) {
        this.resourceData = resourceData;
        this.support = support;
    }

    public ResourceData getResourceData() {
        return resourceData;
    }

    public void setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }
    
    @Override
    public String toString() {
        return "ResourceResponse{" +
                "resourceData=" + resourceData +
                ", support=" + support +
                '}';
    }
}
