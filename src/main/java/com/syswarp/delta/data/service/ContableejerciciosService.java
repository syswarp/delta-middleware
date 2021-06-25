package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contableejercicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Service
public class ContableejerciciosService extends CrudService<Contableejercicios, Integer> {

    private ContableejerciciosRepository repository;

    public ContableejerciciosService(@Autowired ContableejerciciosRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContableejerciciosRepository getRepository() {
        return repository;
    }

}
