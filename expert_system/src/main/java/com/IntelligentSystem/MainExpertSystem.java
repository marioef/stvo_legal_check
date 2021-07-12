package com.IntelligentSystem;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainExpertSystem {

    private static final Logger log = LoggerFactory.getLogger(MainExpertSystem.class);

    /**
     * An SPARQL example
     */
    private static final String query_parking = String.join(System.lineSeparator(),
            "                PREFIX rdf:       <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
            "                PREFIX owl:        <http://www.w3.org/2002/07/owl#>",
            "                PREFIX rdfs:       <http://www.w3.org/2000/01/rdf-schema#>",
            "                PREFIX sys:        <http://www.semanticweb.org/mario/ontologies/2021/6/untitled-ontology-9#>                                                                                ",
            "                                                                                                                                       ",
            "                SELECT ?car ?lane                                                                                                  ",
            "                WHERE {                                                                                                     ",
            "                {                                                                                                                     ",
            "                  ?car rdf:type sys:Car .                                                                                                                 ",
            "                  ?lane rdf:type sys:OneWayLane .                                                 ",
            "                }}");

    private static final String query_intersection = String.join(System.lineSeparator(),
            "                PREFIX rdf:       <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
            "                PREFIX owl:        <http://www.w3.org/2002/07/owl#>",
            "                PREFIX rdfs:       <http://www.w3.org/2000/01/rdf-schema#>",
            "                PREFIX sys:        <http://www.semanticweb.org/mario/ontologies/2021/6/untitled-ontology-9#>                                                                                ",
            "                                                                                                                                       ",
            "                SELECT ?car                                                                                                  ",
            "                WHERE {                                                                                                     ",
            "                {                                                                                                                     ",
            "                  ?car rdf:type sys:Car .                                                                                                                 ",
            "                  ?lane rdf:type ?laneType .                                                 ",
            "                  ?laneType rdfs:subClassOf sys:Lane .                                                 ",
            "                  ?car sys:isOn ?lane .                                                 ",
            "                  ?car sys:Situation \"not legal\" .                                                 ",

            "                }}");


    private static final String document_parking = "../parking.owl";
    private static final String document_intersection = "../intersection_not_legal.owl";

    /**
     * Examples of SPARQL with apache jena
     *
     * @param args none
     */
    public static void main(String[] args) {

        log.info("Example SPARQL query: ");
        log.info(query_intersection);

        log.info("Creates the OWL model");
        //OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
        Model defmodel = ModelFactory.createDefaultModel();

        log.info("Reads the document");
        defmodel.read(document_intersection);

        //List<Rule> rules = Rule.parseRules( ruleSrc );
        //GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);

        GenericRuleReasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "../rules" ) );
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        InfModel model = ModelFactory.createInfModel(reasoner, defmodel);

        log.info("Query execution is created for the example query");
        QueryExecution qexec = QueryExecutionFactory.create(query_intersection, model);

        log.info("Obtains the result set");
        ResultSet results = qexec.execSelect();

        log.info("Iterates over the result set");
        while (results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            log.info("Solution: " + sol);
        }
        /*
        StmtIterator it = model.listStatements();

        while(it.hasNext()) {
            Statement stmt = it.nextStatement();

            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            System.out.println(predicate.getLocalName() + " " + predicate.getNameSpace());
            //System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
        }
        */
        /*log.info("Obtain the properties of the model");
        ExtendedIterator<ObjectProperty> properties = model.listObjectProperties();

		log.info("Iterates over the properties");
		while (properties.hasNext()) {
			log.info("Property: " + properties.next().getLocalName());
		}

		log.info("Obtains an iterator over individual resources");
		ExtendedIterator<Individual> individualResources = model.listIndividuals();

		log.info("Iterates over the resources");
		while (individualResources.hasNext()) {
			log.info("Individual resource: " + individualResources.next());
		}

		log.info("Obtains an extended iterator over classes");
		ExtendedIterator<OntClass> classes = model.listClasses();

		log.info("Iterates over the classes");
		while (classes.hasNext()) {
			log.info("Class: " + classes.next().toString());
		}*/
    }
}