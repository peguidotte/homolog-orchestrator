package com.aegis.tests.orchestrator;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Main test suite that runs all tests in the project.
 * Run this class to execute the complete test suite.
 */
@Suite
@SuiteDisplayName("Aegis Orchestrator - All Tests")
@SelectPackages("com.aegis.tests.orchestrator")
class AegisTestsApplicationTests {

}
