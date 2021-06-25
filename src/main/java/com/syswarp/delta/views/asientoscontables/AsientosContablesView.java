package com.syswarp.delta.views.asientoscontables;

import java.util.Optional;

import com.syswarp.delta.data.entity.Contableasientos;
import com.syswarp.delta.data.service.ContableasientosService;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

@Route(value = "asientos-contables/:contableasientosID?/:action?(edit)", layout = MainLayout.class)
@PageTitle("Asientos Contables")
public class AsientosContablesView extends Div implements BeforeEnterObserver {

    private final String CONTABLEASIENTOS_ID = "contableasientosID";
    private final String CONTABLEASIENTOS_EDIT_ROUTE_TEMPLATE = "asientos-contables/%d/edit";

    private Grid<Contableasientos> grid = new Grid<>(Contableasientos.class, false);

    private TextField idasiento;
    private TextField idejercicio;
    private DatePicker fecha;
    private TextField leyenda;
    private TextField nroasiento;
    private TextField idtipoasiento;
    private TextField asiento_nuevo;
    private TextField sistema;
    private TextField usuarioalt;
    private TextField usuarioact;
    private DatePicker fechaalt;
    private DatePicker fechaact;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contableasientos> binder;

    private Contableasientos contableasientos;

    private ContableasientosService contableasientosService;

    public AsientosContablesView(@Autowired ContableasientosService contableasientosService) {
        addClassNames("asientos-contables-view", "flex", "flex-col", "h-full");
        this.contableasientosService = contableasientosService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idasiento").setAutoWidth(true);
        grid.addColumn("idejercicio").setAutoWidth(true);
        grid.addColumn("fecha").setAutoWidth(true);
        grid.addColumn("leyenda").setAutoWidth(true);
        grid.addColumn("nroasiento").setAutoWidth(true);
        grid.addColumn("idtipoasiento").setAutoWidth(true);
        grid.addColumn("asiento_nuevo").setAutoWidth(true);
        grid.addColumn("sistema").setAutoWidth(true);
        grid.addColumn("usuarioalt").setAutoWidth(true);
        grid.addColumn("usuarioact").setAutoWidth(true);
        grid.addColumn("fechaalt").setAutoWidth(true);
        grid.addColumn("fechaact").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(contableasientosService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CONTABLEASIENTOS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AsientosContablesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contableasientos.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idasiento).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idasiento");
        binder.forField(idejercicio).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idejercicio");
        binder.forField(nroasiento).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("nroasiento");
        binder.forField(idtipoasiento).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idtipoasiento");
        binder.forField(asiento_nuevo).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("asiento_nuevo");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contableasientos == null) {
                    this.contableasientos = new Contableasientos();
                }
                binder.writeBean(this.contableasientos);

                contableasientosService.update(this.contableasientos);
                clearForm();
                refreshGrid();
                Notification.show("Contableasientos details stored.");
                UI.getCurrent().navigate(AsientosContablesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contableasientos details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> contableasientosId = event.getRouteParameters().getInteger(CONTABLEASIENTOS_ID);
        if (contableasientosId.isPresent()) {
            Optional<Contableasientos> contableasientosFromBackend = contableasientosService
                    .get(contableasientosId.get());
            if (contableasientosFromBackend.isPresent()) {
                populateForm(contableasientosFromBackend.get());
            } else {
                Notification.show(String.format("The requested contableasientos was not found, ID = %d",
                        contableasientosId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(AsientosContablesView.class);
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
        idasiento = new TextField("Idasiento");
        idejercicio = new TextField("Idejercicio");
        fecha = new DatePicker("Fecha");
        leyenda = new TextField("Leyenda");
        nroasiento = new TextField("Nroasiento");
        idtipoasiento = new TextField("Idtipoasiento");
        asiento_nuevo = new TextField("Asiento_nuevo");
        sistema = new TextField("Sistema");
        usuarioalt = new TextField("Usuarioalt");
        usuarioact = new TextField("Usuarioact");
        fechaalt = new DatePicker("Fechaalt");
        fechaact = new DatePicker("Fechaact");
        Component[] fields = new Component[]{idasiento, idejercicio, fecha, leyenda, nroasiento, idtipoasiento,
                asiento_nuevo, sistema, usuarioalt, usuarioact, fechaalt, fechaact};

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

    private void populateForm(Contableasientos value) {
        this.contableasientos = value;
        binder.readBean(this.contableasientos);

    }
}
