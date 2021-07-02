package com.natashaval.rules.general

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*

/**
 * Created by natasha.santoso on 28/06/21.
 */ // https://medium.com/@vanniktech/writing-your-first-lint-check-39ad0e90b9e6
class NamingPatternDetector : Detector(), Detector.UastScanner {
  override fun getApplicableUastTypes() = listOf(UClass::class.java)

  override fun createUastHandler(context: JavaContext) = NamingPatternHandler(context)

  class NamingPatternHandler(private val context: JavaContext) : UElementHandler() {
    override fun visitClass(node: UClass) {
      if (node.name?.isDefinedCamelCase() == false) {
        context.report(ISSUE_NAMING_PATTERN, node, context.getNameLocation(node),
            "Class is not named in defined camel case.")
      }
    }

    private fun String.isDefinedCamelCase(): Boolean {
      val charArray = this.toCharArray()
      return charArray.mapIndexed { index, current ->
        current to charArray.getOrNull(index + 1)
      }.none {
        it.first.isUpperCase() && it.second?.isUpperCase() ?: false
      }
    }
  }

  companion object {
    val ISSUE_NAMING_PATTERN = Issue.create("NamingPattern",
        "Class name should be in camel case",
        "The separation of words with a single capitalized letter, and the first word starting with either case",
        Category.CORRECTNESS,
        5,
        Severity.WARNING,
        Implementation(
            NamingPatternDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)
        )
    )
  }
}
