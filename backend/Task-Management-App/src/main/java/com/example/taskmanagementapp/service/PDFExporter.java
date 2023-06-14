package com.example.taskmanagementapp.service;
import com.example.taskmanagementapp.model.dto.TaskDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class PDFExporter {

    private static final String PDF_TITLE = "Your Tasks are waiting to be done";
    public static final String SPACING = "================================";
    public static final String TITLE = "Title";
    public static final String TASK = "Task";
    public static final String DESCRIPTION = "Description";
    public void exportToPDF(List<TaskDto> tasks , String outputPath) throws IOException {

        PDFont font =  PDType1Font.HELVETICA ;
        PDDocument doc    = new PDDocument();
        PDPage page = new PDPage();
        PDPageContentStream content = new PDPageContentStream(doc, page);

        content.beginText();
        content.newLineAtOffset(10, 700);
        content.setFont(font, 12);

        titleOfPDF(content, PDF_TITLE, SPACING);
        creatingContentForPdfPage(tasks, content);
        closingAndSavingTheFile(outputPath, doc, page, content);

    }

    private static void closingAndSavingTheFile(String outputPath, PDDocument doc, PDPage page, PDPageContentStream content) throws IOException {
        content.endText();
        content.close();
        doc.addPage(page);
        doc.save(outputPath);
    }

    private static void creatingContentForPdfPage(List<TaskDto> tasks, PDPageContentStream content) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            TaskDto taskDto = tasks.get(i);
            content.showText(String.format("%s: %d",TASK,i+1));
            content.newLineAtOffset(0, -15);
            content.newLineAtOffset(0, -15);

            content.showText(String.format("%s:",TITLE));
            content.newLineAtOffset(0, -15);
            content.showText(taskDto.getTitle());
            content.newLineAtOffset(0, -15);
            content.newLineAtOffset(0, -15);
            content.showText(String.format("%s:" ,DESCRIPTION));
            content.newLineAtOffset(0, -15);
            content.showText(taskDto.getDescription());
            content.newLineAtOffset(0, -15);
            content.showText(SPACING);
            content.newLineAtOffset(0, -15);
        }
    }

    private static void titleOfPDF(PDPageContentStream content, String title, String spacing) throws IOException {
        content.showText(title);
        content.newLineAtOffset(0, -15);
        content.showText(spacing);
        content.newLineAtOffset(0, -15);
    }
}
