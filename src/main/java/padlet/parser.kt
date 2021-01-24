package padlet

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File

val padletCSV = "/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/1b_woche3.csv"
val padDir = File("/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/data-w3")
val weekDays = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Lösungen")

fun main() {
    for (day in weekDays) {
        processDay(File(padDir, day))
    }
}

private fun processDay(dayRoot: File) {
    val targetRoot = File("$dayRoot-merged")
    targetRoot.deleteRecursively()

    val mergedDocX = File(targetRoot, "all.docx")
    val mergedDocXPdf = File(targetRoot, "all-docs.pdf")
    val imagesPdf = File(targetRoot, "images.pdf")
    val rootPdf = File(targetRoot, "root.pdf")

    mergeDocx(dayRoot, mergedDocX)
    imagesToPdf(dayRoot, imagesPdf)

    if (mergedDocX.isFile) {
        runCatching {
            docxToPdf(mergedDocX, mergedDocXPdf)
            mergePdf(listOf(imagesPdf, mergedDocXPdf).filter { it.isFile }, rootPdf)
        }
    }
}

private fun mergeDocx(path: File, result: File) {
    val allDocX = path.walkTopDown().filter { it.name.endsWith(".docx") }.toList()
    if (allDocX.isEmpty()) return

    println("Merging: " + allDocX.map { it.name }.toSortedSet().joinToString())
    println("Output: $result")

    result.parentFile.mkdirs()
    val args: List<String> = listOf("pandoc", "-s") + allDocX.map { it.path }.toList() + listOf("-o", result.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(path)
        .start().waitFor()

    if (code != 0) error("Failed to run pandoc!")
}

private fun mergePdf(pdfs: List<File>, result: File) {
    println("Merging: " + pdfs.map { it.name }.toSortedSet().joinToString())
    println("Output: $result")

    result.parentFile.mkdirs()
    val args: List<String> = listOf("pandoc", "-s") + pdfs.map { it.path }.toList() + listOf("-o", result.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(result.parentFile)
        .start().waitFor()

    if (code != 0) error("Failed to run pandoc!")
}

private fun imagesToPdf(path: File, result: File) {
    val allImages = path.walkTopDown().filter { it.name.endsWith(".png", ".jpg", ".jpeg") }.toList()
    if (allImages.isEmpty()) return

    println("Merging: " + allImages.map { it.name }.toSortedSet().joinToString())
    println("Output: $result")

    result.parentFile.mkdirs()
    val args: List<String> = listOf("convert") + allImages.map { it.path }.toList() + listOf(result.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(path)
        .start().waitFor()

    if (code != 0) error("Failed to run convert!")
}

private fun docxToPdf(docx: File, pdf: File) {
    println("Converting: " + docx + " to pdf:" + pdf)
    pdf.parentFile.mkdirs()
    val args: List<String> = listOf("pandoc", docx.path, "-o", pdf.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(docx.parentFile)
        .inheritIO()
        .start().waitFor()

    if (code != 0) error("Failed to run pandoc!")
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
