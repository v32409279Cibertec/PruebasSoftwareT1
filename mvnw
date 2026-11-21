#!/bin/sh
set -eu
MAVEN_VERSION=3.9.11
BASE="${HOME}/.m2/wrapper/dists/apache-maven-${MAVEN_VERSION}"
MAVEN_HOME="${BASE}/apache-maven-${MAVEN_VERSION}"
URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.zip"
if [ ! -x "${MAVEN_HOME}/bin/mvn" ]; then
  mkdir -p "${BASE}"
  if command -v curl >/dev/null 2>&1; then
    curl -fL "${URL}" -o "${BASE}/maven.zip"
  else
    wget -O "${BASE}/maven.zip" "${URL}"
  fi
  unzip -q -o "${BASE}/maven.zip" -d "${BASE}"
  rm -f "${BASE}/maven.zip"
fi
exec "${MAVEN_HOME}/bin/mvn" -f "$(dirname "$0")/pom.xml" "$@"
