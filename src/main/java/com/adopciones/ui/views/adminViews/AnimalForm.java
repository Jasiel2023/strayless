package com.adopciones.ui.views.adminViews;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload; 
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class AnimalForm  extends FormLayout{
    
    TextField nombre = new TextField("Nombre del Animal");
    ComboBox<Especie> especie = new ComboBox<>("Especie");
    ComboBox<Raza> raza = new ComboBox<>("Raza");
    ComboBox<SexoEnum> sexoAnimal = new ComboBox<>("Sexo");
    ComboBox<SaludEnum> saludAnimal = new ComboBox<>("Salud");
    ComboBox<DisponibilidadEnum> disponibilidad = new ComboBox<>("Disponibilidad");
    DatePicker fechaLlegada = new DatePicker("Fecha de Llegada");
    TextArea informacion = new TextArea("Información Adicional");

    Upload uploadFoto = new Upload();
    private String rutaImagenGuardada = null;

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

        fechaLlegada.setLocale(Locale.of("es", "EC"));

        binder.bindInstanceFields(this);

        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        guardar.addClickShortcut(Key.ENTER);
        cancelar.addClickShortcut(Key.ESCAPE);  

        //Configuracion Visual
        uploadFoto.setAcceptedFileTypes("image/jpeg", "image/png", "image/webp");
        uploadFoto.setMaxFiles(1);
        uploadFoto.setDropLabel(new Span("Arrasta la foto del animal aqui o haz clic"));

        uploadFoto.setReceiver((nombreOriginal, mimeType) -> {
            try {
                Path carpetaUploads = Paths.get("uploads/animales");
                if (!Files.exists(carpetaUploads)) {
                    Files.createDirectories(carpetaUploads);
                }

                String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                String nuevoNombre = UUID.randomUUID().toString() + extension;

                rutaImagenGuardada = nuevoNombre;

                Path rutaArchivo = carpetaUploads.resolve(nuevoNombre);
                return Files.newOutputStream(rutaArchivo);
            } catch (Exception e) {
                Notification.show("Error al preparar la ruta"  + e.getMessage());
                return null;
            }
        });

        uploadFoto.addSucceededListener(event -> {
            Notification.show("Foto subida").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        add(nombre, raza, sexoAnimal, saludAnimal, disponibilidad, uploadFoto, informacion, crearLayoutBotones());
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
    public String getRutaImagenGuardada() { return rutaImagenGuardada; }
    public void limpiarRutaImagen() { 
        this.rutaImagenGuardada = null;
        this.uploadFoto.clearFileList();
      }
}
