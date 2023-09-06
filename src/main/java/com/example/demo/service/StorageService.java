package com.example.demo.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	public String storeFile(MultipartFile file);
	public Stream<Path> loadAll();
	public byte[] readFileContent(String fileName);
	public void deleteAllFiles();
}
