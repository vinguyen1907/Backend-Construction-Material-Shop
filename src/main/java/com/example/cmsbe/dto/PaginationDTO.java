package com.example.cmsbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
