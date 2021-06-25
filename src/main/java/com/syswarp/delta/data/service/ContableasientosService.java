package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contableasientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDate;

@Service
public class ContableasientosService extends CrudService<Contableasientos, Integer> {

    private ContableasientosRepository repository;

    public ContableasientosService(@Autowired ContableasientosRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContableasientosRepository getRepository() {
        return repository;
    }

}
