/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mc2.util.miscellaneous;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 *
 * @author marco
 */
public class XMLTransformer {

    public XMLTransformer(){

    }
    public String getSource(Element element)
         throws TransformerConfigurationException,
                   TransformerException{

        return getSource(element.getOwnerDocument());

    }
    public String getSource(Document doc)
         throws TransformerConfigurationException,
                   TransformerException{

        doc.normalizeDocument();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //initialize StreamResult with File object to save to file
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        String xmlString = result.getWriter().toString();

        return xmlString;
    }

}
