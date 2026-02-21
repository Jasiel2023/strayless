package com.adopciones.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

public class BotonSecundario extends Button{
    public BotonSecundario(String texto, VaadinIcon icono) {
        super(texto, icono.create());
        this.getStyle().set("cursor", "pointer");
    }
}
