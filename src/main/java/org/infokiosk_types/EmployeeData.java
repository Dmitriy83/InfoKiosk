
package org.infokiosk_types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IndividualId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isFound" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="MonthOfCalculatedSalary" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeData", propOrder = { //NON-NLS
    "individualId", //NON-NLS
    "description", //NON-NLS
    "isFound", //NON-NLS
    "monthOfCalculatedSalary" //NON-NLS
})
public class EmployeeData {

    @XmlElement(name = "IndividualId", required = true) //NON-NLS
    protected String individualId;
    @XmlElement(name = "Description", required = true) //NON-NLS
    protected String description;
    protected boolean isFound;
    @XmlElement(name = "MonthOfCalculatedSalary", required = true, nillable = true) //NON-NLS
    protected Object monthOfCalculatedSalary;

    /**
     * Gets the value of the individualId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndividualId() {
        return individualId;
    }

    /**
     * Sets the value of the individualId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndividualId(String value) {
        this.individualId = value;
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

    /**
     * Gets the value of the isFound property.
     * 
     */
    public boolean isIsFound() {
        return isFound;
    }

    /**
     * Sets the value of the isFound property.
     * 
     */
    public void setIsFound(boolean value) {
        this.isFound = value;
    }

    /**
     * Gets the value of the monthOfCalculatedSalary property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMonthOfCalculatedSalary() {
        return monthOfCalculatedSalary;
    }

    /**
     * Sets the value of the monthOfCalculatedSalary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMonthOfCalculatedSalary(Object value) {
        this.monthOfCalculatedSalary = value;
    }

}
