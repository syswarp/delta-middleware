package com.syswarp.delta.views.acercade;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.syswarp.delta.views.MainLayout;

@Route(value = "about", layout = MainLayout.class)
@PageTitle("Acerca de..")
public class AcercadeView extends Div {

    public AcercadeView() {
        addClassName("acercade-view");
        add(new Text("Content placeholder"));
    }

}
