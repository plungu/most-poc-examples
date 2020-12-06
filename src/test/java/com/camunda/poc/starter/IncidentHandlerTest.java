package com.camunda.poc.starter;

import com.camunda.poc.starter.bpm.IncidentTestDelegate;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * This community extension to Camunda BPM enables you to write robust test suites for process models.
 * @author paul.lungu@camunda.com
 */
public class IncidentHandlerTest {

  private static final String PROCESS_DEFINITION_KEY = "incident-handler-example";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Before
  public void setup(){
    initMocks(this);
    Mocks.register("incidentTestDelegate", new IncidentTestDelegate());
  }

  @Rule
  @ClassRule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @Test
  @Deployment(resources = { "processes/incident-handler-example.bpmn" })
  public void testHappyPath() {
    // Either: Drive the process by API and assert correct behavior by camunda-bpm-assert, e.g.:
    ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, withVariables("error", true));
    
    // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
    assertThat(processInstance).isActive();
    assertThat(processInstance).isWaitingAt("test-incident-handler-activity");
    assertThat(processInstance).hasVariables("error").variables().containsValues(true);
    processEngine().getRuntimeService().setVariable(processInstance.getRootProcessInstanceId(), "error", false);
    execute(job());
    assertThat(processInstance).isEnded();
  }

}
