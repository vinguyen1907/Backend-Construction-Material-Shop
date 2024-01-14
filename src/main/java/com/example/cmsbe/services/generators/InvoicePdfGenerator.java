package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.SaleOrder;
import com.example.cmsbe.utils.DecimalUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class InvoicePdfGenerator {
    private SaleOrder order;
    private   Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    public InvoicePdfGenerator(SaleOrder order) {
        this.order = order;
    }
    
    public   ByteArrayOutputStream generateInvoicePDF() throws DocumentException, IOException {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (var i = 0; i < order.getOrderItems().size(); i++) {
            var item = order.getOrderItems().get(i);
            Map<String, Object> newRow = Map.of(
                    ".No", i + 1,
                    "Product Name", item.getInventoryItem().getProduct().getName(),
                    "Quantity", item.getQuantity(),
                    "Price", item.getQuantity() * item.getInventoryItem().getProduct().getUnitPrice()
            );
            rows.add(newRow);
        }

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        writeHeader(document);

        addEmptyLine(document, 4);

        // Write order information
        writeCustomerInfo(document, order);


        // Add a separator line
        document.add(new LineSeparator());

        addEmptyLine(document, 1);

        createTable(document, rows);

        writeOrderInfo(document, order);

        writeSignature(document);

        document.close();
        return outputStream;
    }

    private   void writeSignature(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell c1 = new PdfPCell(new Phrase("CMS Shop, " + order.getCreatedTime().getDayOfMonth() + ", " + order.getCreatedTime().getMonth() + ", " + order.getCreatedTime().getYear(), boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.NO_BORDER);
        PdfPCell c2 = new PdfPCell(new Phrase(order.getCreatedUser().getName(), boldFont));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        PdfPCell emptyCell = new PdfPCell(new Phrase("\n"));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(c2);
        table.setSpacingBefore(40f);
        document.add(table);
    }

    private   void createTable(Document document, List<Map<String, Object>> rows)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        // t.setBorderColor(BaseColor.GRAY);
//         table.setPaddingTop(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

//        Map<String, Object> firstRow = rows.get(0);
//        writeTableHeader(document, firstRow.keySet());
//        for (Map<String, Object> row : rows) {
//            writeTableRow(document, row.values());
//        }

        var headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        PdfPCell c1 = new PdfPCell(new Phrase(".No", headerFont));
        c1.setPadding(5);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product name", headerFont));
        c1.setPadding(5);
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity", headerFont));
        c1.setPadding(5);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price", headerFont));
        c1.setPadding(5);
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (var i = 0; i < rows.size(); i++) {
            var item = rows.get(i);
            var cell = new PdfPCell(new Phrase(String.valueOf(i + 1)));
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell.setPhrase(new Phrase(item.get("Product Name").toString()));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(item.get("Quantity").toString()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell.setPhrase(new Phrase("$" +DecimalUtil.format((Double) item.get("Price")).toString()));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        }

        table.setSpacingBefore(10f);

        document.add(table);
    }

    private   void writeHeader(Document document) throws DocumentException, IOException {
        // Create a PdfPTable to hold the header content
        PdfPTable headerTable = new PdfPTable(new float[]{90, 10});
        ;
        headerTable.setWidthPercentage(100);

// Column 1: "INVOICE" and "CMS SHOP"
        Paragraph invoiceParagraph = new Paragraph();
        Chunk invoiceChunk = new Chunk("INVOICE", new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD));
        Chunk cmsShopChunk = new Chunk("CMS SHOP", new Font(Font.FontFamily.HELVETICA, 14));
//        Paragraph invoiceParagraph = new Paragraph("INVOICE", new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD));
//        invoiceParagraph.add(new Paragraph("CMS SHOP", new Font(Font.FontFamily.HELVETICA, 14)));
        invoiceParagraph.add(invoiceChunk);
        invoiceParagraph.add(Chunk.NEWLINE); // Add a newline between "INVOICE" and "CMS SHOP"
        invoiceParagraph.add(cmsShopChunk);

        PdfPCell invoiceCell = new PdfPCell(invoiceParagraph);
        invoiceCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(invoiceCell);

// Column 2: Image
        Image image = Image.getInstance("src/main/java/com/example/cmsbe/assets/logo.png");  // Replace with the actual path to your image
        image.scaleAbsolute(10f, 10f);
        PdfPCell imageCell = new PdfPCell(image, true);
        imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        imageCell.setVerticalAlignment(Element.ALIGN_TOP);
        imageCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(imageCell);

        document.add(headerTable);
    }

    private   void writeCustomerInfo(Document document, SaleOrder order) throws DocumentException, IOException {
        // Write order details
//        Paragraph orderDetails = new Paragraph();
//
//        orderDetails.add(new Paragraph("Order ID: " + order.getId()));
//        orderDetails.add(new Paragraph("Created By: " + order.getCreatedUser().getName()));
//        orderDetails.add(new Paragraph("Customer: " + order.getCustomer().getName()));
//        orderDetails.add(new Paragraph("Address: " + order.getCustomer().getContactAddress()));
//
//        orderDetails.setSpacingAfter(10f);
//        orderDetails.setSpacingBefore(10f);
//
//        document.add(orderDetails);
        PdfPTable orderDetailsTable = new PdfPTable(new float[]{30, 70});
        orderDetailsTable.setWidthPercentage(100);

        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font regularFont = new Font(Font.FontFamily.HELVETICA, 12);

        orderDetailsTable.addCell(createCell("Order ID:", boldFont));
        orderDetailsTable.addCell(createCell(String.valueOf(order.getId()), regularFont));
        orderDetailsTable.addCell(createCell("Created By:", boldFont));
        orderDetailsTable.addCell(createCell(order.getCreatedUser().getName(), regularFont));
        orderDetailsTable.addCell(createCell("Customer:", boldFont));
        orderDetailsTable.addCell(createCell(order.getCustomer().getContactAddress(), regularFont));

        orderDetailsTable.setSpacingAfter(10f);
        orderDetailsTable.setSpacingBefore(10f);

        document.add(orderDetailsTable);
    }

    private   PdfPCell createCell(String label, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        Paragraph labelParagraph = new Paragraph(label, font);
        labelParagraph.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(labelParagraph);

        return cell;
    }

    private   void writeOrderInfo(Document document, SaleOrder order) throws DocumentException {
        // Create a PdfPTable with 2 columns
        PdfPTable orderDetailsTable = new PdfPTable(2);
        orderDetailsTable.setWidthPercentage(100);

        // Add order details to the table
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font regularFont = new Font(Font.FontFamily.HELVETICA, 12);

        orderDetailsTable.addCell(createCell("Discount:", boldFont));
        orderDetailsTable.addCell(createCell(DecimalUtil.format(order.getDiscount()) + "%", regularFont));
        orderDetailsTable.addCell(createCell("Total:", boldFont));
        orderDetailsTable.addCell(createCell("$" + DecimalUtil.format(order.getTotal()), regularFont));
        orderDetailsTable.addCell(createCell("Debt:", boldFont));
        orderDetailsTable.addCell(createCell("$" + DecimalUtil.format(order.getDebt() != null  ? order.getDebt().getAmount() : 0), regularFont));

        // Set spacing for the table
        orderDetailsTable.setSpacingAfter(10f);
        orderDetailsTable.setSpacingBefore(10f);

        document.add(orderDetailsTable);
    }


//    private   void writeTableHeader(Document document, Set<String> columns) throws DocumentException {
//        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        PdfPTable table = new PdfPTable(columns.size());
//        table.setWidthPercentage(100);
//
//        for (String column : columns) {
//            PdfPCell cell = new PdfPCell(new Phrase(column, boldFont));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(cell);
//        }
//
//        document.add(table);
//    }
//
//    private   void writeTableRow(Document document, Collection<Object> values) throws DocumentException {
//        PdfPTable table = new PdfPTable(values.size());
//        table.setWidthPercentage(100);
//
//        for (Object value : values) {
//            PdfPCell cell = new PdfPCell(new Phrase(value.toString()));
//            cell.setPadding(5);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(cell);
//        }
//
//        document.add(table);
//    }

    private   void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(new Paragraph("\n"));
        }
    }
}
