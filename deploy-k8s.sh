#!/usr/bin/env bash

KEY_FILE=kubernetes-238012-13f04748987f.json
PROJECT_ID=kubernetes-238012
K8s_CLUSTER=coffee-editor-pr
ZONE=europe-west3-c

IMAGE_NAME=coffee-editor
IMAGE_VERSION=$TRAVIS_PULL_REQUEST

# Greet
echo "Creating coffee-editor:$IMAGE_VERSION"

# Get the deploy key
openssl aes-256-cbc -K $encrypted_3501a9446367_key -iv $encrypted_3501a9446367_iv -in kubernetes-238012-13f04748987f.json.enc -out kubernetes-238012-13f04748987f.json -d

# Setup Cloud SDK
export CLOUDSDK_CORE_DISABLE_PROMPTS=1
if [ ! -d "$HOME/google-cloud-sdk/bin" ]; then
  rm -rf "$HOME/google-cloud-sdk"
  curl https://sdk.cloud.google.com | bash > /dev/null
fi
source $HOME/google-cloud-sdk/path.bash.inc
gcloud components update kubectl

# Build Docker File
#make    deploy

# Authenticate with could
gcloud auth activate-service-account --key-file $KEY_FILE

# Run regular build 
./run.sh -b -c -d -f 

# Wrap in Docker image..
docker build -t eu.gcr.io/$PROJECT_ID/$IMAGE_NAME:$IMAGE_VERSION .

# ..and push it
docker push eu.gcr.io/$PROJECT_ID/$IMAGE_NAME:$IMAGE_VERSION

# TODO launch on kubernetes cluster
