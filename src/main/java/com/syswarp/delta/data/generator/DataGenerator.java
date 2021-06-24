package com.syswarp.delta.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.syswarp.delta.data.service.ContableejerciciosRepository;
import com.syswarp.delta.data.entity.Contableejercicios;
import com.syswarp.delta.data.service.ContablecencostoRepository;
import com.syswarp.delta.data.entity.Contablecencosto;
import com.syswarp.delta.data.service.ContableasientosRepository;
import com.syswarp.delta.data.entity.Contableasientos;
import com.syswarp.delta.data.service.ContableinfiplanRepository;
import com.syswarp.delta.data.entity.Contableinfiplan;
import com.syswarp.delta.data.service.ContabletipificacionRepository;
import com.syswarp.delta.data.entity.Contabletipificacion;
import com.syswarp.delta.data.service.ContableinfimovRepository;
import com.syswarp.delta.data.entity.Contableinfimov;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ContableejerciciosRepository contableejerciciosRepository,
            ContablecencostoRepository contablecencostoRepository,
            ContableasientosRepository contableasientosRepository,
            ContableinfiplanRepository contableinfiplanRepository,
            ContabletipificacionRepository contabletipificacionRepository,
            ContableinfimovRepository contableinfimovRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (contableejerciciosRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Contableejercicios entities...");
            ExampleDataGenerator<Contableejercicios> contableejerciciosRepositoryGenerator = new ExampleDataGenerator<>(
                    Contableejercicios.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setId, DataType.ID);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setId, DataType.NUMBER_UP_TO_10);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setEjercicio, DataType.WORD);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setFechadesde, DataType.DATE_OF_BIRTH);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setFechahasta, DataType.DATE_OF_BIRTH);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setActivo, DataType.BOOLEAN_50_50);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setUsuarioalt, DataType.WORD);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setUsuarioact, DataType.WORD);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setFechaalt,
                    DataType.DATETIME_LAST_10_YEARS);
            contableejerciciosRepositoryGenerator.setData(Contableejercicios::setFechaact,
                    DataType.DATETIME_LAST_10_YEARS);
            contableejerciciosRepository.saveAll(contableejerciciosRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Contablecencosto entities...");
            ExampleDataGenerator<Contablecencosto> contablecencostoRepositoryGenerator = new ExampleDataGenerator<>(
                    Contablecencosto.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setId, DataType.ID);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setIdcencosto, DataType.NUMBER_UP_TO_100);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setDescripcion, DataType.WORD);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setUsuarioalt, DataType.WORD);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setUsuarioact, DataType.WORD);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setFechaalt, DataType.DATETIME_LAST_10_YEARS);
            contablecencostoRepositoryGenerator.setData(Contablecencosto::setFechaact, DataType.DATETIME_LAST_10_YEARS);
            contablecencostoRepository.saveAll(contablecencostoRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Contableasientos entities...");
            ExampleDataGenerator<Contableasientos> contableasientosRepositoryGenerator = new ExampleDataGenerator<>(
                    Contableasientos.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contableasientosRepositoryGenerator.setData(Contableasientos::setId, DataType.ID);
            contableasientosRepositoryGenerator.setData(Contableasientos::setIdasiento, DataType.NUMBER_UP_TO_100);
            contableasientosRepositoryGenerator.setData(Contableasientos::setIdejercicio, DataType.NUMBER_UP_TO_100);
            contableasientosRepositoryGenerator.setData(Contableasientos::setFecha, DataType.DATE_OF_BIRTH);
            contableasientosRepositoryGenerator.setData(Contableasientos::setLeyenda, DataType.WORD);
            contableasientosRepositoryGenerator.setData(Contableasientos::setNroasiento, DataType.NUMBER_UP_TO_100);
            contableasientosRepositoryGenerator.setData(Contableasientos::setIdtipoasiento, DataType.NUMBER_UP_TO_100);
            contableasientosRepositoryGenerator.setData(Contableasientos::setAsiento_nuevo, DataType.NUMBER_UP_TO_100);
            contableasientosRepositoryGenerator.setData(Contableasientos::setSistema, DataType.WORD);
            contableasientosRepositoryGenerator.setData(Contableasientos::setUsuarioalt, DataType.WORD);
            contableasientosRepositoryGenerator.setData(Contableasientos::setUsuarioact, DataType.WORD);
            contableasientosRepositoryGenerator.setData(Contableasientos::setFechaalt, DataType.DATE_OF_BIRTH);
            contableasientosRepositoryGenerator.setData(Contableasientos::setFechaact, DataType.DATE_OF_BIRTH);
            contableasientosRepository.saveAll(contableasientosRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Contableinfiplan entities...");
            ExampleDataGenerator<Contableinfiplan> contableinfiplanRepositoryGenerator = new ExampleDataGenerator<>(
                    Contableinfiplan.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setId, DataType.ID);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setIdejercicio, DataType.NUMBER_UP_TO_100);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setIdcuenta, DataType.NUMBER_UP_TO_100);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setCuenta, DataType.WORD);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setImputable, DataType.BOOLEAN_50_50);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setAjustable, DataType.BOOLEAN_50_50);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setResultado, DataType.BOOLEAN_50_50);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setNivel, DataType.NUMBER_UP_TO_100);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setIdcentrocosto1, DataType.NUMBER_UP_TO_100);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setIdcentrocosto2, DataType.NUMBER_UP_TO_100);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setUsuarioalt, DataType.WORD);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setUsuarioact, DataType.WORD);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setFechaalt, DataType.DATETIME_LAST_10_YEARS);
            contableinfiplanRepositoryGenerator.setData(Contableinfiplan::setFechaact, DataType.DATETIME_LAST_10_YEARS);
            contableinfiplanRepository.saveAll(contableinfiplanRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Contabletipificacion entities...");
            ExampleDataGenerator<Contabletipificacion> contabletipificacionRepositoryGenerator = new ExampleDataGenerator<>(
                    Contabletipificacion.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contabletipificacionRepositoryGenerator.setData(Contabletipificacion::setId, DataType.ID);
            contabletipificacionRepositoryGenerator.setData(Contabletipificacion::setIdtipocuenta,
                    DataType.NUMBER_UP_TO_100);
            contabletipificacionRepositoryGenerator.setData(Contabletipificacion::setTipo, DataType.WORD);
            contabletipificacionRepositoryGenerator.setData(Contabletipificacion::setMostrar, DataType.BOOLEAN_50_50);
            contabletipificacionRepositoryGenerator.setData(Contabletipificacion::setEstotal, DataType.BOOLEAN_50_50);
            contabletipificacionRepository.saveAll(contabletipificacionRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Contableinfimov entities...");
            ExampleDataGenerator<Contableinfimov> contableinfimovRepositoryGenerator = new ExampleDataGenerator<>(
                    Contableinfimov.class, LocalDateTime.of(2021, 6, 24, 0, 0, 0));
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setId, DataType.ID);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setIdasientodet, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setIdasiento, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setRenglon, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setIdcuenta, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setTipomov, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setImporte, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setDetalle, DataType.WORD);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setAsiento_nuevo, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setIdcencost1, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setIdcentcost2, DataType.NUMBER_UP_TO_100);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setUsuarioalt, DataType.WORD);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setUsuarioact, DataType.WORD);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setFechaalt, DataType.DATETIME_LAST_10_YEARS);
            contableinfimovRepositoryGenerator.setData(Contableinfimov::setFechaact, DataType.DATETIME_LAST_10_YEARS);
            contableinfimovRepository.saveAll(contableinfimovRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}