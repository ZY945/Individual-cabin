package com.cabin.file.excel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfToWordConverter {
    public static void main(String[] args) {
        try {
            // 打开PDF文件
            PDDocument pdfDocument = PDDocument.load(new FileInputStream("input.pdf"));

            // 创建一个新的Word文档
            XWPFDocument wordDocument = new XWPFDocument();

            // 提取PDF文本并将其添加到Word文档中
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(pdfDocument);

            XWPFParagraph paragraph = wordDocument.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(pdfText);

            // 保存Word文档
            FileOutputStream out = new FileOutputStream("output.docx");
            wordDocument.write(out);
            out.close();

            // 关闭PDF文档
            pdfDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
