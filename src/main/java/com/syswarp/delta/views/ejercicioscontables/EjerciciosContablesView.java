package com.syswarp.delta.views.ejercicioscontables;

import java.util.Optional;

import com.syswarp.delta.data.entity.Contableejercicios;
import com.syswarp.delta.data.service.ContableejerciciosService;

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
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import java.time.Duration;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

@Route(value = "contable-ejercicios/:contableejerciciosID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Ejercicios Contables")
public class EjerciciosContablesView extends Div implements BeforeEnterObserver {

    private final String CONTABLEEJERCICIOS_ID = "contableejerciciosID";
    private final String CONTABLEEJERCICIOS_EDIT_ROUTE_TEMPLATE = "contable-ejercicios/%d/edit";

    private Grid<Contableejercicios> grid = new Grid<>(Contableejercicios.class, false);

    //private TextField id;
    private TextField ejercicio;
    private DatePicker fechadesde;
    private DatePicker fechahasta;
    private Checkbox activo;
    private TextField usuarioalt;
    private TextField usuarioact;
    private DateTimePicker fechaalt;
    private DateTimePicker fechaact;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contableejercicios> binder;

    private Contableejercicios contableejercicios;

    private ContableejerciciosService contableejerciciosService;

    public EjerciciosContablesView(@Autowired ContableejerciciosService contableejerciciosService) {
        addClassNames("ejercicios-contables-view", "flex", "flex-col", "h-full");
        this.contableejerciciosService = contableejerciciosService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("ejercicio").setAutoWidth(true).setHeader("Ejercicio");
        grid.addColumn("fechadesde").setAutoWidth(true).setHeader("F.Desde");
        grid.addColumn("fechahasta").setAutoWidth(true).setHeader("F.Hasta");
        TemplateRenderer<Contableejercicios> activoRenderer = TemplateRenderer.<Contableejercicios>of(
                "<iron-icon hidden='[[!item.activo]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.activo]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("activo", Contableejercicios::isActivo);
        grid.addColumn(activoRenderer).setHeader("Activo").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(contableejerciciosService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent()
                        .navigate(String.format(CONTABLEEJERCICIOS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(EjerciciosContablesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contableejercicios.class);

        // Bind fields. This where you'd define e.g. validation rules
        //binder.forField(id).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("id");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contableejercicios == null) {
                    this.contableejercicios = new Contableejercicios();
                }
                binder.writeBean(this.contableejercicios);

                contableejerciciosService.update(this.contableejercicios);
                clearForm();
                refreshGrid();
                Notification.show("Contableejercicios details stored.");
                UI.getCurrent().navigate(EjerciciosContablesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contableejercicios details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> contableejerciciosId = event.getRouteParameters().getLong(CONTABLEEJERCICIOS_ID);
        if (contableejerciciosId.isPresent()) {
            Optional<Contableejercicios> contableejerciciosFromBackend = contableejerciciosService
                    .get(Math.toIntExact(contableejerciciosId.get()));
            if (contableejerciciosFromBackend.isPresent()) {
                populateForm(contableejerciciosFromBackend.get());
            } else {
                Notification.show(String.format("The requested contableejercicios was not found, ID = %d",
                        contableejerciciosId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(EjerciciosContablesView.class);
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
        //id = new TextField("Id");
        ejercicio = new TextField("Ejercicio");
        fechadesde = new DatePicker("Fecha Desde");
        fechahasta = new DatePicker("Fecha Hasta");
        activo = new Checkbox("Activo");
        activo.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{ ejercicio, fechadesde, fechahasta, activo};

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

    private void populateForm(Contableejercicios value) {
        this.contableejercicios = value;
        binder.readBean(this.contableejercicios);

    }
}
