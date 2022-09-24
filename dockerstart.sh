#!/bin/bash
docker build -t fatigue .
docker run -d -p 8089:8089 fatigue