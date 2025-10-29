#!/bin/bash
# Push Docker image to Docker Hub

# Exit if any command fails
set -e

# --- CONFIG ---
IMAGE_NAME="rafekpro2/led-app"
TAG="latest"
DOCKERFILE="Dockerfile"
CONTEXT="."
# ---------------

./gradlew clean build -xTest

echo "🔑 Logging in to Docker Hub..."
docker login

echo "📦 Building Docker image: $IMAGE_NAME:$TAG"
docker build -t "$IMAGE_NAME:$TAG" -f "$DOCKERFILE" "$CONTEXT"

echo "⬆️ Pushing Docker image to Docker Hub..."
docker push "$IMAGE_NAME:$TAG"

echo "✅ Done! Image pushed: $IMAGE_NAME:$TAG"