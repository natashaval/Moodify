package com.natashaval.lints

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue
import com.natashaval.lints.general.ISSUE_NAMING_PATTERN

// https://medium.com/@vanniktech/writing-your-first-lint-check-39ad0e90b9e6
class CustomIssueRegistry : IssueRegistry() {
  override val issues: List<Issue>
    get() = listOf(ISSUE_NAMING_PATTERN)

  override val api: Int
    get() = com.android.tools.lint.detector.api.CURRENT_API
}