import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.io.IOException;

public class Main {
    /*
        Este pequeño programa muestra las bibliotecas de ciudad madrid y sus eventos correpondientes
        mediante api de Apache jena y wiki toolkit. El fichero que abre es un fichero RDF.
    */
    public static void main(String args[]) throws IOException, MediaWikiApiErrorException {
        new Ventanas();
    }
}