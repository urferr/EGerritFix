#!/bin/bash
docker run --name gerrit211 -p 28112:8080 -d egerrit/gerrit:v2.11.5
docker run --name gerrit29 -p 28294:8080 -d egerrit/gerrit:v2.9.4
docker run --name gerrit212 -p 28212:8080 -d egerrit/gerrit:v2.12.2
