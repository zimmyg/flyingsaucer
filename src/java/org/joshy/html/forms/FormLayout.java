
/* 
 * {{{ header & license 
 * Copyright (c) 2004 Joshua Marinacci 
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; either version 2.1 
 * of the License, or (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. 
 * }}} 
 */

package org.joshy.html.forms;

import java.util.*;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;
import org.joshy.u;
import org.joshy.html.box.Box;
import org.joshy.html.*;
import org.w3c.dom.*;

public class FormLayout extends Layout {
    
    public FormLayout() {
    }
    
    public Box createBox(Context c, Node node) {
        FormBox form = new FormBox();
        form.node = node;
        return form;
    }
    
    public Box layout(Context c, Element elem) {
        FormBox form = (FormBox)createBox(c,elem);
        layoutChildren(c, form);
        return form;
    }
    
    public Box layoutChildren(Context c, Box box) {
        u.p("calling layout on the children with the box: " + box);
        return super.layoutChildren(c,box);
    }

    public void paint(Context c, Box box) {
        paintChildren(c,box);
    }
    public void paintChildren(Context c, Box box) {
        u.p("calling paint on the children : " + box);
        super.paintChildren(c, box);
    }
    
}
