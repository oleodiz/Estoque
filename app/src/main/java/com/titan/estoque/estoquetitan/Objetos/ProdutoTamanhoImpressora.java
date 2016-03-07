package com.titan.estoque.estoquetitan.Objetos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Leonardo on 05/05/2015.
 */
@XStreamAlias("ProdutoTamanhoImpressora")
public class ProdutoTamanhoImpressora {

        public Integer id_produto_tamanho;
        public Integer id_impressora;
        public Integer id_imagem;
        public String descricao;
        public Boolean isPadrao;

        public ProdutoTamanhoImpressora(){}

}
