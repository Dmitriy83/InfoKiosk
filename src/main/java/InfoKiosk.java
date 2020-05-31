import org.infokiosk_types.EmployeeData;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

public class InfoKiosk {
    private static InfoKioskFrame frame;
    private static JFrame frameSettings;
    private static KeyAdapter frameKeyListener;
    private static String companyName = "Организация не определена.";
    private static String individualId;
    private static Timer sessionTimer;
    private static String password;

    public static void main(String[] args) {
        setThemeNimbus();
        initializeSettingsInEDT();
    }

    private static void setThemeNimbus(){
        // Установим тему Nimbus, чтобы по умолчанию углы кнопок были закруглены
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeSettingsInEDT(){
        initializeSettings();
    }

    private static void initializeSettings(){
        frameSettings = new JFrame("Settings");
        frameSettings.setUndecorated(true);
        frameSettings.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage image = getImage("settings_background.png");

        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        SettingsForm form = new SettingsForm(
                preferences.get("wsdl_address", "http://<ip>/zup/ws/infokiosk.1cws?wsdl"),
                preferences.get("login", "AdminWS"),
                preferences.get("support_phone", "00-00"),
                preferences.getBoolean("always_on_top", false));
        BackgroundPanel panel = form.backgroundPanel;

        panel.setImage(image);
        frameSettings.setContentPane(panel);
        frameSettings.setSize(480, 500);
        frameSettings.setResizable(false);
        frameSettings.setLocationRelativeTo(null); // Отображаем по центру
        frameSettings.getRootPane().setDefaultButton(form.btnStartInfoKiosk);
        frameSettings.setVisible(true);
    }

    private static BufferedImage getImage(String imageName) {
        InputStream is = InfoKiosk.class.getResourceAsStream(imageName);
        BufferedImage image = null;
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            showErrorScreen("Произошла ошибка при загрузке фона формы: " + e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    public static void startInfoKioskInEDT(){
        SwingUtilities.invokeLater(() -> startInfoKiosk());
    }

    private static void startInfoKiosk(){
        frame = new InfoKioskFrame("InfoKiosk");
        frame.setUndecorated(true);                                     // уберем заголовок и рамки окна приложения
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        if (preferences.getBoolean("always_on_top", false)) {
            frame.setAlwaysOnTop(true);
        }

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);                   // установим полноэкранный режим

        // Добавим отслеживание перемещения мыши для завершения сеанса по времени простоя
        Toolkit.getDefaultToolkit().addAWTEventListener(new Listener(), AWTEvent.MOUSE_EVENT_MASK);
        startTimer(15);

        frameKeyListener = getFrameKeyListener(frame);
        WSController wsController = new WSController();
        if (wsController.isConnected()) {
            companyName = wsController.getCompanyName();
            initializeInvitation();
        }

        frame.setVisible(true);
    }

    private static void startTimer(int delaySeconds) {
        sessionTimer = new Timer(delaySeconds * 1000, ae -> {
            System.out.println("Событие таймера произошло.");
            sessionTimer.restart();
            frame.removeKeyListener(frameKeyListener);
            setFieldsInDefaultValue();
            initializeInvitation();
        });
        sessionTimer.setRepeats(false);
        sessionTimer.setInitialDelay(delaySeconds * 1000);
        sessionTimer.start();
    }

    public static void closeSettingsForm() {
        frameSettings.setVisible(false);
        frameSettings.dispose();
    }

    public static void saveSettings(boolean alwaysOnTop, String wsdlAddress, String login, String supportPhone) {
        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        preferences.putBoolean("always_on_top", alwaysOnTop);
        preferences.put("wsdl_address", wsdlAddress);
        preferences.put("login", login);
        preferences.put("support_phone", supportPhone);
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        InfoKiosk.password = password;
    }

    private static class Listener implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
            // Пользователь совершил действие - сбрасываем таймер завершения сеанса
            if (MouseEvent.MOUSE_PRESSED == event.getID()) {
                sessionTimer.restart();
            }
        }
    }

    private static KeyAdapter getFrameKeyListener(InfoKioskFrame frame) {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    WSController wsController = new WSController();
                    if (!wsController.isConnected()) {
                        return;
                    }
                    EmployeeData employeeData = wsController.getEmployeeData(frame.getKeyCardNumber());
                    if (employeeData != null && employeeData.isIsFound()) {
                        setIndividualId(employeeData.getIndividualId());
                        initializePaySlipPrint(employeeData.getDiscription());
                    } else {
                        setIndividualId("");
                        showErrorScreen("Сотрудник по номеру пропуска не найден.");
                    }
                    sessionTimer.restart();

                    // Теперь очистим номер пропуска, чтобы можно было ввести следующий
                    frame.setKeyCardNumber("");
                } else {
                    // Был считан какой-то символ, отличный от Enter. Добавим его в "Кэш номера пропуска"
                    frame.setKeyCardNumber(frame.getKeyCardNumber() + e.getKeyChar());
                }
            }
        };
    }

    public static void initializeInvitation() {
        setFieldsInDefaultValue();
        initializePanel(new Invitation(companyName).backgroundPanel);
        frame.addKeyListener(frameKeyListener);
        frame.setFocusable(true);
        frame.requestFocusInWindow(); // Фрейм всегда должен иметь фокус, чтобы работал listener
    }

    public static void showErrorScreen(String errorDescription) {
        initializeNonInvitationPanel(new ErrorScreen(errorDescription).backgroundPanel);
    }

    private static void initializePaySlipPrint(String employeeDescription) {
        initializeNonInvitationPanel(new PaySlipPrint(employeeDescription).backgroundPanel);
    }

    private static void initializeNonInvitationPanel(BackgroundPanel panel) {
        initializePanel(panel);
        frame.removeKeyListener(frameKeyListener);
    }

    private static void initializePanel(BackgroundPanel panel) {
        panel.setImage(getImage("form_background.jpg"));
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static void setFieldsInDefaultValue() {
        setIndividualId("");
        frame.setKeyCardNumber("");
    }

    public static String getIndividualId() {
        return individualId;
    }

    public static void setIndividualId(String individualId) {
        InfoKiosk.individualId = individualId;
    }
}
