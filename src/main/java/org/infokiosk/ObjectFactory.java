
package org.infokiosk;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.infokiosk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@SuppressWarnings("ALL")
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.infokiosk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCompanyName }
     * 
     */
    public GetCompanyName createGetCompanyName() {
        return new GetCompanyName();
    }

    /**
     * Create an instance of {@link GetCompanyNameResponse }
     * 
     */
    public GetCompanyNameResponse createGetCompanyNameResponse() {
        return new GetCompanyNameResponse();
    }

    /**
     * Create an instance of {@link GetEmployeeData }
     * 
     */
    public GetEmployeeData createGetEmployeeData() {
        return new GetEmployeeData();
    }

    /**
     * Create an instance of {@link GetEmployeeDataResponse }
     * 
     */
    public GetEmployeeDataResponse createGetEmployeeDataResponse() {
        return new GetEmployeeDataResponse();
    }

    /**
     * Create an instance of {@link GetPaySlip }
     * 
     */
    public GetPaySlip createGetPaySlip() {
        return new GetPaySlip();
    }

    /**
     * Create an instance of {@link GetPaySlipResponse }
     * 
     */
    public GetPaySlipResponse createGetPaySlipResponse() {
        return new GetPaySlipResponse();
    }

}
