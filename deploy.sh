#!/bin/bash

cd "$(dirname "$0")"

# Configuration
PROJECT_ID="elemental-axle-442700-r5"
ARTIFACT_ID="main-repo"
SERVICE_NAME="tde-api-rest-luigi"
CLOUD_RUN_SERVICE_NAME="tde-api-rest-luigi"
VERSION="v1.1"
REGION="us-central1"
IMAGE="$REGION-docker.pkg.dev/$PROJECT_ID/$ARTIFACT_ID/$SERVICE_NAME:$VERSION"

# Authenticate gcloud
echo "Authenticating gcloud..."
gcloud auth login
gcloud config set project $PROJECT_ID
gcloud auth configure-docker $REGION-docker.pkg.dev
gcloud config set run/region $REGION


# Build the container image
echo "Building the container image..."
docker build -t $IMAGE .

# Push the container image to Google Artifact Registry
echo "Pushing the container image to Google Artifact Registry..."
docker push $IMAGE


# Deploy the container image  to Cloud Run
echo "Deploying the container image to Cloud Run..."
gcloud run deploy $CLOUD_RUN_SERVICE_NAME \
    --image $REGION-docker.pkg.dev/$PROJECT_ID/$ARTIFACT_ID/$SERVICE_NAME:$VERSION \
    --platform managed \
    --region $REGION \
    --allow-unauthenticated

