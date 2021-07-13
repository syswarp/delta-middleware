package com.syswarp.delta.views.plandecuentascontables;

import java.util.List;
import java.util.Optional;

import com.syswarp.delta.data.entity.Contablecencosto;
import com.syswarp.delta.data.entity.Contableejercicios;
import com.syswarp.delta.data.entity.Contableinfiplan;
import com.syswarp.delta.data.service.ContablecencostoRepository;
import com.syswarp.delta.data.service.ContableejerciciosRepository;
import com.syswarp.delta.data.service.ContableinfiplanService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

@Route(value = "plan-cuentas-contablesl/:contableinfiplanID?/:action?(edit)", layout = MainLayout.class)
@PageTitle("Plan de Cuentas Contables")
public class PlandeCuentasContablesView extends Div implements BeforeEnterObserver {

    @Autowired
    ContablecencostoRepository cc;

    private final String CONTABLEINFIPLAN_ID = "contableinfiplanID";
    private final String CONTABLEINFIPLAN_EDIT_ROUTE_TEMPLATE = "plan-cuentas-contablesl/%d/edit";

    private Grid<Contableinfiplan> grid = new Grid<>(Contableinfiplan.class, false);
    ComboBox<Contablecencosto> cc1;
    ComboBox<Contablecencosto> cc2;


    private TextField idcuenta;
    private TextField cuenta;
    private Checkbox imputable;
    private Checkbox ajustable;
    private Checkbox resultado;
    private TextField nivel;
    //private TextField idcentrocosto1;
    private TextField idcentrocosto2;
    private TextField usuarioalt;
    private TextField usuarioact;
    private DateTimePicker fechaalt;
    private DateTimePicker fechaact;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Contableinfiplan> binder;

    private Contableinfiplan contableinfiplan;

    private ContableinfiplanService contableinfiplanService;

    public PlandeCuentasContablesView(@Autowired ContableinfiplanService contableinfiplanService) {
        addClassNames("plande-cuentas-contables-view", "flex", "flex-col", "h-full");
        this.contableinfiplanService = contableinfiplanService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid

        grid.addColumn("idcuenta").setAutoWidth(true);
        grid.addColumn("cuenta").setAutoWidth(true);
        TemplateRenderer<Contableinfiplan> imputableRenderer = TemplateRenderer.<Contableinfiplan>of(
                "<iron-icon hidden='[[!item.imputable]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.imputable]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("imputable", Contableinfiplan::isImputable);
        grid.addColumn(imputableRenderer).setHeader("Imputable").setAutoWidth(true);

        TemplateRenderer<Contableinfiplan> ajustableRenderer = TemplateRenderer.<Contableinfiplan>of(
                "<iron-icon hidden='[[!item.ajustable]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.ajustable]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("ajustable", Contableinfiplan::isAjustable);
        grid.addColumn(ajustableRenderer).setHeader("Ajustable").setAutoWidth(true);

        TemplateRenderer<Contableinfiplan> resultadoRenderer = TemplateRenderer.<Contableinfiplan>of(
                "<iron-icon hidden='[[!item.resultado]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.resultado]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("resultado", Contableinfiplan::isResultado);
        grid.addColumn(resultadoRenderer).setHeader("Resultado").setAutoWidth(true);

        grid.addColumn("nivel").setAutoWidth(true);
       // grid.addColumn("idcentrocosto1").setAutoWidth(true);
       //grid.addColumn("idcentrocosto2").setAutoWidth(true);
        grid.addColumn("usuarioalt").setAutoWidth(true);
        grid.addColumn("usuarioact").setAutoWidth(true);
        grid.addColumn("fechaalt").setAutoWidth(true);
        grid.addColumn("fechaact").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(contableinfiplanService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
               // UI.getCurrent().navigate(String.format(CONTABLEINFIPLAN_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PlandeCuentasContablesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contableinfiplan.class);

        // Bind fields. This where you'd define e.g. validation rules
        //binder.forField(idejercicio).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("idejercicio");
        binder.forField(idcuenta).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idcuenta");
        binder.forField(nivel).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("nivel");
      //  binder.forField(idcentrocosto1).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
      //          .bind("idcentrocosto1");
       // binder.forField(idcentrocosto2).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
       //         .bind("idcentrocosto2");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.contableinfiplan == null) {
                    this.contableinfiplan = new Contableinfiplan();
                }
                binder.writeBean(this.contableinfiplan);

                contableinfiplanService.update(this.contableinfiplan);
                clearForm();
                refreshGrid();
                Notification.show("Contableinfiplan details stored.");
                UI.getCurrent().navigate(PlandeCuentasContablesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the contableinfiplan details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> contableinfiplanId = event.getRouteParameters().getInteger(CONTABLEINFIPLAN_ID);
        if (contableinfiplanId.isPresent()) {
            Optional<Contableinfiplan> contableinfiplanFromBackend = contableinfiplanService
                    .get(contableinfiplanId.get());
            if (contableinfiplanFromBackend.isPresent()) {
                populateForm(contableinfiplanFromBackend.get());
            } else {
                Notification.show(String.format("The requested contableinfiplan was not found, ID = %d",
                        contableinfiplanId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PlandeCuentasContablesView.class);
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
        idcuenta = new TextField("Idcuenta");
        cuenta = new TextField("Cuenta");
        imputable = new Checkbox("Imputable");
        imputable.getStyle().set("padding-top", "var(--lumo-space-m)");
        ajustable = new Checkbox("Ajustable");
        ajustable.getStyle().set("padding-top", "var(--lumo-space-m)");
        resultado = new Checkbox("Resultado");
        resultado.getStyle().set("padding-top", "var(--lumo-space-m)");
        nivel = new TextField("Nivel");
        //idcentrocosto1 = new TextField("Idcentrocosto1");
        cc1 = new ComboBox<Contablecencosto>("Centro de Costo 1");
        cc2 = new ComboBox<Contablecencosto>("Centro de Costo 2");


       // idcentrocosto2 = new TextField("Idcentrocosto2");
        usuarioalt = new TextField("Usuarioalt");
        usuarioact = new TextField("Usuarioact");
        fechaalt = new DateTimePicker("Fechaalt");
        fechaalt.setStep(Duration.ofSeconds(1));
        fechaact = new DateTimePicker("Fechaact");
        fechaact.setStep(Duration.ofSeconds(1));
        Component[] fields = new Component[]{ idcuenta, cuenta, imputable, ajustable, resultado, nivel,
                cc1, cc2,  usuarioalt, usuarioact, fechaalt, fechaact};

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

    private void populateForm(Contableinfiplan value) {
        this.contableinfiplan = value;

     //Centros de Costo1
     List<Contablecencosto> cc1List = cc.findAll();
     cc1.setItems(cc1List);
     cc1.setItemLabelGenerator(Contablecencosto::getDescripcion);

     //Centros de Costo2
     List<Contablecencosto> cc2List = cc.findAll();
     cc2.setItems(cc2List);
     cc2.setItemLabelGenerator(Contablecencosto::getDescripcion);

     binder.readBean(this.contableinfiplan);

    }
}
