package com.mycompany.app.GraphQL;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mycompany.app.Facade;
import com.mycompany.app.Objects.Students;
import com.mycompany.app.graphQLResolver.MutationResolver;
import com.mycompany.app.graphQLResolver.Query;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
//import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends HttpServlet {
    private SimpleGraphQLServlet graph;

    public GraphQLEndpoint() {
        graph = SimpleGraphQLServlet.builder(buildSchema()).build();
    }

    private static GraphQLSchema buildSchema() {
        Facade.start();
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(), new MutationResolver())
                .build()
                .makeExecutableSchema();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        graph.service(req, resp);
    }
}

