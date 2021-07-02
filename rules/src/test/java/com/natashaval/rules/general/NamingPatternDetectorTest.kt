package com.natashaval.rules.general

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test

/**
 * Created by natasha.santoso on 28/06/21.
 */
class NamingPatternDetectorTest : LintDetectorTest() {
  override fun getDetector(): Detector = NamingPatternDetector()

  override fun getIssues(): MutableList<Issue> = mutableListOf(NamingPatternDetector.ISSUE_NAMING_PATTERN)

  @Test
  fun testCorrectClassName() {
    lint().files(java("""
      |package foo;
      |
      |class XmlHttpRequest {
      |}
    """.trimMargin())).issues(NamingPatternDetector.ISSUE_NAMING_PATTERN)
      .run()
      .expectClean()
  }

  @Test
  fun testIncorrectClassName() {
    lint().files(java("""
      |package foo;
      |
      |class XMLHTTPRequest {
      |}
    """.trimMargin())).issues(NamingPatternDetector.ISSUE_NAMING_PATTERN).run()
      .expect("""
      |src/foo/XMLHTTPRequest.java:3: Warning: Class is not named in defined camel case. [NamingPattern]
      |class XMLHTTPRequest {
      |      ~~~~~~~~~~~~~~
      |0 errors, 1 warnings
    """.trimMargin())
        .expectWarningCount(1)
  }
}