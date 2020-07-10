import org.infokiosk_types.EmployeeData;
import org.jetbrains.annotations.NonNls;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class InfoKiosk {
    private static InfoKioskFrame frame;
    private static JFrame frameSettings;
    private static KeyAdapter frameKeyListener;
    private static String companyName;
    private static String individualId;
    private static Timer sessionTimer;
    private static String password;
    private static AWTListener awtListener;
    @NonNls
    public static final ResourceBundle bundle = ResourceBundle.getBundle("subscription");

    public static void main(String[] args) {
        setThemeNimbus();
        initializeSettingsInEDT();
    }

    public static void Dispose(){
        // Сейчас этот метод нужен для последовательного выполнения тестовых сценариев. Иначе, например, во втором сценарии начнет срабатывать таймер, подключенный в первом сценарии.
        sessionTimer.stop();
        sessionTimer = null;
        frameSettings = null;
        frame = null;
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);
        companyName = bundle.getString("company_name_not_recognized");
        individualId = "";
        password = "";
    }

    private static void setThemeNimbus(){
        // Установим тему Nimbus, чтобы по умолчанию углы кнопок были закруглены
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (bundle.getString("nimbus").equals(info.getName())) {
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
        frameSettings = new JFrame(bundle.getString("settings_frame_title"));
        frameSettings.setUndecorated(true);
        frameSettings.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage image = getImage(bundle.getString("settings_background_file_name"));

        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        SettingsForm form = new SettingsForm(
                preferences.get(bundle.getString("wsdl_address_key"), bundle.getString("infokiosk_wsdl_example")),
                preferences.get(bundle.getString("login_key"), bundle.getString("login_example")),
                preferences.get(bundle.getString("support_phone_key"), bundle.getString("support_phone_example")),
                preferences.getBoolean(bundle.getString("always_on_top_key"), false));
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
            showErrorScreen(bundle.getString("background_downloading_error_message") + " " + e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    public static void startInfoKioskInEDT(){
        SwingUtilities.invokeLater(InfoKiosk::startInfoKiosk);
    }

    private static void startInfoKiosk(){
        frame = new InfoKioskFrame("InfoKiosk");
        frame.setUndecorated(true);                                     // уберем заголовок и рамки окна приложения
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Preferences preferences = Preferences.userRoot().node(InfoKiosk.class.getName());
        if (preferences.getBoolean(bundle.getString("always_on_top_key"), false)) {
            frame.setAlwaysOnTop(true);
        }

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);                   // установим полноэкранный режим

        // Добавим отслеживание перемещения мыши для завершения сеанса по времени простоя
        awtListener = new AWTListener();
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_EVENT_MASK);
        startTimer(15);

        frameKeyListener = getFrameKeyListener(frame);
        WSController wsController = new WSController();
        if (wsController.isConnected()) {
            companyName = wsController.getCompanyName();
            initializeInvitation();
        }

        frame.setVisible(true);
    }

    @SuppressWarnings("SameParameterValue")
    private static void startTimer(int delaySeconds) {
        sessionTimer = new Timer(delaySeconds * 1000, ae -> {
            //System.out.println("Событие таймера произошло: " + LocalDateTime.now());
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
        preferences.putBoolean(bundle.getString("always_on_top_key"), alwaysOnTop);
        preferences.put(bundle.getString("wsdl_address_key"), wsdlAddress);
        preferences.put(bundle.getString("login_key"), login);
        preferences.put(bundle.getString("support_phone_key"), supportPhone);
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        InfoKiosk.password = password;
    }

    private static class AWTListener implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
            // Пользователь совершил действие - сбрасываем таймер завершения сеанса
            if (MouseEvent.MOUSE_PRESSED == event.getID()) {
                //System.out.println("Таймер сброшен: " + LocalDateTime.now());
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
                    System.out.println("Key number: " + frame.getKeyCardNumber());
                    EmployeeData employeeData = wsController.getEmployeeData(frame.getKeyCardNumber());
                    if (employeeData != null && employeeData.isIsFound()) {
                        setIndividualId(employeeData.getIndividualId());
                        initializePaySlipPrint(employeeData.getDescription());
                    } else {
                        setIndividualId("");
                        showErrorScreen(bundle.getString("employee_not_found_error"));
                    }
                    sessionTimer.restart();

                    // Теперь очистим номер пропуска, чтобы можно было ввести следующий
                    frame.setKeyCardNumber("");
                } else if (!(e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)){
                    // Был считан какой-то символ, отличный от Enter. Добавим его в "Кэш номера пропуска"
                    frame.setKeyCardNumber(frame.getKeyCardNumber() + e.getKeyChar());
                }
            }
        };
    }

    public static void initializeInvitation() {
        setFieldsInDefaultValue();
        if (companyName == null) { companyName = bundle.getString("company_name_not_recognized"); }
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
        panel.setImage(getImage(bundle.getString("main_background_file_name")));
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
