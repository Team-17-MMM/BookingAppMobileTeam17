package com.example.bookingappteam17.clients;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.bookingappteam17.dto.accommodation.AccommodationReportDTO;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfGenerator {

    public static String generatePdf(Context context, String startDate, String endDate, List<AccommodationReportDTO> data) {
        try {
            // Get the external storage directory
            File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "PDFs");
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Create a unique filename based on the current timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "baki_report_" + timestamp + ".pdf";

            // Combine the directory and filename to get the full file path
            String filePath = new File(directory, fileName).getPath();

            // Creating a PdfWriter object
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(filePath));

            // Creating a PdfDocument object
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            // Creating a Document object
            Document document = new Document(pdfDocument);

            // Adding title
            document.add(new Paragraph("Report").setFontSize(18).setTextAlignment(TextAlignment.CENTER));

            // Adding date range
            String dateRange = String.format("This is data for period from: %s to %s", startDate, endDate);
            document.add(new Paragraph(dateRange).setFontSize(12));

            // Adding table with data
            addDataTable(document, data);

            // Adding footer
            document.add(new Paragraph("Your bakivookingteam17 with pleasure").setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            // Closing the Document
            document.close();

            Log.d("PdfGenerator", "PDF generated successfully at " + filePath);
            return filePath; // Return the generated file path
        } catch (IOException e) {
            Log.e("PdfGenerator", "Error generating PDF", e);
            return null; // Return null in case of an error
        }
    }

    private static void addDataTable(Document document, List<AccommodationReportDTO> data) {
        int numberOfColumns = 4;

        // Creating a table with as many columns as the data items
        Table table = new Table(numberOfColumns);

        // Set the width of the table to fill the available space
        table.setWidth(UnitValue.createPercentValue(90));
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // Adding column headers
        addTableHeader(table);

        // Adding data to the table
        for (AccommodationReportDTO dto : data) {
            addRow(table, dto.getId().toString(), dto.getName(), String.valueOf(dto.getProfit()), String.valueOf(dto.getNumberOfReservations()));
        }

        // Adding table to the document
        document.add(table);
    }


    private static void addTableHeader(Table table) {
        Cell idHeader = new Cell().add(new Paragraph("ID"));
        Cell nameHeader = new Cell().add(new Paragraph("Name"));
        Cell profitHeader = new Cell().add(new Paragraph("Profit"));
        Cell reservationsHeader = new Cell().add(new Paragraph("Reservations"));

        table.addHeaderCell(idHeader);
        table.addHeaderCell(nameHeader);
        table.addHeaderCell(profitHeader);
        table.addHeaderCell(reservationsHeader);
    }

    private static void addRow(Table table, String id, String name, String profit, String reservations) {
        table.addCell(new Cell().add(new Paragraph(id)));
        table.addCell(new Cell().add(new Paragraph(name)));
        table.addCell(new Cell().add(new Paragraph(profit)));
        table.addCell(new Cell().add(new Paragraph(reservations)));
    }

}

