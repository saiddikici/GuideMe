package com.selimkilicaslan.guideme.classes;

public class ServiceOffered {
    private String serviceName;
    private Boolean isOffered;

    public ServiceOffered() {
    }

    public ServiceOffered(String serviceName, Boolean isOffered) {
        this.serviceName = serviceName;
        this.isOffered = isOffered;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getOffered() {
        return isOffered;
    }

    public void setOffered(Boolean offered) {
        isOffered = offered;
    }
}
