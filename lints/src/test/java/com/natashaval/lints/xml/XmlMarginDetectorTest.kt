package com.natashaval.lints.xml

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by natasha.santoso on 01/07/21.
 */
class XmlMarginDetectorTest : LintDetectorTest() {
  override fun getDetector(): Detector = XmlMarginDetector()

  override fun getIssues(): MutableList<Issue> = mutableListOf(XmlMarginDetector.ISSUE_XML_MARGIN)

  @Test fun testMarginHarcode_Success() {
    lint().files(xml("res/layout/layout.xml", """
        <View xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/margin_8dp" />
        """.trimMargin())).run().expectClean()
  }

  @Test fun testMarginHarcode_Failed() {
    lint().files(xml("res/layout/layout.xml", """
        <View xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp" />
        """.trimIndent())).run().expectWarningCount(1)
  }
}