package org.infokiosk;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.6
 * 2020-07-13T12:58:10.695+03:00
 * Generated source version: 3.3.6
 *
 */
@SuppressWarnings("ALL")
@WebService(targetNamespace = "http://www.infokiosk.org", name = "InfoKioskWSPortType")
@XmlSeeAlso({org.infokiosk_types.ObjectFactory.class, ObjectFactory.class})
public interface InfoKioskWSPortType {

    @WebMethod(operationName = "GetCompanyName", action = "http://www.infokiosk.org#InfoKioskWS:GetCompanyName")
    @RequestWrapper(localName = "GetCompanyName", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetCompanyName")
    @ResponseWrapper(localName = "GetCompanyNameResponse", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetCompanyNameResponse")
    @WebResult(name = "return", targetNamespace = "http://www.infokiosk.org")
    public java.lang.String getCompanyName()
;

    @WebMethod(operationName = "GetEmployeeData", action = "http://www.infokiosk.org#InfoKioskWS:GetEmployeeData")
    @RequestWrapper(localName = "GetEmployeeData", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetEmployeeData")
    @ResponseWrapper(localName = "GetEmployeeDataResponse", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetEmployeeDataResponse")
    @WebResult(name = "return", targetNamespace = "http://www.infokiosk.org")
    public org.infokiosk_types.EmployeeData getEmployeeData(

        @WebParam(name = "KeyCardNumber", targetNamespace = "http://www.infokiosk.org")
        java.lang.String keyCardNumber
    );

    @WebMethod(operationName = "GetPaySlip", action = "http://www.infokiosk.org#InfoKioskWS:GetPaySlip")
    @RequestWrapper(localName = "GetPaySlip", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetPaySlip")
    @ResponseWrapper(localName = "GetPaySlipResponse", targetNamespace = "http://www.infokiosk.org", className = "org.infokiosk.GetPaySlipResponse")
    @WebResult(name = "return", targetNamespace = "http://www.infokiosk.org")
    public byte[] getPaySlip(

        @WebParam(name = "IndividualId", targetNamespace = "http://www.infokiosk.org")
        java.lang.String individualId,
        @WebParam(name = "Month", targetNamespace = "http://www.infokiosk.org")
        javax.xml.datatype.XMLGregorianCalendar month,
        @WebParam(name = "FileType", targetNamespace = "http://www.infokiosk.org")
        org.infokiosk_types.FileTypes fileType
    );
}
