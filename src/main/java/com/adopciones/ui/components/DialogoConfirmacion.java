package com.adopciones.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DialogoConfirmacion extends Dialog {
    public DialogoConfirmacion(String titulo, String mensaje, Runnable accionConfirmar) {
       H3 tituloDialogo = new H3(titulo);
       Paragraph texto = new Paragraph(mensaje);
       Button btnCancelar = new Button("Cancelar", e -> this.close());
       Button btnConfirmar = new Button("Si, estoy seguro", e -> {
           accionConfirmar.run();
           this.close();
       });

       btnConfirmar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
       
       HorizontalLayout botones = new HorizontalLayout(btnCancelar, btnConfirmar);
       add(tituloDialogo, texto, botones);
    }
    
}
