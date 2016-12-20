import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by mirkodimartino on 18/12/2016.
 */
public class main1 {


    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();
        model.read("LinkedinData.RDF_XML","RDF/XML");
        System.out.println(model.size());
        model.read("seed.nt");
        System.out.println(model.size());
        model.read("LinkedDataProfilesInL4All.nt");
        System.out.println(model.size());

        File file = new File("CoreData.nt");
        FileOutputStream outputstream = null;
        try {
            outputstream =  new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        model.write(outputstream, "NT");

    }
}
