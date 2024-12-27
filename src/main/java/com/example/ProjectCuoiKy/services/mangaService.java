package com.example.ProjectCuoiKy.services;

import com.example.ProjectCuoiKy.models.manga;
import com.example.ProjectCuoiKy.repository.mangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class mangaService {

    @Autowired
    private mangaRepository mangaRepository;

    public manga addManga(manga manga) {
        return mangaRepository.save(manga);
    }

    public List<manga> getAllManga() {
        return mangaRepository.findAll();
    }

    public manga findMangabyid(long id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga not found"));
    }

    public manga updateManga(manga manga) {
        return mangaRepository.save(manga);
    }

    public void deleteManga(long id) {
        if (!mangaRepository.existsById(id)) {
            throw new RuntimeException("Manga not found");
        }
        mangaRepository.deleteById(id);
    }
    public Page<manga> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mangaRepository.findAll(pageable);
    }
}
