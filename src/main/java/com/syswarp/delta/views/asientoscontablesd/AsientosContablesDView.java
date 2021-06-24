package com.syswarp.delta.views.asientoscontablesd;

import java.util.Optional;

import com.syswarp.delta.data.entity.Contableinfimov;
import com.syswarp.delta.data.service.ContableinfimovService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.syswarp.delta.views.MainLayout;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import java.time.Duration;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

@Route(value = "asientos-contables-d/:contableinfimovID?/:action?(edit)", layout = MainLayout.class)
@PageTitle("Asientos Contables-D")
public class AsientosContablesDView extends Div implements BeforeEnterObserver {

    private final String CONTABLEINFIMOV_ID = "contableinfimovID";
    private final String CONTABLEINFIMOV_EDIT_ROUTE_TEMPLATE = "asientos-contables-d/%d/edit";

    private Grid<Contableinfimov> grid = new Grid<>(Contableinfimov.class, false);

    private TextField idasientodet;
    private TextField idasiento;
    private TextField renglon;
    private TextField idcuenta;
    private TextField tipomov;
    private TextField importe;
    private TextField detalle;
    private TextField asiento_nuevo;
    private TextField idcencost1;
    private TextField idcentcost2;
    private TextField usuarioalt;
    private TextField usuarioact;
    private DateTimePicker fechaalt;
    private DateTimePicker fechaact;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contableinfimov> binder;

    private Contableinfimov contableinfimov;

    private ContableinfimovService contableinfimovService;

    public AsientosContablesDView(@Autowired ContableinfimovService contableinfimovService) {
        addClassNames("asientos-contables-d-view", "flex", "flex-col", "h-full");
        this.contableinfimovService = contableinfimovService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idasientodet").setAutoWidth(true);
        grid.addColumn("idasiento").setAutoWidth(true);
        grid.addColumn("renglon").setAutoWidth(true);
        grid.addColumn("idcuenta").setAutoWidth(true);
        grid.addColumn("tipomov").setAutoWidth(true);
        grid.addColumn("importe").setAutoWidth(true);
        grid.addColumn("detalle").setAutoWidth(true);
        grid.addColumn("asiento_nuevo").setAutoWidth(true);
        grid.addColumn("idcencost1").setAutoWidth(true);
        grid.addColumn("idcentcost2").setAutoWidth(true);
        grid.addColumn("usuarioalt").setAutoWidth(true);
        grid.addColumn("usuarioact").setAutoWidth(true);
        grid.addColumn("fechaalt").setAutoWidth(true);
        grid.addColumn("fechaact").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(contableinfimovService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CONTABLEINFIMOV_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AsientosContablesDView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contableinfimov.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idasientodet).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idasientodet");
        binder.forField(idasiento).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idasiento");
        binder.forField(renglon).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("renglon");
        binder.forField(idcuenta).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idcuenta");
        binder.forField(tipomov).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("tipomov");
        binder.forField(importe).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("importe");
        binder.forField(asiento_nuevo).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("asiento_nuevo");
        binder.forField(idcencost1).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idcencost1");
        binder.forField(idcentcost2).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idcentcost2");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contableinfimov == null) {
                    this.contableinfimov = new Contableinfimov();
                }
                binder.writeBean(this.contableinfimov);

                contableinfimovService.update(this.contableinfimov);
                clearForm();
                refreshGrid();
                Notification.show("Contableinfimov details stored.");
                UI.getCurrent().navigate(AsientosContablesDView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contableinfimov details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> contableinfimovId = event.getRouteParameters().getInteger(CONTABLEINFIMOV_ID);
        if (contableinfimovId.isPresent()) {
            Optional<Contableinfimov> contableinfimovFromBackend = contableinfimovService.get(contableinfimovId.get());
            if (contableinfimovFromBackend.isPresent()) {
                populateForm(contableinfimovFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested contableinfimov was not found, ID = %d", contableinfimovId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(AsientosContablesDView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        idasientodet = new TextField("Idasientodet");
        idasiento = new TextField("Idasiento");
        renglon = new TextField("Renglon");
        idcuenta = new TextField("Idcuenta");
        tipomov = new TextField("Tipomov");
        importe = new TextField("Importe");
        detalle = new TextField("Detalle");
        asiento_nuevo = new TextField("Asiento_nuevo");
        idcencost1 = new TextField("Idcencost1");
        idcentcost2 = new TextField("Idcentcost2");
        usuarioalt = new TextField("Usuarioalt");
        usuarioact = new TextField("Usuarioact");
        fechaalt = new DateTimePicker("Fechaalt");
        fechaalt.setStep(Duration.ofSeconds(1));
        fechaact = new DateTimePicker("Fechaact");
        fechaact.setStep(Duration.ofSeconds(1));
        Component[] fields = new Component[]{idasientodet, idasiento, renglon, idcuenta, tipomov, importe, detalle,
                asiento_nuevo, idcencost1, idcentcost2, usuarioalt, usuarioact, fechaalt, fechaact};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Contableinfimov value) {
        this.contableinfimov = value;
        binder.readBean(this.contableinfimov);

    }
}
