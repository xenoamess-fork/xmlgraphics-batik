/*

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================

 Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.

 4. The names "Batik" and  "Apache Software Foundation" must  not  be
    used to  endorse or promote  products derived from  this software without
    prior written permission. For written permission, please contact
    apache@apache.org.

 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation. For more  information on the
 Apache Software Foundation, please see <http://www.apache.org/>.

*/

package org.apache.batik.svggen;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.geom.AffineTransform;

/**
 * This test validates fix to Bug #4945 which checks that 
 * the generator handles Font transform.
 *
 * @author <a href="mailto:vhardy@apache.org">Vincent Hardy</a>
 * @version $Id$
 */
public class Bug4945 implements Painter {
    public void paint(Graphics2D g){
        Font origFont = g.getFont(); 

        g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                           java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
       
        // 1) create scaled font
        Font font = origFont.deriveFont(AffineTransform.getScaleInstance(1.5, 3));
        g.setFont(font);
        g.drawString("Scaled Font", 20, 40);

        // 2) create translated font
        font = origFont.deriveFont(AffineTransform.getTranslateInstance(50, 20));
        g.setFont(font);
        g.drawString("Translated Font", 20, 80);
        g.drawLine(20, 80, 120, 80);

        // 3) create sheared font
        font = origFont.deriveFont(AffineTransform.getShearInstance(.5, .5));
        g.setFont(font);
        g.drawString("Sheared Font", 20, 120);

        // 4) create rotated font 
        font = origFont.deriveFont(AffineTransform.getRotateInstance(Math.PI/4));
        g.setFont(font);
        g.drawString("Rotated Font", 220, 120);
    }
}

