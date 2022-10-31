package com.Quan.TryJWT.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Quan.TryJWT.model.Product;
import com.Quan.TryJWT.model.User;
import com.Quan.TryJWT.repository.ProductRepository;
import com.Quan.TryJWT.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/image")
public class FileUploadController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	public String saveFile(String fileName, MultipartFile file, String folder) {
		Path uploadDirectory = Paths.get("src\\main\\resources\\images\\" + folder);
		fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")) + StringUtils.delete(fileName, " ");
		
		try(InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadDirectory.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(fileName);
		return fileName;
	}

	public String saveFile2(String fileName, MultipartFile file, String folder) {
		Path uploadDirectory = Paths.get("src\\main\\resources\\static\\images\\" + folder);
		fileName = StringUtils.delete(fileName, " ");
		
		try(InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadDirectory.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(fileName);
		return fileName;
}
	
	@PostMapping("/poster")
	public ResponseEntity<?> postPosterImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "posters");
		
		return ResponseEntity.ok(newFileName);
	}

	@PostMapping("/product")
	public ResponseEntity<?> postProductImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile2(fileName, file, "products");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/category")
	public ResponseEntity<?> postCategoryImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "categories");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> postUserImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile2(fileName, file, "users");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/user2/{id}")
	public ResponseEntity<?> postUserImage2(@RequestParam("file") MultipartFile file,@PathVariable("id") long id) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile2(fileName, file, "users");
		
		User user =userRepository.findById(id).get();
		user.setImage(newFileName);
		userRepository.save(user);
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/product2/{id}")
	public ResponseEntity<?> postProductImage2(@RequestParam("file") MultipartFile file,@PathVariable("id") long id) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile2(fileName, file, "products");
		
		Product product =productRepository.findById(id).get();
		product.setImage(newFileName);
		productRepository.save(product);
		return ResponseEntity.ok(newFileName);
	}
	
}
