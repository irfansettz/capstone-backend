#!/bin/bash

# Get a list of all Docker image IDs
images=$(docker images -q)

# Check if there are any images to remove
if [[ -z "$images" ]]; then
  echo "No images to remove"
  exit 0
fi

# Remove each image
for image in $images; do
  docker rmi $image
done

echo "All Docker images have been removed"
