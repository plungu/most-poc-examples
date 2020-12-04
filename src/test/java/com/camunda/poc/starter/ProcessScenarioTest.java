package com.camunda.poc.starter;

import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.scenario.Scenario;
import org.junit.Before;

import static org.mockito.Mockito.*;


/**
 * This community extension to Camunda BPM enables you to write robust test suites for process models.
 * https://github.com/camunda/camunda-bpm-assert-scenario
 * @author paul.lungu@camunda.com
 */
public class ProcessScenarioTest {

    private static final String PROCESS_DEFINITION_KEY = "unit-test-process-example";


//  static {
//    LoggerFactory.useSlf4jLogging(); // MyBatis
//  }

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Mock
    protected ProcessScenario scenario;

    @Mock
    protected ProcessScenario otherScenario;

    public Map<String, Object> variables = new HashMap<String, Object>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void conditions() {
        variables.put("approved", false);
    }

    @Test
    @Deployment(resources = { "processes/unit-test-process-example.bpmn" })
    public void testHappyPath() {

      // Define scenarios by using camunda-bpm-assert-scenario:
     when(scenario.waitsAtUserTask(anyString())).thenReturn((task) -> {
      task.complete();
     });

     //OK - everything prepared - let's go and execute the scenario
      Scenario.run(scenario).startByKey(PROCESS_DEFINITION_KEY, variables).execute();

      //now you can do some assertions
      verify(scenario, times(1)).hasFinished("unit-test-user-task");
      verify(scenario, times(1)).hasFinished("end");

    }

}
