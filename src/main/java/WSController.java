import org.infokiosk.InfoKioskWS;
import org.infokiosk.InfoKioskWSPortType;
import org.infokiosk_types.EmployeeData;
import org.infokiosk_types.FileTypes;

import javax.xml.datatype.XMLGregorianCalendar;
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
                return new java.net.PasswordAuthentication(preferences.get(InfoKiosk.bundle.getString("login_key"), InfoKiosk.bundle.getString("login_example")), InfoKiosk.getPassword().toCharArray());
            }
        });
        try {
            Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
            URL wsdlURL = new URL(preferences.get(InfoKiosk.bundle.getString("wsdl_address_key"), InfoKiosk.bundle.getString("infokiosk_wsdl_default")));
            InfoKioskWS ws = new InfoKioskWS(wsdlURL);
            wsPort = ws.getInfoKioskWSSoap();
            isConnected = true;
        } catch (Exception e) {
            InfoKiosk.showErrorScreen(InfoKiosk.bundle.getString("connection_web_service_error_message") + " " + e.getMessage());
            //e.printStackTrace();
            isConnected = false;
        }
    }

    public String getCompanyName() {
        if (wsPort != null) {
            return wsPort.getCompanyName();
        } else {
            return InfoKiosk.bundle.getString("company_name_not_recognized");
        }
    }

    public EmployeeData getEmployeeData(String keyCardNumber) {
        if (wsPort != null) {
            try {
                return wsPort.getEmployeeData(keyCardNumber);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public byte[] getPaySlipPDF(String individualId, XMLGregorianCalendar month) {
        if (wsPort != null) {
            try {
                return wsPort.getPaySlip(individualId, month, FileTypes.PDF);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
