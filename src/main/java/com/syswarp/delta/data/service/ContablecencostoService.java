package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contablecencosto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Service
public class ContablecencostoService extends CrudService<Contablecencosto, Integer> {

    private ContablecencostoRepository repository;

    public ContablecencostoService(@Autowired ContablecencostoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContablecencostoRepository getRepository() {
        return repository;
    }

}
