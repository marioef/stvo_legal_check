package com.IntelligentSystem;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainExpertSystem {

    private static final Logger log = LoggerFactory.getLogger(MainExpertSystem.class);

    private static final String query_intersection = String.join(System.lineSeparator(),
            "                PREFIX rdf:       <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
            "                PREFIX owl:        <http://www.w3.org/2002/07/owl#>",
            "                PREFIX rdfs:       <http://www.w3.org/2000/01/rdf-schema#>",
            "                PREFIX sys:        <http://www.semanticweb.org/mario/ontologies/2021/6/untitled-ontology-9#>                                                                                ",
            "                                                                                                                                       ",
            "                SELECT ?car ?rule                                                                                                  ",
            "                WHERE {                                                                                                     ",
            "                {                                                                                                                     ",
            "                  ?car rdf:type ?carType .                                                                                                                 ",
            "                  ?carType rdfs:subClassOf sys:Participants .                                                                                                                 ",
            "                  ?lane rdf:type ?laneType .                                                 ",
            "                  ?laneType rdfs:subClassOf sys:Lane .                                                 ",
            "                  ?car sys:isOn ?lane .                                                 ",
            "                  ?car sys:Situation ?rule .                                                 ",
            "                }}");


    //private static final String document_intersection = "../parking.owl";
    private static final String document_intersection = "../geidorfplatzkreuzung.owl";
    //private static final String document_intersection = "../intersection_not_legal.owl";
    //private static final String document_intersection = "../intersection_not_legal2.owl";
    /**
     * Examples of SPARQL with apache jena
     *
     * @param args none
     */
    public static void main(String[] args) {

        log.info("Example SPARQL query: ");
        log.info(query_intersection);

        log.info("Creates the OWL model");
        Model defmodel = ModelFactory.createDefaultModel();

        log.info("Reads the document");
        defmodel.read(document_intersection);

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
    }
}