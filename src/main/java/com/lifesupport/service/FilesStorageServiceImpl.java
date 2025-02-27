package com.lifesupport.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FilesStorageServiceImpl implements FilesStorageService{

	

	@Override
	public void saveFile(String uploadDir, MultipartFile file, String filename) {
		try {
			//String uploadDir = "public/images/galary/";
		//	String uploadDir = new ClassPathResource("public/images/galary/") + File.separator;
			Path uploadPath = Paths.get(uploadDir);
			System.out.print(uploadDir + " == " + filename);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + filename), StandardCopyOption.REPLACE_EXISTING);
			}	
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
	}

	@Override
	public void deleteFile(String uploadDir, String filename) {
		// TODO Auto-generated method stub
		Path imagePath = Paths.get(uploadDir + filename);
		try {
			Files.delete(imagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
