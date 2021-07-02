package com.natashaval.rules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue
import com.natashaval.rules.general.NamingPatternDetector
import com.natashaval.rules.xml.XmlMarginDetector

// https://medium.com/@vanniktech/writing-your-first-lint-check-39ad0e90b9e6
class CustomIssueRegistry : IssueRegistry() {
  override val issues: List<Issue>
    get() = listOf(
        NamingPatternDetector.ISSUE_NAMING_PATTERN,
        XmlMarginDetector.ISSUE_XML_MARGIN
    )

  override val api: Int
    get() = com.android.tools.lint.detector.api.CURRENT_API
}