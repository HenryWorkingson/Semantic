package querys;

import Clases.Bibliotecas;
import Clases.Eventos;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SPARQL {
    //ubicacion del fichero RDF
    static String uri = "C:\\Users\\donyi\\Documents\\GitHub\\Curso2022-2023\\HandsOn\\Group45\\Semantic APP\\knowledge-graph-linked.nt";
    public SPARQL() {

    }
    //convertir ArrayList<QuerySolution> a ArrayList<Bibliotecas>
    public ArrayList<Bibliotecas> conv(ArrayList<QuerySolution> arr) throws IOException, MediaWikiApiErrorException {
        ArrayList<Bibliotecas> sol= new ArrayList<>();
        for(int i=0;i<arr.size();i++){
            String a=arr.get(i).toString();
            String[] b =a.split("\"");
            sol.add(ConvertBiblio(b));
        }
        return sol;
    }
    //Convertir array String al Biblioteca
    public Bibliotecas ConvertBiblio(String[] s) throws IOException, MediaWikiApiErrorException {
        Bibliotecas b=new Bibliotecas();
        for(int i=1;i<s.length;i+=2){
            switch (i){
                case 1:
                    b.setPk(s[i]);
                    break;
                case 3:
                    b.setDireccion(s[i]);
                    break;
                case 5:
                    b.setNombre(s[i]);
                    break;
                case 7:
                    b.setDescripcion(s[i]);
                    break;
                case 9:
                    b.setHorario(s[i]);
                    break;
                case 11:
                    b.setTelefono(s[i]);
                    break;
                case 13:
                    b.setUrl(s[i]);
                    break;
            }
        }
        return b;
    }
    //Devuelve una lista de bilbiotecas
    public ArrayList<Bibliotecas> queryBiblioteca() throws IOException, MediaWikiApiErrorException {
        ArrayList<QuerySolution> lista = new ArrayList<QuerySolution>();
        Query query = null;
        //Cargar el fichero
        Model model = FileManager.get().loadModel(uri);
        //Query SPARQL que devuelve los datos de bibliotecas
        String queryString =
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "SELECT DISTINCT ?Nombre ?Descripcion ?Horario ?Localiza ?Telefono ?Url ?pk\n" +
                        "\t\n" +
                        "\tWHERE {\n" +
                        "  \t\t?s rdf:type <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/Biblioteca>. \n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/haspk> ?pk .\n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasdireccion> ?Localiza .\n" +
                        "\t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasnombre> ?Nombre .\n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasdescription> ?Descripcion .\n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hashorarioBib> ?Horario .\n" +
                        "\t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hastelefono> ?Telefono .\n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasbiblioteca-url> ?Url .\n" +
                        "\n" +
                        "\t}";
        //ejecuta el query
        query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while(results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                lista.add(soln);
            }
        } finally {
            qexec.close();
        }
        //Genera una Arraylista de Biblioteca
        ArrayList<Bibliotecas> bibliotecas = new DatosWiki().DatosMadrid(conv(lista));
        return bibliotecas;
    }
    //convertir ArrayList<QuerySolution> a ArrayList<Eventos>
    public ArrayList<Eventos> convEventos(ArrayList<QuerySolution> arr){
        ArrayList<Eventos> sol= new ArrayList<>();
        for(int i=0;i<arr.size();i++){
            String a=arr.get(i).toString();
            String[] b =a.split("\"");
            sol.add(ConvertEvento(b));
        }
        return sol;
    }
    //Convertir String a Evento
    public Eventos ConvertEvento(String[] s) {
        Eventos b = new Eventos();
        for (int i = 1; i < s.length; i += 2) {
            switch (i) {
                case 1:
                    b.setNombreEvento(s[i]);
                    break;
                case 3:
                    b.setFechaInicio(s[i]);
                    break;
                case 5:
                    b.setFechaFin(s[i]);
                    break;
                case 7:
                    b.setHoraEmpiezo(s[i]);
                    break;
                case 9:
                    b.setNombreIntalacion(s[i]);
                    break;
                case 11:
                    b.setWikidataEvento(s[i].split("/")[4]);
                    break;
            }
        }
        return b;
    }
    //Devuelve una lista de Eventos
    public ArrayList<Eventos> queryEventos(String id ) throws IOException, MediaWikiApiErrorException {
        ArrayList<QuerySolution> lista = new ArrayList<QuerySolution>();
        Query query = null;
        //Cargar el fichero
        Model model = FileManager.get().loadModel(uri);
        //Query SPARQL que devuelve los datos de bibliotecas
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "SELECT DISTINCT ?Titulo ?FechaInicio ?FechaFin ?Hora ?NombreInstalacion ?wikidata\n" +
                        "\tWHERE {\n" +
                        "  \t\t?s rdf:type <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/Biblioteca>. \n" +
                        "  \t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/haspk> \t\""+id+"\"^^<http://www.w3.org/2001/XMLbase#int> .\n" +
                        "\t\t?s <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasEvento> ?Eventos .\n" +
                        "   \t\t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hastitulo> ?Titulo .\n" +
                        "    \t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasfecha-ini> ?FechaInicio .\n" +
                        "    \t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasfecha-fin> ?FechaFin .\n" +
                        "  \t\t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hashoraEvent> ?Hora .\n" +
                        "  \t\t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/hasnombre-instalacion> ?NombreInstalacion .\n" +
                        "\t\t?Eventos <http://bibliotecaevento.linkeddata.es/bibliotecas/ontology/wikidata-evento> ?wikidata .\n" +
                        "\t}";
        //ejecuta el query
        query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while(results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                //System.out.println(soln);
                lista.add(soln);
            }
        } finally {
            qexec.close();
        }
        //Genera una Arraylista de Evento
        ArrayList<Eventos> sol =new DatosWiki().DatosEventos(convEventos(lista));
        return sol;
    }
}
