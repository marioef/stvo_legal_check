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
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainExpertSystem {

    private static final Logger log = LoggerFactory.getLogger(MainExpertSystem.class);

    /**
     * An SPARQL example
     */
    private static final String query = String.join(System.lineSeparator(),
            "                PREFIX rdf:       <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
            "                PREFIX owl:        <http://www.w3.org/2002/07/owl#>",
            "                PREFIX rdfs:       <http://www.w3.org/2000/01/rdf-schema#>",
            "                PREFIX sys:        <http://www.semanticweb.org/mario/ontologies/2021/6/untitled-ontology-9#>                                                                                ",
            "                                                                                                                                       ",
            "                SELECT ?individual                                                                                                  ",
            "                WHERE {                                                                                                     ",
            "                {                                                                                                                     ",
            "                  ?individual rdf:type sys:Car                                                                                                                 ",
            "                }}");

    private static final String document = "../parking.owl";

    /**
     * Examples of SPARQL with apache jena
     *
     * @param args none
     */
    public static void main(String[] args) {

        log.info("Example SPARQL query: ");
        log.info(query);

        log.info("Creates the OWL model");
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        log.info("Reads the document");
        model.read(document);

        log.info("Query execution is created for the example query");
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        log.info("Obtains the result set");
        ResultSet results = qexec.execSelect();

        log.info("Iterates over the result set");
        while (results.hasNext()) {
            QuerySolution sol = results.nextSolution();
            log.info("Solution: " + sol);
        }

        log.info("Obtain the properties of the model");
        ExtendedIterator<ObjectProperty> properties = model.listObjectProperties();

		/*log.info("Iterates over the properties");
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