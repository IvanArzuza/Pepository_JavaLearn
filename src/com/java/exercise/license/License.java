/**
 * 
 */
package com.java.exercise.license;

import java.io.*;
import java.util.*;

public class License implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;
    private String licenseNumber;
    private LicenseType licenseType;
    private Date expiration;
    private String version;
    
    public License() {
        name = "";
        email = "";
        licenseNumber = "";
        expiration = new Date();
        version = "";
        licenseType = LicenseType.TRIAL;
    }
    
    public License(String name, String email, String licenseNumber, Date expiration, LicenseType licenseType, String version) {
        this.name = name;
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.expiration = expiration;
        this.licenseType = licenseType;
        this.version = version;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the licenseNumber
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * @param licenseNumber the licenseNumber to set
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * @param expiration the expiration to set
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the licenseType
     */
    public LicenseType getLicenseType() {
        return licenseType;
    }

    /**
     * @param licenseType the licenseType to set
     */
    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
}
