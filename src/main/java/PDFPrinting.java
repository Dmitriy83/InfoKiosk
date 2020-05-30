import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.print.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public final class PDFPrinting {
    /** Get pdf document throw web service and print the pay slip
     * @param month Month for printing pay slip
     */
    public static void printPaySlip(LocalDate month) {
        try {
            WSController wsController = new WSController();
            GregorianCalendar gCal = GregorianCalendar.from(month.atStartOfDay(ZoneId.systemDefault()));
            XMLGregorianCalendar xmlMonth = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
            byte[] paySlip = wsController.getPaySlipPDF(InfoKiosk.getIndividualId(), xmlMonth);
            PDDocument document = PDDocument.load(paySlip);
            PDFPrinting.print(document);
            document.close();

            // Открываем начальную страницу
            InfoKiosk.initializeInvitation();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Произошла ошибка при печати расчетного листка. Обратитесь в тех. поддержку.");
            e.printStackTrace();
        }
    }

    /**
     * Prints the document at its actual size. This is the recommended way to print.
     */
    static void print(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }

    /**
     * Prints using custom PrintRequestAttribute values.
     */
    private static void printWithAttributes(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1

        job.print(attr);
    }

    /**
     * Prints with a print preview dialog.
     */
    private static void printWithDialog(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        if (job.printDialog())
        {
            job.print();
        }
    }

    /**
     * Prints with a print preview dialog and custom PrintRequestAttribute values.
     */
    private static void printWithDialogAndAttributes(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1

        PDViewerPreferences vp = document.getDocumentCatalog().getViewerPreferences();
        if (vp != null && vp.getDuplex() != null)
        {
            String dp = vp.getDuplex();
            if (PDViewerPreferences.DUPLEX.DuplexFlipLongEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_LONG_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.DuplexFlipShortEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_SHORT_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.Simplex.toString().equals(dp))
            {
                attr.add(Sides.ONE_SIDED);
            }
        }

        if (job.printDialog(attr))
        {
            job.print(attr);
        }
    }

    /**
     * Prints using a custom page size and custom margins.
     */
    private static void printWithPaper(PDDocument document) throws PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        // define custom paper
        Paper paper = new Paper();
        paper.setSize(306, 396); // 1/72 inch
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // no margins

        // custom page format
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);

        // override the page format
        Book book = new Book();
        // append all pages
        book.append(new PDFPrintable(document), pageFormat, document.getNumberOfPages());
        job.setPageable(book);

        job.print();
    }
}
