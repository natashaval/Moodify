package com.natashaval.rules.xml

import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

/**
 * Created by natasha.santoso on 05/07/21.
 */
class AndroidSrcDetector : LayoutDetector() {
    companion object {
        val USING_IMAGE_VIEW = Issue.create(
            "UsingImageView",
            "Using ImageView",
            "Please use AppCompatImageView instead of ImageView",
            Category.CORRECTNESS,
            3,
            Severity.WARNING,
            Implementation(
                AndroidSrcDetector::class.java, Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun getApplicableElements(): Collection<String>? = XmlScannerConstants.ALL
    override fun visitElement(context: XmlContext, element: Element) {
        if (element.localName == "ImageView") {
            context.report(
                USING_IMAGE_VIEW,
                element,
                context.getElementLocation(element),
                "Please use AppCompatImageView at the place of ImageView"
            )
        }
    }
}