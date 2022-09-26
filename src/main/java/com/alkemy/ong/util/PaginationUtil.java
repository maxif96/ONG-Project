package com.alkemy.ong.util;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public abstract class PaginationUtil<T, ID, R extends JpaRepository<T, ID>> {

    protected static final Integer PAGE_SIZE = 10;
    protected static final String PAGE_PATH = "/get-all?page=";
    @Autowired
    protected R repository;

    protected Page<T> getPage(Integer page) throws EntityNotFoundException {
        return repository.findAll(PageRequest.of(page - 1, PAGE_SIZE));
    }

    protected String urlGetPrevious(Integer page) {
        if (page > 1) return PAGE_PATH + (page - 1);
        return null;
    }

    protected String urlGetNext(Page<T> pageT, Integer page) {
        if (pageT.hasNext()) return PAGE_PATH + (page + 1);
        return null;
    }


}
