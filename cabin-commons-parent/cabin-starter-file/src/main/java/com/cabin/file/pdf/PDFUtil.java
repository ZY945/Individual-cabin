package com.cabin.file.pdf;

import com.cabin.file.fileUtil.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 伍六七
 * @date 2023/7/13 16:21
 */
public class PDFUtil {
    /**
     * 多图片合成pdf的限制后缀
     */
    private static final List<String> IMAGE_SUFFIX = Arrays.asList("jpg", "png", "jpeg");

    public static void OnePdfToOneWord(String PdfPath, String target) throws IOException {
        PDDocument doc = PDDocument.load(new File(PdfPath));
        int pagenumber = doc.getNumberOfPages();
//        PdfPath = PdfPath.substring(0, PdfPath.lastIndexOf("."));
        String fileName = null;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            fileName = FileUtil.getFileNameByWindows(PdfPath);
        } else if (osName.startsWith("Linux")) {
            fileName = FileUtil.getFileNameByLinux(PdfPath);
        }
        if (fileName == null) {
            throw new FileNotFoundException("没有该文件");
        }
        FileOutputStream fos = new FileOutputStream(target + fileName + ".doc");
        Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);// 排序
        stripper.setStartPage(1);// 设置转换的开始页
        stripper.setEndPage(pagenumber);// 设置转换的结束页
        stripper.writeText(doc, writer);
        writer.close();
        doc.close();
    }

    public static File getPdfByteByOneImg(String imgPath, String target) throws IOException {
        byte[] pdfByteByOneImg = getPdfByteByOneImg(imgPath);

        // 创建临时文件
        int index = target.lastIndexOf(".");
        File tempFile = File.createTempFile(target.substring(0, index), "pdf");
        System.out.println("临时文件路径：" + tempFile.getAbsolutePath());
        // 删除临时文件
        tempFile.delete();
        System.out.println(System.getProperty("java.io.tmpdir"));
        return tempFile;
    }

    public static byte[] getPdfByteByOneImg(String imgPath) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page;
        PDImageXObject pdImage;
        PDPageContentStream contents;
        BufferedImage bufferedImage;
        String fileName;
        float w, h;
        String suffix;
        int index;
        File img = new File(imgPath);
        if (!img.isFile()) {
            throw new FileNotFoundException("请传入文件路径");
        }

        fileName = img.getName();
        index = fileName.lastIndexOf(".");
        //获取文件的后缀
        suffix = fileName.substring(index + 1);
        //如果文件后缀不是图片格式,跳过当前循环
        if (!IMAGE_SUFFIX.contains(suffix)) {
            throw new FileNotFoundException("请传入图片格式");
        }

        bufferedImage = ImageIO.read(img);
        //Retrieving the page
        pdImage = LosslessFactory.createFromImage(doc, bufferedImage);
        w = pdImage.getWidth();
        h = pdImage.getHeight();
        page = new PDPage(new PDRectangle(w, h));
        contents = new PDPageContentStream(doc, page);
        contents.drawImage(pdImage, 0, 0, w, h);
        contents.close();
        doc.addPage(page);
        //保存pdf
//        File file = new File(target + fileName.substring(0, index) + ".pdf");
//        doc.save(file);
        PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();

        if (acroForm != null) {

            PDTextField field = (PDTextField) acroForm.getField("sampleField");
            field.setValue("Text Entry");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        byte[] pdfBytes = baos.toByteArray(); // PDF Bytes
        //关闭pdf
        doc.close();
        return pdfBytes;
    }

    /**
     * 多个图片合成一个pdf,按页去放图片
     *
     * @param imgFolder 多图片的文件夹路径  例如:"D:\\image\\"
     * @param target    合并的图片路径          "D:\\image\\merge.pdf"
     * @throws IOException
     */
    public static void manyImageToOnePdf(String imgFolder, String target) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page;
        PDImageXObject pdImage;
        PDPageContentStream contents;
        BufferedImage bufferedImage;
        String fileName;
        float w, h;
        String suffix;
        File tempFile;
        int index;

        File folder = new File(imgFolder);
        for (int i = 0; i < Objects.requireNonNull(folder.listFiles()).length; i++) {
            tempFile = Objects.requireNonNull(folder.listFiles())[i];

            if (!tempFile.isFile()) {
                continue;
            }

            fileName = tempFile.getName();
            index = fileName.lastIndexOf(".");
            if (index == -1) {
                continue;
            }
            //获取文件的后缀
            suffix = fileName.substring(index + 1);
            //如果文件后缀不是图片格式,跳过当前循环
            if (!IMAGE_SUFFIX.contains(suffix)) {
                continue;
            }

            bufferedImage = ImageIO.read(Objects.requireNonNull(folder.listFiles())[i]);
            //Retrieving the page
            pdImage = LosslessFactory.createFromImage(doc, bufferedImage);
            w = pdImage.getWidth();
            h = pdImage.getHeight();
            page = new PDPage(new PDRectangle(w, h));
            contents = new PDPageContentStream(doc, page);
            contents.drawImage(pdImage, 0, 0, w, h);
            System.out.println("Image inserted");
            contents.close();
            doc.addPage(page);
        }
        //保存pdf
        doc.save(target);
        //关闭pdf
        doc.close();
    }

    public static void main(String[] args) throws IOException {
        /*保存文件*/
        //多图片转一pdf
        String pdf = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-file\\src\\main\\resources\\";
        String pdfOut = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-file\\src\\main\\resources\\manyImageToOnePdf.pdf";
        manyImageToOnePdf(pdf, pdfOut);//success
        //一图片转一pdf
        String img = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-file\\src\\main\\resources\\test1.jpg";
        String pdfIn = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-file\\src\\main\\resources\\mysql.pdf";
        String pdfOutWord = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-file\\src\\main\\resources\\";
//        byte[] bytes = getPdfByteByOneImg(img);
//        OnePdfToOneWord(pdfIn,pdfOutWord);
        /*返回文件*/
    }
}
