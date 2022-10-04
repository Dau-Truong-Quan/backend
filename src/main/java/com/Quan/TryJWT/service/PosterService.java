package com.Quan.TryJWT.service;

import java.util.List;

import com.Quan.TryJWT.dto.PosterDTO;
import com.Quan.TryJWT.model.Poster;

public interface PosterService {
//	public List<PosterDTO> getListPosterDTO();
	public List<Poster> getListPoster();
	public Poster getPosterById(long id);
	public Poster savePoster(Poster poster);
	public void deletePoster(long id);
}
