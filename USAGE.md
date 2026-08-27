# Usage

## Starting the Server

Follow the [INSTALL.md](INSTALL.md) guide to launch the application. Once running, open a browser and go to:

```
http://localhost:8080
```

(Replace `8080` with the port you configured via the `PORT` environment variable.)

## Viewing Bookmarks

The home page (`/`) displays a list of all bookmarks. Each entry shows a title, a clickable URL, and its tags. Pre-seeded example bookmarks are included so you can explore immediately.

## Adding a Bookmark

1. Click the **Add Bookmark** link (or navigate to `/add`).
2. Fill in the form with the bookmark's title, URL, and comma-separated tags.
3. Submit the form. The new bookmark will appear on the home page.

## Filtering by Tag

On the home page, click any tag (displayed alongside a bookmark) to filter the list. Only bookmarks with that tag will be shown.