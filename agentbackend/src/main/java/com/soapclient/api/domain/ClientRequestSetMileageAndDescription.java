//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.11 at 09:51:46 PM CEST 
//


package com.soapclient.api.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="rentalRequestId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="mileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "carId",
    "rentalRequestId",
    "mileage",
    "description"
})
@XmlRootElement(name = "ClientRequestSetMileageAndDescription")
public class ClientRequestSetMileageAndDescription {

    protected long carId;
    protected long rentalRequestId;
    protected int mileage;
    @XmlElement(required = true)
    protected String description;

    /**
     * Gets the value of the carId property.
     * 
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets the value of the carId property.
     * 
     */
    public void setCarId(long value) {
        this.carId = value;
    }

    /**
     * Gets the value of the rentalRequestId property.
     * 
     */
    public long getRentalRequestId() {
        return rentalRequestId;
    }

    /**
     * Sets the value of the rentalRequestId property.
     * 
     */
    public void setRentalRequestId(long value) {
        this.rentalRequestId = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     */
    public void setMileage(int value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
