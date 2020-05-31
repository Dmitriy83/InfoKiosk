import javax.swing.*;

public class InfoKioskFrame extends JFrame {
    private String mKeyCardNumber = "";
    public void setKeyCardNumber(String keyCardNumber){
        mKeyCardNumber = keyCardNumber;
    }
    public String getKeyCardNumber(){
        return mKeyCardNumber;
    }
    public InfoKioskFrame(String name){
        setName(name);
    }
}
