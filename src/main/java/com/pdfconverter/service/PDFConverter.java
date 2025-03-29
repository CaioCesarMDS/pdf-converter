package com.pdfconverter.service;

import com.pdfconverter.exceptions.ConversionFailedException;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.file.*;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.rendering.*;

public class PDFConverter {
    public static void toImage(File file, Path directory, String format) throws ConversionFailedException {

        if (!Files.exists(directory)) {
            throw new ConversionFailedException("Output directory does not exist");
        }

        try {
            PDDocument document = Loader.loadPDF(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 1200, ImageType.RGB);

                String outputPath = directory.resolve(
                        String.format("page_%03d.%s", page + 1, format)
                ).toString();

                if (!ImageIO.write(bim, format, new File(outputPath))) {
                    throw new ConversionFailedException(
                            "Image format not available: " + format);
                }
            }
        } catch (IOException e) {
            throw new ConversionFailedException("Failed to convert PDF to image: " + e.getMessage());
        }
    }

    public static void toDocx() {

    }
}
