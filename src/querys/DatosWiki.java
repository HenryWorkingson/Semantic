package querys;

import Clases.Bibliotecas;
import Clases.Eventos;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.examples.ExampleHelpers;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static org.wikidata.wdtk.examples.FetchOnlineDataExample.printDocumentation;

public class DatosWiki {
    /*
        Llamada de wiki data tools
     */
    public DatosWiki() {
    }


     /*
        Entra un ArrayList<Bibliotecas> sin informacion de wikidata (nivel de mar) y devuelve un ArrayList<Bibliotecas> con informacion de wikidata
     */
    public ArrayList<Bibliotecas> DatosMadrid(ArrayList<Bibliotecas> list) throws IOException, MediaWikiApiErrorException {
        ExampleHelpers.configureLogging();
        printDocumentation();
        //Llama al api de wiki database para establecer las conexiones y establezco el lenguaje en español
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(
                ApiConnection.getWikidataApiConnection(),
                Datamodel.SITE_WIKIDATA);
        wbdf.getFilter().setLanguageFilter(Collections.singleton("es"));
        //Devuelve el sujeto Q2807 (Madrid), dentro de este sujeto busco el predicado P2044(nivel de mar de Madrid)
        EntityDocument madrid = wbdf.getEntityDocument("Q2807");
        Statement stat=  ((ItemDocument) madrid).findStatement("P2044");
        //convierto la respuesta en String
        String respuesta= stat.getClaim().getValue().toString().split(" ")[0];
        //relleno la lista de bibliotecas con la solucion dado.
        list.forEach(n->n.setNivelDeMar(respuesta));
        return list;
    }
     /*
        Entra un ArrayList<Eventos> sin informacion de wikidata (calle, coordenadas) y devuelve un ArrayList<Eventos> con informacion de wikidata
     */
    public ArrayList<Eventos> DatosEventos(ArrayList<Eventos> eventos) throws IOException, MediaWikiApiErrorException {
        if (eventos.size()==0)
            return eventos;
        ExampleHelpers.configureLogging();
        printDocumentation();
        //Llama al api de wiki database para establecer las conexiones y establezco el lenguaje en español
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(
                ApiConnection.getWikidataApiConnection(),
                Datamodel.SITE_WIKIDATA);
        wbdf.getFilter().setLanguageFilter(Collections.singleton("es"));
        //saco el id del biblioteca
        String id=eventos.get(0).getWikidataEvento();
        //Devuelve el sujeto biblioteca correspondiente, dentro de este sujeto busco el predicado P6375 (calle de biblioteca)
        // y P625 (calle de coordenadas del biblioteca correspondiente)
        EntityDocument madrid = wbdf.getEntityDocument(id);
        Statement Calle=  ((ItemDocument) madrid).findStatement("P6375");
        Statement Coordenadas=  ((ItemDocument) madrid).findStatement("P625");
        String calle= Calle.getClaim().getValue().toString().split("\"")[1];
        String coord=Coordenadas.getClaim().getValue().toString().split("\\(")[0];
        //relleno la lista de bibliotecas con la solucion dado.
        for (int i=0;i<eventos.size();i++){
            eventos.get(i).setWikiCoor(coord);
            eventos.get(i).setWikiStreet(calle);
        }
        return eventos;
    }
}

