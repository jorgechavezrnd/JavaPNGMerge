package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import javax.imageio.ImageIO;
 
public class PngFileWriter2 {
 
    public void append(List<String> inputFileNameList, String outputFileName, boolean isX, boolean withDate) {
        if (inputFileNameList == null || inputFileNameList.size() == 0) {
            return;
        }
 
        try {
            boolean isFirstPng = true;
            BufferedImage outputImg = null;
            int outputImgW = 0;
            int outputImgH = 0;
            for (String pngFileName : inputFileNameList) {
                if (isFirstPng) {
                    isFirstPng = false;
                    outputImg = ImageIO.read(new File(pngFileName));
                    outputImgW = outputImg.getWidth();
                    outputImgH = outputImg.getHeight();
                } else {
                    BufferedImage appendImg = ImageIO.read(new File(pngFileName));
                    int appendImgW = appendImg.getWidth();
                    int appendImgH = appendImg.getHeight();
 
                    if (isX) {
                        outputImgW = outputImgW + appendImgW;
                        outputImgH = outputImgH > appendImgH ? outputImgH : appendImgH;
                    } else {
                        outputImgW = outputImgW > appendImgW ? outputImgW : appendImgW;
                        outputImgH = outputImgH + appendImgH;
                    }
 
                    // create basic image
                    Graphics2D g2d = outputImg.createGraphics();
                    BufferedImage imageNew = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, outputImgH,
                            Transparency.TRANSLUCENT);
                    g2d.dispose();
                    g2d = imageNew.createGraphics();
                    
                    int oldImgW = outputImg.getWidth();
                    int oldImgH = outputImg.getHeight();
                    g2d.drawImage(outputImg, 0, 0, oldImgW, oldImgH, null);
                    if (isX) {
                        g2d.drawImage(appendImg, oldImgW, 0, appendImgW, appendImgH, null);
                    } else {
                        g2d.drawImage(appendImg, 0, oldImgH, appendImgW, appendImgH, null);
                    }

                    if (withDate) {
                        addDate(g2d, outputImgW, outputImgH);
                    }
                    
                    g2d.dispose();
                    outputImg = imageNew;
                }
            }
            writeImageLocal(outputFileName, outputImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void writeImageLocal(String fileName, BufferedImage image) {
        if (fileName != null && image != null) {
            try {
                File file = new File(fileName);
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addDate(Graphics2D g2d, int outputImgW, int outputImgH) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        // set font for the text
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.setColor(Color.BLACK);
        String dateText = sdfDate.format(now);

        // add the text
        g2d.drawString(dateText, (outputImgW*2)/100, (outputImgH*97)/100);
    }
}