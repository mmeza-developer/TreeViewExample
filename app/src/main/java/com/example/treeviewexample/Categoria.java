package com.example.treeviewexample;

import java.util.List;

public class Categoria {
    private Long indexCategoria;
    private Long IndexPadreCategoria;
    private Long codCategoria;
    private Long codPadreCategoria;
    private String nombre;
    private List<Categoria> childCategoria;

    public Long getIndexCategoria() {
        return indexCategoria;
    }

    public void setIndexCategoria(Long indexCategoria) {
        this.indexCategoria = indexCategoria;
    }

    public Long getIndexPadreCategoria() {
        return IndexPadreCategoria;
    }

    public void setIndexPadreCategoria(Long indexPadreCategoria) {
        IndexPadreCategoria = indexPadreCategoria;
    }

    public Long getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Long codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Long getCodPadreCategoria() {
        return codPadreCategoria;
    }

    public void setCodPadreCategoria(Long codPadreCategoria) {
        this.codPadreCategoria = codPadreCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Categoria> getChildCategoria() {
        return childCategoria;
    }

    public void setChildCategoria(List<Categoria> childCategoria) {
        this.childCategoria = childCategoria;
    }
}
