package main.java;


import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

import org.jdesktop.beansbinding.AutoBinding;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetbuilder.OverriddenWidgetBuilder;
import org.metawidget.swing.widgetbuilder.ReadOnlyWidgetBuilder;
import org.metawidget.swing.widgetbuilder.SwingWidgetBuilder;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilder;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilderConfig;
import org.metawidget.widgetprocessor.iface.WidgetProcessor;

public class WidgetConfig {

    public static void setCommonSettings(SwingMetawidget metawidget) {
        metawidget.setWidgetBuilder(new CompositeWidgetBuilder<>(new CompositeWidgetBuilderConfig()
                .setWidgetBuilders(
                        new OverriddenWidgetBuilder(),
                        new ReadOnlyWidgetBuilder(),
                        new SwingWidgetBuilder()

                )));

        metawidget.addWidgetProcessor(new BeansBindingProcessor(
                new BeansBindingProcessorConfig()
                        .setUpdateStrategy(AutoBinding.UpdateStrategy.READ_WRITE)));


    }
}

