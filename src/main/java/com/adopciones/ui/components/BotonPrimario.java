package com.adopciones.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class BotonPrimario extends Button {
    public BotonPrimario(String texto, VaadinIcon icono) {
        super(texto, icono.create());
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.getStyle().set("cursor", "pointer");
    }
    
}
