package com.alkemy.ong.util;

import com.alkemy.ong.dto.CategoryRequestDTO;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Data
public class CategoryResponse {

    private List<CategoryRequestDTO> content;
    private int numPage;
    private int sizePage;
    private long totalElements;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private Pageable nextPage;
    private Pageable previousPage;
}