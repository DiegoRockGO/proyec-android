package com.uy.csi.sige.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Propiedades {

    private Integer firstResult;
    private Integer maxResults;
    private Boolean inDeletes;
    private String query;

    public Propiedades(){
        includeDeletes( false );
    }

    List<Propiedad> listaPropiedadesAnd = new ArrayList<>();
    public List<Propiedad> getListaPropiedadesAnd() {
        return listaPropiedadesAnd;
    }

    public void setListaPropiedadesAnd(List<Propiedad> listaPropiedadesAnd) {
        this.listaPropiedadesAnd = listaPropiedadesAnd;
    }

    List<Propiedad> listaPropiedadesOr = new ArrayList<Propiedad>();

    public List<Propiedad> getListPropertiesOr() {
        return listaPropiedadesOr;
    }

    public void setListPropertiesOr(List<Propiedad> listPropertiesOr) {
        this.listaPropiedadesOr = listPropertiesOr;
    }

    List<PropertyOrder> listPropertiesOrder = new ArrayList<PropertyOrder>();
    List<Map<String, Object>> listaParamsQuery = new ArrayList<Map<String, Object>>();

    /**
     * Método para agregar campo de Secuencia como filtro a la query dinámica
     *
     * @param campo
     * @param valor
     */
    public void agregarPropiedad(String campo, Object valor) {
        agregarPropiedad(campo, valor, false);
    }

    /**
     * Método para agregar campo(como igual/like) de Secuencia como filtro a la
     * query dinámica <br/>
     * <br/>
     * <b>Ejemplo: addProperty("nombreCampo", "texto" + "%", true/false)</b>
     *
     * @param campo
     * @param valor
     * @param like
     */
    public void agregarPropiedad(String campo, Object valor, Boolean like) {
        listaPropiedadesAnd.add(new Propiedad(campo, valor, like));
        Collections.sort(listaPropiedadesAnd);
    }

    /**
     * Permite agregar una propiedad con el conector AND y el operador personalizado.
     * @param campo
     * @param valor
     * @param operador
     */
    public void agregarPropiedadAnd(String campo, Object valor, String operador) {
        listaPropiedadesAnd.add(new Propiedad(campo, valor, operador));
        Collections.sort(listaPropiedadesAnd);
    }

    /**
     * Permite agregar una propiedad asociada con el conector OR.
     * @param campo
     * @param valor
     * @param like
     */
    public void agregarPropiedadOr(String campo, Object valor, Boolean like) {
        listaPropiedadesOr.add(new Propiedad(campo, valor, like));
        Collections.sort(listaPropiedadesOr);
    }

    /**
     * Permite agregar una propiedad con el conector OR y el operador personalizado.
     * @param campo
     * @param valor
     * @param operador
     */
    public void agregarPropiedadOr(String campo, Object valor, String operador) {
        listaPropiedadesOr.add(new Propiedad(campo, valor, operador));
        Collections.sort(listaPropiedadesOr);
    }

    /**
     * Método para agregar una propiedad dentro del ORDER BY, por defecto en
     * forma ASCENDENTE
     *
     * @param campo
     */
    public void addPropertyOrder(String campo) {
        addPropertyOrder(campo, false);
    }

    /**
     * Método para agregar una propiedad dentro del ORDER BY, forma
     * ASCENDENTE/DESCENDENTE
     *
     * @param campo
     * @param descendente
     */
    public void addPropertyOrder(String campo, Boolean descendente) {
        listPropertiesOrder.add(new PropertyOrder(campo, descendente));
    }

    public Propiedad getProperty(String campo) {
        int index = Collections.binarySearch(listaPropiedadesAnd, new Propiedad(campo));
        if (index < 0) {
            return null;
        }
        return listaPropiedadesAnd.get(index);
    }

    public int size() {
        return listaPropiedadesAnd.size();
    }

    public int sizeOrder() {
        return listPropertiesOrder.size();
    }

    public Iterator<Propiedad> iterator() {
        return listaPropiedadesAnd.iterator();
    }

    public Iterator<PropertyOrder> iteratorOrder() {
        return listPropertiesOrder.iterator();
    }

    public void setListPropertiesOrder(List<PropertyOrder> listPropertiesOrder) {
        this.listPropertiesOrder = listPropertiesOrder;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public void setOrderBy(String... orderByArray) {
        setOrderBy(false, orderByArray);
    }

    public void setOrderBy(Boolean descendente, String... orderByArray) {
        for (String campo : orderByArray) {
            if (StringUtil.isEmpty(campo)) {
                continue;
            }
            addPropertyOrder(campo, descendente);
        }
    }

    public class Propiedad implements Comparable<Propiedad> {

        private String campo;
        private Object valor;
        private Boolean esParaLike;
        private String operador = "=";

        public Propiedad(String campo) {
            this.campo = campo;
        }

        public Propiedad(String campo, Object valor, Boolean forLike) {
            this.campo = campo;
            this.valor = valor;
            this.esParaLike = forLike;
        }

        public Propiedad(String campo, Object valor, String operador) {
            this.campo = campo;
            this.valor = valor;
            this.esParaLike = false;
            this.operador = operador;
        }

        public String getCampo() {
            return campo;
        }

        public void setCampo(String campo) {
            this.campo = campo;
        }

        public Object getValor() {
            return valor;
        }

        public void setValor(Object valor) {
            this.valor = valor;
        }

        public Boolean esParaLike() {
            return esParaLike;
        }

        public void setParaLike(Boolean paraLike) {
            this.esParaLike = paraLike;
        }

        public String getOperador() {
            return operador;
        }

        public void setOperador(String operador) {
            this.operador = operador;
        }


        public int compareTo(Propiedad o) {
            return getCampo().compareToIgnoreCase(o.getCampo());
        }

    }

    public class PropertyOrder {

        private String campo;
        private Boolean descendente;

        public PropertyOrder(String campo) {
            this.campo = campo;
            this.descendente = false;
        }

        public PropertyOrder(String campo, Boolean descendente) {
            this.campo = campo;
            this.descendente = descendente;
        }

        public String getCampo() {
            return campo;
        }

        public void setCampo(String campo) {
            this.campo = campo;
        }

        public Boolean getDescendente() {
            return descendente;
        }

        public void setDescendente(Boolean descendente) {
            this.descendente = descendente;
        }
    }

    public Boolean includeDeletes() {
        return inDeletes;
    }

    /**
     * para incluir eliminados enviar el parametro inDeletes en true,
     * por defecto no se incluiran los registros eliminados
     *
     * @param inDeletes
     */
    public void includeDeletes(Boolean inDeletes) {
        this.inDeletes = inDeletes;
    }

    /**
     * para agregar query adicional
     *
     * @return
     */
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Map<String, Object>> getListaParamsQuery() {
        return listaParamsQuery;
    }

    public void setListaParamsQuery(List<Map<String, Object>> listaParamsQuery) {
        this.listaParamsQuery = listaParamsQuery;
    }




}
