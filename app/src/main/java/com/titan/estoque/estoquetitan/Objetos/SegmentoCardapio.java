package com.titan.estoque.estoquetitan.Objetos;

import java.util.ArrayList;

public class SegmentoCardapio {

    private String pName;

    private ArrayList<ProdutoCardapio> mSubCategoryList;

    public SegmentoCardapio(String pName, ArrayList<ProdutoCardapio> mSubCategoryList) {
        super();
        this.pName = pName;
        this.mSubCategoryList = mSubCategoryList;
    }

    public SegmentoCardapio(String pName) {
        super();
        this.pName = pName;
    }

    public void addProduto(ProdutoCardapio produto)
    {
        mSubCategoryList.add(produto);
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public ArrayList<ProdutoCardapio> getmSubCategoryList() {
        return mSubCategoryList;
    }

    public void setmSubCategoryList(ArrayList<ProdutoCardapio> mSubCategoryList) {
        this.mSubCategoryList = mSubCategoryList;
    }

    /**
     *
     * second level item
     *
     */

    public static class ProdutoCardapio {

        private String pSubCatName;
        private ArrayList<TamanhoCardapio> mItemListArray;

        public ProdutoCardapio(String pSubCatName,
                               ArrayList<TamanhoCardapio> mItemListArray) {
            super();
            this.pSubCatName = pSubCatName;
            this.mItemListArray = mItemListArray;
        }

        public ProdutoCardapio(String pSubCatName) {
            super();
            this.pSubCatName = pSubCatName;
        }

        public void addTamanho(TamanhoCardapio tamanho)
        {
            mItemListArray.add(tamanho);
        }

        public String getpSubCatName() {
            return pSubCatName;
        }

        public void setpSubCatName(String pSubCatName) {
            this.pSubCatName = pSubCatName;
        }

        public ArrayList<TamanhoCardapio> getmItemListArray() {
            return mItemListArray;
        }

        public void setmItemListArray(ArrayList<TamanhoCardapio> mItemListArray) {
            this.mItemListArray = mItemListArray;
        }

        /**
         *
         * third level item
         *
         */
        public static class TamanhoCardapio {

            private String itemName;
            private String itemPrice;
            private int id_produto;

            public TamanhoCardapio(String itemName, String itemPrice, int id_produto) {
                super();
                this.itemName = itemName;
                this.itemPrice = itemPrice;
                this.id_produto = id_produto;

            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }

            public int getId_produto(){return  id_produto;}

        }

    }

}