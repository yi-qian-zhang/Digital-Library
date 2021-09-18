package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import domain.Paper;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * QR code generator and PDF tool class.
 */
public class QRUtils
{
    private static final String CHARSET = "utf-8", FORMAT = "png";
    private static final int WIDTH = 128, HEIGHT = 128, MARGIN = 0, FONTSIZE = 12;

    /**
     * Generate a QR code which contains information of the paper,
     * then add a new page with the QR code and texts there,
     * modify PDF attributes according to the paper information finally.
     *
     * @param paper The paper object.
     * @param pdf   The PDF file object to be modified. (Must exist)
     * @param img   The image file object to be generated. (Don't need to exist)
     * @throws WriterException If error occurred when generating QR code.
     * @throws IOException     If the file not found.
     */
    public static void AddInfoToPDF(Paper paper, File pdf, File img) throws WriterException, IOException
    {
        // Assemble the content that the QR code will store
        final String content = paper.getAuthor() + " (" + paper.getPublish_date().replaceAll("-0?",
                ".") + "). " + paper.getTitle() + ".";

        // Set QR code configuration
        HashMap<EncodeHintType, Object> config = new HashMap<>();
        config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        config.put(EncodeHintType.CHARACTER_SET, CHARSET);
        config.put(EncodeHintType.MARGIN, MARGIN);

        // Generate QR code
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, config);
        OutputStream imgStream = new FileOutputStream(img);
        MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, imgStream);

        // Set the text that pdf will show
        final String header = "This paper has been published on " + paper.getPublish_date() + ".";
        final String text = "Feel free to read this paper, it has passed through a full review process.";

        // Load the pdf file and create a new page
        PDDocument document = PDDocument.load(pdf);
        PDPage page = new PDPage();

        PDPageContentStream stream = new PDPageContentStream(document, page);

        // Add QR code to the new page
        PDImageXObject image = PDImageXObject.createFromFileByContent(img, document);
        stream.drawImage(image, FONTSIZE, page.getMediaBox().getHeight() - HEIGHT - FONTSIZE);

        // Add the publish information to the new page
        stream.beginText();
        stream.setFont(PDType1Font.HELVETICA, FONTSIZE);
        stream.newLineAtOffset(WIDTH + 2 * FONTSIZE,
                page.getMediaBox().getHeight() - 0.75F * FONTSIZE - (float) HEIGHT / 2);
        stream.showText(header);
        stream.endText();

        // Add the text to the new page
        stream.beginText();
        stream.setFont(PDType1Font.HELVETICA, FONTSIZE);
        stream.newLineAtOffset(WIDTH + 2 * FONTSIZE,
                page.getMediaBox().getHeight() - 2.25F * FONTSIZE - (float) HEIGHT / 2);
        stream.showText(text);
        stream.endText();

        stream.close();

        // Add the new page as the fist page of the pdf file
        PDPageTree pageTree = document.getDocumentCatalog().getPages();
        pageTree.insertBefore(page, document.getPage(0));

        // Set information of the pdf file
        PDDocumentInformation info = document.getDocumentInformation();
        info.setTitle(paper.getTitle());
        info.setAuthor(paper.getAuthor());
        info.setKeywords(paper.getKeyword());
        info.setSubject(paper.getMajor());
        info.setCreator(paper.getUniversity());
        info.setProducer("Digital Library for Computer Science Research");

        // Save changes
        document.save(pdf);
        document.close();
    }
}
