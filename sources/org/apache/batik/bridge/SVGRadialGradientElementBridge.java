/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.bridge;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import org.apache.batik.ext.awt.RadialGradientPaint;
import org.apache.batik.ext.awt.MultipleGradientPaint;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.GraphicsNodeRenderContext;

import org.w3c.dom.Element;

/**
 * Bridge class for the &lt;radialGradient> element.
 *
 * @author <a href="mailto:tkormann@apache.org">Thierry Kormann</a>
 * @version $Id$
 */
public class SVGRadialGradientElementBridge
    extends SVGAbstractGradientElementBridge {


    /**
     * Constructs a new SVGRadialGradientElementBridge.
     */
    public SVGRadialGradientElementBridge() {}

    /**
     * Builds a radial gradient according to the specified parameters.
     *
     * @param paintElement the element that defines a Paint
     * @param paintedElement the element referencing the paint
     * @param paintedNode the graphics node on which the Paint will be applied
     * @param spreadMethod the spread method
     * @param colorSpace the color space (sRGB | LinearRGB)
     * @param transform the gradient transform
     * @param colors the colors of the gradient
     * @param offsets the offsets
     * @param ctx the bridge context to use
     */
    protected
        Paint buildGradient(Element paintElement,
                            Element paintedElement,
                            GraphicsNode paintedNode,
                            MultipleGradientPaint.CycleMethodEnum spreadMethod,
                            MultipleGradientPaint.ColorSpaceEnum colorSpace,
                            AffineTransform transform,
                            Color [] colors,
                            float [] offsets,
                            BridgeContext ctx) {

        // 'cx' attribute - default is 50%
        String cxStr = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_CX_ATTRIBUTE, ctx);
        if (cxStr.length() == 0) {
            cxStr = SVG_RADIAL_GRADIENT_CX_DEFAULT_VALUE;
        }

        // 'cy' attribute - default is 50%
        String cyStr = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_CY_ATTRIBUTE, ctx);
        if (cyStr.length() == 0) {
            cyStr = SVG_RADIAL_GRADIENT_CY_DEFAULT_VALUE;
        }

        // 'r' attribute - default is 50%
        String rStr = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_R_ATTRIBUTE, ctx);
        if (rStr.length() == 0) {
            rStr = SVG_RADIAL_GRADIENT_R_DEFAULT_VALUE;
        }

        // 'fx' attribute - default is same as cx
        String fxStr = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_FX_ATTRIBUTE, ctx);
        if (fxStr.length() == 0) {
            fxStr = cxStr;
        }

        // 'fy' attribute - default is same as cy
        String fyStr = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_FY_ATTRIBUTE, ctx);
        if (fyStr.length() == 0) {
            fyStr = cyStr;
        }

        // 'gradientUnits' attribute - default is objectBoundingBox
        short coordSystemType;
        String s = SVGUtilities.getChainableAttributeNS
            (paintElement, null, SVG_GRADIENT_UNITS_ATTRIBUTE, ctx);
        if (s.length() == 0) {
            coordSystemType = SVGUtilities.OBJECT_BOUNDING_BOX;
        } else {
            coordSystemType = SVGUtilities.parseCoordinateSystem
                (paintElement, SVG_GRADIENT_UNITS_ATTRIBUTE, s);
        }

        // additional transform to move to objectBoundingBox coordinate system
        if (coordSystemType == SVGUtilities.OBJECT_BOUNDING_BOX) {
            GraphicsNodeRenderContext rc = ctx.getGraphicsNodeRenderContext();
            transform = SVGUtilities.toObjectBBox(transform,
                                                  paintedNode,
                                                  rc);
        }
        UnitProcessor.Context uctx
            = UnitProcessor.createContext(ctx, paintElement);

        float r = SVGUtilities.convertLength(rStr,
                                             SVG_R_ATTRIBUTE,
                                             coordSystemType,
                                             uctx);
        if (r == 0) {
            return colors[colors.length-1];
        } else {
            Point2D c = SVGUtilities.convertPoint(cxStr,
                                                  SVG_CX_ATTRIBUTE,
                                                  cyStr,
                                                  SVG_CY_ATTRIBUTE,
                                                  coordSystemType,
                                                  uctx);

            Point2D f = SVGUtilities.convertPoint(fxStr,
                                                  SVG_FX_ATTRIBUTE,
                                                  fyStr,
                                                  SVG_FY_ATTRIBUTE,
                                                  coordSystemType,
                                                  uctx);

            // <!> FIXME: colorSpace ignored for radial gradient at this time
            return new RadialGradientPaint(c,
                                           r,
                                           f,
                                           offsets,
                                           colors,
                                           spreadMethod,
                                           RadialGradientPaint.SRGB,
                                           transform);
        }
    }
}
