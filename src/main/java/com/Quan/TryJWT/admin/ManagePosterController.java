package com.Quan.TryJWT.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quan.TryJWT.Exception.AppUtils;
import com.Quan.TryJWT.model.Poster;
import com.Quan.TryJWT.service.PosterService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/poster")
public class ManagePosterController {
	
	@Autowired
	private PosterService posterService;
	
	@PostMapping
	public ResponseEntity<?> postPoster(@Valid @RequestBody Poster poster) {
		poster = posterService.savePoster(poster);
		return AppUtils.returnJS(HttpStatus.OK, "Add poster successfully!", poster);
	}
  
	@PutMapping(value = "")
	public ResponseEntity<?> putPoster(@Valid @RequestBody Poster poster) {
		poster = posterService.savePoster(poster);
		return AppUtils.returnJS(HttpStatus.OK, "Update poster successfully!", poster);
	}
  
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
		Poster poster = posterService.getPosterById(id);
		if(poster == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Poster not found!", null);

		posterService.deletePoster(id);

		return AppUtils.returnJS(HttpStatus.OK, "Remove poster successfully!", null);
	
	}
}
