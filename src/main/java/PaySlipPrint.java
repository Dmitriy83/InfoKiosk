import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaySlipPrint {
    private JPanel mainPanel;
    private JLabel lblEmployeeData;
    private JButton brnMonthDecrease;
    private JButton btnMonthIncrease;
    private JButton btnCloseSession;
    private JButton btnPaySlipPrint;
    private JLabel lblMonth;
    public BackgroundPanel backgroundPanel;
    private LocalDate month;

    public PaySlipPrint(String employeeDescription) {
        lblEmployeeData.setText(employeeDescription);
        month = LocalDate.now();
        month = month.withDayOfMonth(1);
        updateMonthLabel();
        btnCloseSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoKiosk.initializeInvitation();
            }
        });
        brnMonthDecrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                month = month.minusMonths(1);
                updateMonthLabel();
            }
        });
        btnMonthIncrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                month = month.plusMonths(1);
                updateMonthLabel();
            }
        });
        btnPaySlipPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFPrinting.printPaySlip(month);
            }
        });
    }

    private void updateMonthLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy 'г.'");
        lblMonth.setText(formatter.format(month));
    }

}
