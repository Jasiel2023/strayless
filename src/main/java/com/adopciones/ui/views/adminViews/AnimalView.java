package com.adopciones.ui.views.adminViews;

import com.adopciones.ui.components.BotonPeligro;
import com.adopciones.ui.components.BotonPrimario;
import com.adopciones.ui.components.BotonSecundario;
import com.adopciones.ui.components.DialogoConfirmacion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.adopciones.server.models.Animal;
import com.adopciones.server.models.Especie;
import com.adopciones.server.models.Raza;
import com.adopciones.server.services.AnimalServices;
import com.adopciones.server.services.EspecieServices;
import com.adopciones.server.services.RazaServices;
@Route(value = "admin/animales")
@PageTitle("Gestion de Animales")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Animales")
public class AnimalView extends VerticalLayout {
    
    private final Grid<Animal> grid = new Grid<>(Animal.class, false);
    private final AnimalServices animalService;
    private final EspecieServices especieService;
    private final RazaServices razaServices;
    private final AnimalForm form;

    private final BotonPrimario btnAgregar = new BotonPrimario("Registrar Animal", VaadinIcon.PLUS);
    private final BotonSecundario btnEditar = new BotonSecundario("Editar", VaadinIcon.EDIT);
    private final BotonPeligro btnEliminar = new BotonPeligro("Eliminar", VaadinIcon.TRASH);

    private final BotonSecundario btnAgregarEspecie = new BotonSecundario("Registrar Especie", VaadinIcon.PLUS_CIRCLE);
    private final BotonSecundario btnAgregarRaza = new BotonSecundario("Registrar Raza", VaadinIcon.PLUS_CIRCLE);


    public AnimalView(AnimalServices animalServices, EspecieServices especieServices, RazaServices razaServices) {
        this.especieService = especieServices;
        this.razaServices = razaServices;
        this.animalService = animalServices;
        this.form = new AnimalForm(razaServices.getAllRazas());
        setSizeFull();
        configurarGrid();
        configurarFormulario();

        HorizontalLayout toolbar = getToolbar();

        HorizontalLayout contenidoPrincipal = new HorizontalLayout(grid, form);
        contenidoPrincipal.setSizeFull();
        contenidoPrincipal.setFlexGrow(2, grid);
        contenidoPrincipal.setFlexGrow(1, form);
        add(toolbar, contenidoPrincipal);

        actualizarGrid();
        cerrarFormulario();
    }

    private HorizontalLayout getToolbar(){
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        btnAgregar.addClickListener(e -> abrirFormularioVacio());
        btnEditar.addClickListener(e -> editarAnimalSeleccionado());
        btnEliminar.addClickListener(e -> confirmarEliminacion());

        btnAgregarEspecie.addClickListener(e -> abrirModalNuevaEspecie());
        btnAgregarRaza.addClickListener(e -> abrirModalNuevaRaza());

        HorizontalLayout grupoAnimal = new HorizontalLayout(btnAgregar, btnEditar, btnEliminar);
        HorizontalLayout grupoCatalogos = new HorizontalLayout(btnAgregarEspecie, btnAgregarRaza);

        HorizontalLayout toolbar = new HorizontalLayout(grupoAnimal, grupoCatalogos);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return toolbar;
        
    }

    private void configurarGrid(){
        grid.setSizeFull();

        grid.addColumn(Animal:: getNombre).setHeader("Nombre").setSortable(true);
        grid.addColumn(animal -> animal.getEspecie() != null ? animal.getEspecie().getNombre() : "N/A").setHeader("Especie");
        grid.addColumn(Animal:: getDisponibilidad).setHeader("Estado");

        grid.addColumn(Animal::getSexoAnimal).setHeader("Sexo").setAutoWidth(true);

        grid.addColumn(Animal::getSaludAnimal).setHeader("Salud").setAutoWidth(true);
        //grid.addColumn(Animal::getDisponibilidad).setHeader("Disponibilidad").setAutoWidth(true);
        grid.addColumn(Animal::getFechaLlegada).setHeader("Fecha de Llegada").setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(event -> {
            boolean haySeleccion = event.getValue() != null;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);

            if (!haySeleccion) {
                cerrarFormulario();
            }
        });
        
    }

    

    private void configurarFormulario(){

        form.getGuardarBtn().addClickListener(e -> guardarAnimal());
        form.getCancelarBtn().addClickListener(e -> cerrarFormulario());
    }

    private void abrirFormularioVacio(){
        grid.asSingleSelect().clear();
        form.setAnimal(new Animal());
        form.setVisible(true);
    }

    private void editarAnimalSeleccionado(){
        Animal animalSeleccionado = grid.asSingleSelect().getValue();
        if (animalSeleccionado != null) {
            form.setAnimal(animalSeleccionado);
            form.setVisible(true);
        } else {
            Notification.show("Por favor, selecciona un animal para editar").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void confirmarEliminacion(){

        Animal animal = grid.asSingleSelect().getValue();
        if (animal == null) return;

        DialogoConfirmacion dialogo = new DialogoConfirmacion(
            "Eliminar Animal", "Estas seguro de eliminar a " + animal.getNombre() + "?",
            () -> {
                animalService.deleteAnimal(animal.getId());
                actualizarGrid();
                cerrarFormulario();
                Notification.show("Animal eliminado").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        );
        dialogo.open();
    }


    private void guardarAnimal() {
       if (form.getBinder().isValid()) {
        Animal animal = form.getBinder().getBean();
        if (animal.getId() == null) {
            animalService.createAnimal(animal);
        } else {
            animalService.updateAnimal(animal);

       }
        cerrarFormulario();
        actualizarGrid();
        Notification.show("Guardado Exitosamente").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("Por favor, corrige los campos en rojo").addThemeVariants(NotificationVariant.LUMO_ERROR);
       }
    }
private void abrirModalNuevaEspecie() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Registrar Nueva Especie");

        TextField txtNombre = new TextField("Nombre de la Especie");
        txtNombre.setWidthFull();

        BotonPrimario btnGuardar = new BotonPrimario("Guardar", VaadinIcon.CHECK);
        Button btnCancelar = new Button("Cancelar", e -> dialog.close());

        btnGuardar.addClickListener(e -> {
            String valorIngresado = txtNombre.getValue();
            
            // Validamos que no sea nulo ni esté lleno de espacios en blanco
            if (valorIngresado != null && !valorIngresado.trim().isEmpty()) {
                try {
                    Especie nuevaEspecie = new Especie();
                    nuevaEspecie.setNombre(valorIngresado.trim()); // trim() quita espacios al inicio y final
                    
                    // Asegúrate de que este método esté bien escrito según tu Service
                    especieService.createEspecie(nuevaEspecie); 
                    
                    form.actualizarEspecies(especieService.getAllEspecies());
                    
                    Notification.show("Especie guardada exitosamente").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    dialog.close();
                } catch (Exception ex) {
                    // SI FALLA ALGO EN LA BASE DE DATOS, LO VEREMOS AQUÍ
                    Notification.show("Error interno al guardar: " + ex.getMessage())
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    ex.printStackTrace(); // Muestra el error en la consola de tu IDE (VSCode/Eclipse/IntelliJ)
                }
            } else {
                Notification.show("El nombre no puede estar vacío").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        dialog.add(new VerticalLayout(txtNombre));
        dialog.getFooter().add(btnCancelar, btnGuardar);
        dialog.open();
    }

    private void abrirModalNuevaRaza(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Registrar Nueva Raza");

        TextField textNombre = new TextField("Nombre de la Raza");
        textNombre.setWidthFull();

        ComboBox<Especie> comboEspecies = new ComboBox<>("Selecciona la Especie");
        comboEspecies.setItems(especieService.getAllEspecies());
        comboEspecies.setItemLabelGenerator(Especie::getNombre);
        comboEspecies.setWidthFull();


        BotonPrimario btnGuardar = new BotonPrimario("Guardar", VaadinIcon.CHECK);
        Button btnCancelar = new Button("Cancelar", e -> dialog.close());

        btnGuardar.addClickListener(e -> {
            String nombreRaza = textNombre.getValue();
            Especie especieSeleccionada = comboEspecies.getValue();

            if (especieSeleccionada == null) {
                Notification.show("Por favor, selecciona una especie para la raza").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;

            }

            try {
                 Raza nuevaRaza = new Raza();
                nuevaRaza.setNombre(nombreRaza.trim());
                nuevaRaza.setEspecie(comboEspecies.getValue());
                razaServices.createRaza(nuevaRaza);
               
                 form.actualizarRazas(razaServices.getAllRazas());

                Notification.show("Raza guardada Exitosamente");
                dialog.close();
            } catch (Exception ex) {
                Notification.show("Error al guardar la raza: " + ex.getMessage())
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        });
        dialog.add(new VerticalLayout(textNombre, comboEspecies));
        dialog.getFooter().add(btnCancelar, btnGuardar);
        dialog.open();
    }

    private void cerrarFormulario(){
        form.setAnimal(null);
        form.setVisible(false);
    }

    private void actualizarGrid(){
        grid.setItems(animalService.getAllAnimals());
    }





}
