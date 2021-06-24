package com.syswarp.delta.views.centrosdecosto;

import java.util.Optional;

import com.syswarp.delta.data.entity.Contablecencosto;
import com.syswarp.delta.data.service.ContablecencostoService;

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

@Route(value = "centros-costo/:contablecencostoID?/:action?(edit)", layout = MainLayout.class)
@PageTitle("Centros de Costo")
public class CentrosdeCostoView extends Div implements BeforeEnterObserver {

    private final String CONTABLECENCOSTO_ID = "contablecencostoID";
    private final String CONTABLECENCOSTO_EDIT_ROUTE_TEMPLATE = "centros-costo/%d/edit";

    private Grid<Contablecencosto> grid = new Grid<>(Contablecencosto.class, false);

    private TextField idcencosto;
    private TextField descripcion;
    private TextField usuarioalt;
    private TextField usuarioact;
    private DateTimePicker fechaalt;
    private DateTimePicker fechaact;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contablecencosto> binder;

    private Contablecencosto contablecencosto;

    private ContablecencostoService contablecencostoService;

    public CentrosdeCostoView(@Autowired ContablecencostoService contablecencostoService) {
        addClassNames("centrosde-costo-view", "flex", "flex-col", "h-full");
        this.contablecencostoService = contablecencostoService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idcencosto").setAutoWidth(true);
        grid.addColumn("descripcion").setAutoWidth(true);
        grid.addColumn("usuarioalt").setAutoWidth(true);
        grid.addColumn("usuarioact").setAutoWidth(true);
        grid.addColumn("fechaalt").setAutoWidth(true);
        grid.addColumn("fechaact").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(contablecencostoService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CONTABLECENCOSTO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(CentrosdeCostoView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contablecencosto.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idcencosto).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idcencosto");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contablecencosto == null) {
                    this.contablecencosto = new Contablecencosto();
                }
                binder.writeBean(this.contablecencosto);

                contablecencostoService.update(this.contablecencosto);
                clearForm();
                refreshGrid();
                Notification.show("Contablecencosto details stored.");
                UI.getCurrent().navigate(CentrosdeCostoView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contablecencosto details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> contablecencostoId = event.getRouteParameters().getInteger(CONTABLECENCOSTO_ID);
        if (contablecencostoId.isPresent()) {
            Optional<Contablecencosto> contablecencostoFromBackend = contablecencostoService
                    .get(contablecencostoId.get());
            if (contablecencostoFromBackend.isPresent()) {
                populateForm(contablecencostoFromBackend.get());
            } else {
                Notification.show(String.format("The requested contablecencosto was not found, ID = %d",
                        contablecencostoId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(CentrosdeCostoView.class);
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
        idcencosto = new TextField("Idcencosto");
        descripcion = new TextField("Descripcion");
        usuarioalt = new TextField("Usuarioalt");
        usuarioact = new TextField("Usuarioact");
        fechaalt = new DateTimePicker("Fechaalt");
        fechaalt.setStep(Duration.ofSeconds(1));
        fechaact = new DateTimePicker("Fechaact");
        fechaact.setStep(Duration.ofSeconds(1));
        Component[] fields = new Component[]{idcencosto, descripcion, usuarioalt, usuarioact, fechaalt, fechaact};

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

    private void populateForm(Contablecencosto value) {
        this.contablecencosto = value;
        binder.readBean(this.contablecencosto);

    }
}
