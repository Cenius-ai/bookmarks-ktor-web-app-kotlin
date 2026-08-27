package templates

import db.Bookmark
import kotlinx.html.*

object BookmarkListTemplate {

    fun render(div: DIV, bookmarks: List<Bookmark>, currentTag: String?) {
        with(div) {
            if (currentTag != null) {
                div("active-filter") {
                    +"Filtering by tag: "
                    strong { +currentTag }
                    a("/", classes = "btn btn-sm") { +"✕ Clear" }
                }
            }

            h2 {
                if (currentTag != null) +"\"$currentTag\""
                else +"All Bookmarks"
            }

            div("bm-count") {
                val count = bookmarks.size
                +"$count bookmark${if (count != 1) "s" else ""}"
            }

            if (bookmarks.isEmpty()) {
                div("empty-state") {
                    p { +"No bookmarks found." }
                    if (currentTag != null) {
                        p { +"Try another tag or " ; a("/") { +"view all" } ; +"." }
                    } else {
                        p { a("/add", classes = "btn btn-primary") { +"Add your first bookmark" } }
                    }
                }
            } else {
                div("bookmark-list") {
                    for (bm in bookmarks) {
                        div("bookmark-card") {
                            div("bm-title") {
                                a(bm.url, target = "_blank") { +bm.title }
                            }
                            div("bm-url") {
                                +bm.url
                            }
                            val tagList = bm.tags.split(",").map { it.trim().lowercase() }.filter { it.isNotEmpty() }
                            if (tagList.isNotEmpty()) {
                                div("bm-tags") {
                                    for (tag in tagList) {
                                        a("/?tag=${java.net.URLEncoder.encode(tag, "UTF-8")}", classes = "tag") {
                                            +tag
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
