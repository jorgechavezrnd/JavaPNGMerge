package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
public class PngFileWriter2 {
 
    public BufferedImage append(List<BufferedImage> inputFileBufList, boolean isX) throws Exception {
        if (inputFileBufList == null || inputFileBufList.size() == 0) {
            return null;
        }
 
        try {
            boolean isFirstPng = true;
            BufferedImage outputImg = null;
            int outputImgW = 0;
            int outputImgH = 0;
            for (BufferedImage pngFileBuf : inputFileBufList) {
                if (isFirstPng) {
                    isFirstPng = false;
                    outputImg = pngFileBuf;
                    outputImgW = outputImg.getWidth();
                    outputImgH = outputImg.getHeight();
                } else {
                    BufferedImage appendImg = pngFileBuf;
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
                    
                    g2d.dispose();
                    outputImg = imageNew;
                }
            }

            return outputImg;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
 
    public BufferedImage addDate(BufferedImage inputBuf) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        int bufWidth = inputBuf.getWidth();
        int bufHeight = inputBuf.getHeight();

        Graphics2D g2d = inputBuf.createGraphics();

        // set font for the text
        g2d.setFont(new Font("Arial", Font.BOLD, 35));
        g2d.setColor(Color.BLACK);
        String dateText = sdfDate.format(now);

        // add the text
        g2d.drawString(dateText, (bufWidth*2)/100, (bufHeight*97)/100);
        g2d.dispose();

        return inputBuf;
    }
}