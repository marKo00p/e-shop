package com.shopy.trainshop.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "src/main/resources/static/images/";
    @Transactional
    public boolean  uploadImage(MultipartFile image){

        boolean isUpload = false;
//        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        try {
//            byte[] imageBytes = image.getBytes();
//            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            Files.copy(image.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator, image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile image){
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + "\\" + image.getOriginalFilename());
            isExisted = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }
}
