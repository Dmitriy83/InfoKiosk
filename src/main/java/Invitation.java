import javax.swing.*;

public class Invitation {
    private JPanel mainPanel;
    private JLabel lblCompanyName;
    public BackgroundPanel backgroundPanel;
    private JLabel lblTouchKeyCard;

    public Invitation(String companyName) {
        setCompanyName(companyName);
    }

    private void setCompanyName(String name) {
        lblCompanyName.setText(name.toUpperCase());
    }

}
