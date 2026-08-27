#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

echo "=== Bookmarker: Install ==="
echo "Installing dependencies and compiling..."

sh gradlew --no-daemon classes -q

echo ""
echo "=== Install complete ==="
echo "Run the app: sh gradlew run --no-daemon -q"
echo "The server starts at http://0.0.0.0:8080"
