package com.example.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.image.AImage;
import org.zkoss.zul.Image;

import java.io.IOException;

public class AImageConverter implements Converter<AImage, byte[], Image> {
    @Override
    public AImage coerceToUi(byte[] beanProp, Image component, BindContext ctx) {
        try {
            if (beanProp != null) {
                AImage image = new AImage("", beanProp);
                component.setContent(image);
                return image;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] coerceToBean(AImage compAttr, Image component, BindContext ctx) {
        return null;
    }
}
