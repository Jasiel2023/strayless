package com.adopciones.ui.views.userViews;

import java.util.List;

import com.adopciones.server.models.Animal;
import com.adopciones.server.services.AnimalServices;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;


@Route("adopciones") // La URL será: localhost:8080/adopciones
@PageTitle("Adoptar una Mascota")
public class AdopcionesView extends VerticalLayout {

    private final AnimalServices animalService;

    public AdopcionesView(AnimalServices animalService) {
        this.animalService = animalService;
        
        // Estilos para la vista principal
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        
        H1 titulo = new H1("Nuestros Peluditos Disponibles");
        titulo.addClassNames(LumoUtility.Margin.Top.LARGE, LumoUtility.Margin.Bottom.MEDIUM);

        // Contenedor tipo "Grid" (Cuadrícula) para las cartas
        Div contenedorCartas = new Div();
        contenedorCartas.setWidthFull();
        contenedorCartas.setMaxWidth("1200px"); // Para que no se estire demasiado en pantallas gigantes
        
        // Magia CSS de Vaadin para hacer una cuadrícula responsiva (Cartas al lado de otras)
        contenedorCartas.getStyle().set("display", "grid");
        contenedorCartas.getStyle().set("grid-template-columns", "repeat(auto-fill, minmax(280px, 1fr))");
        contenedorCartas.getStyle().set("gap", "20px"); // Espacio entre cartas
        contenedorCartas.getStyle().set("padding", "20px");

        // Obtenemos SOLO los disponibles
        List<Animal> disponibles = animalService.getAnimalsDisponibles();

        // Por cada animal, creamos una carta y la metemos al contenedor
        for (Animal animal : disponibles) {
            contenedorCartas.add(crearCartaAnimal(animal));
        }

        add(titulo, contenedorCartas);
    }

    // --- Método que "Dibuja" una sola carta ---
    private Div crearCartaAnimal(Animal animal) {
        Div carta = new Div();
        
        // Estilos de la carta (Fondo blanco, borde redondeado, sombra suave)
        carta.addClassNames(
            LumoUtility.Background.BASE, 
            LumoUtility.BorderRadius.LARGE,
            LumoUtility.BoxShadow.SMALL,
            LumoUtility.Display.FLEX,
            LumoUtility.FlexDirection.COLUMN,
            LumoUtility.Overflow.HIDDEN // Para que la foto no se salga de los bordes redondeados
        );
        carta.getStyle().set("transition", "transform 0.2s"); // Animación suave

        // 1. La Foto
        String rutaFoto = animal.getImgUrl();
        Image foto = new Image();
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            foto.setSrc("/fotos-animales/" + rutaFoto);
        } else {
            // Imagen por defecto si no tiene
            foto.setSrc("https://via.placeholder.com/300x200?text=Sin+Foto"); 
        }
        foto.setAlt("Foto de " + animal.getNombre());
        foto.setWidthFull();
        foto.setHeight("200px");
        foto.getStyle().set("object-fit", "cover"); // Recorta la imagen sin deformarla

        // Contenedor para la información de texto (con padding)
        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setPadding(true);
        infoLayout.setSpacing(false);

        // 2. Título (Nombre)
        H3 nombre = new H3(animal.getNombre());
        nombre.addClassNames(LumoUtility.Margin.NONE, LumoUtility.Margin.Bottom.SMALL);

        // 3. Etiquetas de información
        String nombreRaza = animal.getRaza() != null ? animal.getRaza().getNombre() : "Desconocida";
        Span razaSpan = new Span("🐾 Raza: " + nombreRaza);
        Span sexoSpan = new Span("⚧ Sexo: " + animal.getSexoAnimal().name());
        Span saludSpan = new Span("⚕️ Salud: " + animal.getSaludAnimal().name());
        Span fechaSpan = new Span("📅 Llegó: " + animal.getFechaLlegada().toString());
        
        // Agregamos un poco de estilo al texto gris
        razaSpan.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        sexoSpan.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        saludSpan.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        fechaSpan.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);

        // 4. Descripción adicional
        Paragraph descripcion = new Paragraph(animal.getInformacion());
        descripcion.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.Margin.Top.SMALL);
        // Para que la carta no se haga larguísima si hay mucho texto:
        descripcion.getStyle().set("display", "-webkit-box");
        descripcion.getStyle().set("-webkit-line-clamp", "3"); // Máximo 3 líneas
        descripcion.getStyle().set("-webkit-box-orient", "vertical");
        descripcion.getStyle().set("overflow", "hidden");

        // 5. Botón de Adoptar
        Button btnAdoptar = new Button("Adoptar");
        btnAdoptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAdoptar.setWidthFull(); // Botón ancho
        btnAdoptar.addClassNames(LumoUtility.Margin.Top.AUTO); // Empuja el botón al final de la carta
        
        // El botón no hace nada por ahora, pero le ponemos un mensaje visual
        btnAdoptar.addClickListener(e -> {
            // Futura lógica de adopción
            com.vaadin.flow.component.notification.Notification.show("¡Próximamente: Proceso de adopción para " + animal.getNombre() + "!");
        });

        // Metemos los textos e info al layout interno
        infoLayout.add(nombre, razaSpan, sexoSpan, saludSpan, fechaSpan, descripcion, btnAdoptar);
        
        // Metemos la foto y la info a la carta principal
        carta.add(foto, infoLayout);

        return carta;
    }
}