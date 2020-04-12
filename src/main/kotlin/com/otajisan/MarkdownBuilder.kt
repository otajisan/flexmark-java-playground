package com.otajisan

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.toc.TocExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.data.MutableDataSet

class MarkdownBuilder {

  fun build() {
    // markdown settings
    val options = MutableDataSet()
    options.set(Parser.EXTENSIONS,
        listOf(
            AnchorLinkExtension.create(),
            StrikethroughExtension.create(),
            TablesExtension.create(),
            TocExtension.create()
        )
    )

    val parser = Parser.builder(options).build()
    val renderer = HtmlRenderer.builder(options).build()

    // convert markdown -> html
    val sampleMarkDown = ClassLoader.getSystemResource("sample.md").readText()
    val document: Node = parser.parse(sampleMarkDown)
    val outputHtml: String = renderer.render(document)

    println(outputHtml)

    // convert html -> pdf
    PdfConverterExtension.exportToPdf("./sample.pdf", outputHtml, "", options)
  }
}