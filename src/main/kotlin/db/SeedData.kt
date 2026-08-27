package db

import org.jetbrains.exposed.sql.transactions.transaction

object SeedData {

    private val bookmarks = listOf(
        Triple("Ktor Documentation", "https://ktor.io/docs", "kotlin,framework,documentation"),
        Triple("Kotlin Language", "https://kotlinlang.org", "kotlin,programming,language"),
        Triple("Hacker News", "https://news.ycombinator.com", "news,tech,discussion"),
        Triple("GitHub", "https://github.com", "development,git,collaboration"),
        Triple("Exposed ORM Guide", "https://jetbrains.github.io/Exposed", "kotlin,database,orm"),
        Triple("MDN Web Docs", "https://developer.mozilla.org", "web,documentation,reference"),
        Triple("Bandcamp", "https://bandcamp.com", "music,discovery,independent"),
        Triple("Ars Technica", "https://arstechnica.com", "tech,news,reviews"),
        Triple("Simon Willison's Blog", "https://simonwillison.net", "blog,programming,data"),
        Triple("Internet Archive", "https://archive.org", "history,reference,web")
    )

    fun seedIfEmpty() {
        val count = BookmarkRepository.count()
        if (count == 0L) {
            transaction {
                for ((title, url, tags) in bookmarks) {
                    BookmarkRepository.addBookmark(title, url, tags)
                }
            }
            println("Seeded ${bookmarks.size} bookmarks.")
        } else {
            println("Database already contains $count bookmarks — skipping seed.")
        }
    }
}
