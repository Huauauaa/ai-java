#!/usr/bin/env bash
# Builds libjnidemo.{dylib,so} into target/jni. Requires JAVA_HOME and a C compiler (clang/gcc).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

if [[ -z "${JAVA_HOME:-}" ]]; then
  if [[ "$(uname -s)" == "Darwin" ]] && [[ -x /usr/libexec/java_home ]]; then
    JAVA_HOME="$(/usr/libexec/java_home)"
  fi
fi
if [[ -z "${JAVA_HOME:-}" ]]; then
  echo "JAVA_HOME is not set and could not be detected." >&2
  exit 1
fi

OS="$(uname -s)"
case "$OS" in
  Darwin)
    INC_OS=darwin
    LIB_EXT=dylib
    ;;
  Linux)
    INC_OS=linux
    LIB_EXT=so
    ;;
  *)
    echo "Unsupported OS for JNI demo build: $OS" >&2
    exit 1
    ;;
esac

mkdir -p target/classes target/jni target/jni-headers

SRC_JAVA="src/main/java/com/huauauaa/syntax/jni/JniMath.java"
javac --release 17 -d target/classes -h target/jni-headers "$SRC_JAVA"

gcc -shared -fPIC \
  -I"$JAVA_HOME/include" \
  -I"$JAVA_HOME/include/$INC_OS" \
  -Itarget/jni-headers \
  -o "target/jni/libjnidemo.$LIB_EXT" \
  src/main/native/jnidemo.c

echo "JNI library -> target/jni/libjnidemo.$LIB_EXT"
