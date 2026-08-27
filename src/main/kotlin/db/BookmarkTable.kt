package db

import org.jetbrains.exposed.sql.Table

object BookmarkTable : Table("bookmarks") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 256)
    val url = varchar("url", 2048)
    val tags = varchar("tags", 1024)

    override val primaryKey = PrimaryKey(id)
}
