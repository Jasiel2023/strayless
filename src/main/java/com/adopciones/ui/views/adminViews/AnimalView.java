package com.adopciones.ui.views.adminViews;

import com.adopciones.ui.components.BotonPeligro;
import com.adopciones.ui.components.BotonPrimario;
import com.adopciones.ui.components.BotonSecundario;
import com.adopciones.ui.components.DialogoConfirmacion;
import com.adopciones.ui.layout.MainLayout;

import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.adopciones.server.models.Animal;
import com.adopciones.server.services.AnimalServices;
import com.adopciones.server.services.EspecieServices;
@Route(value = "admin/animales", layout = MainLayout.class)
@PageTitle("Gestion de Animales")
public class AnimalView extends VerticalLayout {
    
    private final Grid<Animal> grid = new Grid<>(Animal.class, false);
    private final AnimalServices animalService;
    private final AnimalForm form;

    private final BotonPrimario btnAgregar = new BotonPrimario("Registrar Animal", VaadinIcon.PLUS);
    private final BotonSecundario btnEditar = new BotonSecundario("Editar", VaadinIcon.EDIT);
    private final BotonPeligro btnEliminar = new BotonPeligro("Eliminar", VaadinIcon.TRASH);

    public AnimalView(AnimalServices animalServices, EspecieServices especieServices) {

        this.animalService = animalServices;
        this.form = new AnimalForm(especieServices.getAllEspecies());
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
        return new HorizontalLayout(btnAgregar, btnEditar, btnEliminar);
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

    private void cerrarFormulario(){
        form.setAnimal(null);
        form.setVisible(false);
    }

    private void actualizarGrid(){
        grid.setItems(animalService.getAllAnimals());
    }





}
