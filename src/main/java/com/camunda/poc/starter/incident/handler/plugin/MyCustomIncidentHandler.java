package com.camunda.poc.starter.incident.handler.plugin;

import org.camunda.bpm.engine.impl.cfg.TransactionState;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.logging.Logger;

@Profile("incident")
public class MyCustomIncidentHandler extends DefaultIncidentHandler {

    private final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public MyCustomIncidentHandler(String type){
        super(type);
        LOGGER.info("EmailIncidentHandler created for type '{}'"+ type);
    }

    @Override
    public String getIncidentHandlerType() {
        return super.getIncidentHandlerType();
    }

    @Override
    public Incident handleIncident(IncidentContext incidentContext, String s) {

        IncidentEntity incidentEntity = (IncidentEntity) super.handleIncident(incidentContext, s);

        LOGGER.info(" \n\n ***** HANDLE INCIDENT: "+ incidentContext.getActivityId()+ "\n\n");

        return incidentEntity;
    }

    @Override
    public void resolveIncident(IncidentContext incidentContext) {
        super.resolveIncident(incidentContext);
    }

    @Override
    public void deleteIncident(IncidentContext incidentContext) {
        super.deleteIncident(incidentContext);
    }
}
