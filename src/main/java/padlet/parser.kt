package padlet

import com.opencsv.CSVReader
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

val padletCSV = "/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/1b_woche_n1.csv"
val padDir = File("/Users/jonnyzzz/Work/kotlin-demo/src/main/java/padlet/data-n1")

val extensionDocX = ".docx"
val extensionsToDownload = listOf(".jpg", ".jpeg", ".png", extensionDocX, ".pdf")
val weekDaysOrder = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag")

fun main() {
//    download()

    padDir.listFiles()!!
        .filter { it.isDirectory }
        .sortedBy { it.name.toLowerCase() }
        .forEachIndexed { idx, dayRoot ->
            processDay(1+idx, dayRoot)
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

private fun processDay(idx: Int, dayRoot: File) {
    val targetRoot = File(dayRoot.parent + "-merged", dayRoot.name)
    targetRoot.deleteRecursively()
    targetRoot.mkdirs()

    val rootPdf = File(dayRoot.parent + "-merged", "" + idx.toString().padStart(2, '0') + "-" + dayRoot.name + ".pdf")

    val convertedPdfsRoot = File(targetRoot, "docx2pdf")
    val allDocX = dayRoot.walkTopDown()
        .filter { it.isFile && it.name.endsWith(extensionDocX) }
        .toList()
        .associateWith { docx ->
            val pdf = File(convertedPdfsRoot, docx.name + ".pdf")
            pdf.parentFile.mkdirs()
            if (!pdf.isFile) {
                println("Converting to PDF: $docx ...")
                docxToPdf2(docx, pdf)
            } else {
                println("Converting to PDF: $docx  [UP-TO-DATE]")
            }
            pdf
        }

    val allToMerge = dayRoot.walkTopDown()
        .filter { it.isFile && !it.name.endsWith(extensionDocX) }.toList() + allDocX.values

    mergePdf(allToMerge.sortedBy { it.name }, rootPdf)
}

private fun mergePdf(files: List<File>, resultPdf: File) {
    println("Merging: " + files.map { it.name }.toSortedSet().joinToString())
    println("Output: $resultPdf")

    resultPdf.parentFile.mkdirs()
    val args: List<String> = listOf("convert") + files.map { it.path }.toList() + listOf(resultPdf.path)
    println("Running command: " + args.joinToString("") { "\n    $it"})

    val code = ProcessBuilder()
        .command(args)
        .directory(resultPdf.parentFile)
        .inheritIO()
        .start().waitFor()

    if (code != 0) error("Failed to run pandoc!")
}

private fun download() {
    val sections = run {
        val allRows = File(padletCSV).reader().use {
            CSVReader(it).readAll()
        }.filter { it.isNotEmpty() }

        val weekDays = mutableMapOf<String, MutableSet<String>>()

        var currentDay: String? = null
        for (line in allRows) {

            if (line.size == 1) {
                currentDay = line.single()
                continue
            }

            requireNotNull(currentDay) {
                "The line: $line has no day"
            }

            weekDays
                .computeIfAbsent(currentDay) { TreeSet() }
                .addAll(
                    line.flatMap { it.trim().split("\\n+\\s+".toRegex()) }
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                )
        }
        weekDays
    }

    val okHttp = OkHttpClient.Builder().build()
    padDir.mkdirs()

    for ((day, data) in sections) {
        val count = AtomicInteger(0)
        val dayId = weekDaysOrder.withIndex().firstOrNull { it.value.equals(day.trim(), ignoreCase = true) }?.index?.plus(1) ?: weekDaysOrder.size + 2

        println("================")
        println("Day: $day")

        val allLinks = data
            .filter { it.startsWith("http://") || it.startsWith("https://") }
            .toSortedSet()

        println("Downloading Attachments for $day")

        for (link in allLinks) {
            //this is the school book picture
            if (link.contains("/Scan_20210108.png")) continue
            if (link.contains("/Scan_20210114__2_.png")) continue
            if (link.contains("/Scan_20210108__3_.png")) continue
            if (link.contains("/1b_AH_M.png")) continue

            println("Checking: $link")
            if (link.endsWith(*extensionsToDownload.toTypedArray())) {
                val linkFile = File(
                    File(padDir, "${dayId}-$day"),
                    count.incrementAndGet().toString().padStart(3, '0') + "_" + link.substringAfterLast("/")
                )

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
                    println("Already exists $link... in ${linkFile.name}")
                }
                continue
            }

            if (link.contains("learningapps.org", ignoreCase = true)) continue
            if (link.contains("youtube", ignoreCase = true)) continue
            if (link.contains("youtu.be", ignoreCase = true)) continue
            if (link.endsWith(".mp3", ignoreCase = true)) continue
            if (link.endsWith(".", ignoreCase = true)) continue
            if (link.contains("bookcreator.com", ignoreCase = true)) continue
            if (link.contains("bing.com/videos", ignoreCase = true)) continue
            error("Ignoring $link")
        }
    }
}

private fun String.endsWith(vararg texts: String): Boolean {
    for (text in texts) {
        if (this.trim().endsWith(text.trim(), ignoreCase = true)) return true
    }
    return false
}
