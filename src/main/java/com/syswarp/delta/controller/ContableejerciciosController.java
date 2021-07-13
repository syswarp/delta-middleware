package com.syswarp.delta.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.syswarp.delta.data.entity.Contableejercicios;
import com.syswarp.delta.data.service.ContableejerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ContableejerciciosController {
    @Autowired
    ContableejerciciosRepository cer;

    // traer los ejercicios contables
    @GetMapping("/contableejercicios")
    public ResponseEntity<List<Contableejercicios>> getAllContableEjercicios(@RequestParam(required = false) String ejercicio) {
        try {
            List<Contableejercicios> contableejercicios = new ArrayList<Contableejercicios>();

            if (ejercicio == null)
                cer.findAll().forEach(contableejercicios::add);
            else
                // falta crear un metodo de filtro que aca no aplica
                //cer.findAllById(new Integer).forEach(tutorials::add);

            if (contableejercicios.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contableejercicios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get PK
    @GetMapping("/contableejercicios/{id}")
    public ResponseEntity<Contableejercicios> getContableEjerciciosById(@PathVariable("id") Integer id) {
       
        Optional<Contableejercicios> contableejerciciosData = cer.findById(id);

        if (contableejerciciosData.isPresent()) {
            return new ResponseEntity<>(contableejerciciosData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
