package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contableinfiplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Service
public class ContableinfiplanService extends CrudService<Contableinfiplan, Integer> {

    private ContableinfiplanRepository repository;

    public ContableinfiplanService(@Autowired ContableinfiplanRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContableinfiplanRepository getRepository() {
        return repository;
    }

}
