package com.syswarp.delta.views.tiposdecuenta;

import java.util.Optional;

import com.syswarp.delta.data.entity.Contabletipificacion;
import com.syswarp.delta.data.service.ContabletipificacionService;

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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

@Route(value = "tipo-cuenta/:contabletipificacionID?/:action?(edit)", layout = MainLayout.class)
@PageTitle("Tipos de Cuenta")
public class TiposdeCuentaView extends Div implements BeforeEnterObserver {

    private final String CONTABLETIPIFICACION_ID = "contabletipificacionID";
    private final String CONTABLETIPIFICACION_EDIT_ROUTE_TEMPLATE = "tipo-cuenta/%d/edit";

    private Grid<Contabletipificacion> grid = new Grid<>(Contabletipificacion.class, false);

    private TextField idtipocuenta;
    private TextField tipo;
    private Checkbox mostrar;
    private Checkbox estotal;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contabletipificacion> binder;

    private Contabletipificacion contabletipificacion;

    private ContabletipificacionService contabletipificacionService;

    public TiposdeCuentaView(@Autowired ContabletipificacionService contabletipificacionService) {
        addClassNames("tiposde-cuenta-view", "flex", "flex-col", "h-full");
        this.contabletipificacionService = contabletipificacionService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idtipocuenta").setAutoWidth(true);
        grid.addColumn("tipo").setAutoWidth(true);
        TemplateRenderer<Contabletipificacion> mostrarRenderer = TemplateRenderer.<Contabletipificacion>of(
                "<iron-icon hidden='[[!item.mostrar]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.mostrar]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("mostrar", Contabletipificacion::isMostrar);
        grid.addColumn(mostrarRenderer).setHeader("Mostrar").setAutoWidth(true);

        TemplateRenderer<Contabletipificacion> estotalRenderer = TemplateRenderer.<Contabletipificacion>of(
                "<iron-icon hidden='[[!item.estotal]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.estotal]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("estotal", Contabletipificacion::isEstotal);
        grid.addColumn(estotalRenderer).setHeader("Estotal").setAutoWidth(true);

        grid.setDataProvider(new CrudServiceDataProvider<>(contabletipificacionService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent()
                        .navigate(String.format(CONTABLETIPIFICACION_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TiposdeCuentaView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contabletipificacion.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idtipocuenta).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idtipocuenta");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contabletipificacion == null) {
                    this.contabletipificacion = new Contabletipificacion();
                }
                binder.writeBean(this.contabletipificacion);

                contabletipificacionService.update(this.contabletipificacion);
                clearForm();
                refreshGrid();
                Notification.show("Contabletipificacion details stored.");
                UI.getCurrent().navigate(TiposdeCuentaView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contabletipificacion details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> contabletipificacionId = event.getRouteParameters().getInteger(CONTABLETIPIFICACION_ID);
        if (contabletipificacionId.isPresent()) {
            Optional<Contabletipificacion> contabletipificacionFromBackend = contabletipificacionService
                    .get(contabletipificacionId.get());
            if (contabletipificacionFromBackend.isPresent()) {
                populateForm(contabletipificacionFromBackend.get());
            } else {
                Notification.show(String.format("The requested contabletipificacion was not found, ID = %d",
                        contabletipificacionId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TiposdeCuentaView.class);
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
        idtipocuenta = new TextField("Idtipocuenta");
        tipo = new TextField("Tipo");
        mostrar = new Checkbox("Mostrar");
        mostrar.getStyle().set("padding-top", "var(--lumo-space-m)");
        estotal = new Checkbox("Estotal");
        estotal.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{idtipocuenta, tipo, mostrar, estotal};

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

    private void populateForm(Contabletipificacion value) {
        this.contabletipificacion = value;
        binder.readBean(this.contabletipificacion);

    }
}
