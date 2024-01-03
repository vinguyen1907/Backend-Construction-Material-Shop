package com.example.cmsbe.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationDTO<T> {
    long pageCount;
    long total;
    Integer currentPage;
    Integer pageSize;
    List<T> results;

    public PaginationDTO(Page<T> page) {
        this.pageCount = page.getTotalPages();
        this.total = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.results = page.getContent();
    }
}
