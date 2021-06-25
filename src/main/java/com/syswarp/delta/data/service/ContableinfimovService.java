package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contableinfimov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Service
public class ContableinfimovService extends CrudService<Contableinfimov, Integer> {

    private ContableinfimovRepository repository;

    public ContableinfimovService(@Autowired ContableinfimovRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContableinfimovRepository getRepository() {
        return repository;
    }

}
