package com.luigivismara.modeldomain.utils;

import com.luigivismara.modeldomain.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service
public class PageableTools {

    public HttpResponse<PaginationDto> pagination(Object repositoryValueObject) {
        return pageableRepository(repositoryValueObject);
    }

    public static HttpResponse<PaginationDto> pageableRepository(Object repositoryValueObject) {
        try {
            final var pageTuts = (Page<?>) repositoryValueObject;

            final var paginationDto = new PaginationDto(pageTuts.getContent(),
                    pageTuts.getNumber(),
                    pageTuts.getTotalPages() - 1,
                    pageTuts.getTotalElements());

            return new HttpResponse<>(HttpStatus.OK, paginationDto);

        } catch (Exception e) {
            return new HttpResponse<>(HttpStatus.OK);
        }
    }

    @Data
    @AllArgsConstructor
    public static class PaginationDto implements Serializable {
        private Object value;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalItems;
    }
}
