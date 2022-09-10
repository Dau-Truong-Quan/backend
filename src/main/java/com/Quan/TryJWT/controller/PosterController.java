package com.Quan.TryJWT.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.model.Poster;
import com.Quan.TryJWT.serviceImpl.PosterServiceImpl;

@RestController
@RequestMapping("/api/posters")
public class PosterController {

	@Autowired
	private PosterServiceImpl posterService;
	
	@GetMapping("")
	public ResponseEntity<List<Poster>> getListPoster() {
		List<Poster> list = posterService.getListPoster();
		if (list == null) {
			list = new ArrayList<Poster>();
		}
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(value = "/{imageName}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) throws IOException {

		try {
			ClassPathResource imgFile = new ClassPathResource("images/posters/" + imageName);
			return ResponseEntity
	                .ok()
	                .contentType(MediaType.IMAGE_JPEG)
	                .body(new InputStreamResource(imgFile.getInputStream()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Image not found!");
		}       
    }
}
