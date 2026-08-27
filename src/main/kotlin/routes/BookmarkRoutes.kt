package routes

import db.BookmarkRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import templates.*

fun Route.configureBookmarkRoutes() {
    get("/") {
        val tagParam = call.request.queryParameters["tag"]
        val bookmarks = if (tagParam != null && tagParam.isNotBlank()) {
            BookmarkRepository.bookmarksByTag(tagParam.trim().lowercase())
        } else {
            BookmarkRepository.allBookmarks()
        }
        val allTags = BookmarkRepository.allBookmarks()
            .flatMap { bm -> bm.tags.split(",").map { t -> t.trim().lowercase() } }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()

        call.respondHtml {
            PageShell.build(this, "All Bookmarks", allTags, tagParam?.trim()?.lowercase()) {
                BookmarkListTemplate.render(this, bookmarks, tagParam?.trim()?.lowercase())
            }
        }
    }

    get("/add") {
        val allTags = BookmarkRepository.allBookmarks()
            .flatMap { bm -> bm.tags.split(",").map { t -> t.trim().lowercase() } }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()

        call.respondHtml {
            PageShell.build(this, "Add Bookmark", allTags) {
                AddFormTemplate.render(this)
            }
        }
    }

    post("/add") {
        val params = call.receiveParameters()
        val title = params["title"]?.trim() ?: ""
        val url = params["url"]?.trim() ?: ""
        val tags = params["tags"]?.trim() ?: ""

        val errors = mutableListOf<String>()
        if (title.isBlank()) errors.add("Title is required.")
        if (url.isBlank()) errors.add("URL is required.")
        if (url.isNotBlank() && !url.startsWith("http://") && !url.startsWith("https://")) {
            errors.add("URL must start with http:// or https://.")
        }

        if (errors.isNotEmpty()) {
            val allTags = BookmarkRepository.allBookmarks()
                .flatMap { bm -> bm.tags.split(",").map { t -> t.trim().lowercase() } }
                .filter { it.isNotEmpty() }
                .distinct()
                .sorted()

            call.respondHtml {
                PageShell.build(this, "Add Bookmark", allTags) {
                    AddFormTemplate.render(this, errors.joinToString(" "))
                }
            }
        } else {
            BookmarkRepository.addBookmark(title, url, tags)
            call.respondRedirect("/")
        }
    }
}
