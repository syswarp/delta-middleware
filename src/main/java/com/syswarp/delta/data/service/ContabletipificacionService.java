package com.syswarp.delta.data.service;

import com.syswarp.delta.data.entity.Contabletipificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ContabletipificacionService extends CrudService<Contabletipificacion, Integer> {

    private ContabletipificacionRepository repository;

    public ContabletipificacionService(@Autowired ContabletipificacionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContabletipificacionRepository getRepository() {
        return repository;
    }

}
