import javax.swing.*;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

public class ErrorScreen {
    private JPanel mainPanel;
    private JButton btnCloseSession;
    public BackgroundPanel backgroundPanel;
    private JLabel lblSupportText;
    private JLabel lblErrorDescription;

    public ErrorScreen(String errorDescription) {
        lblErrorDescription.setText(MessageFormat.format(InfoKiosk.bundle.getString("html_string"), errorDescription));
        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        lblSupportText.setText(InfoKiosk.bundle.getString("support_phone_message_template")  + " " + preferences.get(InfoKiosk.bundle.getString("support_phone_key"), "00-00") + ".");
        btnCloseSession.addActionListener(e -> InfoKiosk.initializeInvitation());
    }

}
