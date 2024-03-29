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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientRequestCar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientRequestCar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="doors" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descripton" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fuelType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="transmission" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="classCar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mileage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plannedMileage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CDW" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="capacitySeats" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="capacitySeatsForKids" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cubicCapacity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="horsePower" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="usb" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gps" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fuelTankCapacity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cityLocation" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientRequestCar", propOrder = {
    "rating",
    "id",
    "doors",
    "descripton",
    "image",
    "brand",
    "model",
    "fuelType",
    "transmission",
    "classCar",
    "price",
    "mileage",
    "plannedMileage",
    "cdw",
    "capacitySeats",
    "capacitySeatsForKids",
    "cubicCapacity",
    "horsePower",
    "usb",
    "gps",
    "fuelTankCapacity",
    "cityLocation",
    "userId"
})
public class ClientRequestCar {

    protected double rating;
    protected long id;
    @XmlElement(required = true)
    protected String doors;
    @XmlElement(required = true)
    protected String descripton;
    @XmlElement(required = true)
    protected String image;
    @XmlElement(required = true)
    protected String brand;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String fuelType;
    @XmlElement(required = true)
    protected String transmission;
    @XmlElement(required = true)
    protected String classCar;
    @XmlElement(required = true)
    protected String price;
    @XmlElement(required = true)
    protected String mileage;
    @XmlElement(required = true)
    protected String plannedMileage;
    @XmlElement(name = "CDW", required = true)
    protected String cdw;
    @XmlElement(required = true)
    protected String capacitySeats;
    @XmlElement(required = true)
    protected String capacitySeatsForKids;
    @XmlElement(required = true)
    protected String cubicCapacity;
    @XmlElement(required = true)
    protected String horsePower;
    @XmlElement(required = true)
    protected String usb;
    @XmlElement(required = true)
    protected String gps;
    @XmlElement(required = true)
    protected String fuelTankCapacity;
    @XmlElement(required = true)
    protected String cityLocation;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Gets the value of the rating property.
     * 
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     */
    public void setRating(double value) {
        this.rating = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the doors property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoors() {
        return doors;
    }

    /**
     * Sets the value of the doors property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoors(String value) {
        this.doors = value;
    }

    /**
     * Gets the value of the descripton property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripton() {
        return descripton;
    }

    /**
     * Sets the value of the descripton property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripton(String value) {
        this.descripton = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the fuelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Sets the value of the fuelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuelType(String value) {
        this.fuelType = value;
    }

    /**
     * Gets the value of the transmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets the value of the transmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmission(String value) {
        this.transmission = value;
    }

    /**
     * Gets the value of the classCar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCar() {
        return classCar;
    }

    /**
     * Sets the value of the classCar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCar(String value) {
        this.classCar = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMileage(String value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the plannedMileage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlannedMileage() {
        return plannedMileage;
    }

    /**
     * Sets the value of the plannedMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlannedMileage(String value) {
        this.plannedMileage = value;
    }

    /**
     * Gets the value of the cdw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDW() {
        return cdw;
    }

    /**
     * Sets the value of the cdw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDW(String value) {
        this.cdw = value;
    }

    /**
     * Gets the value of the capacitySeats property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacitySeats() {
        return capacitySeats;
    }

    /**
     * Sets the value of the capacitySeats property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacitySeats(String value) {
        this.capacitySeats = value;
    }

    /**
     * Gets the value of the capacitySeatsForKids property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacitySeatsForKids() {
        return capacitySeatsForKids;
    }

    /**
     * Sets the value of the capacitySeatsForKids property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacitySeatsForKids(String value) {
        this.capacitySeatsForKids = value;
    }

    /**
     * Gets the value of the cubicCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCubicCapacity() {
        return cubicCapacity;
    }

    /**
     * Sets the value of the cubicCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCubicCapacity(String value) {
        this.cubicCapacity = value;
    }

    /**
     * Gets the value of the horsePower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorsePower() {
        return horsePower;
    }

    /**
     * Sets the value of the horsePower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorsePower(String value) {
        this.horsePower = value;
    }

    /**
     * Gets the value of the usb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsb() {
        return usb;
    }

    /**
     * Sets the value of the usb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsb(String value) {
        this.usb = value;
    }

    /**
     * Gets the value of the gps property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGps() {
        return gps;
    }

    /**
     * Sets the value of the gps property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGps(String value) {
        this.gps = value;
    }

    /**
     * Gets the value of the fuelTankCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    /**
     * Sets the value of the fuelTankCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuelTankCapacity(String value) {
        this.fuelTankCapacity = value;
    }

    /**
     * Gets the value of the cityLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityLocation() {
        return cityLocation;
    }

    /**
     * Sets the value of the cityLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityLocation(String value) {
        this.cityLocation = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
