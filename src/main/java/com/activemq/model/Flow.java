package com.activemq.model;

/**
 * @author vishalgupta
 * @project _ADEL;-service
 */
public class Flow {
    private String flowName;
    private String flowVersion;

    public Flow(String flowName, String flowVersion) {
        this.flowName = flowName;
        this.flowVersion = flowVersion;
    }

    public String getFlowName() {
        return flowName;
    }

    public Flow setFlowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public String getFlowVersion() {
        return flowVersion;
    }

    public Flow setFlowVersion(String flowVersion) {
        this.flowVersion = flowVersion;
        return this;
    }
}
