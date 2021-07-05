package com.natashaval.rules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.natashaval.rules.general.LogWtfDetector
import com.natashaval.rules.general.NamingPatternDetector
import com.natashaval.rules.general.NoisyDetector
import com.natashaval.rules.xml.AndroidSrcDetector
import com.natashaval.rules.xml.XmlMarginDetector

class CustomIssueRegistry : IssueRegistry() {
  override val issues: List<Issue>
    get() = listOf(
        NamingPatternDetector.ISSUE_NAMING_PATTERN,
        XmlMarginDetector.ISSUE_XML_MARGIN,
        AndroidSrcDetector.USING_IMAGE_VIEW,
        NoisyDetector.NOISY_ISSUE,
        LogWtfDetector.WTF_ISSUE)

  override val api: Int
    get() = CURRENT_API
}