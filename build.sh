#!/bin/bash
# Simple build script for ValenceYou

export ANDROID_HOME=/opt/android-sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH

# Use local gradle
GRADLE_BIN=/root/.openclaw/workspace/apps/valence-you/gradle-8.2/bin/gradle

cd /root/.openclaw/workspace/apps/valence-you
$GRADLE_BIN assembleDebug "$@"
