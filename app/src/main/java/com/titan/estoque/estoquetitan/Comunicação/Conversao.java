package com.titan.estoque.estoquetitan.Comunicação;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.titan.estoque.estoquetitan.Activitys.LoginActivity;
import com.titan.estoque.estoquetitan.Objetos.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Leonardo on 18/03/14.
 */
public class Conversao {
    private XStream xs;
    public Conversao()
    {
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("ddd", "_");
        xs = new XStream(new DomDriver("UTF-8", replacer));

        xs.omitField(Produto.class,"id_impressora");
        xs.omitField(Produto.class,"Segmento");
        xs.omitField(Produto.class,"impresso");

        xs.omitField(Ingrediente.class,"estoque_atual");
        xs.omitField(Ingrediente.class,"codigo_barra");

        xs.omitField(Usuario.class,"id_nivel");

        xs.alias("ItemImpressao", ItemImpressao.class);
        xs.alias("Usuario", Usuario.class);
        xs.alias("Produto", Produto.class);
        xs.alias("Imagem", Imagem.class);
        xs.alias("ItemProdutoTamanho", ItemProdutoTamanho.class);
        xs.alias("ItemProdutoTamanhoIngrediente", ItemProdutoTamanhoIngrediente.class);
        xs.alias("ProdutoTamanho", ProdutoTamanho.class);
        xs.alias("ProdutoTamanhoIngrediente", ProdutoTamanhoIngrediente.class);
        xs.alias("Segmento", Segmento.class);
        xs.alias("Tamanho", Tamanho.class);
        xs.alias("Ingrediente", Ingrediente.class);
        xs.alias("Divisor", Divisor.class);
        xs.alias("SegmentoTamanho", SegmentoTamanho.class);
        xs.alias("SubDivisor", SubDivisor.class);
        xs.alias("TamanhoDivisor", TamanhoDivisor.class);
        xs.alias("Cartao", Card.class);
    }

    public  <T> Document parseToXML(T dicionario)
    {
        xs.alias(dicionario.getClass().getSimpleName(), dicionario.getClass());
        return parserToXml(xs.toXML(dicionario));
    }

    private Document parserToXml(String dados)
    {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(dados)));
        } catch (ParserConfigurationException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Conversão XML", e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Conversão XML", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Conversão XML", e.getMessage());
            e.printStackTrace();
        }

        return  document;
    }

    public <T> T XMLtoClass(T dicionario, String xml)
    {
        xs.alias(dicionario.getClass().getSimpleName(), dicionario.getClass());
        if(xml != null && xml != "") {
            try {
                dicionario = (T) xs.fromXML(xml);
            } catch (RuntimeException e) {
                if (LoginActivity.logAtivo)
                    LoginActivity.erro.escreverLog("Conversão XML", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        else
            return null;

        return dicionario;
    }

    public String getStringFromDocument(Document doc)
    {
        try
        {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(TransformerException ex)
        {
            if (LoginActivity.logAtivo)
                LoginActivity.erro.escreverLog("Conversão XML", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
