//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.10 at 01:27:14 PM MESZ 
//


package com.hello2morrow.sonarplugin.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xsdCycleGroups complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xsdCycleGroups">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cycleGroup" type="{}xsdCycleGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="numberOf" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xsdCycleGroups", propOrder = {
    "cycleGroup"
})
public class XsdCycleGroups {

    @XmlElement(nillable = true)
    protected List<XsdCycleGroup> cycleGroup;
    @XmlAttribute(required = true)
    protected String numberOf;

    /**
     * Gets the value of the cycleGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cycleGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCycleGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XsdCycleGroup }
     * 
     * 
     */
    public List<XsdCycleGroup> getCycleGroup() {
        if (cycleGroup == null) {
            cycleGroup = new ArrayList<XsdCycleGroup>();
        }
        return this.cycleGroup;
    }

    /**
     * Gets the value of the numberOf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOf() {
        return numberOf;
    }

    /**
     * Sets the value of the numberOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOf(String value) {
        this.numberOf = value;
    }

}
