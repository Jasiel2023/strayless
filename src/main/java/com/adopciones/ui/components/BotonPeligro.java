package com.adopciones.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class BotonPeligro extends Button {
    public BotonPeligro(String texto, VaadinIcon icono) {
        super(texto, icono.create());
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        this.addClassName("boton-peligro");
        this.getStyle().set("cursor", "pointer");
    }
}
