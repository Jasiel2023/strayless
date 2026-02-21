package com.adopciones.ui.views.adminViews;

import com.adopciones.ui.layout.MainLayout;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.t;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Minus.Horizontal;
import com.adopciones.server.models.Animal;
import com.adopciones.server.services.AnimalServices;
import com.adopciones.server.services.EspecieServices;
@Route(value = "admin/animales", layout = MainLayout.class)
@PageTitle("Gestion de Animales")
public class AnimalView extends VerticalLayout {
    
    private final Grid<Animal> grid = new Grid<>(Animal.class, false);
    private final AnimalServices animalService;
    private final AnimalForm form;

    public AnimalView(AnimalServices animalServices, EspecieServices especieServices) {

        this.animalService = animalServices;
        this.form = new AnimalForm(especieServices.getAllEspecies());
        setSizeFull();
        configurarGrid();

        Button btnAgregar = new Button("Registrar Animal", new Icon(VaadinIcon.PLUS));
        btnAgregar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout toolbar = new HorizontalLayout(btnAgregar);

        add(toolbar, grid);

        actualizarGrid();
    }

    private void configurarGrid(){
        grid.setSizeFull();

        grid.addColumn(Animal:: getNombre).setHeader("Nombre").
        setSortable(true).setAutoWidth(true);

        grid.addColumn(animal -> {
            if (animal.getEspecie() != null) {
                return animal.getEspecie().getNombre();
            }
            return "Sin definir";
        }).setHeader("Especie").setAutoWidth(true);


        grid.addColumn(Animal::getSexoAnimal).setHeader("Sexo").setAutoWidth(true);

        grid.addColumn(Animal::getSaludAnimal).setHeader("Salud").setAutoWidth(true);
        grid.addColumn(Animal::getDisponibilidad).setHeader("Disponibilidad").setAutoWidth(true);
        grid.addColumn(Animal::getFechaLlegada).setHeader("Fecha de Llegada").setAutoWidth(true);

        grid.getColumns().forEach(col -> col.setResizable(true));
    }

    private void actualizarGrid(){
        grid.setItems(animalService.getAllAnimals());
    }





}
