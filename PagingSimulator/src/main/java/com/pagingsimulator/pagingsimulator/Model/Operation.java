package com.pagingsimulator.pagingsimulator.Model;

import java.util.ArrayList;
import java.util.List;

public class Operation {
    private String name;
    private List<Integer> parameters;

    public Operation(String name, String parameters) {
        this.name = name;
        this.parameters = new ArrayList<>();
        for(String parameter : parameters.split(",")){
            this.parameters.add(Integer.parseInt(parameter.trim()));
        }
    }

    public Operation(String name, int parameter) {
        this.name = name;
        this.parameters = new ArrayList<>();
        parameters.add(parameter);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getParameters() {
        return parameters;
    }

    public void setParameters(List<Integer> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return name + parameters.toString().replace("]", ")").replace("[", "(").replace(" ", "");
    }
}
