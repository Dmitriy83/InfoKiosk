import org.infokiosk.InfokioskPortType;
import org.infokiosk.InfokioskWS;
import org.infokiosk_types.EmployeeData;
import org.infokiosk_types.FileTypes;

import javax.swing.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.prefs.Preferences;

public class WSController {
    private InfokioskPortType wsPort;

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
            InfokioskWS ws = new InfokioskWS(wsdlURL);
            wsPort = ws.getInfokioskSoap();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public String getCompanyName() {
        try {
            return wsPort.getCompanyName();
        } catch (Exception e) {
            return "Организация не определена.";
        }
    }

    public EmployeeData getEmployeeData(String keyCardNumber) {
        try {
            return wsPort.getEmployeeData(keyCardNumber);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getPaySlipPDF(String individualId, XMLGregorianCalendar month) {
        try {
            return wsPort.getPaySlip(individualId, month, FileTypes.PDF);
        } catch (Exception e) {
            return null;
        }
    }
}
