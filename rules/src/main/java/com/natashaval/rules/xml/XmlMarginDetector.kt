package com.natashaval.rules.xml

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

// https://coderamblings.dev/posts/custom-android-xml-lint-rules-with-kotlin/
//https://github.com/alexjlockwood/android-lint-checks-demo

/**
 * Created by natasha.santoso on 28/06/21.
 */
class XmlMarginDetector : LayoutDetector() {
  override fun getApplicableAttributes(): Collection<String>? {
    return listOf(
        SdkConstants.ATTR_LAYOUT_MARGIN,
        SdkConstants.ATTR_LAYOUT_MARGIN_TOP,
        SdkConstants.ATTR_LAYOUT_MARGIN_BOTTOM,
        SdkConstants.ATTR_LAYOUT_MARGIN_START,
        SdkConstants.ATTR_LAYOUT_MARGIN_END,
        SdkConstants.ATTR_LAYOUT_MARGIN_HORIZONTAL,
        SdkConstants.ATTR_LAYOUT_MARGIN_VERTICAL,
    )
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    val matchResult = "\\w*([0-9]+)dp".toRegex().matchEntire(attribute.value)
    matchResult?.let {
      if (!it.value.startsWith("@dimen/margin_")) {
        context.report(
            ISSUE_XML_MARGIN,
            attribute,
            context.getValueLocation(attribute),
            "do not use hardcoded margin, use margin from dimen module"
        )
      }
    }
  }

  companion object {
    val ISSUE_XML_MARGIN = Issue.create(
        id = "DlsMargin",
        briefDescription = "should use margin from dimen module",
        explanation = "margin should be increment of 2",
        category = Category.CORRECTNESS,
        priority = 2,
        severity = Severity.WARNING,
        implementation = Implementation(
            XmlMarginDetector::class.java,
            Scope.RESOURCE_FILE_SCOPE
        )
    )
  }
}