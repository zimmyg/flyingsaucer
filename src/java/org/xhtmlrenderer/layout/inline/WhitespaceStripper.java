package org.xhtmlrenderer.layout.inline;


import org.w3c.dom.Node;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.Context;
import org.xhtmlrenderer.render.InlineBox;

import java.awt.*;
import java.util.regex.Pattern;

public class WhitespaceStripper {
    public static final String SPACE = " ";
    public static final String EOL = "\n";
    // update this to work on linefeeds on multiple platforms;
    final Pattern linefeed_space_collapse = Pattern.compile("\\s+\\n\\s+");
    final Pattern linefeed_to_space = Pattern.compile("\\n");
    final Pattern tab_to_space = Pattern.compile("\\t");
    final Pattern space_collapse = Pattern.compile("( )+");


    public InlineBox createInline(Context c, Node node, String text, InlineBox prev, InlineBox prev_align, int avail, int max, Font font) {
        InlineBox inline = new InlineBox();
        inline.setNode(node);
        CalculatedStyle style = c.css.getStyle(node);
        inline.whitespace = getWhitespace(style);
        
        // prepare a new inline with a substring that goes
        // from the end of the previous (if applicable) to the
        // end of the master string
        if (prev == null || prev.getNode() != node) {
            text = stripWhitespace(style, prev, text);
            inline.setMasterText(text);
            inline.setSubstring(0, text.length());
        } else {
            //grab text from the previous inline
            text = prev.getMasterText();
            inline.setMasterText(text);
            inline.setSubstring(prev.end_index, text.length());
        }

        Breaker.breakText(c, inline, prev, prev_align, avail, max, font);
        BoxBuilder.prepBox(c, inline, prev_align, font);
        return inline;
    }


    // this function strips all whitespace from the text according to the
    // CSS 2.1 spec on whitespace handling. It accounts for the different
    // whitespace settings like normal, nowrap, pre, etc
    public String stripWhitespace(CalculatedStyle style, InlineBox prev, String text) {

        String whitespace = getWhitespace(style);
        //u.p("stripWhitespace: text = -" + text + "-");
        //u.p("whitespace = " + whitespace);
        

        // do step 1
        if (whitespace.equals("normal") ||
                whitespace.equals("nowrap") ||
                whitespace.equals("pre-line")) {
            text = linefeed_space_collapse.matcher(text).replaceAll(SPACE);
        }
        //u.p("step 1 = \"" + text + "\"");
        

        // do step 2
        // pull out pre's for breaking
        // still not sure here
        

        // do step 3
        // convert line feeds to spaces
        if (whitespace.equals("normal") ||
                whitespace.equals("nowrap")) {
            text = linefeed_to_space.matcher(text).replaceAll(SPACE);
        }
        //u.p("step 3 = \"" + text +"\"");
        

        // do step 4
        if (whitespace.equals("normal") ||
                whitespace.equals("nowrap") ||
                whitespace.equals("pre-line")) {

            text = tab_to_space.matcher(text).replaceAll(SPACE);
            //u.p("step 4.1 = \"" + text + "\"");

            text = space_collapse.matcher(text).replaceAll(SPACE);
            //u.p("step 4.2 = \"" + text + "\"");

            // collapse first space against prev inline
            if (text.startsWith(SPACE) &&
                    (prev != null) &&
                    (prev.whitespace.equals("normal") ||
                    prev.whitespace.equals("nowrap") ||
                    prev.whitespace.equals("pre-line")) &&
                    (prev.getSubstring().endsWith(SPACE))) {
                text = text.substring(1, text.length());
            }
            //u.p("step 4.3 = \"" + text + "\"");

        }

        //u.p("final text = \"" + text + "\"");
        return text;
    }


    public void unbreakable(InlineBox box, int n) {
        if (box.start_index == -1) {
            box.start_index = 0;
        }
        box.setSubstring(box.start_index, box.start_index + n);
        return;
    }

    public String getWhitespace(CalculatedStyle style) {
        String whitespace = style.getStringProperty("white-space");
        if (whitespace == null) {//should never happen
            whitespace = "normal";
        }
        return whitespace;
    }

    public static void df(Context c, String text, Font f) {
        /*
        u.p("-------------------------");
        ((Graphics2D)c.getGraphics()).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            
        ((Graphics2D)c.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        FontMetrics fm = c.getGraphics().getFontMetrics();
        u.p("graphics = " + c.getGraphics());
        u.p("fm = " + fm);
        u.p("text = -" + text + "-");
        u.p("real len = " + fm.stringWidth(text));
        u.p("real height = " + fm.getHeight());
        u.p("-------------------------");
        */
    }

}

