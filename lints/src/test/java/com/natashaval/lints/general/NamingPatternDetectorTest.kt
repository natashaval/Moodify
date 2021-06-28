package com.natashaval.lints.general

import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

/**
 * Created by natasha.santoso on 28/06/21.
 */
class NamingPatternDetectorTest {
  @Test fun correctClassName() {
    lint().files(java("""
      |package foo;
      |
      |class XmlHttpRequest {
      |}
    """.trimMargin())).issues(ISSUE_NAMING_PATTERN).run().expectClean()
  }

  @Test fun incorrectClassName() {
    lint().files(java("""
      |package foo;
      |
      |class XMLHTTPRequest {
      |}
    """.trimMargin())).issues(ISSUE_NAMING_PATTERN).run().expect("""
      |src/foo/XMLHTTPRequest.java:3: Warning: Class is not named in defined camel case. [NamingPattern]
      |class XMLHTTPRequest {
      |      ~~~~~~~~~~~~~~
      |0 errors, 1 warnings
    """.trimMargin())
  }
}