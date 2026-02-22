package com.adopciones.ui.views.adminViews;

import java.util.List;

import com.adopciones.server.enums.DisponibilidadEnum;
import com.adopciones.server.enums.SaludEnum;
import com.adopciones.server.enums.SexoEnum;
import com.adopciones.server.models.Animal;
import com.adopciones.server.models.Especie;
import com.adopciones.server.models.Raza;
import com.adopciones.ui.components.BotonPrimario;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class AnimalForm  extends FormLayout{
    
    TextField nombre = new TextField("Nombre del Animal");
    ComboBox<Especie> especie = new ComboBox<>("Especie");
    ComboBox<Raza> raza = new ComboBox<>("Raza");
    ComboBox<SexoEnum> sexoAnimal = new ComboBox<>("Sexo");
    ComboBox<SaludEnum> saludAnimal = new ComboBox<>("Salud");
    ComboBox<DisponibilidadEnum> disponibilidad = new ComboBox<>("Disponibilidad");
    TextArea informacion = new TextArea("Información Adicional");
    TextField imgUrl = new TextField("Url de la Foto");

    BotonPrimario guardar = new BotonPrimario("Guardar", VaadinIcon.CHECK);
    Button cancelar = new Button("Cancelar");

    Binder<Animal> binder = new BeanValidationBinder<>(Animal.class);

    public AnimalForm(List<Raza> razasDisponibles) {
        addClassName("animal-form");


        raza.setItems(razasDisponibles);
        raza.setItemLabelGenerator(Raza::getNombre);

        sexoAnimal.setItems(SexoEnum.values());
        saludAnimal.setItems(SaludEnum.values());
        disponibilidad.setItems(DisponibilidadEnum.values());

        binder.bindInstanceFields(this);

        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        guardar.addClickShortcut(Key.ENTER);
        cancelar.addClickShortcut(Key.ESCAPE);  

        add(nombre, raza, sexoAnimal, saludAnimal, disponibilidad, imgUrl, informacion, crearLayoutBotones());
    }

    private HorizontalLayout crearLayoutBotones(){
        return new HorizontalLayout(guardar,  cancelar);
    }

    public void setAnimal(Animal animal) {
        binder.setBean(animal);
    }

    public void actualizarEspecies(List<Especie> nuevasEspecies) {
        this.especie.setItems(nuevasEspecies);
    }

    public void actualizarRazas(List<Raza> nuevasRazas) {
        this.raza.setItems(nuevasRazas);
    }
    public BotonPrimario getGuardarBtn(){return guardar;}
    public Button getCancelarBtn(){return cancelar;}
    public Binder<Animal> getBinder(){return binder;}
}
