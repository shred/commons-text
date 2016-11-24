# Usage

`commons-text` consists of a number of text filters. They receive a `CharSequence` with the text to filter, and return a `CharSequence` with the filtered text. If a `StringBuilder` instance is passed in as `CharSequence`, it may be reused for performance reasons, and should not be used after the invocation any more.

`commons-text` v2.0 is optimized for the new Java 8 streaming API.

## Example

The `KeepFilter` is the most simple filter, as it just keeps the text unchanged.

```java
KeepFilter filter = new KeepFilter();
CharSequence result = filter.apply("Hello World");
System.out.println(result.toString());
```

All filters implement the `Function` functional interface and can thus be used in Java 8 streams:

```java
String result = Arrays.asList("foo", "<b>bar</b>", "bla")
        .stream()
        .map(new StripHtmlFilter())
        .map(new ParagraphFilter())
        .collect(Collectors.joining(" "));
```

This example strips HTML from all texts in the stream, then adding paragraphs and finally joining all texts into a single String.

## Available Filters

The filters can be roughly divided into two groups: one that expects plaintext as input, and another that expects HTML markup.

### Text input

* `KeepFilter`: Keeps the text unchanged.
* `HtmlEscapeFilter`: Escapes all characters with special meaning in HTML. This is a basic text to HTML converter.
* `LinkToUrlFilter`: Detects http, https and ftp links within a plain text, and converts them to HTML links. Optionally, rel="nofollow" can be added to each link, and a link target can be chosen.
* `MarkdownFilter`: Converts [CommonMark](http://commonmark.org/) markdown to HTML. Optionally, a `LinkAnalyzer` can be applied.
* `NormalizeFilter`: Converts Windows (CRLF) and Mac (CR) line endings to Un*x line endings (LF).
* `ParagraphFilter`: Surrounds all paragraphs with &lt;p> tags. A paragraph ends with a double line feed. Additionally, &lt;br> tags are added at each simple line feed. Can be used for simply paragraphing a text in HTML.
* `SmilyFilter`: Detects a set of text emoticons and replaces them by &lt;img> tags.
* `TextileFilter`: Converts textile markup to HTML. Optionally, a `LinkAnalyzer` can be applied.

### HTML input

* `NofollowLinksFilter`: Adds rel="nofollow" to all links in the given HTML document.
* `SimplifyHtmlFilter`: Only keeps a defined set of HTML tags (and attributes). All other HTML tags are removed. Can be used to offer simple HTML markup, but remove everything that could inflict XSS.
* `StripHtmlFilter`: Removes all HTML markup from the text. Whitespaces are inserted where needed (e.g. "Hello&lt;br>World" is converted to "Hello World").
