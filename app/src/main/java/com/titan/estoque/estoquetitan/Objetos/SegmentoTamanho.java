package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 26/03/2014.
 */
@XStreamAlias("SegmentoTamanho")
public class SegmentoTamanho {
    public int id_segmento;
    public int id_tamanho;

    public SegmentoTamanho(){}

    public SegmentoTamanho(int id_segmento,
                           int id_tamanho)
    {
        super();
        this.id_segmento = id_segmento;
        this.id_tamanho = id_tamanho;
    }
}
