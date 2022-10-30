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

import static org.wikidata.wdtk.examples.FetchOnlineDataExample.printDocumentation;

public class DatosWiki {

    public DatosWiki() {
    }

    public static String DatosMadrid() throws IOException, MediaWikiApiErrorException {
        ExampleHelpers.configureLogging();
        printDocumentation();

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(
                ApiConnection.getWikidataApiConnection(),
                Datamodel.SITE_WIKIDATA);
        wbdf.getFilter().setLanguageFilter(Collections.singleton("es"));
        //---------------
        EntityDocument madrid = wbdf.getEntityDocument("Q2807");
        Statement stat=  ((ItemDocument) madrid).findStatement("P2044");
        return stat.getClaim().getValue().toString();
    }
    public static void DatosEventos(ArrayList<Eventos> eventos){

    }
    public static void main(String[] args){

    }
}

