package templates

import kotlinx.html.*

object PageShell {

    fun build(
        html: HTML,
        pageTitle: String,
        allTags: List<String> = emptyList(),
        currentTag: String? = null,
        bodyBlock: DIV.() -> Unit
    ) {
        with(html) {
            head {
                meta(charset = "utf-8")
                meta(name = "viewport", content = "width=device-width, initial-scale=1")
                title { +"$pageTitle — Bookmarker" }
                style {
                    unsafe {
                        raw(
                            """
                            *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
                            :root{
                              --accent:oklch(0.58 0.16 209);
                              --accent-hex:#0091ad;
                              --accent-soft:oklch(0.58 0.16 209 / 0.08);
                              --accent-hover:oklch(0.58 0.16 209 / 0.15);
                              --bg:#faf8f5;
                              --surface:#fffefb;
                              --border:oklch(0.88 0.01 85);
                              --text:oklch(0.22 0.01 85);
                              --text-muted:oklch(0.48 0.01 85);
                              --radius:0.625rem;
                              --font-display:'Bricolage Grotesque',system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;
                              --font-body:'Hanken Grotesk',system-ui,-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;
                            }
                            html{font-size:15px;background:var(--bg);color:var(--text);font-family:var(--font-body);line-height:1.55}
                            body{min-height:100vh;display:flex;flex-direction:column}
                            a{color:var(--accent-hex);text-decoration:none}
                            a:hover{text-decoration:underline}
                            h1,h2,h3{font-family:var(--font-display);font-weight:600;letter-spacing:-0.01em}
                            .app-bar{
                              background:var(--surface);border-bottom:1px solid var(--border);
                              padding:0.75rem 1.5rem;display:flex;align-items:center;justify-content:space-between;
                              position:sticky;top:0;z-index:10
                            }
                            .app-bar .logo{font-family:var(--font-display);font-weight:700;font-size:1.2rem;color:var(--text)}
                            .app-bar .logo span{color:var(--accent-hex)}
                            .btn{
                              display:inline-flex;align-items:center;gap:0.4rem;
                              padding:0.45rem 1rem;border-radius:var(--radius);
                              font-family:var(--font-body);font-size:0.875rem;font-weight:500;
                              cursor:pointer;border:1px solid var(--border);background:var(--surface);
                              color:var(--text);transition:background 0.15s,border-color 0.15s;
                              text-decoration:none;white-space:nowrap
                            }
                            .btn:hover{background:var(--accent-soft);border-color:var(--accent-hex);text-decoration:none}
                            .btn-primary{background:var(--accent-hex);color:#fff;border-color:var(--accent-hex)}
                            .btn-primary:hover{background:oklch(0.52 0.16 209);border-color:oklch(0.52 0.16 209)}
                            .btn-sm{padding:0.25rem 0.6rem;font-size:0.8rem}
                            .layout{display:flex;flex:1}
                            .sidebar{
                              width:220px;flex-shrink:0;border-right:1px solid var(--border);
                              background:var(--surface);padding:1.25rem;
                              overflow-y:auto
                            }
                            .sidebar h3{font-size:0.8rem;text-transform:uppercase;letter-spacing:0.06em;color:var(--text-muted);margin-bottom:0.75rem}
                            .tag-cloud{display:flex;flex-wrap:wrap;gap:0.4rem}
                            .tag{
                              display:inline-block;padding:0.2rem 0.55rem;border-radius:999px;
                              font-size:0.78rem;font-weight:500;
                              background:var(--accent-soft);color:var(--accent-hex);
                              border:1px solid transparent;transition:background 0.15s,border-color 0.15s
                            }
                            .tag:hover{background:var(--accent-hover);border-color:var(--accent-hex);text-decoration:none}
                            .tag.active{background:var(--accent-hex);color:#fff}
                            .sidebar .tag-clear{margin-top:0.75rem;font-size:0.78rem}
                            .main{flex:1;padding:1.5rem;overflow-y:auto}
                            .main h2{font-size:1.4rem;margin-bottom:1rem}
                            .bookmark-list{display:flex;flex-direction:column;gap:0.5rem}
                            .bookmark-card{
                              background:var(--surface);border:1px solid var(--border);border-radius:var(--radius);
                              padding:0.85rem 1rem;transition:border-color 0.15s;display:flex;flex-direction:column;gap:0.3rem
                            }
                            .bookmark-card:hover{border-color:var(--accent-hex)}
                            .bookmark-card .bm-title{font-family:var(--font-display);font-weight:600;font-size:1rem}
                            .bookmark-card .bm-url{font-size:0.8rem;color:var(--text-muted);word-break:break-all}
                            .bookmark-card .bm-tags{display:flex;flex-wrap:wrap;gap:0.3rem;margin-top:0.15rem}
                            .bookmark-card .bm-tags .tag{font-size:0.72rem;padding:0.15rem 0.45rem}
                            .form-card{
                              max-width:540px;background:var(--surface);border:1px solid var(--border);
                              border-radius:var(--radius);padding:1.5rem
                            }
                            .form-group{margin-bottom:1rem}
                            .form-group label{display:block;font-weight:500;margin-bottom:0.3rem;font-size:0.875rem;color:var(--text-muted)}
                            .form-group input,.form-group textarea{
                              width:100%;padding:0.55rem 0.75rem;border:1px solid var(--border);border-radius:var(--radius);
                              font-family:var(--font-body);font-size:0.9rem;background:var(--bg);color:var(--text);
                              transition:border-color 0.15s
                            }
                            .form-group input:focus,.form-group textarea:focus{outline:none;border-color:var(--accent-hex);box-shadow:0 0 0 3px var(--accent-soft)}
                            .form-actions{display:flex;gap:0.75rem;align-items:center;margin-top:1.25rem}
                            .empty-state{text-align:center;padding:3rem 1rem;color:var(--text-muted)}
                            .empty-state p{font-size:1rem;margin-bottom:1rem}
                            .active-filter{
                              display:flex;align-items:center;gap:0.5rem;margin-bottom:1rem;
                              font-size:0.85rem;color:var(--text-muted)
                            }
                            .active-filter strong{color:var(--text)}
                            .bm-count{font-size:0.85rem;color:var(--text-muted);margin-bottom:0.75rem}
                            @media(max-width:640px){
                              .layout{flex-direction:column}
                              .sidebar{width:100%;border-right:none;border-bottom:1px solid var(--border);padding:0.75rem 1rem}
                              .main{padding:1rem}
                            }
                            """.trimIndent()
                        )
                    }
                }
            }
            body {
                div("app-bar") {
                    a("/", classes = "logo") {
                        +"Bookmark"
                        span { +"er" }
                    }
                    a("/add", classes = "btn btn-primary") {
                        +"+ New Bookmark"
                    }
                }
                div("layout") {
                    div("sidebar") {
                        h3 { +"Tags" }
                        if (allTags.isNotEmpty()) {
                            div("tag-cloud") {
                                for (tag in allTags) {
                                    val isActive = currentTag != null && currentTag.equals(tag, ignoreCase = true)
                                    val classes = if (isActive) "tag active" else "tag"
                                    a("/?tag=${java.net.URLEncoder.encode(tag, "UTF-8")}", classes = classes) {
                                        +tag
                                    }
                                }
                            }
                            div("tag-clear") {
                                a("/") { +"Show all" }
                            }
                        } else {
                            p { +"No tags yet." }
                        }
                    }
                    div("main") {
                        bodyBlock()
                    }
                }
            }
        }
    }
}
