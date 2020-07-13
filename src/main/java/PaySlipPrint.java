import javax.swing.*;
import javax.xml.datatype.XMLGregorianCalendar;
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
    private LocalDate monthLimitUntil;

    public PaySlipPrint(String employeeDescription, Object monthOfCalculatedSalary) {
        lblEmployeeData.setText(employeeDescription);
        if (monthOfCalculatedSalary == null) {
            month = LocalDate.now();
            monthLimitUntil = null;
        } else {
            XMLGregorianCalendar xmlMonth = (XMLGregorianCalendar)monthOfCalculatedSalary;
            monthLimitUntil = xmlMonth.toGregorianCalendar().toZonedDateTime().toLocalDate();
            month = monthLimitUntil;
        }
        month = month.withDayOfMonth(1);
        updateMonthLabel();
        btnCloseSession.addActionListener(e -> InfoKiosk.initializeInvitation());
        brnMonthDecrease.addActionListener(e -> {
            //System.out.println("Месяц уменьшен.");
            month = month.minusMonths(1);
            updateMonthLabel();
        });
        btnMonthIncrease.addActionListener(e -> {
            //System.out.println("Месяц увеличен.");
            LocalDate tempMonth = month.plusMonths(1);
            if (monthLimitUntil.compareTo(tempMonth) >= 0){
                month = month.plusMonths(1);
                updateMonthLabel();
            }
        });
        btnPaySlipPrint.addActionListener(e -> PDFPrinting.printPaySlip(month));
    }

    private void updateMonthLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy 'г.'"); //NON-NLS
        lblMonth.setText(formatter.format(month));
    }

}
