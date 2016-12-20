import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by mirkodimartino on 18/12/2016.
 */
public class main3 {


    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();
        model.read("rdfexample1.nt");
        System.out.println(model.size());
        Reasoner reasonerexample = ReasonerRegistry.getOWLMiniReasoner();
        InfModel infmodelexample = ModelFactory.createInfModel(reasonerexample,model);
        System.out.println(infmodelexample.size());


    }
}
