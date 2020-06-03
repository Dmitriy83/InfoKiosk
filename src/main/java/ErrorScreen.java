import javax.swing.*;
import java.util.prefs.Preferences;

public class ErrorScreen {
    private JPanel mainPanel;
    private JButton btnCloseSession;
    public BackgroundPanel backgroundPanel;
    private JLabel lblSupportText;
    private JLabel lblErrorDescription;

    public ErrorScreen(String errorDescription) {
        lblErrorDescription.setText("<html>"+ errorDescription +"</html>");
        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        lblSupportText.setText("Обратитесь в тех. поддержку по телефону " + preferences.get("support_phone", "00-00") + ".");
        btnCloseSession.addActionListener(e -> InfoKiosk.initializeInvitation());
    }

}
