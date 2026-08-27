import db.DatabaseFactory
import db.SeedData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import routes.configureBookmarkRoutes

fun main() {
    DatabaseFactory.init()
    SeedData.seedIfEmpty()

    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                call.respondHtml(status = HttpStatusCode.InternalServerError) {
                    head {
                        title { +"Error — Bookmarker" }
                    }
                    body {
                        h1 { +"Something went wrong" }
                        p { +"An unexpected error occurred. Please try again." }
                        a("/") { +"Go to Home" }
                    }
                }
                cause.printStackTrace()
            }
        }

        intercept(ApplicationCallPipeline.Call) {
            call.response.header("X-Content-Type-Options", "nosniff")
            call.response.header("X-Frame-Options", "DENY")
            call.response.header("Referrer-Policy", "strict-origin-when-cross-origin")
            call.response.header("Content-Security-Policy", "default-src 'self'; style-src 'self' 'unsafe-inline'")
        }

        routing {
            get("/health") {
                call.respondText("ok", contentType = ContentType.Text.Plain)
            }

            configureBookmarkRoutes()
        }
    }.start(wait = true)
}
