package com.camunda.poc.starter;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * This community extension to Camunda BPM enables you to write robust test suites for process models.
 * @author paul.lungu@camunda.com
 */
public class ProcessTest {

  private static final String PROCESS_DEFINITION_KEY = "unit-test-process-example";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Rule
  @ClassRule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @Test
  @Deployment(resources = { "processes/unit-test-process-example.bpmn" })
  public void testHappyPath() {
    // Either: Drive the process by API and assert correct behavior by camunda-bpm-assert, e.g.:
    ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
    
    // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
    assertThat(processInstance).isActive();
    assertThat(processInstance).isWaitingAt("unit-test-user-task");
    complete(task());
    assertThat(processInstance).isEnded();
  }

}
