package main.java;


import java.awt.*;
import java.beans.PropertyChangeListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.jdatepicker.JDatePicker;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Converter;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetbuilder.OverriddenWidgetBuilder;
import org.metawidget.swing.widgetbuilder.ReadOnlyWidgetBuilder;
import org.metawidget.swing.widgetbuilder.SwingWidgetBuilder;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilder;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilderConfig;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

import static org.metawidget.inspector.InspectionResultConstants.NAME;
import static org.metawidget.inspector.InspectionResultConstants.TYPE;

public class WidgetConfig {

    public static void setCommonSettings(SwingMetawidget metawidget) {

        CompositeWidgetBuilderConfig builderConfig = new CompositeWidgetBuilderConfig();
        builderConfig.setWidgetBuilders(
                new OverriddenWidgetBuilder(),
                new ReadOnlyWidgetBuilder(),
                new DateWidgetBuilder(),
                new SwingWidgetBuilder()
        );

        CompositeWidgetBuilder builder = new CompositeWidgetBuilder<>(builderConfig);
        metawidget.setWidgetBuilder(builder);

        BeansBindingProcessorConfig config = new BeansBindingProcessorConfig();
        config.setUpdateStrategy(AutoBinding.UpdateStrategy.READ_WRITE);
        metawidget.addWidgetProcessor(new BeansBindingProcessor(config));

        metawidget.addWidgetProcessor((w, string, map, m) -> {
            if (w instanceof JDatePicker) {
                ((JDatePicker) w).getFormattedTextField().addPropertyChangeListener((PropertyChangeListener) e -> {
                    if ("VALUE".equals(e.getPropertyName().toUpperCase())) {
                        try {
                            Object obj = m.getToInspect();
                            String name = map.get("name");
                            Method method = obj.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), Date.class);
                            if(e.getNewValue() == null) {
                                method.invoke(obj, (Object) null);
                                return;
                            }
                            GregorianCalendar cal = (GregorianCalendar) e.getNewValue();
                            method.invoke(obj, cal.getTime());
                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            return w;
        });

    }

    public static class DateWidgetBuilder implements WidgetBuilder<Component, SwingMetawidget> {

        @Override
        public Component buildWidget(String elementName, Map<String, String> attributes, SwingMetawidget metawidget) {
            if (Date.class.getName().equalsIgnoreCase(attributes.get(TYPE))) {
                try {
                    Object obj = metawidget.getToInspect();
                    Class cl = obj.getClass();
                    Method method = cl.getMethod("get" + attributes.get(NAME).substring(0, 1).toUpperCase() + attributes.get(NAME).substring(1));
                    Date d = (Date) method.invoke(obj);
                    if (d == null) return new JDatePicker();
                    return new JDatePicker(d);
                } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }
}

