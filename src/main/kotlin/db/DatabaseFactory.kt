package db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(dbPath: String = "bookmarks.db") {
        val url = System.getenv("DATABASE_URL") ?: "jdbc:sqlite:$dbPath"
        val db = Database.connect(url, driver = "org.sqlite.JDBC")
        transaction(db) {
            SchemaUtils.create(BookmarkTable)
        }
    }
}
