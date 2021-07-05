package com.natashaval.rules.general

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Created by natasha.santoso on 05/07/21.
 */
class LogWtfDetector : Detector(), SourceCodeScanner {
  override fun getApplicableMethodNames(): List<String>? = listOf("wtf")

  override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    val evaluator = context.evaluator
    if (evaluator.isMemberInClass(method, "android.util.Log")) {
      reportUsage(context, node, method)
    }
  }

  private fun reportUsage(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    val quickfixData = LintFix.create()
        .name("Use Log.e()")
        .replace()
        .text(method.name)
        .with("e")
        .robot(true)
        .independent(true)
        .build()

    context.report(
     issue = WTF_ISSUE,
     scope = node,
     location = context.getCallLocation(node, includeReceiver = false, includeArguments = false),
      message = "Usage of `Log.wtf()` is prohibited",
      quickfixData = quickfixData
    )
  }

  companion object {
    val WTF_ISSUE = Issue.create(
        id = "LogWtfUsageError",
        briefDescription = "Prohibited logging level",
        explanation = "This lint check prevents usage of `Log.wtf()`.",
        category = Category.CORRECTNESS,
        priority = 3,
        severity = Severity.ERROR,
        implementation = Implementation(LogWtfDetector::class.java, Scope.JAVA_FILE_SCOPE)
    ).setAndroidSpecific(true)
  }
}