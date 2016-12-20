import org.apache.jena.graph.BlankNodeId;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerException;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.*;

/**
 * Created by mirkodimartino on 12/12/2016.
 */
public class main {

    public static void main(String[] args) throws IOException {

        String epStart = "http://www.L4All.com/epStart" ;
        String epEnd = "http://www.L4All.com/epEnd" ;
        String atTime = "http://www.semanticweb.org/mirko/ontologies/2016/1/LinkedIn#atTime" ;
        String hasEnd = "http://www.w3.org/2006/time#hasEnd";
        String hasBeginning = "http://www.w3.org/2006/time#hasBeginning";
        String label = "http://www.w3.org/2000/01/rdf-schema#label";
        String inXml = "http://www.w3.org/2006/time#inXSDDateTime";
        String type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
        String learnerEpisode = "http://www.L4All.com/learnerEpisode";
        String educationalEpisode = "http://www.L4All.com/Educational_Episode";
        String occupationalEpisode = "http://www.L4All.com/Occupational_Episode";
        String hasEducation = "http://www.semanticweb.org/mirko/ontologies/2016/1/LinkedIn#hasEducation";
        String hasExperience = "http://www.semanticweb.org/mirko/ontologies/2016/1/LinkedIn#hasExperience";

        Model model = ModelFactory.createDefaultModel();

        //model.read("LinkedinData.RDF_XML","RDF/XML");
        //System.out.println(model.size());
        //model.read("seed.nt");
        //System.out.println(model.size());
        //model.read("Ontology.RDF");
        //System.out.println(model.size());
        //model.read("L4Allontology.nt");
        //model.read("LinkedDataProfilesInL4All.nt");
        //System.out.println("Infmodel size before:" + model.size());
        model.read("CoreData+mappings.nt");
        model.read("L4Allontology.nt");
        model.read("Ontology.RDF");
        System.out.println("Infmodel size before:" + model.size());

        //InfModel infmodel = ModelFactory.createRDFSModel(model);
        //InfModel infmodel = null;
        //System.out.println("Infmodel size after:" + infmodel.size());
        File file = new File("MaterialisedData.nt");
        Model modelexample = ModelFactory.createDefaultModel();
        Reasoner reasonerexample = ReasonerRegistry.getOWLMiniReasoner();
        Reasoner reasoner = ReasonerRegistry.getOWLMiniReasoner();

        modelexample.read("rdfexample.nt");
        InfModel infmodelexample = ModelFactory.createInfModel(reasonerexample,modelexample);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, model);
        //infmodelexample.write(System.out, "NT");
        System.out.println("infmodel size after: " +infmodel.size());
        infmodel.read("schemaMappings.nt");
        System.out.println("infmodel size after schema mappings: " +infmodel.size());
        //infmodel.write(System.out,"NT");

        FileOutputStream outputstream = null;
        try {
             outputstream =  new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileWriter out = new FileWriter("MaterialisedData2.nt");



        //ParameterizedSparqlString pss = new ParameterizedSparqlString();
       // pss.setCommandText("SELECT ?test ?stub  WHERE { ?stub  ?epEnd  ?test }");
       // Node n = NodeFactory.createURI("http://example.org");
       // pss.setParam("stub", n);
        // System.out.println(pss);




        boolean added = true;
        while (added) {
            System.out.println("entra while");
            Model temporarymodel = ModelFactory.createDefaultModel();

            added = false;

           /* // atTime, hasEnd , inXml -> epEnd
            String queryString =

                    "SELECT ?ep ?end " +
                            "WHERE { ?ep  <" + atTime + "> ?x . ?x <" + hasEnd + "> ?y . ?y <" + inXml + "> ?end } ";
           // String queryString2 = "SELECT ?ep ?end " +
             //       "WHERE { ?ep  " + label +  "?end } ";
            Query query = QueryFactory.create(queryString);
            //Query query1 = QueryFactory.create(pss.toString());
            QueryExecution qexec = QueryExecutionFactory.create(query, infmodel);
            ResultSet results = qexec.execSelect();
            Property epEndPropr = ResourceFactory.createProperty(epEnd);
            ReasonerRegistry.getOWLMiniReasoner();


            while (results.hasNext()) {
                System.out.println("entrato1");
                QuerySolution binding = results.nextSolution();
                ParameterizedSparqlString parString = new ParameterizedSparqlString();
                parString.setCommandText(" ASK WHERE { ?x <"+ epEnd + "> ?y }");



                Resource subj =  binding.getResource("ep");
                System.out.println("Subject: " + subj);
                parString.setParam("x", subj);
                Literal node =  binding.getLiteral("end");

                System.out.println("Object: " + node);
                parString.setParam("y", node);
                if (!node.isAnon()&&!subj.isAnon()) {
                    System.out.println(parString.toString());
                    Query boolquery = QueryFactory.create(parString.toString());
                    System.out.println(boolquery.toString());
                    QueryExecution booleanex = QueryExecutionFactory.create(boolquery, infmodel);
                    boolean result = booleanex.execAsk();


                    if (!result) {

                        temporarymodel.add(subj, epEndPropr, node);

                        temporarymodel.write(System.out,"NT");

                        added = true;
                        QueryExecution booleanex1 = QueryExecutionFactory.create(boolquery, temporarymodel);
                        System.out.println(booleanex1.execAsk());


                    }
                }



                // if (subj.isAnon()) System.out.println("blank");
            }
            infmodel.add(temporarymodel);
            */


            // epEnd -> atTime, hasEnd , inXml
            String queryString1 =

                    "SELECT ?ep ?end " +
                            "WHERE { ?ep  <" + epEnd + "> ?end } ";
            // String queryString2 = "SELECT ?ep ?end " +
            //       "WHERE { ?ep  " + label +  "?end } ";
            Query query1 = QueryFactory.create(queryString1);
            //Query query1 = QueryFactory.create(pss.toString());
            QueryExecution qexec1 = QueryExecutionFactory.create(query1, infmodel);
            ResultSet results1 = qexec1.execSelect();




            while (results1.hasNext()) {
                //System.out.println("entrato2");
                QuerySolution binding = results1.nextSolution();
                ParameterizedSparqlString parString = new ParameterizedSparqlString();
                parString.setCommandText(" ASK WHERE { ?ep  <" + atTime + "> ?x . ?x <" + hasEnd + "> ?y . ?y <" + inXml + "> ?end }");



                Resource subj =  binding.getResource("ep");
                //System.out.println("Subject: " + subj);
                parString.setParam("ep", subj);
                Literal node =  binding.getLiteral("end");

                //System.out.println("Object: " + node);
                parString.setParam("end", node);
                if (!node.isAnon()&&!subj.isAnon()) {
                    //System.out.println(parString.toString());
                    Query boolquery = QueryFactory.create(parString.toString());
                    //System.out.println(boolquery.toString());
                    QueryExecution booleanex = QueryExecutionFactory.create(boolquery, infmodel);
                    boolean result = booleanex.execAsk();
                    //System.out.println(result);


                    if (!result) {


                        Property atTimeprop = ResourceFactory.createProperty(atTime);
                        Property hasEndprop = ResourceFactory.createProperty(hasEnd);
                        Property inXmlprop = ResourceFactory.createProperty(inXml);
                        temporarymodel.createResource(subj.toString()).addProperty(atTimeprop,
                                temporarymodel.createResource().addProperty(hasEndprop,temporarymodel.createResource().addProperty(inXmlprop,node)));
                        added = true;
                        QueryExecution booleanex1 = QueryExecutionFactory.create(boolquery, temporarymodel);
                        //System.out.println(booleanex1.execAsk());


                    }
                }



                // if (subj.isAnon()) System.out.println("blank");
            }
            infmodel.add(temporarymodel);
            System.out.println("size after first step of chase: " + infmodel.size());


            // epStart -> atTime, hasBeginning , inXml
            String queryString2 =

                    "SELECT ?ep ?end " +
                            "WHERE { ?ep  <" + epStart + "> ?end } ";
            // String queryString2 = "SELECT ?ep ?end " +
            //       "WHERE { ?ep  " + label +  "?end } ";
            Query query2 = QueryFactory.create(queryString2);
            //Query query1 = QueryFactory.create(pss.toString());
            QueryExecution qexec2 = QueryExecutionFactory.create(query2, infmodel);
            ResultSet results2 = qexec2.execSelect();




            while (results2.hasNext()) {
                //System.out.println("entrato2");
                QuerySolution binding = results2.nextSolution();
                ParameterizedSparqlString parString = new ParameterizedSparqlString();
                parString.setCommandText(" ASK WHERE { ?ep  <" + atTime + "> ?x . ?x <" + hasBeginning + "> ?y . ?y <" + inXml + "> ?end }");



                Resource subj =  binding.getResource("ep");
                //System.out.println("Subject: " + subj);
                parString.setParam("ep", subj);
                Literal node =  binding.getLiteral("end");

                //System.out.println("Object: " + node);
                parString.setParam("end", node);
                if (!node.isAnon()&&!subj.isAnon()) {
                    //System.out.println(parString.toString());
                    Query boolquery = QueryFactory.create(parString.toString());
                    //System.out.println(boolquery.toString());
                    QueryExecution booleanex = QueryExecutionFactory.create(boolquery, infmodel);
                    boolean result = booleanex.execAsk();
                    //System.out.println(result);


                    if (!result) {


                        Property atTimeprop = ResourceFactory.createProperty(atTime);
                        Property hasBeginningprop = ResourceFactory.createProperty(hasBeginning);
                        Property inXmlprop = ResourceFactory.createProperty(inXml);
                        temporarymodel.createResource(subj.toString()).addProperty(atTimeprop,
                                temporarymodel.createResource().addProperty(hasBeginningprop,temporarymodel.createResource().addProperty(inXmlprop,node)));
                        added = true;
                        QueryExecution booleanex1 = QueryExecutionFactory.create(boolquery, temporarymodel);
                        //System.out.println(booleanex1.execAsk());


                    }
                }



                // if (subj.isAnon()) System.out.println("blank");
            }
            infmodel.add(temporarymodel);



            // learnerEpisode isA Educational  -> hasEducation
            String queryString3 =

                    "SELECT ?learner ?episode " +
                            "WHERE { ?learner  <" + learnerEpisode + "> ?episode . ?episode <"+type + "> <"+educationalEpisode+"> } ";
            // String queryString2 = "SELECT ?ep ?end " +
            //       "WHERE { ?ep  " + label +  "?end } ";
            Query query3 = QueryFactory.create(queryString3);
            //Query query1 = QueryFactory.create(pss.toString());
            QueryExecution qexec3 = QueryExecutionFactory.create(query3, infmodel);
            ResultSet results3 = qexec3.execSelect();



            System.out.println("size before third step of chase: " + infmodel.size());
            while (results3.hasNext()) {

                //System.out.println("entrato3");
                QuerySolution binding = results3.nextSolution();
                ParameterizedSparqlString parString = new ParameterizedSparqlString();
                parString.setCommandText(" ASK WHERE { ?learner  <" + hasEducation+ "> ?episode .  }");



                Resource subj =  binding.getResource("learner");
                //System.out.println("Subject: " + subj);
                parString.setParam("learner", subj);
                Resource node =  binding.getResource("episode");

                //System.out.println("Object: " + node);
                parString.setParam("episode", node);
                if (!node.isAnon()&&!subj.isAnon()) {
                    //System.out.println(parString.toString());
                    Query boolquery = QueryFactory.create(parString.toString());
                    //System.out.println(boolquery.toString());
                    QueryExecution booleanex = QueryExecutionFactory.create(boolquery, infmodel);
                    boolean result = booleanex.execAsk();
                    //System.out.println(result);


                    if (!result) {



                        Property hasEducationProperty = ResourceFactory.createProperty(hasEducation);
                        temporarymodel.createResource(subj.toString()).addProperty(hasEducationProperty,node);
                        added = true;
                        QueryExecution booleanex1 = QueryExecutionFactory.create(boolquery, temporarymodel);
                        //System.out.println(booleanex1.execAsk());


                    }
                }



                // if (subj.isAnon()) System.out.println("blank");
            }
            infmodel.add(temporarymodel);
            System.out.println("size after third step of chase: " + infmodel.size());


            // learnerEpisode isA Occupational  -> hasOccupation
            String queryString4 =

                    "SELECT ?learner ?episode " +
                            "WHERE { ?learner  <" + learnerEpisode + "> ?episode . ?episode <"+type + "> <"+occupationalEpisode+"> } ";
            // String queryString2 = "SELECT ?ep ?end " +
            //       "WHERE { ?ep  " + label +  "?end } ";
            Query query4 = QueryFactory.create(queryString4);
            //Query query1 = QueryFactory.create(pss.toString());
            QueryExecution qexec4 = QueryExecutionFactory.create(query4, infmodel);
            ResultSet results4 = qexec4.execSelect();



            System.out.println("size before forth step of chase: " + infmodel.size());
            while (results4.hasNext()) {

                //System.out.println("entrato3");
                QuerySolution binding = results4.nextSolution();
                ParameterizedSparqlString parString = new ParameterizedSparqlString();
                parString.setCommandText(" ASK WHERE { ?learner  <" + hasExperience + "> ?episode .  }");



                Resource subj =  binding.getResource("learner");
                //System.out.println("Subject: " + subj);
                parString.setParam("learner", subj);
                Resource node =  binding.getResource("episode");

                //System.out.println("Object: " + node);
                parString.setParam("episode", node);
                if (!node.isAnon()&&!subj.isAnon()) {
                    //System.out.println(parString.toString());
                    Query boolquery = QueryFactory.create(parString.toString());
                    //System.out.println(boolquery.toString());
                    QueryExecution booleanex = QueryExecutionFactory.create(boolquery, infmodel);
                    boolean result = booleanex.execAsk();
                    //System.out.println(result);


                    if (!result) {



                        Property hasExperienceproperty = ResourceFactory.createProperty(hasExperience);
                        temporarymodel.createResource(subj.toString()).addProperty(hasExperienceproperty,node);
                        added = true;
                        QueryExecution booleanex1 = QueryExecutionFactory.create(boolquery, temporarymodel);
                        //System.out.println(booleanex1.execAsk());


                    }
                }



                // if (subj.isAnon()) System.out.println("blank");
            }
            infmodel.add(temporarymodel);
            System.out.println("size after forth step of chase: " + infmodel.size());



        }



        //infmodel.write(outputstream, "N-TRIPLES");
        RDFDataMgr.write(outputstream, infmodel, Lang.NTRIPLES) ;


        System.out.println("size at the end: " + infmodel.size());

    }
}
