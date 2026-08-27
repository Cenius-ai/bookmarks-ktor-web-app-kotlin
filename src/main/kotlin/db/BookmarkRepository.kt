package db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Bookmark(
    val id: Int,
    val title: String,
    val url: String,
    val tags: String
)

object BookmarkRepository {

    fun allBookmarks(): List<Bookmark> = transaction {
        BookmarkTable.selectAll().orderBy(BookmarkTable.id, SortOrder.DESC).map { it.toBookmark() }
    }

    fun bookmarksByTag(tag: String): List<Bookmark> = transaction {
        BookmarkTable.selectAll()
            .where { BookmarkTable.tags like "%$tag%" }
            .orderBy(BookmarkTable.id, SortOrder.DESC)
            .map { it.toBookmark() }
    }

    fun addBookmark(title: String, url: String, tags: String): Bookmark = transaction {
        val insertStmt = BookmarkTable.insert {
            it[BookmarkTable.title] = title
            it[BookmarkTable.url] = url
            it[BookmarkTable.tags] = tags
        }
        val newId = insertStmt[BookmarkTable.id]
        BookmarkTable.selectAll().where { BookmarkTable.id eq newId }.single().toBookmark()
    }

    fun count(): Long = transaction {
        BookmarkTable.selectAll().count()
    }

    private fun ResultRow.toBookmark() = Bookmark(
        id = this[BookmarkTable.id],
        title = this[BookmarkTable.title],
        url = this[BookmarkTable.url],
        tags = this[BookmarkTable.tags]
    )
}
