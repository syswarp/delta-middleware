package com.syswarp.delta.data.generator;

import com.syswarp.delta.data.entity.*;
import com.vaadin.flow.spring.annotation.SpringComponent;

import com.syswarp.delta.data.service.ContableejerciciosRepository;
import com.syswarp.delta.data.service.ContablecencostoRepository;
import com.syswarp.delta.data.service.ContableasientosRepository;
import com.syswarp.delta.data.service.ContableinfiplanRepository;
import com.syswarp.delta.data.service.ContabletipificacionRepository;
import com.syswarp.delta.data.service.ContableinfimovRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

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

            logger.info("Generando datos iniciales");

            logger.info("... generarando ejercicios contables...");


            LocalDateTime hoy = LocalDateTime.now();
            int aniodesde = hoy.getYear() - 11;
            int aniohasta = hoy.getYear();


            while(aniodesde<=aniohasta){
                Contableejercicios ce = new Contableejercicios();

                //ce.setId(1);
                ce.setEjercicio( aniodesde+ " - Ejercicio Contable Enero "+aniodesde+" a Diciembre "+aniodesde);
                ce.setActivo(false);
                ce.setUsuarioalt("syswarp");
                ce.setUsuarioact("syswarp");
                ce.setFechaalt(hoy);
                ce.setFechaact(hoy);
                LocalDate desde = LocalDate.of(aniodesde, Month.JANUARY, 1);
                LocalDate hasta = LocalDate.of(aniodesde, Month.DECEMBER, 31);
                ce.setFechadesde(desde);
                ce.setFechahasta(hasta);
                if(aniodesde==aniohasta) ce.setActivo(true); //else ce.setActivo(false);


                contableejerciciosRepository.save(ce);
                ++aniodesde;
            }
            logger.info("... generando centros de costos...");


            for (int i = 1; i<100;i++){
                Contablecencosto cc = new Contablecencosto();
                cc.setDescripcion("Centro de Costo Numero: "+i);
                cc.setUsuarioalt("syswarp");
                cc.setUsuarioact("syswarp");
                cc.setFechaalt(hoy);
                cc.setFechaact(hoy);
                contablecencostoRepository.save(cc);
            }

            logger.info("... generando tipos de cuenta contable...");

            Contabletipificacion ct = new Contabletipificacion();
            ct.setTipo("Cuentas De Resultado");
            ct.setEstotal(true);
            ct.setUsuarioalt("syswarp");
            ct.setUsuarioact("syswarp");
            ct.setFechaalt(hoy);
            ct.setFechaact(hoy);
            contabletipificacionRepository.save(ct);

            ct = new Contabletipificacion();
            ct.setTipo("Cuentas De Orden");
            ct.setEstotal(true);
            ct.setUsuarioalt("syswarp");
            ct.setUsuarioact("syswarp");
            ct.setFechaalt(hoy);
            ct.setFechaact(hoy);
            contabletipificacionRepository.save(ct);

            ct = new Contabletipificacion();
            ct.setTipo("Cuentas De Ajuste");
            ct.setEstotal(true);
            ct.setUsuarioalt("syswarp");
            ct.setUsuarioact("syswarp");
            ct.setFechaalt(hoy);
            ct.setFechaact(hoy);
            contabletipificacionRepository.save(ct);


            logger.info("... generando cuentas contables...");


            Contableinfiplan pc = new Contableinfiplan();
            Contablecencosto cc = contablecencostoRepository.getOne(100);
            //pc.setIdejercicio(1);
            pc.setIdcuenta(10000);
            pc.setCuenta("ACTIVO");
            pc.setNivel(1);
            pc.setCc1(cc);
            pc.setCc1(cc);
            pc.setAjustable(false);
            pc.setImputable(false);
            pc.setResultado(false);
            pc.setUsuarioalt("syswarp");
            pc.setUsuarioact("syswarp");
            pc.setFechaalt(hoy);
            pc.setFechaact(hoy);


            contableinfiplanRepository.save(pc);



            logger.info("... generando asientos contables...");
            /*
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
            */
            /*
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
            */


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