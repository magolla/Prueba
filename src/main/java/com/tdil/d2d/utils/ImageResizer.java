package com.tdil.d2d.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

public class ImageResizer {

    public static byte[] resize(MultipartFile image) {
        try {
        	InputStream stream = new ByteArrayInputStream(image.getBytes());
        	BufferedImage originalImage = ImageIO.read(stream);

        	originalImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 1024, 1024);
            //To save with original ratio uncomment next line and comment the above.
            //originalImage= Scalr.resize(originalImage, 153, 128);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            if(image.getContentType().equals("image/gif")) {
            	ImageIO.write(originalImage, "gif", baos);
            } else if(image.getContentType().equals("image/png")) {
            	ImageIO.write(originalImage, "png", baos);
            } else {
            	ImageIO.write(originalImage, "jpg", baos);
            }
            
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return null;
        }


    }
}