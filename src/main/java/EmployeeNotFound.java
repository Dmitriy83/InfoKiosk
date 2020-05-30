import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class EmployeeNotFound {
    private JPanel mainPanel;
    private JButton btnCloseSession;
    public BackgroundPanel backgroundPanel;
    private JLabel lblSupportText;

    public EmployeeNotFound() {
        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        lblSupportText.setText("Обратитесь в тех. поддержку по телефону " + preferences.get("support_phone", "00-00") + ".");
        btnCloseSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoKiosk.initializeInvitation();
            }
        });
    }

}
