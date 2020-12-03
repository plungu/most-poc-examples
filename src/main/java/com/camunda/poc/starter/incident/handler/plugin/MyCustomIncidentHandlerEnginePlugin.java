package com.camunda.poc.starter.incident.handler.plugin;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Profile("incident")
public class MyCustomIncidentHandlerEnginePlugin implements ProcessEnginePlugin {

    private final Logger LOGGER = Logger.getLogger(Class.class.getName());

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

        LOGGER.info(" \n\n ******* Setting Custom Incident Handler "+this.getClass().getSimpleName()+" \n\n");

        List<IncidentHandler> customIncidentHandlers = new ArrayList<IncidentHandler>();
        customIncidentHandlers.add(new MyCustomIncidentHandler(Incident.FAILED_JOB_HANDLER_TYPE));
        customIncidentHandlers.add(new MyCustomIncidentHandler(Incident.EXTERNAL_TASK_HANDLER_TYPE));
        processEngineConfiguration.setCustomIncidentHandlers(customIncidentHandlers);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {



    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }

}
