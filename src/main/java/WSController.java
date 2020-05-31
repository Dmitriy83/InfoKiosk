import org.infokiosk.InfoKioskWS;
import org.infokiosk.InfoKioskWSPortType;
import org.infokiosk_types.EmployeeData;
import org.infokiosk_types.FileTypes;

import javax.swing.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.prefs.Preferences;

public class WSController {
    private InfoKioskWSPortType wsPort;
    private boolean isConnected;

    public WSController(){
        // Установим параметры аутентификации для доступа к веб-сервису по умолчанию
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
                return new java.net.PasswordAuthentication(preferences.get("login", "AdminWS"), InfoKiosk.getPassword().toCharArray());
            }
        });
        try {
            Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
            URL wsdlURL = new URL(preferences.get("wsdl_address", "http://localhost/zup/ws/infokiosk.1cws?wsdl"));
            InfoKioskWS ws = new InfoKioskWS(wsdlURL);
            wsPort = ws.getInfoKioskWSSoap();
            isConnected = true;
        } catch (Exception e) {
            InfoKiosk.showErrorScreen("Произошла ошибка при установке соединения с веб-сервисом: " + e.getMessage());
            //e.printStackTrace();
            isConnected = false;
        }
    }

    public String getCompanyName() {
        if (wsPort != null) {
            return wsPort.getCompanyName();
        } else {
            return "Организация не определена.";
        }
    }

    public EmployeeData getEmployeeData(String keyCardNumber) {
        if (wsPort != null) {
            return wsPort.getEmployeeData(keyCardNumber);
        } else {
            return null;
        }
    }

    public byte[] getPaySlipPDF(String individualId, XMLGregorianCalendar month) {
        if (wsPort != null) {
            return wsPort.getPaySlip(individualId, month, FileTypes.PDF);
        } else {
            return null;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
