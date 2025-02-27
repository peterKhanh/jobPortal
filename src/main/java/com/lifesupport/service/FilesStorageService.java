package com.lifesupport.service;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	 public void saveFile(String uploadDir,MultipartFile file, String filename);
	 public void deleteFile(String uploadDir, String filename);
}
