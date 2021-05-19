package com.kenny.api.composite.car;

public class ServiceAddress {
        private String compositeAddress;
        private String carModelAddress;
        private String customerAddress;
        private String buildPriceAddress;

        public ServiceAddress(String compositeAddress, String carModelAddress, String customerAddress, String buildPriceAddress) {
            this.compositeAddress = compositeAddress;
            this.carModelAddress = carModelAddress;
            this.customerAddress = customerAddress;
            this.buildPriceAddress = buildPriceAddress;
        }

    public ServiceAddress() {

    }

    public String getCompositeAddress() {
        return compositeAddress;
    }

    public void setCompositeAddress(String compositeAddress) {
        this.compositeAddress = compositeAddress;
    }

    public String getCarModelAddress() {
        return carModelAddress;
    }

    public void setCarModelAddress(String carModelAddress) {
        this.carModelAddress = carModelAddress;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getBuildPriceAddress() {
        return buildPriceAddress;
    }

    public void setBuildPriceAddress(String buildPriceAddress) {
        this.buildPriceAddress = buildPriceAddress;
    }
}
