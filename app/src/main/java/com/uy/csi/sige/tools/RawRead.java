package com.uy.csi.sige.tools;

import android.content.Context;

import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.services.ItemSpinnerService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by dtrinidad on 05/08/2016.
 */
public class RawRead {

    public static List<ItemSpinner> getItemsSpinner(Context c, int xml,int type,ItemSpinnerService service) {
        List<ItemSpinner> lista = new ArrayList<>();
        try {
            InputStream is = c.getResources().openRawResource(xml);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            Element root = dom.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName("ROW");

            for (int i = 0; i < nodeList.getLength(); i++) {
                ItemSpinner item = new ItemSpinner();
                Element element = (Element) nodeList.item(i);
                NodeList nodeList2 = element.getElementsByTagName("COLUMN");

                if (nodeList2 != null && nodeList2.getLength() > 0) {
                    for (int j = 0; j < nodeList2.getLength(); j++) {

                        String value = "";
                        Element el = (Element) nodeList2.item(j);
                        String etiqueta = el.getAttribute("NAME");
                        if (etiqueta.equalsIgnoreCase("ID")) {
                            Node n = el.getFirstChild();
                            if (n != null) {
                                value = n.getNodeValue();
                                item.setIdItem(Integer.parseInt(value));
                            }


                        }else if (etiqueta.equalsIgnoreCase("CODIGO")) {
                            Node n = el.getFirstChild();
                            if (n != null) {
                                value = n.getNodeValue();

                            }

                            item.setCodigo(value);
                        }  else if (etiqueta.equalsIgnoreCase("DESCRIPTION")) {
                            Node n = el.getFirstChild();
                            if (n != null) {
                                value = n.getNodeValue();
                            }
                            item.setDescription(value);

                        } else if (etiqueta.equalsIgnoreCase("ID_FATHER")) {
                            Node n = el.getFirstChild();
                            if (n != null ) {
                                value = n.getNodeValue();
                                item.setIdFather(Integer.parseInt(value));
                            }


                        }else if (etiqueta.equalsIgnoreCase("GROUP_COL")) {
                            Node n = el.getFirstChild();
                            if (n != null ) {
                                value = n.getNodeValue();
                                item.setGroupCol(Integer.parseInt(value));
                            }
                        }

                    }
                }
                item.setState(ConstanteNumeric.OPEN);
                item.setType(type);

                try {
                    System.out.println("**********Guardando item");
                    service.save(item);
                    lista.add(item);
                }catch (Exception e){
                    e.getMessage();
                    System.out.println("**********Exception al guardar item spinner");
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
