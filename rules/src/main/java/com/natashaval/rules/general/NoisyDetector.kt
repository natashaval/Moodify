package com.natashaval.rules.general

import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

/**
 * Created by natasha.santoso on 05/07/21.
 */
class NoisyDetector : Detector(), XmlScanner {
  override fun getApplicableElements(): Collection<String>? = listOf("manifest")

  override fun visitElement(context: XmlContext, element: Element) {
  }

  override fun afterCheckFile(context: Context) {
    context.report(NOISY_ISSUE, Location.create(context.file), "This is a noisy issue. Feel free to ignore for now.")
  }

  companion object {
    private const val NoisyIssueId = "NoisyIssueId"
    private const val NoisyIssueDescription = "NoisyIssueDescription"

    val NOISY_ISSUE = Issue.create(
        id = NoisyIssueId,
        briefDescription = NoisyIssueDescription,
        explanation = NoisyIssueDescription,
        category = Category.CORRECTNESS,
        priority = 4,
        severity = Severity.INFORMATIONAL,
        implementation = Implementation(NoisyDetector::class.java, Scope.MANIFEST_SCOPE)
    )
  }
}