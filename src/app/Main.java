package app;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Main {
    public static void main(String[] args) {

        try {

            withDate();
            withSignature();
            withSignatureAndDate();

        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void withDate() throws Exception {
        PngFileWriter2 writer = new PngFileWriter2();

        BufferedImage stampBuf = ImageIO.read(new File("assets/stamp.png"));

        List<BufferedImage> inputFileBufList = new ArrayList<BufferedImage>();

        inputFileBufList.add(stampBuf);

        BufferedImage outputBuf = writer.append(inputFileBufList, false);
        outputBuf = writer.addDate(outputBuf);

        File outputFile = new File("withDate.png");
        try {
                ImageIO.write(outputBuf, "png", outputFile);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void withSignature() throws Exception {
        PngFileWriter2 writer = new PngFileWriter2();

        BufferedImage stampBuf = ImageIO.read(new File("assets/stamp.png"));
        BufferedImage signBuf = ImageIO.read(new File("assets/sign.png"));

        List<BufferedImage> inputFileBufList = new ArrayList<BufferedImage>();

        inputFileBufList.add(stampBuf);
        inputFileBufList.add(signBuf);

        BufferedImage outputBuf = writer.append(inputFileBufList, false);

        File outputFile = new File("withSign.png");
        try {
                ImageIO.write(outputBuf, "png", outputFile);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void withSignatureAndDate() throws Exception {
        PngFileWriter2 writer = new PngFileWriter2();

        BufferedImage stampBuf = ImageIO.read(new File("assets/stamp.png"));
        BufferedImage signBuf = ImageIO.read(new File("assets/sign.png"));

        List<BufferedImage> inputFileBufList = new ArrayList<BufferedImage>();

        inputFileBufList.add(stampBuf);
        inputFileBufList.add(signBuf);

        BufferedImage outputBuf = writer.append(inputFileBufList, false);
        outputBuf = writer.addDate(outputBuf);

        File outputFile = new File("withSignAndDate.png");
        try {
                ImageIO.write(outputBuf, "png", outputFile);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void addTextToImage2() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        File origFile = new File("assets/stamp.png");
        ImageIcon icon = new ImageIcon(origFile.getPath());

        // create BufferedImage object of same width and height as of original image
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // create graphics object and add original image to it
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(icon.getImage(), 0, 0, null);

        // set font for the watermark text
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.setColor(Color.BLACK);
        String watermark =  sdfDate.format(now);

        // add the watermark text
        // graphics.drawString(watermark, (icon.getIconWidth()*50)/100, (icon.getIconHeight()*90)/100);
        graphics.drawString(watermark, (icon.getIconWidth()*2)/100, (icon.getIconHeight()*97)/100);
        graphics.dispose();

        File newFile = new File("test2.png");
        try {
                ImageIO.write(bufferedImage, "png", newFile);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void addTextToImage() throws Exception {
        BufferedImage image = ImageIO.read(new File("assets/stamp.png"));

        Graphics graphics = image.getGraphics();
        graphics.setFont(graphics.getFont().deriveFont(30f));
        graphics.drawString("Hello world", 100, 100);
        graphics.dispose();

        ImageIO.write(image, "png", new File("test.png"));
    }

    public static void joinImages2() {
        PngFileWriter writer = new PngFileWriter();
        List<String> inputFileNameList = new ArrayList<String>();
        inputFileNameList.add("assets/stamp.png");
        inputFileNameList.add("assets/sign.png");
        writer.append(inputFileNameList, "joined2.png", false);
    }

    public static void joinImages() throws Exception {
        BufferedImage img1 = ImageIO.read(new File("assets/stamp.png"));
        BufferedImage img2 = ImageIO.read(new File("assets/sign.png"));
        BufferedImage joinedImg = joinBufferedImage(img1, img2);
        ImageIO.write(joinedImg, "png", new File("joined.png"));
    }

    public static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {
        int offset = 2;
        int width = img1.getWidth() + img2.getWidth() + offset;
        int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth() + offset, 0);
        g2.dispose();

        return newImage;
    }

}
