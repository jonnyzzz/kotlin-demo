package padlet

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File

val padletCSV = "/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/1b_woche3.csv"
val padDir = File("/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/data-w3")
val weekDays = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Lösungen", "Zusatzaufgaben leicht")

fun main() {
//    download()

    for (day in weekDays) {
        val dayRoot = File(padDir, day)
        if (dayRoot.isDirectory) {
            processDay(dayRoot)
        }
    }
}

private fun docxToPdf2(docx: File, pdf: File) {
    runCatching { pdf.delete() }
    runCatching { pdf.parentFile.mkdirs() }

    val script = """
        set tmpDocFile to POSIX file "${docx.canonicalPath}"
        set outputPdf to POSIX file "${pdf.canonicalPath}"

        tell application "Pages"
           activate
           close every document without saving

           set thisDoc to open tmpDocFile
           export thisDoc to outputPdf as PDF
           close thisDoc without saving
        end tell
    """.trimIndent()

    val code = ProcessBuilder()
        .command("osascript", "-e", script)
        .directory(docx.parentFile)
        .inheritIO()
        .start()
        .waitFor()

    if (code != 0) {
        error("Failed to convert docx to pdf")
    }
}

private fun processDay(dayRoot: File) {
    val targetRoot = File(dayRoot.parent + "-merged", dayRoot.name)
    targetRoot.deleteRecursively()
    targetRoot.mkdirs()

    val imagesPdf = File(targetRoot, "images.pdf")
    val rootPdf = File(targetRoot, "all-merged.pdf")

    val convertedPdfsRoot = File(targetRoot, "docx2pdf")
    val allDocX = dayRoot.walkTopDown()
        .filter { it.isFile && it.name.endsWith(".docx") }
        .toList()
        .associateWith { docx ->
            runCatching {
                val pdf = File(convertedPdfsRoot, docx.name + ".pdf")
                pdf.parentFile.mkdirs()
                if (!pdf.isFile) {
                    println("Converting to PDF: $docx ...")
                    docxToPdf2(docx, pdf)
                } else {
                    println("Converting to PDF: $docx  [UP-TO-DATE]")
                }
                pdf
            }.getOrNull()
        }

    imagesToPdf(dayRoot, imagesPdf)

    val allPdfsToMerge = allDocX.values + imagesPdf.takeIf { it.isFile }
    mergePdf(allPdfsToMerge.filterNotNull(), rootPdf)
}

private fun mergePdf(pdfs: List<File>, result: File) {
    println("Merging: " + pdfs.map { it.name }.toSortedSet().joinToString())
    println("Output: $result")

    result.parentFile.mkdirs()
    val args: List<String> = listOf("convert") + pdfs.map { it.path }.toList() + listOf(result.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(result.parentFile)
        .inheritIO()
        .start().waitFor()

    if (code != 0) error("Failed to run pandoc!")
}

private fun imagesToPdf(path: File, result: File) {
    result.parentFile.mkdirs()
    val allImages = path.walkTopDown().filter { it.name.endsWith(".png", ".jpg", ".jpeg") }.toList()
    if (allImages.isEmpty()) return

    println("Merging: " + allImages.map { it.name }.toSortedSet().joinToString())
    println("Output: $result")

    return mergePdf(allImages, result)
}

private fun download() {
    val sections = run {
        val text = File(padletCSV).readText()
        val allLines = text.split("\n")

        val planPerDay = weekDays.map { it to mutableListOf<String>() }.toMap()

        var currentDay: String? = null
        for (line in allLines) {
            if (line.trim().isBlank()) continue

            val nextDay = weekDays.singleOrNull { it.equals(line.trim(), ignoreCase = true) }
            if (nextDay != null) {
                currentDay = nextDay
                continue
            }

            if (currentDay != null) {
                planPerDay.getValue(currentDay).add(line)
            } else {
                println("IGNORED: $line")
            }
        }
        planPerDay
    }

    val okHttp = OkHttpClient.Builder().build()
    padDir.mkdirs()

    for ((day, data) in sections) {
        println("================")
        println("Day: $day")

        val allLinks =
            data.flatMap { it.split(",") }.map { it.trim() }.filter { it.startsWith("http") }.toSortedSet()

        println("Downloading Attachments for $day:")
        for (link in allLinks) {
            if (link.endsWith(".jpeg", ".png", "docx", ".pdf")) {
                val linkFile = File(File(padDir, day), link.substringAfterLast("/"))
                linkFile.parentFile?.mkdirs()

                if (!linkFile.isFile) {
                    println("Downloading $link... to ${linkFile.name}")
                    okHttp.newCall(Request.Builder().get().url(link).build()).execute().use { response ->
                        if (!response.isSuccessful) {
                            println("ERROR: $link ${response.code} ${response.message}")
                        } else {
                            response.body!!.use {
                                linkFile.sink(false).buffer().use { r -> r.writeAll(it.source()) }
                            }
                        }
                    }
                } else {
                    println("Already exissts $link... in ${linkFile.name}")
                }
            } else {
                println("Ignoring $link")
            }
        }
    }
}

private fun String.endsWith(vararg texts: String): Boolean {
    for (text in texts) {
        if (this.trim().endsWith(text.trim(), ignoreCase = true)) return true
    }
    return false
}
