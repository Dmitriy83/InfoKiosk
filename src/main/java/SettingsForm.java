import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsForm {
    public BackgroundPanel backgroundPanel;
    private JTextField txtWSDLAddress;
    private JPasswordField pswPassword;
    private JTextField txtSupportPhone;
    private JCheckBox chkAlwaysOnTop;
    public JButton btnStartInfoKiosk;
    private JTextField txtLogin;

    public SettingsForm(String wsdlAddress, String login, String supportPhone, boolean alwaysOnTop){
        txtWSDLAddress.setText(wsdlAddress);
        txtLogin.setText(login);
        chkAlwaysOnTop.setSelected(alwaysOnTop);
        txtSupportPhone.setText(supportPhone);
        btnStartInfoKiosk.addActionListener(e -> {
            InfoKiosk.saveSettings(chkAlwaysOnTop.isSelected(), txtWSDLAddress.getText(), txtLogin.getText(), txtSupportPhone.getText());
            InfoKiosk.setPassword(new String(pswPassword.getPassword())); // Пароль в настройках пользователя не сохраняем
            InfoKiosk.closeSettingsForm();
            InfoKiosk.startInfoKioskInEDT();
        });
    }
}
